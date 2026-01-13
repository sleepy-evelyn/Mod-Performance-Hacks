package dev.sleepy_evelyn.create_configured.network.s2c;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static dev.sleepy_evelyn.create_configured.CreateConfigured.rl;

public record PrepStationScreenPayload(boolean canBypassTrainDisassembly, boolean disassemblyLockEnabled) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PrepStationScreenPayload> TYPE =
            new CustomPacketPayload.Type<>(rl("prep_station_screen"));

    public static final StreamCodec<FriendlyByteBuf, PrepStationScreenPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL, PrepStationScreenPayload::canBypassTrainDisassembly,
                    ByteBufCodecs.BOOL, PrepStationScreenPayload::disassemblyLockEnabled,
                    PrepStationScreenPayload::new
            );

    public @NotNull CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
