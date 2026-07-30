package com.hbm.inventory.fluid.trait;

import com.hbm.config.VersatileConfig;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;

import api.hbm.fluidmk2.IFillableItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class Injectables {

	public static void process(EntityLivingBase entity, FluidType type, float amount, float intensity, boolean isInjection) {

		if(isInjection && VersatileConfig.hasPotionSickness(entity))
			return;

		if(isInjection && type.isCorrosive()) {
			entity.attackEntityFrom(DamageSource.generic, amount * intensity * 0.5F);
		}

		if(type.hasTrait(FT_Consumable.class)) {
			type.getTrait(FT_Consumable.class).apply(entity, intensity);
		}

		if(type.hasTrait(FT_Drug.class)) {
			type.getTrait(FT_Drug.class).apply(entity, intensity);
		}

		if(type == Fluids.ENDERJUICE) {
			double x = entity.posX + entity.getRNG().nextGaussian() * 16;
			double y = entity.posY;
			double z = entity.posZ + entity.getRNG().nextGaussian() * 16;
			for(int i = 0; i < 10; i++) {
				if(entity.worldObj.getTopSolidOrLiquidBlock((int)x, (int)z) > 0) break;
				y++;
			}
			entity.setPositionAndUpdate(x, y, z);
		}
		if(type == Fluids.XPJUICE && entity instanceof EntityPlayer) {
			((EntityPlayer) entity).addExperience((int)(10 * intensity));
		}

		if(isInjection) {
			int sickness = type == Fluids.SUPER_STIMPAK ? 15 : 5;
			VersatileConfig.applyPotionSickness(entity, sickness);
		}
	}

	public static void injectEntity(ItemStack stack, EntityLivingBase target, EntityPlayer player, int dose, float intensity) {
		FluidType type = IFillableItem.getFluidType(stack);
		Injectables.process(target, type, dose, intensity, true);
		short fill = IFillableItem.getFluidFill(stack);
		short newFill = (short) (fill - dose);
		if(newFill == 0) IFillableItem.setFluidFill(stack, Fluids.NONE, (short) 0);
		else IFillableItem.setFluidFill(stack, type, newFill);
		player.worldObj.playSoundAtEntity(player, "hbm:item.syringe", 1.0F, 1.0F);
	}
}
