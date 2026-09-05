package io.github.robak132.libgui_forge.client;

import io.github.robak132.libgui_forge.LibGui;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minecraftforge.common.ForgeConfigSpec;

@Getter
@Setter
@Slf4j(topic = LibGui.MOD_ID)
public final class LibGuiConfig {

    public static final ForgeConfigSpec GENERAL_SPEC;
    public static final ForgeConfigSpec.BooleanValue DARK_MODE;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        DARK_MODE = configBuilder.define("darkMode", false);
        GENERAL_SPEC = configBuilder.build();
    }
}
