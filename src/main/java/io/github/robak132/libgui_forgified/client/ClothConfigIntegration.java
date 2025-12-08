package io.github.robak132.libgui_forgified.client;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigIntegration {

    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent)
                .setTitle(Component.translatable("options.libgui_forge.libgui_settings")).setDoesConfirmSave(true);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory configs = builder.getOrCreateCategory(
                Component.translatable("options.libgui_forge.libgui_settings"));

        configs.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.libgui_forge.darkmode"),
                        LibGuiConfig.isDarkMode()).setDefaultValue(false)
                .setSaveConsumer(LibGuiConfig::setDarkMode)
                .setTooltip(Component.translatable("option.libgui_forge.darkmode.desc"))
                .build());

        builder.setSavingRunnable(LibGuiConfig::save);

        return builder.build();
    }
}

