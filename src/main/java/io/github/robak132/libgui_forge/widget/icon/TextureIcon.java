package io.github.robak132.libgui_forge.widget.icon;


import io.github.robak132.libgui_forge.client.ScreenDrawing;
import io.github.robak132.libgui_forge.widget.data.Texture;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * An icon that draws a texture.
 *
 * @since 2.2.0
 */
@Getter
@Setter
public class TextureIcon implements Icon {

    private final Texture texture;
    private float opacity = 1f;
    private int color = 0xFF_FFFFFF;

    /**
     * Constructs a new texture icon.
     *
     * @param texture the identifier of the icon texture
     */
    public TextureIcon(ResourceLocation texture) {
        this(new Texture(texture));
    }

    /**
     * Constructs a new texture icon.
     *
     * @param texture the identifier of the icon texture
     * @since 3.0.0
     */
    public TextureIcon(Texture texture) {
        this.texture = texture;
    }

    /**
     * Sets the opacity of the texture.
     *
     * @param opacity the new opacity between 0 (fully transparent) and 1 (fully opaque)
     * @return this icon
     */
    public TextureIcon setOpacity(float opacity) {
        this.opacity = opacity;
        return this;
    }

    /**
     * Sets the color tint of the texture.
     *
     * @param color the new color tint
     * @return this icon
     */
    public TextureIcon setColor(int color) {
        this.color = color;
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void paint(GuiGraphics context, int x, int y, int size) {
        ScreenDrawing.texturedRect(context, x, y, size, size, texture, color, opacity);
    }
}
