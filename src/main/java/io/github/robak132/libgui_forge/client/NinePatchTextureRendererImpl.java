package io.github.robak132.libgui_forge.client;

import juuxel.libninepatch.ContextualTextureRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import juuxel.libninepatch.ContextualTextureRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public enum NinePatchTextureRendererImpl implements ContextualTextureRenderer<ResourceLocation, GuiGraphics> {
    INSTANCE;

    @Override
    public void draw(ResourceLocation texture, GuiGraphics context, int x, int y, int width, int height, float u1,
        float v1, float u2, float v2) {
        if (width <= 0 || height <= 0) return;

        Matrix4f pose = context.pose().last().pose();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buffer = tess.getBuilder();

        buffer.begin(com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        // Counter-clockwise quad with UVs mapped to the full rectangle
        buffer.vertex(pose, x, y, 0f).uv(u1, v1).endVertex();
        buffer.vertex(pose, x, y + height, 0f).uv(u1, v2).endVertex();
        buffer.vertex(pose, x + width, y + height, 0f).uv(u2, v2).endVertex();
        buffer.vertex(pose, x + width, y, 0f).uv(u2, v1).endVertex();

        BufferUploader.drawWithShader(buffer.end());
    }

    @Override
    public void drawTiled(ResourceLocation texture, GuiGraphics context, int x, int y, int regionWidth,
        int regionHeight, int tileWidth, int tileHeight, float u1, float v1, float u2, float v2) {
        if (regionWidth <= 0 || regionHeight <= 0 || tileWidth <= 0 || tileHeight <= 0) return;
        
        // Precompute u/v deltas for a full tile
        final float du = u2 - u1;
        final float dv = v2 - v1;

        // Bind vanilla shader + texture once for entire batch
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        Matrix4f pose = context.pose().last().pose();

        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buffer = tess.getBuilder();

        buffer.begin(com.mojang.blaze3d.vertex.VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        final int endX = x + regionWidth;
        final int endY = y + regionHeight;

        // Loop over tiles; compute clipped tile sizes and corresponding UVs for edges
        for (int ty = y; ty < endY; ty += tileHeight) {
            int drawH = Math.min(tileHeight, endY - ty);
            // v range for this tile in [v1, v2]
            float vStart = v1;
            float vEnd = v1 + dv * ((float) drawH / (float) tileHeight);

            for (int tx = x; tx < endX; tx += tileWidth) {
                int drawW = Math.min(tileWidth, endX - tx);
                // u range for this tile in [u1, u2]
                float uStart = u1;
                float uEnd = u1 + du * ((float) drawW / (float) tileWidth);

                // Append quad (counter-clockwise) for the tile
                buffer.vertex(pose, tx, ty, 0f).uv(uStart, vStart).endVertex();
                buffer.vertex(pose, tx, ty + drawH, 0f).uv(uStart, vEnd).endVertex();
                buffer.vertex(pose, tx + drawW, ty + drawH, 0f).uv(uEnd, vEnd).endVertex();
                buffer.vertex(pose, tx + drawW, ty, 0f).uv(uEnd, vStart).endVertex();
            }
        }

        BufferUploader.drawWithShader(buffer.end());
    }
}
