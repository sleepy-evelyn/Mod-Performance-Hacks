package dev.sleepy_evelyn.create_configured.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public final class SideHelper {

    @OnlyIn(Dist.CLIENT)
    public static boolean inSingleplayer() {
        return Minecraft.getInstance().getSingleplayerServer() != null;
    }

    public static boolean isDedicatedServer(Level level) {
        return level.getServer() != null && level.getServer().isDedicatedServer();
    }
}
