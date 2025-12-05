package io.github.cottonmc.cotton.gui.widget.data;

import lombok.Getter;

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
    UP(WidgetAxis.VERTICAL, false),
    DOWN(WidgetAxis.VERTICAL, true),
    LEFT(WidgetAxis.HORIZONTAL, true),
    RIGHT(WidgetAxis.HORIZONTAL, false);

    /**
     * -- GETTER --
     *  Gets the direction's axis.
     */
    private final WidgetAxis axis;
    /**
     * -- GETTER --
     *  Returns whether this slider is inverted.
     *  <p>An inverted slider will have reversed keyboard control.
     */
    private final boolean inverted;

    WidgetDirection(WidgetAxis axis, boolean inverted) {
        this.axis = axis;
        this.inverted = inverted;
    }

}