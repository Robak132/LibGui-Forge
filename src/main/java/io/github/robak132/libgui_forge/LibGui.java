package io.github.robak132.libgui_forge;

import io.github.robak132.libgui_forge.client.LibGuiClient;
import io.github.robak132.libgui_forge.client.LibGuiConfig;
import lombok.extern.slf4j.Slf4j;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LibGui.MOD_ID)
@Slf4j(topic = LibGui.MOD_ID)
public class LibGui {

    public static final String MOD_ID = "libgui_forge";

    public LibGui() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLJavaModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, LibGuiConfig.GENERAL_SPEC, MOD_ID + ".toml");
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> LibGuiClient::init);
    }
}