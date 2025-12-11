package io.github.robak132.libgui_forge.client;

import juuxel.libninepatch.ContextualTextureRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * Shader-free implementation of LibNinePatch's {@link ContextualTextureRenderer}.
 */
public enum NinePatchTextureRendererImpl implements ContextualTextureRenderer<ResourceLocation, GuiGraphics> {
    INSTANCE;

    @Override
    public void draw(ResourceLocation texture, GuiGraphics context, int x, int y, int width, int height, float u1,
            float v1, float u2, float v2) {
        ScreenDrawing.texturedRect(context, x, y, width, height, texture, u1, v1, u2, v2, 0xFF_FFFFFF);
    }

    @Override
    public void drawTiled(ResourceLocation texture, GuiGraphics context, int x, int y, int regionWidth,
            int regionHeight, int tileWidth, int tileHeight, float u1, float v1, float u2, float v2) {
        int endX = x + regionWidth;
        int endY = y + regionHeight;

        for (int ty = y; ty < endY; ty += tileHeight) {
            int drawH = Math.min(tileHeight, endY - ty);

            for (int tx = x; tx < endX; tx += tileWidth) {
                int drawW = Math.min(tileWidth, endX - tx);

                ScreenDrawing.texturedRect(context, tx, ty, drawW, drawH, texture, u1, v1, u2, v2, 0xFFFFFFFF);
            }
        }
    }
}
