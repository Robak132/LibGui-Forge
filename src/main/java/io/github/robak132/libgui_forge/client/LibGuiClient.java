package io.github.robak132.libgui_forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public class LibGuiClient {

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(event -> ClothConfigIntegration.init());
    }
}
