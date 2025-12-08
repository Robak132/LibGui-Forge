package io.github.robak132.libgui_forgified.widget;

import io.github.robak132.libgui_forgified.widget.data.HorizontalAlignment;
import io.github.robak132.libgui_forgified.widget.data.Insets;
import io.github.robak132.libgui_forgified.widget.data.VerticalAlignment;
import lombok.Getter;
import net.minecraft.core.Direction;

import java.util.Objects;

/**
 * Similar to the BoxLayout in Swing, this widget represents a list of widgets along an axis.
 *
 * @since 2.0.0
 */
@Getter
public class WBox extends WPanelWithInsets {
	/**
	 * The spacing between widgets.
	 */
	protected int spacing = 4;
	protected Direction.Plane axis;
	protected HorizontalAlignment horizontalAlignment = HorizontalAlignment.LEFT;
	protected VerticalAlignment verticalAlignment = VerticalAlignment.TOP;

	/**
	 * Constructs a box.
	 *
	 * @param axis the box axis
	 * @throws NullPointerException if the axis is null
	 */
	public WBox(Direction.Plane axis) {
		this.axis = Objects.requireNonNull(axis, "axis");
	}

	/**
	 * Adds a widget to this box.
	 * If the widget is resizeable, resizes it to the provided dimensions.
	 *
	 * @param widget the widget
	 * @param width  the new width of the widget
	 * @param height the new height of the widget
	 */
	public void add(WWidget widget, int width, int height) {
		widget.setParent(this);
		children.add(widget);
		if (widget.canResize()) {
			widget.setSize(width, height);
		}
	}

	/**
	 * Adds a widget to this box.
	 * If the widget is resizeable, resizes it to 18x18.
	 *
	 * @param widget the widget
	 */
	public void add(WWidget widget) {
		add(widget, 18, 18);
	}

	@Override
	public void layout() {
		int dimension = (axis == Direction.Plane.HORIZONTAL) ? insets.left() : insets.top();

		// Set position offset from alignment along the box axis
		if (axis == Direction.Plane.HORIZONTAL) {
			if (horizontalAlignment != HorizontalAlignment.LEFT) {
				int widgetWidth = spacing * (children.size() - 1);
				for (WWidget child : children) {
					widgetWidth += child.getWidth();
				}

				if (horizontalAlignment == HorizontalAlignment.CENTER) {
					dimension = (getWidth() - widgetWidth) / 2;
				} else { // right
					dimension = getWidth() - widgetWidth;
				}
			}
		} else if (verticalAlignment != VerticalAlignment.TOP) {
			int widgetHeight = spacing * (children.size() - 1);
			for (WWidget child : children) {
				widgetHeight += child.getHeight();
			}

			if (verticalAlignment == VerticalAlignment.CENTER) {
				dimension = (getHeight() - widgetHeight) / 2;
			} else { // bottom
				dimension = getHeight() - widgetHeight;
			}
		}

		for (int i = 0; i < children.size(); i++) {
			WWidget child = children.get(i);

			if (axis == Direction.Plane.HORIZONTAL) {
				int y = switch (verticalAlignment) {
					case TOP -> insets.top();
					case CENTER -> insets.top() + (getHeight() - insets.top() - insets.bottom() - child.getHeight()) / 2;
					case BOTTOM -> getHeight() - insets.bottom() - child.getHeight();
				};

				child.setLocation(dimension, y);
			} else {
				int x = switch (horizontalAlignment) {
					case LEFT -> insets.left();
					case CENTER -> insets.left() + (getWidth() - insets.left() - insets.right() - child.getWidth()) / 2;
					case RIGHT -> getWidth() - insets.right() - child.getWidth();
				};

				child.setLocation(x, dimension);
			}

			if (child instanceof WPanel panel) panel.layout();
			expandToFit(child, insets);

			if (i != children.size() - 1) {
				dimension += spacing;
			}

			dimension += (axis == Direction.Plane.HORIZONTAL) ? child.getWidth() : child.getHeight();
		}
	}

    /**
	 * Sets the spacing between widgets in this box.
	 *
	 * @param spacing the new spacing
	 * @return this box
	 */
	public WBox setSpacing(int spacing) {
		this.spacing = spacing;

		return this;
	}

    /**
	 * Sets the axis of this box.
	 *
	 * @param axis the new axis
	 * @return this box
	 * @throws NullPointerException if the axis is null
	 */
	public WBox setAxis(Direction.Plane axis) {
		this.axis = Objects.requireNonNull(axis, "axis");
		return this;
	}

    /**
	 * Sets the horizontal alignment of this box.
	 *
	 * @param alignment the new alignment
	 * @return this box
	 * @throws NullPointerException if the alignment is null
	 * @since 2.1.0
	 */
	public WBox setHorizontalAlignment(HorizontalAlignment alignment) {
		this.horizontalAlignment = Objects.requireNonNull(alignment, "alignment");
		return this;
	}

    /**
	 * Sets the vertical alignment of this box.
	 *
	 * @param alignment the new alignment
	 * @return this box
	 * @throws NullPointerException if the alignment is null
	 * @since 2.1.0
	 */
	public WBox setVerticalAlignment(VerticalAlignment alignment) {
		this.verticalAlignment = Objects.requireNonNull(alignment, "alignment");
		return this;
	}

	public WBox setInsets(Insets insets) {
		super.setInsets(insets);
		return this;
	}
}
