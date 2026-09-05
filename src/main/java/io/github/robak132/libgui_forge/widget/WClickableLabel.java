package io.github.robak132.libgui_forge.widget;

import io.github.robak132.libgui_forge.widget.data.InputResult;
import java.util.function.IntFunction;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

/**
 * A label that can display alternate text while hovered and invoke a callback when clicked.
 */
public class WClickableLabel extends WLabel {

    @Nullable
    private Component hoveredText;
    @Nullable
    private IntFunction<InputResult> onClick;

    public WClickableLabel(Component text) {
        this(text, null);
    }

    public WClickableLabel(Component text, @Nullable Component hoveredText) {
        super(text);
        this.hoveredText = hoveredText;
    }

    @Override
    protected Component getRenderedText(int mouseX, int mouseY) {
        return hoveredText != null && isWithinBounds(mouseX, mouseY) ? hoveredText : text;
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        InputResult result = super.onClick(x, y, button);
        if (result == InputResult.PROCESSED || onClick == null) {
            return result;
        }
        return onClick.apply(button);
    }

    public WClickableLabel setHoveredText(@Nullable Component hoveredText) {
        this.hoveredText = hoveredText;
        return this;
    }

    public WClickableLabel setOnClick(@Nullable IntFunction<InputResult> onClick) {
        this.onClick = onClick;
        return this;
    }
}
