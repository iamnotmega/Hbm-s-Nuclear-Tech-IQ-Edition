package com.hbm.entity.mob.glyphid;

import com.hbm.entity.effect.EntityMist;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidNetherBrenda extends EntityGlyphidBrenda {

	public EntityGlyphidNetherBrenda(World world) {
		super(world);
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nether_brenda_tex;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isFireDamage() || damage.isExplosion() || damage.isProjectile()) return new float[] {0F, 1F};
		return super.getCurrentDTDR(damage, amount, pierceDT, pierce);
	}

	@Override
	protected void onDeathSpawn() {
		EntityMist mist = new EntityMist(worldObj);
		mist.setType(Fluids.MUSKY_PHEROMONE);
		mist.setPosition(posX, posY, posZ);
		mist.setArea(14, 6);
		mist.setDuration(80);
		worldObj.spawnEntityInWorld(mist);
		for(int i = 0; i < 12; ++i) {
			EntityGlyphidNether glyphid = new EntityGlyphidNether(worldObj);
			glyphid.setLocationAndAngles(this.posX, this.posY + 0.5D, this.posZ, rand.nextFloat() * 360.0F, 0.0F);
			this.worldObj.spawnEntityInWorld(glyphid);
			glyphid.moveEntity(rand.nextGaussian(), 0, rand.nextGaussian());
		}
	}

	@Override
	protected void dropGland() {
		if(rand.nextInt(3) == 0) this.entityDropItem(new ItemStack(ModItems.glyphid_gland, 1, Fluids.MUSKY_PHEROMONE.getID()), 1);
	}

	@Override
	protected Item getMeatItem() {
		return isBurning() ? ModItems.glyphid_meat_nether_grilled : ModItems.glyphid_meat_nether;
	}
}
