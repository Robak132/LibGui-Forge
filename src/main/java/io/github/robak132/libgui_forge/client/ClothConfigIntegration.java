package io.github.robak132.libgui_forge.client;

import static io.github.robak132.libgui_forge.client.LibGuiConfig.DARK_MODE;
import static io.github.robak132.libgui_forge.client.LibGuiConfig.GENERAL_SPEC;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.BooleanToggleBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigIntegration {

    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.translatable("options.libgui_forge.libgui_settings"))
                .setDoesConfirmSave(true);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();
        ConfigCategory configs = builder.getOrCreateCategory(Component.translatable("options.libgui_forge.libgui_settings"));

        BooleanToggleBuilder darkMode = entryBuilder.startBooleanToggle(Component.translatable("option.libgui_forge.darkmode"), DARK_MODE.get());
        darkMode.setDefaultValue(DARK_MODE.getDefault()).setSaveConsumer(DARK_MODE::set)
                .setTooltip(Component.translatable("option.libgui_forge.darkmode.desc"));
        configs.addEntry(darkMode.build());

        builder.setSavingRunnable(GENERAL_SPEC::save);

        return builder.build();
    }
}

