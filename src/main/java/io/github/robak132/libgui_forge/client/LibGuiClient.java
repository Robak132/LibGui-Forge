package io.github.robak132.libgui_forge.client;

import static io.github.robak132.libgui_forge.LibGui.MOD_ID;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Bus.MOD, value = Dist.CLIENT)
public class LibGuiClient {
    private LibGuiClient() {
    }

    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        ClothConfigIntegration.init(event);
    }
}
