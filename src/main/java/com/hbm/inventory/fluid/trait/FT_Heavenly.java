package com.hbm.inventory.fluid.trait;

import java.util.List;

import com.hbm.util.i18n.I18nUtil;

import net.minecraft.util.EnumChatFormatting;

public class FT_Heavenly extends FluidTrait {

	@Override
	public void addInfoHidden(List<String> info) {
		info.add(EnumChatFormatting.GOLD + "[" + I18nUtil.resolveKey("hbmfluid.trait.heavenly") + "]");
	}

}
