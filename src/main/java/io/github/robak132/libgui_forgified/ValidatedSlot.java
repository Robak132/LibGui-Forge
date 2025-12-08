package io.github.robak132.libgui_forgified;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.robak132.libgui_forgified.client.VisualLogger;
import io.github.robak132.libgui_forgified.widget.WItemSlot;
import java.util.Objects;
import java.util.function.Predicate;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ValidatedSlot extends Slot {

    /**
     * The default {@linkplain #setInputFilter(Predicate) item filter} that allows all items.
     *
     * @since 5.1.1
     */
    public static final Predicate<ItemStack> DEFAULT_ITEM_FILTER = stack -> true;
    private static final VisualLogger LOGGER = new VisualLogger(ValidatedSlot.class);
    protected final Multimap<WItemSlot, WItemSlot.ChangeListener> listeners = HashMultimap.create();
    private final int slotNumber;

    @Getter
    @Setter
    private boolean insertingAllowed = true;

    @Setter
    @Getter
    private boolean takingAllowed = true;

    @Setter
    @Getter
    private Predicate<ItemStack> inputFilter = DEFAULT_ITEM_FILTER;

    @Setter
    @Getter
    private Predicate<ItemStack> outputFilter = DEFAULT_ITEM_FILTER;

    @Setter
    @Getter
    private boolean visible = true;

    public ValidatedSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        if (inventory == null) {
            throw new IllegalArgumentException("Can't make an itemslot from a null inventory!");
        }
        this.slotNumber = index;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return insertingAllowed && container.canPlaceItem(slotNumber, stack) && inputFilter.test(stack);
    }

    @Override
    public boolean mayPickup(@NotNull Player player) {
        return takingAllowed && container.stillValid(player) && outputFilter.test(getItem());
    }

    @Override
    public @NotNull ItemStack getItem() {
        if (container == null) {
            LOGGER.warn("Prevented null-inventory from WItemSlot with slot #: {}", slotNumber);
            return ItemStack.EMPTY;
        }

        ItemStack result = super.getItem();
        if (result == null) {
            LOGGER.warn("Prevented null-itemstack crash from: {}", container.getClass().getCanonicalName());
            return ItemStack.EMPTY;
        }

        return result;
    }

    @Override
    public void setChanged() {
        listeners.forEach((slot, listener) -> listener.onStackChanged(slot, container, getInventoryIndex(), getItem()));
        super.setChanged();
    }

    /**
     * Gets the index of this slot in its inventory.
     *
     * @return the inventory index
     */
    public int getInventoryIndex() {
        return slotNumber;
    }

    /**
     * Adds a change listener to this slot. Does nothing if the listener is already registered.
     *
     * @param owner    the owner of this slot
     * @param listener the listener
     * @throws NullPointerException if either parameter is null
     * @since 3.0.0
     */
    public void addChangeListener(WItemSlot owner, WItemSlot.ChangeListener listener) {
        Objects.requireNonNull(owner, "owner");
        Objects.requireNonNull(listener, "listener");
        listeners.put(owner, listener);
    }

    @Override
    public boolean isActive() {
        return isVisible();
    }

}
