package io.github.robak132.libgui_forge;

import static io.github.robak132.libgui_forge.LibGui.MOD_ID;

import io.github.robak132.libgui_forge.client.ClothConfigIntegration;
import io.github.robak132.libgui_forge.client.LibGuiConfig;
import lombok.extern.slf4j.Slf4j;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = MOD_ID)
@Slf4j(topic = LibGui.MOD_ID)
public class LibGui {
    public static final String MOD_ID = "libgui_forge";

    public LibGui() {
        // Register client setup listener
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        FMLJavaModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, LibGuiConfig.GENERAL_SPEC, String.format("%s.toml", MOD_ID));
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static boolean isClothConfigLoaded() {
        if (ModList.get().isLoaded("cloth_config")) {
            try {
                Class.forName("me.shedaniel.clothconfig2.api.ConfigBuilder");
                return true;
            } catch (ClassNotFoundException e) {
                log.error("Cloth Config is installed but ConfigBuilder class not found", e);
            }
        }
        return false;
    }

    private void clientSetup(FMLClientSetupEvent event) {
        if (isClothConfigLoaded()) {
            log.info("Cloth Config detected, registering config screen.");
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (client, parent) -> ClothConfigIntegration.getConfigScreen(parent)
                    )
            );
        } else {
            log.warn("Cloth Config not found, config screen will be unavailable.");
        }
    }
}

