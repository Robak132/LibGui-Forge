package io.github.robak132.libgui_forgified.client;

import io.github.robak132.libgui_forgified.GuiDescription;
import io.github.robak132.libgui_forgified.widget.WWidget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public interface CottonScreenImpl {
	GuiDescription getDescription();

	@Nullable
	WWidget getLastResponder();

	void setLastResponder(@Nullable WWidget lastResponder);
}
