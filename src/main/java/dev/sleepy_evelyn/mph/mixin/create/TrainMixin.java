package dev.sleepy_evelyn.mph.mixin.create;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.simibubi.create.content.trains.entity.Train;
import dev.sleepy_evelyn.mph.MPHConfig;
import net.createmod.catnip.data.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Train.class, priority = 1000000, remap = false)
public abstract class TrainMixin {

    @WrapOperation(
            method = "collideWithOtherTrains",
            at=@At(
                    value = "INVOKE",
                    target = "Lcom/simibubi/create/content/trains/entity/Train;findCollidingTrain(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/resources/ResourceKey;)Lnet/createmod/catnip/data/Pair;"
            )
    )
    private Pair<Train, Vec3> railwayTweaks$removeTrainCollisions(Train instance, Level otherLeading, Vec3 otherTrailing, Vec3 otherDimension, ResourceKey<Level> start2, Operation<Pair<Train, Vec3>> original) {
        return (MPHConfig.CREATE_TRAIN_COLLISIONS.getAsBoolean()) ? original.call(instance, otherLeading, otherTrailing, otherDimension, start2) : null;
    }

    @ModifyExpressionValue(
            method = "updateNavigationTarget",
            at = @At(value = "CONSTANT", args = "intValue=100")
    )
    private int increaseFullRefreshDistance(int original) {
        return (MPHConfig.CREATE_REDUCE_TRAIN_NAV_CALLS.getAsBoolean()) ? 200 : original;
    }
}
