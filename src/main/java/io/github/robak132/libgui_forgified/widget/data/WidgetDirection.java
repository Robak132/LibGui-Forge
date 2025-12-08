package io.github.robak132.libgui_forgified.widget.data;

import lombok.Getter;
import net.minecraft.core.Direction;

/**
 * The direction enum represents all four directions a slider can face.
 *
 * <p>For example, a slider whose value grows towards the right faces right.
 *
 * <p>The default direction for vertical sliders is {@link #UP} and
 * the one for horizontal sliders is {@link #RIGHT}.
 *
 * @since 2.0.0
 */
@Getter
public enum WidgetDirection {
    UP(Direction.Plane.VERTICAL, false),
    DOWN(Direction.Plane.VERTICAL, true),
    LEFT(Direction.Plane.HORIZONTAL, true),
    RIGHT(Direction.Plane.HORIZONTAL, false);

    /**
     * -- GETTER --
     *  Gets the direction's axis.
     */
    private final Direction.Plane axis;
    /**
     * -- GETTER --
     *  Returns whether this slider is inverted.
     *  <p>An inverted slider will have reversed keyboard control.
     */
    private final boolean inverted;

    WidgetDirection(Direction.Plane axis, boolean inverted) {
        this.axis = axis;
        this.inverted = inverted;
    }

}