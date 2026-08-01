package com.hbm.handler;

import com.hbm.items.armor.ItemSymbol;
import com.hbm.lib.ModDamageSource;
import com.hbm.util.EntityDamageUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class SymbolBehaviors {
	/// GUILT ///
	public static boolean bypassesHazards(EntityLivingBase entity) {
		return SymbolHandler.hasSymbol(entity, ItemSymbol.SymbolType.GUILT);
	}
	public static boolean bypassesSuffocation(EntityLivingBase entity) {
		if(!SymbolHandler.hasSymbol(entity, ItemSymbol.SymbolType.GUILT)) return false;
		if(entity.getAir() < 300) entity.setAir(300);
		return true;
	}
	public static boolean bypassesCorrosion(EntityLivingBase entity) {
		return SymbolHandler.hasSymbol(entity, ItemSymbol.SymbolType.GUILT);
	}
	public static boolean cancelsDamage(DamageSource source, EntityPlayer player) {
		return SymbolHandler.hasSymbol(player, ItemSymbol.SymbolType.GUILT) &&
			(source == ModDamageSource.acid || ModDamageSource.s_acid.equals(source.getDamageType()) || source == ModDamageSource.pc);
	}
	public static boolean tryHandleFireAttack(DamageSource source, EntityPlayer player, float amount) {
		if(!SymbolHandler.hasSymbol(player, ItemSymbol.SymbolType.GUILT) || !source.isFireDamage()) return false;
		if(player.getEntityData().getBoolean("ntmGuiltFire")) return false;
		player.getEntityData().setBoolean("ntmGuiltFire", true);
		try {
			EntityDamageUtil.attackEntityFromNT(player, source, amount, true, false, 1.0D, 100F, 1F);
		} finally {
			player.getEntityData().removeTag("ntmGuiltFire");
		}
		return true;
	}
	public static boolean ignoresFireRes(EntityLivingBase entity) {
		return SymbolHandler.hasSymbol(entity, ItemSymbol.SymbolType.GUILT);
	}
	public static boolean bypassesFireArmor(EntityLivingBase entity) {
		return SymbolHandler.hasSymbol(entity, ItemSymbol.SymbolType.GUILT);
	}
	public static float getFireDamage(EntityLivingBase entity, float amount) {
		return SymbolHandler.hasSymbol(entity, ItemSymbol.SymbolType.GUILT) ? amount * 2F : amount;
	}
}
