package io.github.robak132.libgui_forgified.client;

import static io.github.robak132.libgui_forgified.client.LibGui.MOD_ID;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = MOD_ID)
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LibGui {

    public static final String MOD_ID = "libgui_forge";

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        if (isClothConfigLoaded()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory(
                            (client, parent) -> ClothConfigIntegration.getConfigScreen(parent)));
        }
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
