package com.hbm.entity.mob.glyphid;

import com.hbm.main.ResourceManager;
import com.hbm.items.ModItems;

import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidNether extends EntityGlyphid {

	public EntityGlyphidNether(World world) {
		super(world);
		this.isImmuneToFire = true;
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nether_tex;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isFireDamage()) return new float[] {0F, 1F};
		return super.getCurrentDTDR(damage, amount, pierceDT, pierce);
	}

	@Override
	protected Item getMeatItem() {
		return isBurning() ? ModItems.glyphid_meat_nether_grilled : ModItems.glyphid_meat_nether;
	}
}
