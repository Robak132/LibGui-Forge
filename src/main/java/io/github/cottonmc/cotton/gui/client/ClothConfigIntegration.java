package io.github.cottonmc.cotton.gui.client;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigIntegration {
    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.translatable("options.libgui.libgui_settings")).setDoesConfirmSave(true);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory configs = builder.getOrCreateCategory(Component.translatable("options.libgui.libgui_settings"));

        configs.addEntry(entryBuilder.startBooleanToggle(Component.translatable("option.libgui.darkmode"),
                LibGuiConfig.isDarkMode()).setDefaultValue(false)
                .setSaveConsumer(newValue -> LibGuiConfig.setDarkMode(newValue))
                .setTooltip(Component.translatable("option.libgui.darkmode.desc"))
                .build());

        builder.setSavingRunnable(LibGuiConfig::save);

        return builder.build();
    }
}

