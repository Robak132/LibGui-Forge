package io.github.robak132.libgui_forge.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.robak132.libgui_forge.widget.data.InputResult;
import java.nio.ByteBuffer;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

/**
 * A sprite that samples its rendered texture when clicked or dragged.
 */
public class WPickableTexture extends WSprite {

    private float texU1;
    private float texV1;
    private float texU2;
    private float texV2;
    private int atlasWidth;
    private int atlasHeight;
    private int glId;
    @Nullable
    private Integer pixelColor;
    private int lastButton = -1;
    protected boolean transparent;
    @Nullable
    private BiConsumer<Integer, Integer> colorPickListener;

    public WPickableTexture(ResourceLocation image, float u1, float v1, float u2, float v2) {
        super(image, u1, v1, u2, v2);
        updateTexture(image);
        updateUv(u1, v1, u2, v2);
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        transparent = pickColor(x, y, button);
        return InputResult.PROCESSED;
    }

    @Override
    public InputResult onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
        transparent = pickColor(x, y, button);
        return InputResult.PROCESSED;
    }

    /**
     * Samples the texture at widget-space coordinates.
     *
     * @return true if the sampled pixel is fully transparent
     */
    public boolean pickColor(int x, int y, int button) {
        if (x < 0 || y < 0 || x >= width || y >= height || width == 0 || height == 0) {
            return false;
        }

        int trueX = (int) (texU1 + Math.floor((x / (float) width) * (texU2 - texU1)));
        int trueY = (int) (texV1 + Math.floor((y / (float) height) * (texV2 - texV1)));
        int size = atlasHeight * atlasWidth;

        RenderSystem.bindTexture(glId);
        ByteBuffer buffer = BufferUtils.createByteBuffer(size * 4);
        GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        byte[] pixels = new byte[size * 4];
        buffer.get(pixels);

        int pos = (trueY * atlasWidth + trueX) * 4;
        if (pixels[pos + 3] == 0) {
            return true;
        }

        int pickedColor = FastColor.ARGB32.color(pixels[pos + 3], pixels[pos] & 0xFF,
                pixels[pos + 1] & 0xFF, pixels[pos + 2] & 0xFF);
        pickedColor = FastColor.ARGB32.multiply(pickedColor, tint);
        if (pixelColor != null && pixelColor == pickedColor && lastButton == button) {
            return false;
        }

        pixelColor = pickedColor;
        lastButton = button;
        if (colorPickListener != null) {
            colorPickListener.accept(pickedColor, button);
        }
        return false;
    }

    public WPickableTexture setColorPickListener(@Nullable BiConsumer<Integer, Integer> colorPickListener) {
        this.colorPickListener = colorPickListener;
        return this;
    }

    @Override
    public WPickableTexture setImage(ResourceLocation image) {
        super.setImage(image);
        updateTexture(image);
        return this;
    }

    @Override
    public WPickableTexture setUv(float u1, float v1, float u2, float v2) {
        super.setUv(u1, v1, u2, v2);
        updateUv(u1, v1, u2, v2);
        return this;
    }

    private void updateTexture(ResourceLocation image) {
        glId = Minecraft.getInstance().getTextureManager().getTexture(image).getId();
        RenderSystem.bindTexture(glId);
        atlasWidth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
        atlasHeight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
    }

    private void updateUv(float u1, float v1, float u2, float v2) {
        texU1 = u1 * atlasWidth;
        texV1 = v1 * atlasHeight;
        texU2 = u2 * atlasWidth;
        texV2 = v2 * atlasHeight;
    }
}
