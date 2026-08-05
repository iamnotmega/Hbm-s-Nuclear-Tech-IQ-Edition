package com.hbm.entity.mob.glyphid;

import com.hbm.main.ResourceManager;
import com.hbm.items.ModItems;
import com.hbm.world.feature.GlyphidHiveNether;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidNetherScout extends EntityGlyphidScout {

	public EntityGlyphidNetherScout(World world) {
		super(world);
		this.isImmuneToFire = true;
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nether_scout_tex;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isFireDamage()) return new float[] {0F, 1F};
		return super.getCurrentDTDR(damage, amount, pierceDT, pierce);
	}

	@Override
	protected void applyOnHitEffect(EntityLivingBase victum) {
		victum.addPotionEffect(new PotionEffect(Potion.wither.id, 10 * 20, 3));
	}

	@Override
	protected void buildHive() {
		GlyphidHiveNether.generateLarge(worldObj, (int) Math.floor(posX), (int) Math.floor(posY) + 1, (int) Math.floor(posZ), rand);
	}

	@Override
	protected Item getMeatItem() {
		return isBurning() ? ModItems.glyphid_meat_nether_grilled : ModItems.glyphid_meat_nether;
	}
}
