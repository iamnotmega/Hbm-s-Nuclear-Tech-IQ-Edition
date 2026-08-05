package com.hbm.entity.mob.glyphid;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.main.ResourceManager;
import com.hbm.items.ModItems;

import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidNetherNuclear extends EntityGlyphidNuclear {

	public EntityGlyphidNetherNuclear(World world) {
		super(world);
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nether_nuclear_tex;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isFireDamage()) return new float[] {0F, 1F};
		return super.getCurrentDTDR(damage, amount, pierceDT, pierce);
	}

	@Override
	protected void nuclearExplosion() {

		EntityBalefire bf = new EntityBalefire(worldObj);
		bf.posX = posX;
		bf.posY = posY;
		bf.posZ = posZ;
		bf.destructionRange = 25;
		worldObj.spawnEntityInWorld(bf);
		EntityNukeTorex.statFacBale(worldObj, posX, posY, posZ, 25);
	}

	@Override
	protected Item getMeatItem() {
		return isBurning() ? ModItems.glyphid_meat_nether_grilled : ModItems.glyphid_meat_nether;
	}
}
