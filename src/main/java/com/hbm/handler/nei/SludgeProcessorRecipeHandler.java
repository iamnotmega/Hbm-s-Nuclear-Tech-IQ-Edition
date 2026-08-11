package com.hbm.handler.nei;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.recipes.SludgeProcessorRecipes;

public class SludgeProcessorRecipeHandler extends NEIGenericRecipeHandler {
	public SludgeProcessorRecipeHandler() {
		super(ModBlocks.machine_sludge_processor.getLocalizedName(), SludgeProcessorRecipes.INSTANCE, ModBlocks.machine_sludge_processor);
	}

	@Override
	public String getRecipeID() {
		return "ntmSludgeProcessor";
	}
}
