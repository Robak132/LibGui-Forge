package io.github.robak132.libgui_forge.widget;

import io.github.robak132.libgui_forge.widget.data.InputResult;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import lombok.Getter;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/**
 * A color swatch that can capture, clear, and select a color.
 */
public class WColorSwatch extends WSprite {

    @Getter
    private int color = 0xFFFFFFFF;
    private int clearedColor = 0xFFFFFFFF;
    private boolean interactable = true;
    @Nullable
    private IntSupplier colorSupplier;
    @Nullable
    private IntConsumer colorConsumer;

    public WColorSwatch(ResourceLocation image, @Nullable IntSupplier colorSupplier,
            @Nullable IntConsumer colorConsumer) {
        super(image);
        this.colorSupplier = colorSupplier;
        this.colorConsumer = colorConsumer;
        setTint(color);
    }

    @Override
    public InputResult onClick(int x, int y, int button) {
        switch (button) {
            case 0 -> {
                if (interactable && colorSupplier != null) {
                    setColor(colorSupplier.getAsInt());
                }
            }
            case 1 -> {
                if (interactable) {
                    setColor(clearedColor);
                }
            }
            case 2 -> {
                if (colorConsumer != null) {
                    colorConsumer.accept(color);
                }
            }
            default -> {
                return InputResult.IGNORED;
            }
        }
        return InputResult.PROCESSED;
    }

    public WColorSwatch setColor(int color) {
        this.color = color;
        setTint(color);
        return this;
    }

    public WColorSwatch setClearedColor(int clearedColor) {
        this.clearedColor = clearedColor;
        return this;
    }

    public WColorSwatch setInteractable(boolean interactable) {
        this.interactable = interactable;
        return this;
    }

    public WColorSwatch setColorSupplier(@Nullable IntSupplier colorSupplier) {
        this.colorSupplier = colorSupplier;
        return this;
    }

    public WColorSwatch setColorConsumer(@Nullable IntConsumer colorConsumer) {
        this.colorConsumer = colorConsumer;
        return this;
    }
}
