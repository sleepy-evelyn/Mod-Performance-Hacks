package dev.sleepy_evelyn.create_configured.network.c2s;

import dev.sleepy_evelyn.create_configured.TrainDisassemblyLock;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import static dev.sleepy_evelyn.create_configured.CreateConfigured.rl;

public record ChangeDisassemblyLockPayload(BlockPos stationPos, TrainDisassemblyLock lock) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ChangeDisassemblyLockPayload> TYPE =
            new CustomPacketPayload.Type<>(rl("change_disassembly_lock"));

    public static final StreamCodec<ByteBuf, TrainDisassemblyLock> ID_STREAM_CODEC =
            ByteBufCodecs.idMapper(TrainDisassemblyLock.BY_ID, TrainDisassemblyLock::getId);

    public static final StreamCodec<FriendlyByteBuf, ChangeDisassemblyLockPayload> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, ChangeDisassemblyLockPayload::stationPos,
                    ID_STREAM_CODEC, ChangeDisassemblyLockPayload::lock,
                    ChangeDisassemblyLockPayload::new
            );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
