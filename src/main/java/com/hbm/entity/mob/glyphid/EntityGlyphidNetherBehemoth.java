package com.hbm.entity.mob.glyphid;

import com.hbm.entity.effect.EntityMist;
import com.hbm.entity.projectile.EntityChemical;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidNetherBehemoth extends EntityGlyphidBehemoth {

	public EntityGlyphidNetherBehemoth(World world) {
		super(world);
		this.isImmuneToFire = true;
		this.netherGlyphid = true;
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nether_behemoth_tex;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isFireDamage()) return new float[] {0F, 1F};
		return super.getCurrentDTDR(damage, amount, pierceDT, pierce);
	}

	@Override
	public void acidAttack() {
		if(!worldObj.isRemote && entityToAttack instanceof EntityLivingBase && this.getDistanceToEntity(entityToAttack) < 20) {
			this.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 2 * 20, 6));
			EntityChemical chem = new EntityChemical(worldObj, this, 0, 0, 0);
			chem.setFluid(Fluids.BURNING_BLOATMUSK);
			worldObj.spawnEntityInWorld(chem);
		}
	}

	@Override
	protected void onDeathSpawn() {
		EntityMist mist = new EntityMist(worldObj);
		mist.setType(Fluids.MUSTY_BLOATMUSK);
		mist.setPosition(posX, posY, posZ);
		mist.setArea(10, 4);
		mist.setDuration(120);
		worldObj.spawnEntityInWorld(mist);
	}

	@Override
	protected void dropGland() {
		this.entityDropItem(new ItemStack(ModItems.glyphid_gland, 1, Fluids.BLOATMUSK.getID()), 1);
	}

	@Override
	protected Item getMeatItem() {
		return isBurning() ? ModItems.glyphid_meat_nether_grilled : ModItems.glyphid_meat_nether;
	}
}
