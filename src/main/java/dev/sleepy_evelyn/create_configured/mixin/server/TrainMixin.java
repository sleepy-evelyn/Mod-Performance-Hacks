package dev.sleepy_evelyn.create_configured.mixin.server;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.content.trains.station.GlobalStation;
import com.simibubi.create.content.trains.station.StationBlockEntity;
import dev.sleepy_evelyn.create_configured.config.CCConfigs;
import dev.sleepy_evelyn.create_configured.mixin_interfaces.DisassemblyLockable;
import dev.sleepy_evelyn.create_configured.utils.PermissionChecks;
import net.createmod.catnip.data.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(value = Train.class, remap = false, priority = 10000)
public abstract class TrainMixin {

    @Shadow public abstract GlobalStation getCurrentStation();
    @Shadow @Nullable public UUID owner;

    @WrapOperation(
            method = "collideWithOtherTrains",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/Train;findCollidingTrain(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/resources/ResourceKey;)Lnet/createmod/catnip/data/Pair;"
            )
    )
    private Pair<Train, Vec3> removeTrainCollisions(Train instance, Level otherLeading, Vec3 otherTrailing, Vec3 otherDimension, ResourceKey<Level> start2, Operation<Pair<Train, Vec3>> original) {
        return (CCConfigs.server().trainCollisions.get()) ? original.call(instance, otherLeading, otherTrailing, otherDimension, start2) : null;
    }

    @Inject(
            method = "disassemble",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/CarriageContraptionEntity;level()Lnet/minecraft/world/level/Level;",
                    shift = At.Shift.AFTER
            )
    )
    public void disassemble(Direction assemblyDirection, BlockPos pos, CallbackInfoReturnable<Boolean> cir, @Local Level level) {
        var station = getCurrentStation();

        if (station != null) {
            var stationPos = station.blockEntityPos;

            if (stationPos != null && level.getBlockEntity(stationPos) instanceof StationBlockEntity sbe) {
                var disassemblyLockable = (DisassemblyLockable) sbe;
                var lock = disassemblyLockable.cc$getLock();
                var disassembler = disassemblyLockable.cc$getLastDisassembler();

                PermissionChecks.canDisassembleTrain(level.getServer(), disassembler, this.owner, lock);
            }
        }
    }
}
