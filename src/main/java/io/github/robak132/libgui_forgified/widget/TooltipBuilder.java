package io.github.robak132.libgui_forgified.widget;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * A builder for widget tooltips.
 *
 * @since 3.0.0
 */
@OnlyIn(Dist.CLIENT)
public final class TooltipBuilder {

    final List<FormattedCharSequence> lines = new ArrayList<>();

    int size() {
        return lines.size();
    }

    /**
     * Adds the lines to this builder.
     *
     * @param lines the lines
     * @return this builder
     */
    public TooltipBuilder add(Component... lines) {
        for (Component line : lines) {
            this.lines.add(line.getVisualOrderText());
        }

        return this;
    }

    /**
     * Adds the lines to this builder.
     *
     * @param lines the lines
     * @return this builder
     */
    public TooltipBuilder add(FormattedCharSequence... lines) {
        Collections.addAll(this.lines, lines);

        return this;
    }
}
