package dev.sleepy_evelyn.mph;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(MPH.MOD_ID)
public class MPH {
    public static final String MOD_ID = "mph";
    public static final Logger LOGGER = LogUtils.getLogger();

    public MPH(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, MPHConfig.SPEC);
    }
}
