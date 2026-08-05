package com.hbm.entity.mob.glyphid;

import com.hbm.entity.projectile.EntityBloatBomb;
import com.hbm.main.ResourceManager;
import com.hbm.items.ModItems;

import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGlyphidNetherBlaster extends EntityGlyphidBlaster {

	public EntityGlyphidNetherBlaster(World world) {
		super(world);
		this.isImmuneToFire = true;
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nether_blaster_tex;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isFireDamage()) return new float[] {0F, 1F};
		return super.getCurrentDTDR(damage, amount, pierceDT, pierce);
	}

	@Override
	protected void fireProjectile(Vec3 fireVec, double v0, int i) {
		EntityBloatBomb bomb = new EntityBloatBomb(worldObj, posX, posY + 1, posZ);
		bomb.setThrower(this);
		bomb.setThrowableHeading(fireVec.xCoord, fireVec.yCoord, fireVec.zCoord, (float) v0, i * getSpreadMult());
		bomb.damage = getBombDamage();
		worldObj.spawnEntityInWorld(bomb);
	}

	@Override
	protected Item getMeatItem() {
		return isBurning() ? ModItems.glyphid_meat_nether_grilled : ModItems.glyphid_meat_nether;
	}
}
