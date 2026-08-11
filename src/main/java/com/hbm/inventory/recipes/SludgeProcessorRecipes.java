package com.hbm.inventory.recipes;

import com.hbm.inventory.FluidStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.recipes.loader.GenericRecipes;
import com.hbm.items.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class SludgeProcessorRecipes extends GenericRecipes<SludgeProcessorRecipe> {
	public static final SludgeProcessorRecipes INSTANCE = new SludgeProcessorRecipes();

	@Override
	public int inputItemLimit() {
		return 1;
	}
	@Override
	public int inputFluidLimit() {
		return 1;
	}
	@Override
	public int outputItemLimit() {
		return 4;
	}
	@Override
	public int outputFluidLimit() {
		return 4;
	}

	@Override
	public String getFileName() {
		return "hbmSludgeProcessor.json";
	}
	@Override
	public SludgeProcessorRecipe instantiateRecipe(String name) {
		return new SludgeProcessorRecipe(name);
	}

	@Override
	public void registerDefaults() {
		this.register((SludgeProcessorRecipe) new SludgeProcessorRecipe("sludge.natcrete")
			.setup(100, 10_000).setNameWrapper("sludge.concrete")
			.inputFluids(new FluidStack(Fluids.NATCRETE, 2000))
			.outputFluids(
				new FluidStack(Fluids.CONCRETE, 1000),
				new FluidStack(Fluids.BUURCRETE, 500),
				new FluidStack(Fluids.SCRAPCRETE, 500),
				new FluidStack(Fluids.LAVA, 500)
			)
			.setIconToFirstIngredient()
		);
	}
}
