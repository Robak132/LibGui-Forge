package io.github.robak132.libgui_forgified.widget;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * A widget that displays an item or a list of items.
 *
 * @since 1.8.0
 */
public class WItem extends WWidget {

    @Getter
    private List<ItemStack> items;
    /**
     * -- GETTER -- Returns the animation duration of this. Defaults to 25 screen ticks.
     */
    @Getter
    private int duration = 25;
    private int ticks = 0;
    private int current = 0;

    public WItem(List<ItemStack> items) {
        setItems(items);
    }

    public WItem(TagKey<? extends ItemLike> tag) {
        this(getRenderStacks(tag));
    }

    public WItem(ItemStack stack) {
        this(Collections.singletonList(stack));
    }

    @Override
    public boolean canResize() {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void tick() {
        if (ticks++ >= duration) {
            ticks = 0;
            current = (current + 1) % items.size();
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void paint(GuiGraphics context, int x, int y, int mouseX, int mouseY) {
        RenderSystem.enableDepthTest();
        context.renderFakeItem(items.get(current), x + getWidth() / 2 - 8, y + getHeight() / 2 - 8);
    }

    public WItem setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    /**
     * Sets the item list of this {@code WItem} and resets the animation state.
     *
     * @param items the new item list
     * @return this instance
     */
    public WItem setItems(List<ItemStack> items) {
        Objects.requireNonNull(items, "stacks == null!");
        if (items.isEmpty()) {
            throw new IllegalArgumentException("The stack list is empty!");
        }

        this.items = items;

        // Reset the state
        current = 0;
        ticks = 0;

        return this;
    }

    /**
     * Gets the default stacks ({@link Item#getDefaultInstance()}) of each item in a tag.
     */
    @SuppressWarnings("unchecked")
    private static List<ItemStack> getRenderStacks(TagKey<? extends ItemLike> tag) {
        Registry<ItemLike> registry = (Registry<ItemLike>) BuiltInRegistries.REGISTRY.get(tag.registry().location());
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();

        for (Holder<ItemLike> item : registry.getOrCreateTag((TagKey<ItemLike>) tag)) {
            builder.add(item.value().asItem().getDefaultInstance());
        }

        return builder.build();
    }
}
