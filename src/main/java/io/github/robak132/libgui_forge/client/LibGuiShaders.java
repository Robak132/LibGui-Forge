package io.github.robak132.libgui_forge.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import com.mojang.blaze3d.vertex.VertexFormatElement.Usage;
import io.github.robak132.libgui_forge.LibGui;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@Slf4j
@Mod.EventBusSubscriber(modid = LibGui.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class LibGuiShaders {
    private static @Nullable ShaderInstance tiledRectangle;

    public static final VertexFormat POSITION_TEX = new VertexFormat(
            ImmutableMap.<String, VertexFormatElement>builder()
                    .put("Position", new VertexFormatElement(0, VertexFormatElement.Type.FLOAT, Usage.POSITION, 3))
                    .put("UV0",      new VertexFormatElement(0, VertexFormatElement.Type.FLOAT, Usage.UV,       2))
                    .build()
    );

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) {
        try {
            ShaderInstance shaderInstance = new ShaderInstance(event.getResourceProvider(),
                    ResourceLocation.fromNamespaceAndPath(LibGui.MOD_ID, "tiled_rectangle"),
                    POSITION_TEX);
            log.info("Loaded shader libgui: tiled_rectangle");
            event.registerShader(shaderInstance, shader -> tiledRectangle = shader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to register shader", e);
        }
    }

    public static ShaderInstance getTiledRectangle() {
        if (tiledRectangle == null) {
            throw new NullPointerException("Shader libgui: tiled_rectangle not initialised!");
        }
        return tiledRectangle;
    }
}
