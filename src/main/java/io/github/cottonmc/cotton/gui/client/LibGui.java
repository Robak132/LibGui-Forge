package io.github.cottonmc.cotton.gui.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Mod(LibGui.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LibGui {
    private static @Nullable ShaderInstance tiledRectangle;
    public static final String MOD_ID = "libgui";

    public LibGui(FMLJavaModLoadingContext context) {
        context.getModEventBus().addListener(this::clientSetup);
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

    public void clientSetup(FMLClientSetupEvent event) {
        if (isClothConfigLoaded()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
                    () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> ClothConfigIntegration.getConfigScreen(parent)));
        }
    }

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) {
        try {
            ShaderInstance shaderInstance = new ShaderInstance(event.getResourceProvider(), ResourceLocation.tryBuild(MOD_ID, "tiled_rectangle"), DefaultVertexFormat.POSITION_COLOR_TEX);
            event.registerShader(shaderInstance, shader -> tiledRectangle = shader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to register libgui:tiled_rectangle shader");
        }
    }

    private static ShaderInstance assertPresent(ShaderInstance program, String name) {
        if (program == null) {
            throw new NullPointerException("Shader libgui:" + name + " not initialised!");
        }

        return program;
    }

    public static ShaderInstance getTiledRectangle() {
        return assertPresent(tiledRectangle, "tiled_rectangle");
    }
}