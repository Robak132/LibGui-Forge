package io.github.robak132.libgui_forge.widget;

import io.github.robak132.libgui_forge.client.ScreenDrawing;
import io.github.robak132.libgui_forge.widget.data.Texture;
import io.github.robak132.libgui_forge.widget.data.WidgetDirection;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;

/**
 * A slider rendered with caller-supplied track and thumb textures.
 */
public class WGradientSlider extends WSlider {

    private final Texture trackTexture;
    private final Texture thumbTexture;
    private int trackWidth = 8;
    private int thumbSize = THUMB_SIZE;

    public WGradientSlider(int min, int max, Direction.Plane axis, Texture trackTexture, Texture thumbTexture) {
        super(min, max, axis);
        this.trackTexture = trackTexture;
        this.thumbTexture = thumbTexture;
    }

    @Override
    public void paint(GuiGraphics context, int x, int y, int mouseX, int mouseY) {
        int thumbX;
        int thumbY;
        if (axis == Direction.Plane.VERTICAL) {
            int renderedTrackWidth = Math.min(trackWidth, width);
            ScreenDrawing.texturedRect(context, x + (width - renderedTrackWidth) / 2, y,
                    renderedTrackWidth, height, trackTexture, 0xFFFFFFFF);
            thumbX = width / 2 - thumbSize / 2;
            thumbY = widgetDirection == WidgetDirection.UP
                    ? (height - thumbSize) + 1 - (int) (coordToValueRatio * (value - min))
                    : Math.round(coordToValueRatio * (value - min));
        } else {
            int renderedTrackWidth = Math.min(trackWidth, height);
            ScreenDrawing.texturedRect(context, x, y + (height - renderedTrackWidth) / 2,
                    width, renderedTrackWidth, trackTexture, 0xFFFFFFFF);
            thumbX = widgetDirection == WidgetDirection.LEFT
                    ? (width - thumbSize) - (int) (coordToValueRatio * (value - min))
                    : Math.round(coordToValueRatio * (value - min));
            thumbY = height / 2 - thumbSize / 2;
        }
        ScreenDrawing.texturedRect(context, x + thumbX, y + thumbY, thumbSize, thumbSize,
                thumbTexture, 0xFFFFFFFF);
    }

    @Override
    protected int getThumbWidth() {
        return thumbSize;
    }

    public WGradientSlider setTrackWidth(int trackWidth) {
        if (trackWidth <= 0) {
            throw new IllegalArgumentException("Track width must be positive");
        }
        this.trackWidth = trackWidth;
        return this;
    }

    public WGradientSlider setThumbSize(int thumbSize) {
        if (thumbSize <= 0) {
            throw new IllegalArgumentException("Thumb size must be positive");
        }
        this.thumbSize = thumbSize;
        updateValueCoordRatios();
        return this;
    }
}
