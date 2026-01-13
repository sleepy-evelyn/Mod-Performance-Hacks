package dev.sleepy_evelyn.create_configured.network;

import com.simibubi.create.content.trains.station.StationBlockEntity;
import dev.sleepy_evelyn.create_configured.CreateConfigured;
import dev.sleepy_evelyn.create_configured.mixin_interfaces.DisassemblyLockable;
import dev.sleepy_evelyn.create_configured.network.c2s.ChangeDisassemblyLockPayload;
import dev.sleepy_evelyn.create_configured.utils.SideHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

@EventBusSubscriber(modid = CreateConfigured.MOD_ID)
public final class CCServerboundPackets {

    @SubscribeEvent
    public static void onRegisterPayloadHandler(RegisterPayloadHandlersEvent e) {
        var registrar = e.registrar("1");

        registrar.playToServer(
                ChangeDisassemblyLockPayload.TYPE,
                ChangeDisassemblyLockPayload.STREAM_CODEC,
                (payload, ctx) -> {
                    var level = ctx.player().level();

                    if (SideHelper.isDedicatedServer(level)) {
                        var stationPos = payload.stationPos();
                        var disassemblyLock = payload.lock();

                        if (level.getBlockEntity(stationPos) instanceof StationBlockEntity sbe)
                            ((DisassemblyLockable) sbe).cc$setLock(disassemblyLock);
                    }
                }
        );
    }
}
