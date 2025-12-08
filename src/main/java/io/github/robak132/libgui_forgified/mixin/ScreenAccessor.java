package io.github.robak132.libgui_forgified.mixin;

import java.util.List;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Screen.class)
public interface ScreenAccessor {

    @Accessor("children")
    List<GuiEventListener> libgui_forge$getChildren(); //NOSONAR
}
