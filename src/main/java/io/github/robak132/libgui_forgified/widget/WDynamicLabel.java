package io.github.robak132.libgui_forgified.widget;

import io.github.robak132.libgui_forgified.client.ScreenDrawing;
import io.github.robak132.libgui_forgified.widget.data.HorizontalAlignment;
import java.util.function.Supplier;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WDynamicLabel extends WWidget {

    protected Supplier<String> text;
    protected HorizontalAlignment alignment = HorizontalAlignment.LEFT;
    protected int color;
    protected int darkmodeColor;

    public static final int DEFAULT_TEXT_COLOR = 0x404040;
    public static final int DEFAULT_DARKMODE_TEXT_COLOR = 0xbcbcbc;

    public WDynamicLabel(Supplier<String> text, int color) {
        this.text = text;
        this.color = color;
        this.darkmodeColor = (color == DEFAULT_TEXT_COLOR) ? DEFAULT_DARKMODE_TEXT_COLOR : color;
    }

    public WDynamicLabel(Supplier<String> text) {
        this(text, DEFAULT_TEXT_COLOR);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void paint(GuiGraphics context, int x, int y, int mouseX, int mouseY) {
        String tr = text.get();
        ScreenDrawing.drawString(context, tr, alignment, x, y, this.getWidth(),
                shouldRenderInDarkMode() ? darkmodeColor : color);
    }

    @Override
    public boolean canResize() {
        return true;
    }

    @Override
    public void setSize(int x, int y) {
        super.setSize(x, 20);
    }

    public WDynamicLabel setDarkmodeColor(int color) {
        darkmodeColor = color;
        return this;
    }

    public WDynamicLabel disableDarkmode() {
        this.darkmodeColor = this.color;
        return this;
    }

    public WDynamicLabel setColor(int color, int darkmodeColor) {
        this.color = color;
        this.darkmodeColor = darkmodeColor;
        return this;
    }

    public WDynamicLabel setText(Supplier<String> text) {
        this.text = text;
        return this;
    }

    public WDynamicLabel setAlignment(HorizontalAlignment align) {
        this.alignment = align;
        return this;
    }
}
