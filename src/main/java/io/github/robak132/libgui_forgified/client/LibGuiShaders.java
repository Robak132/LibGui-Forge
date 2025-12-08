package io.github.robak132.libgui_forgified.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = LibGui.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class LibGuiShaders {
    private static @Nullable ShaderInstance tiledRectangle;

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) {
        try {
            ShaderInstance shaderInstance = new ShaderInstance(event.getResourceProvider(), new ResourceLocation(LibGui.MOD_ID, "tiled_rectangle"), DefaultVertexFormat.POSITION_COLOR_TEX);
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
