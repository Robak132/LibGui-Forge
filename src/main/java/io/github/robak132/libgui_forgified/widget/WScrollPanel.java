package io.github.robak132.libgui_forgified.widget;

import io.github.robak132.libgui_forgified.GuiDescription;
import io.github.robak132.libgui_forgified.widget.data.InputResult;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Similar to the JScrollPane in Swing, this widget represents a scrollable widget.
 *
 * @since 2.0.0
 */
public class WScrollPanel extends WClippedPanel {

    private static final int SCROLL_BAR_SIZE = 8;
    private final WWidget widget;
    /**
     * The horizontal scroll bar of this panel.
     */
    protected WScrollBar horizontalScrollBar = new WScrollBar(Direction.Plane.HORIZONTAL);
    /**
     * The vertical scroll bar of this panel.
     */
    protected WScrollBar verticalScrollBar = new WScrollBar(Direction.Plane.VERTICAL);
    private Boolean scrollingHorizontally = null;
    private Boolean scrollingVertically = null;
    private int lastHorizontalScroll = -1;
    private int lastVerticalScroll = -1;

    /**
     * Creates a vertically scrolling panel.
     *
     * @param widget the viewed widget
     */
    public WScrollPanel(WWidget widget) {
        this.widget = widget;

        widget.setParent(this);
        horizontalScrollBar.setParent(this);
        verticalScrollBar.setParent(this);

        children.add(widget);
        children.add(verticalScrollBar); // Only vertical scroll bar
    }

    /**
     * Returns whether this scroll panel has a horizontal scroll bar.
     *
     * @return true if there is a horizontal scroll bar, default if a scroll bar should be added if needed, and false
     * otherwise
     */
    public Boolean isScrollingHorizontally() {
        return scrollingHorizontally;
    }

    public WScrollPanel setScrollingHorizontally(Boolean scrollingHorizontally) {
        if (!Objects.equals(scrollingHorizontally, this.scrollingHorizontally)) {
            this.scrollingHorizontally = scrollingHorizontally;
            layout();
        }

        return this;
    }

    /**
     * Returns whether this scroll panel has a vertical scroll bar.
     *
     * @return true if there is a vertical scroll bar, *         default if a scroll bar should be added if needed, *
     * and false otherwise
     */
    public Boolean isScrollingVertically() {
        return scrollingVertically;
    }

    public WScrollPanel setScrollingVertically(Boolean scrollingVertically) {
        if (!Objects.equals(scrollingVertically, this.scrollingVertically)) {
            this.scrollingVertically = scrollingVertically;
            layout();
        }

        return this;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void paint(GuiGraphics context, int x, int y, int mouseX, int mouseY) {
        if (verticalScrollBar.getValue() != lastVerticalScroll
                || horizontalScrollBar.getValue() != lastHorizontalScroll) {
            layout();
            lastHorizontalScroll = horizontalScrollBar.getValue();
            lastVerticalScroll = verticalScrollBar.getValue();
        }

        super.paint(context, x, y, mouseX, mouseY);
    }

    @Override
    public void layout() {
        children.clear();

        boolean horizontal = hasHorizontalScrollbar();
        boolean vertical = hasVerticalScrollbar();

        int offset = (horizontal && vertical) ? SCROLL_BAR_SIZE : 0;
        verticalScrollBar.setSize(SCROLL_BAR_SIZE, this.height - offset);
        verticalScrollBar.setLocation(this.width - verticalScrollBar.getWidth(), 0);
        horizontalScrollBar.setSize(this.width - offset, SCROLL_BAR_SIZE);
        horizontalScrollBar.setLocation(0, this.height - horizontalScrollBar.getHeight());

        if (widget instanceof WPanel) {
            ((WPanel) widget).layout();
        }
        children.add(widget);
        int x = horizontal ? -horizontalScrollBar.getValue() : 0;
        int y = vertical ? -verticalScrollBar.getValue() : 0;
        widget.setLocation(x, y);

        verticalScrollBar.setWindow(this.height - (horizontal ? SCROLL_BAR_SIZE : 0));
        verticalScrollBar.setMaxValue(widget.getHeight());
        horizontalScrollBar.setWindow(this.width - (vertical ? SCROLL_BAR_SIZE : 0));
        horizontalScrollBar.setMaxValue(widget.getWidth());

        if (vertical) {
            children.add(verticalScrollBar);
        }
        if (horizontal) {
            children.add(horizontalScrollBar);
        }
    }

    private boolean hasHorizontalScrollbar() {
        return (scrollingHorizontally == null) ? (widget.width > this.width - SCROLL_BAR_SIZE) : scrollingHorizontally;
    }

    private boolean hasVerticalScrollbar() {
        return (scrollingVertically == null) ? (widget.height > this.height - SCROLL_BAR_SIZE) : scrollingVertically;
    }

    @Override
    public InputResult onMouseScroll(int x, int y, double amount) {
        if (hasVerticalScrollbar()) {
            return verticalScrollBar.onMouseScroll(0, 0, amount);
        }

        return InputResult.IGNORED;
    }

    @Override
    public void validate(GuiDescription c) {
        //you have to validate these ones manually since they are not in children list
        this.horizontalScrollBar.validate(c);
        this.verticalScrollBar.validate(c);
        super.validate(c);
    }
}
