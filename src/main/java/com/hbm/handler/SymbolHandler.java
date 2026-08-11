package com.hbm.handler;

import com.hbm.extprop.HbmPlayerProps;
import com.hbm.items.armor.ItemSymbol;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class SymbolHandler {

	/**
	 * Returns the item meta of the player's active symbol.
	 * @param player the player to check
	 * BTW, -1 meta is none.. this should be obvious, you fucking dumbass. Just kidding i love you.
	 */
	public static int getActiveSymbolMeta(EntityPlayer player) {
		return HbmPlayerProps.getData(player).symbol;
	}

	/**
	 * Sets the player's active symbol tag.
	 * @param player the player to tag
	 * @param meta the SymbolType ordinal to set, or -1 to clear cus its none
	 */
	public static void setActiveSymbol(EntityPlayer player, int meta) {
		HbmPlayerProps.getData(player).symbol = meta;
	}

	/**
		 * @param entity the entity to check
		 * @return true if the entity has the specified symvol type
	 */
  // voltie iq
	public static boolean hasSymbol(EntityLivingBase entity, ItemSymbol.SymbolType type) {
		if(!(entity instanceof EntityPlayer)) return false;
		return ItemSymbol.fromMeta(HbmPlayerProps.getData((EntityPlayer) entity).symbol) == type;
	}

	public static boolean hasSymbolInInventory(EntityPlayer player, ItemSymbol.SymbolType type) {
		for(ItemStack stack : player.inventory.mainInventory) {
			if(stack != null && stack.getItem() instanceof ItemSymbol && ItemSymbol.fromMeta(stack.getItemDamage()) == type) return true;
		}
		for(ItemStack stack : player.inventory.armorInventory) {
			if(stack != null && stack.getItem() instanceof ItemSymbol && ItemSymbol.fromMeta(stack.getItemDamage()) == type) return true;
		}
		return false;
	}
}
