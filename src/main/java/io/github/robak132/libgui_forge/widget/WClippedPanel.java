package io.github.robak132.libgui_forge.widget;

import io.github.robak132.libgui_forge.client.Scissors;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * A panel that is clipped to only render widgets inside its bounds.
 */
public class WClippedPanel extends WPanel {

    @OnlyIn(Dist.CLIENT)
    @Override
    public void paint(GuiGraphics context, int x, int y, int mouseX, int mouseY) {
        if (getBackgroundPainter() != null) {
            getBackgroundPainter().paintBackground(context, x, y, this);
        }

        try (Scissors.Frame ignored = Scissors.push(x, y, width, height)) {
            for (WWidget child : children) {
                child.paint(context, x + child.getX(), y + child.getY(), mouseX - child.getX(), mouseY - child.getY());
            }
        }
    }
}
