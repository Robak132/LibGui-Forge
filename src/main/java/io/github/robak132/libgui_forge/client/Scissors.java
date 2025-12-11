package io.github.robak132.libgui_forge.client;

import com.google.errorprone.annotations.MustBeClosed;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayDeque;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * A stack-driven scissor manager for GUI rendering. Must be used exclusively with try-with-resources.
 */
@OnlyIn(Dist.CLIENT)
public final class Scissors {

    private static final ArrayDeque<Frame> STACK = new ArrayDeque<>();

    private Scissors() {
    }

    /**
     * Pushes a new scissor frame. Must be closed via try-with-resources.
     */
    @MustBeClosed
    public static Frame push(int x, int y, int width, int height) {
        Frame frame = new Frame(x, y, width, height);
        STACK.push(frame);
        refreshScissors();
        return frame;
    }

    /**
     * Pops the top frame; INTERNAL ONLY.
     */
    @SuppressWarnings("resource")
    static void internalPop(Frame frame) {
        if (STACK.isEmpty()) {
            throw new IllegalStateException("No scissors on the stack!");
        }
        if (STACK.peek() != frame) {
            throw new IllegalStateException("%s is not on top of the stack!".formatted(frame));
        }

        STACK.pop();
        refreshScissors();
    }

    /**
     * Recomputes the scissor region based on stack intersection.
     */
    static void refreshScissors() {
        Minecraft mc = Minecraft.getInstance();

        if (STACK.isEmpty()) {
            RenderSystem.enableScissor(0, 0, mc.getWindow().getWidth(), mc.getWindow().getHeight());
            return;
        }

        int left = Integer.MIN_VALUE;
        int top = Integer.MIN_VALUE;
        int right = Integer.MAX_VALUE;
        int bottom = Integer.MAX_VALUE;

        for (Frame f : STACK) {
            left = Math.max(left, f.x);
            top = Math.max(top, f.y);
            right = Math.min(right, f.x + f.width);
            bottom = Math.min(bottom, f.y + f.height);
        }

        int width = Math.max(0, right - left);
        int height = Math.max(0, bottom - top);

        double scale = mc.getWindow().getGuiScale();
        int fbLeft = (int) Math.round(left * scale);
        int fbWidth = (int) Math.round(width * scale);
        int fbHeight = (int) Math.round(height * scale);
        int fbBottom = (int) Math.round(mc.getWindow().getHeight() - (top * scale) - fbHeight);

        RenderSystem.enableScissor(fbLeft, fbBottom, fbWidth, fbHeight);
    }

    /**
     * Debug utility
     */
    static void checkStackIsEmpty() {
        if (!STACK.isEmpty()) {
            throw new IllegalStateException("Unpopped scissor frames: " + STACK.stream().map(Frame::toString).collect(Collectors.joining(", ")));
        }
    }

    /**
     * A single scissor frame; closing it pops from the stack.
     */
    public static final class Frame implements AutoCloseable {

        private final int x;
        private final int y;
        private final int width;
        private final int height;

        private Frame(int x, int y, int width, int height) {
            if (width < 0) {
                throw new IllegalArgumentException("Negative width");
            }
            if (height < 0) {
                throw new IllegalArgumentException("Negative height");
            }

            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        @Override
        public void close() {
            Scissors.internalPop(this);
        }

        @Override
        public String toString() {
            return "Frame{ at=(" + x + ", " + y + "), size=(" + width + ", " + height + ") }";
        }
    }
}
