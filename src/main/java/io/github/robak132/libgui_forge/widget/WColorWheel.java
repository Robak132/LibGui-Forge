package io.github.robak132.libgui_forge.widget;

import io.github.robak132.libgui_forge.client.ScreenDrawing;
import io.github.robak132.libgui_forge.widget.data.InputResult;
import io.github.robak132.libgui_forge.widget.data.Texture;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * A pickable texture with a movable cursor indicating the selected pixel.
 */
public class WColorWheel extends WPickableTexture {

    private final Texture cursorTexture;
    private int cursorSize = 8;
    private int cursorX;
    private int cursorY;
    private boolean selected;

    public WColorWheel(ResourceLocation image, float u1, float v1, float u2, float v2, Texture cursorTexture) {
        super(image, u1, v1, u2, v2);
        this.cursorTexture = cursorTexture;
    }

    @Override
    public void paint(GuiGraphics context, int x, int y, int mouseX, int mouseY) {
        super.paint(context, x, y, mouseX, mouseY);
        if (!selected) {
            cursorX = width / 2;
            cursorY = height / 2;
        }

        int cursorRadius = cursorSize / 2;
        ScreenDrawing.texturedRect(context, x + cursorX - cursorRadius, y + cursorY - cursorRadius,
                cursorSize, cursorSize, cursorTexture, 0xFFFFFFFF);
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        selected = true;
        InputResult result = super.onClick(x, y, button);
        updateCursor(x, y);
        return result;
    }

    @Override
    public InputResult onMouseDrag(int x, int y, int button, double deltaX, double deltaY) {
        selected = true;
        InputResult result = super.onMouseDrag(x, y, button, deltaX, deltaY);
        updateCursor(x, y);
        return result;
    }

    public void pickAtCursor() {
        transparent = pickColor(cursorX, cursorY, 0);
    }

    public WColorWheel setCursorSize(int cursorSize) {
        if (cursorSize <= 0) {
            throw new IllegalArgumentException("Cursor size must be positive");
        }
        this.cursorSize = cursorSize;
        return this;
    }

    private void updateCursor(int x, int y) {
        if (!transparent && isWithinBounds(x, y)) {
            cursorX = x;
            cursorY = y;
        }
    }
}
