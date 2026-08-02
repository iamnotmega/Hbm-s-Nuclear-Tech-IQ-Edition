package com.hbm.blocks;

import com.hbm.blocks.ModSoundType;
import com.hbm.blocks.generic.BlockLogNT;
import com.hbm.lib.RefStrings;

public class BlockBloatsproutLog extends BlockLogNT {

	public BlockBloatsproutLog() {
		super(RefStrings.MODID + ":bloatsprout_log_side", RefStrings.MODID + ":bloatsprout_log_top");
		this.setStepSound(ModSoundType.mod("plantsquish", 1.0F, 1.0F));
	}
}
