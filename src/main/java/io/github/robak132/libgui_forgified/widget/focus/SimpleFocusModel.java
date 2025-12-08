package io.github.robak132.libgui_forgified.widget.focus;

import io.github.robak132.libgui_forgified.widget.WWidget;
import io.github.robak132.libgui_forgified.widget.data.Rect2i;
import java.util.stream.Stream;
import org.jetbrains.annotations.Nullable;

record SimpleFocusModel(WWidget widget, Rect2i area) implements FocusModel<@Nullable Void> {

    @Override
    public boolean isFocused(Focus<@Nullable Void> focus) {
        return widget.isFocused();
    }

    @Override
    public void setFocused(Focus<@Nullable Void> focusArea) {
    }

    @Override
    public Stream<Focus<@Nullable Void>> foci() {
        return Stream.of(Focus.of(area));
    }
}
