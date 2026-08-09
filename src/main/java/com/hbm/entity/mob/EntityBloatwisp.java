package com.hbm.entity.mob;

import api.hbm.entity.IResistanceProvider;
import com.hbm.config.MobConfig;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

	public class EntityBloatwisp extends EntityFlying implements IMob, IResistanceProvider {

	protected int scanCooldown;
	protected int attackCooldown;
	protected int waypointX;
	protected int waypointY;
	protected int waypointZ;
	protected Entity target;

	public EntityBloatwisp(World world) {
		super(world);
		this.setSize(0.6F, 0.6F);
		this.isImmuneToFire = true;
		this.experienceValue = 9;
	}

	public static boolean isOverCap(World world) {
		int count = 0;
		for(Object e : world.loadedEntityList) {
			if(e instanceof EntityBloatwisp && ++count >= MobConfig.bloatwispCap) return true;
		}
		return false;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		if(this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage) == null) {
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
		}
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2D);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(worldObj.isRemote) {
			if(ticksExisted % 2 == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "wisp");
				data.setDouble("posX", posX);
				data.setDouble("posY", posY + height * 0.5);
				data.setDouble("posZ", posZ);
				MainRegistry.proxy.effectNT(data);
			}
			return;
		}

		if(scanCooldown-- <= 0) {
			scanCooldown = 40;
			this.target = this.worldObj.getClosestVulnerablePlayerToEntity(this, 24);
		}
		if(this.target != null && !this.target.isEntityAlive()) {
			this.target = null;
		}

		if(this.ticksExisted % 40 == 0) {
			this.setCourse();
		}
		this.approachPosition();

		if(this.attackCooldown > 0) this.attackCooldown--;
		if(this.target instanceof EntityLivingBase && this.attackCooldown <= 0 && this.getDistanceToEntity(this.target) < 20) {
			this.attackCooldown = 60 + rand.nextInt(40);
			fireShrapnel((EntityLivingBase) this.target);
		}
	}

	protected void setCourse() {
		if(this.target != null) {
			this.setWaypoint(
					(int) (this.target.posX + rand.nextGaussian() * 3),
					(int) (this.target.posY + 2 + rand.nextFloat() * 2),
					(int) (this.target.posZ + rand.nextGaussian() * 3));
		} else {
			int x = (int) Math.floor(posX + rand.nextGaussian() * 8);
			int z = (int) Math.floor(posZ + rand.nextGaussian() * 8);
			this.setWaypoint(x, this.worldObj.getHeightValue(x, z) + 3 + rand.nextInt(3), z);
		}
	}

	protected void approachPosition() {

		double dx = this.waypointX + 0.5 - this.posX;
		double dy = this.waypointY - this.posY;
		double dz = this.waypointZ + 0.5 - this.posZ;
		Vec3 delta = Vec3.createVectorHelper(dx, dy, dz);
		double len = delta.lengthVector();

		if(len > 1) {
			double speed = 0.4D;
			double vx = delta.xCoord * speed / len;
			double vy = delta.yCoord * speed / len;
			double vz = delta.zCoord * speed / len;
			this.motionX += (vx - this.motionX) * 0.12D;
			this.motionY += (vy - this.motionY) * 0.12D;
			this.motionZ += (vz - this.motionZ) * 0.12D;
		} else {
			this.motionX *= 0.8D;
			this.motionZ *= 0.8D;
			this.motionY = Math.sin(this.ticksExisted * 0.25) * 0.08D;
		}
	}

	protected void fireShrapnel(EntityLivingBase e) {

		EntityShrapnel shrapnel = new EntityShrapnel(worldObj, posX, posY + 0.5, posZ);
		shrapnel.setTrail(true);

		Vec3 dir = Vec3.createVectorHelper(e.posX - posX, (e.posY + e.height * 0.5) - (posY + 0.5), e.posZ - posZ);
		double len = dir.lengthVector();
		if(len < 0.1D) return;
		dir = Vec3.createVectorHelper(dir.xCoord / len, dir.yCoord / len, dir.zCoord / len);

		shrapnel.setThrowableHeading(dir.xCoord, dir.yCoord, dir.zCoord, 1.0F, 0.05F);
		worldObj.spawnEntityInWorld(shrapnel);
		this.swingItem();
	}

	public void setWaypoint(int x, int y, int z) {
		this.waypointX = x;
		this.waypointY = y;
		this.waypointZ = z;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isFireDamage() || damage == ModDamageSource.shrapnel) return new float[] {0F, 1F};
		return new float[] {0F, 0F};
	}

	@Override
	public void onDamageDealt(DamageSource damage, float amount) { }

	@Override
	protected void dropFewItems(boolean byPlayer, int looting) {
		super.dropFewItems(byPlayer, looting);
		this.entityDropItem(new ItemStack(Items.glowstone_dust, 1 + rand.nextInt(2) + looting), 0F);
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
	}
}
