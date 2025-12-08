package io.github.cottonmc.cotton.gui.client;

import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

@Mod(LibGui.MOD_ID)
public class LibGui {
    private static @Nullable ShaderInstance tiledRectangle;
    public static final String MOD_ID = "libgui_forge";

    public LibGui() {
        if (isClothConfigLoaded()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> ClothConfigIntegration.getConfigScreen(parent)));
        }
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static boolean isClothConfigLoaded() {
        if (ModList.get().isLoaded("cloth-config-forge")) {
            try {
                Class.forName("me.shedaniel.clothconfig2.api.ConfigBuilder");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}