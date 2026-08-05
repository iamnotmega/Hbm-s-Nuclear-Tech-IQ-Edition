package com.hbm.entity.projectile;

import com.hbm.entity.effect.EntityMist;
import com.hbm.entity.mob.glyphid.EntityGlyphid;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.lib.ModDamageSource;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBloatBomb extends EntityThrowableInterp {

	public float damage = 1.5F;

	public EntityBloatBomb(World world) {
		super(world);
	}

	public EntityBloatBomb(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	protected void onImpact(MovingObjectPosition mop) {

		if(worldObj.isRemote) return;

		double cloudX;
		double cloudY;
		double cloudZ;

		if(mop.typeOfHit == mop.typeOfHit.ENTITY) {

			if(!(mop.entityHit instanceof EntityGlyphid)) {
				mop.entityHit.attackEntityFrom(new EntityDamageSourceIndirect(ModDamageSource.s_acid, this, thrower), damage);
			}

			cloudX = mop.entityHit.posX;
			cloudY = mop.entityHit.posY;
			cloudZ = mop.entityHit.posZ;
		} else {
			cloudX = mop.blockX + 0.5;
			cloudY = mop.blockY + 0.5;
			cloudZ = mop.blockZ + 0.5;
		}

		EntityMist mist = new EntityMist(worldObj);
		mist.setType(Fluids.MUSTY_BLOATMUSK);
		mist.setPosition(cloudX, cloudY, cloudZ);
		mist.setArea(4, 2);
		mist.setDuration(60);
		worldObj.spawnEntityInWorld(mist);

		this.setDead();
	}

	@Override
	public double getGravityVelocity() {
		return 0.04D;
	}

	@Override
	protected float getAirDrag() {
		return 1.0F;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("damage", damage);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.damage = nbt.getFloat("damage");
	}
}
