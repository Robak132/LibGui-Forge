package io.github.robak132.libgui_forgified.client;

import io.github.robak132.libgui_forgified.widget.WWidget;
import io.github.robak132.libgui_forgified.widget.data.Texture;
import java.util.function.Consumer;
import juuxel.libninepatch.NinePatch;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Nine-patch background painters paint rectangles using a special nine-patch texture. The texture is divided into nine
 * sections: four corners, four edges and a center part. The edges and the center are either tiled or stretched,
 * depending on the mode of the painter, to fill the area between the corners. By default, the texture is tiled.
 *
 * <p>Nine-patch background painters can be created using {@link BackgroundPainter#createNinePatch(ResourceLocation)},
 * {@link #createNinePatch(Texture, Consumer)}, or with the constructor directly. The latter two let you customise the
 * look of the background more finely.
 *
 * <p>{@code NinePatchBackgroundPainter} has a customizable padding that can be applied.
 * By default there is no padding, but you can set it using {@link NinePatchBackgroundPainter#setPadding(int)}.
 *
 * @since 4.0.0
 */
@OnlyIn(Dist.CLIENT)
public final class NinePatchBackgroundPainter implements BackgroundPainter {

    private final NinePatch<ResourceLocation> ninePatch;
    @Getter
    private int topPadding = 0;
    @Getter
    private int leftPadding = 0;
    @Getter
    private int bottomPadding = 0;
    @Getter
    private int rightPadding = 0;

    public NinePatchBackgroundPainter(NinePatch<ResourceLocation> ninePatch) {
        this.ninePatch = ninePatch;
    }

    public NinePatchBackgroundPainter setTopPadding(int topPadding) {
        this.topPadding = topPadding;
        return this;
    }

    public NinePatchBackgroundPainter setLeftPadding(int leftPadding) {
        this.leftPadding = leftPadding;
        return this;
    }

    public NinePatchBackgroundPainter setBottomPadding(int bottomPadding) {
        this.bottomPadding = bottomPadding;
        return this;
    }

    public NinePatchBackgroundPainter setRightPadding(int rightPadding) {
        this.rightPadding = rightPadding;
        return this;
    }

    public NinePatchBackgroundPainter setPadding(int padding) {
        this.topPadding = this.leftPadding = this.bottomPadding = this.rightPadding = padding;
        return this;
    }

    public NinePatchBackgroundPainter setPadding(int vertical, int horizontal) {
        this.topPadding = this.bottomPadding = vertical;
        this.leftPadding = this.rightPadding = horizontal;
        return this;
    }

    public NinePatchBackgroundPainter setPadding(int topPadding, int leftPadding, int bottomPadding, int rightPadding) {
        this.topPadding = topPadding;
        this.leftPadding = leftPadding;
        this.bottomPadding = bottomPadding;
        this.rightPadding = rightPadding;

        return this;
    }

    @Override
    public void paintBackground(GuiGraphics context, int left, int top, WWidget panel) {
        var matrices = context.pose();
        matrices.pushPose();
        matrices.translate(left - leftPadding, top - topPadding, 0);
        ninePatch.draw(NinePatchTextureRendererImpl.INSTANCE, context, panel.getWidth() + leftPadding + rightPadding,
                panel.getHeight() + topPadding + bottomPadding);
        matrices.popPose();
    }
}
