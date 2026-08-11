package com.hbm.entity.item;

import com.hbm.handler.SymbolHandler;
import com.hbm.items.armor.ItemSymbol;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemSymbol extends EntityItem {

	public EntityItemSymbol(World world) {
		super(world);
	}

	public EntityItemSymbol(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public EntityItemSymbol(World world, double x, double y, double z, ItemStack stack) {
		super(world, x, y, z, stack);
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {
		if(worldObj.isRemote || delayBeforeCanPickup > 0) return;

		ItemStack stack = getEntityItem();
		ItemSymbol.SymbolType type = ItemSymbol.fromMeta(stack.getItemDamage());

		if(type != null && SymbolHandler.hasSymbolInInventory(player, type)) return;

		super.onCollideWithPlayer(player);

		if(isDead && type != null) SymbolHandler.setActiveSymbol(player, type.ordinal());
	}
}
