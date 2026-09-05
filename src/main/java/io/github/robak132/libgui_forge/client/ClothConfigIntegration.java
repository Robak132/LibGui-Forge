package io.github.robak132.libgui_forge.client;

import static io.github.robak132.libgui_forge.LibGui.MOD_ID;
import static io.github.robak132.libgui_forge.client.LibGuiConfig.DARK_MODE;
import static io.github.robak132.libgui_forge.client.LibGuiConfig.GENERAL_SPEC;

import java.util.function.BiFunction;
import lombok.extern.slf4j.Slf4j;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Slf4j(topic = MOD_ID)
public final class ClothConfigIntegration {

    private ClothConfigIntegration() {
        /* This utility class should not be instantiated */
    }

    private static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.translatable(Localisation.OPTIONS_SETTINGS))
                .setDoesConfirmSave(true);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory configs = builder.getOrCreateCategory(Component.translatable(Localisation.OPTIONS_SETTINGS));

        BooleanToggleBuilder darkMode = entryBuilder.startBooleanToggle(Component.translatable(Localisation.OPTION_DARK_MODE), DARK_MODE.get());
        darkMode.setDefaultValue(DARK_MODE.getDefault()).setSaveConsumer(DARK_MODE::set)
                .setTooltip(Component.translatable(Localisation.OPTION_DARK_MODE_TOOLTIP));
        configs.addEntry(darkMode.build());

        builder.setSavingRunnable(GENERAL_SPEC::save);

        return builder.build();
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

    private static boolean registerConfigScreen(
            String modId, BiFunction<Minecraft, Screen, Screen> screenFactory) {
        if (!isClothConfigLoaded()) {
            return false;
        }

        ModList.get().getModContainerById(modId).orElseThrow().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(screenFactory));
        return true;
    }

    public static void init(FMLClientSetupEvent event) {
        if (registerConfigScreen(MOD_ID, (client, parent) -> getConfigScreen(parent))) {
            log.info("Cloth Config detected, registering config screen.");
        } else {
            log.warn("Cloth Config not found, config screen will be unavailable.");
        }
    }
}
