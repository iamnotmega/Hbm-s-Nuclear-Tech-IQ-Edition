package com.hbm.module.machine;

import api.hbm.energymk2.IEnergyHandlerMK2;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.recipes.SludgeProcessorRecipes;
import net.minecraft.item.ItemStack;

public class ModuleMachineSludgeProcessor extends ModuleMachineBase {
	public ModuleMachineSludgeProcessor(int index, IEnergyHandlerMK2 battery, ItemStack[] slots) {
		super(index, battery, slots);
		this.inputSlots = new int[1];
		this.outputSlots = new int[4];
		this.inputTanks = new FluidTank[1];
		this.outputTanks = new FluidTank[4];
	}

	@Override
	public SludgeProcessorRecipes getRecipeSet() {
		return SludgeProcessorRecipes.INSTANCE;
	}

	public ModuleMachineSludgeProcessor itemInput(int start) {
		for(int i = 0; i < this.inputSlots.length; i++) this.inputSlots[i] = start + i;
		return this;
	}
	public ModuleMachineSludgeProcessor itemOutput(int start) {
		for(int i = 0; i < this.outputSlots.length; i++) this.outputSlots[i] = start + i;
		return this;
	}
	public ModuleMachineSludgeProcessor fluidInput(FluidTank a) {
		this.inputTanks[0] = a;
		return this;
	}
	public ModuleMachineSludgeProcessor fluidOutput(FluidTank a, FluidTank b, FluidTank c, FluidTank d) {
		this.outputTanks[0] = a;
		this.outputTanks[1] = b;
		this.outputTanks[2] = c;
		this.outputTanks[3] = d;
		return this;
	}
}
