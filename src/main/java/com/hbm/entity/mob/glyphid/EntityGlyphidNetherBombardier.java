package com.hbm.entity.mob.glyphid;

import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.ResourceManager;
import com.hbm.items.ModItems;

import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGlyphidNetherBombardier extends EntityGlyphidBombardier {

	public EntityGlyphidNetherBombardier(World world) {
		super(world);
		this.isImmuneToFire = true;
		this.netherGlyphid = true;
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nether_bombardier_tex;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isFireDamage()) return new float[] {0F, 1F};
		if(damage == ModDamageSource.shrapnel) return new float[] {0F, 1F};
		return super.getCurrentDTDR(damage, amount, pierceDT, pierce);
	}

	@Override
	protected void fireProjectile(Vec3 fireVec, double v0, int i) {
		EntityShrapnel shrapnel = new EntityShrapnel(worldObj, posX, posY + 1, posZ);
		shrapnel.setTrail(true);
		shrapnel.setThrowableHeading(fireVec.xCoord, fireVec.yCoord, fireVec.zCoord, (float) v0, i * getSpreadMult());
		worldObj.spawnEntityInWorld(shrapnel);
	}

	@Override
	public int getBombCount() {
		return 3;
	}

	@Override
	protected Item getMeatItem() {
		return isBurning() ? ModItems.glyphid_meat_nether_grilled : ModItems.glyphid_meat_nether;
	}
}
