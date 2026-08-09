package com.hbm.entity.mob.glyphid;

import com.hbm.explosion.vanillant.ExplosionVNT;
import com.hbm.explosion.vanillant.standard.BlockAllocatorStandard;
import com.hbm.explosion.vanillant.standard.BlockProcessorStandard;
import com.hbm.explosion.vanillant.standard.EntityProcessorStandard;
import com.hbm.explosion.vanillant.standard.ExplosionEffectStandard;
import com.hbm.explosion.vanillant.standard.PlayerProcessorStandard;
import com.hbm.main.ResourceManager;
import com.hbm.items.ModItems;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGlyphidNetherBrawler extends EntityGlyphidBrawler {

	public EntityGlyphidNetherBrawler(World world) {
		super(world);
		this.isImmuneToFire = true;
		this.netherGlyphid = true;
	}

	@Override
	public ResourceLocation getSkin() {
		return ResourceManager.glyphid_nether_brawler_tex;
	}

	@Override
	public float[] getCurrentDTDR(DamageSource damage, float amount, float pierceDT, float pierce) {
		if(damage.isFireDamage() || damage.isExplosion() || damage.isProjectile()) return new float[] {0F, 1F};
		return super.getCurrentDTDR(damage, amount, pierceDT, pierce);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source.isExplosion() || source.isProjectile()) return false;
		if(!worldObj.isRemote && !(source.getEntity() instanceof EntityGlyphid)) {
			ExplosionVNT vnt = new ExplosionVNT(worldObj, posX, posY, posZ, 2.5F, this);
			vnt.setBlockAllocator(new BlockAllocatorStandard(3));
			vnt.setBlockProcessor(new BlockProcessorStandard().setNoDrop());
			vnt.setEntityProcessor(new EntityProcessorStandard());
			vnt.setPlayerProcessor(new PlayerProcessorStandard());
			vnt.setSFX(new ExplosionEffectStandard());
			vnt.explode();
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	protected Item getMeatItem() {
		return isBurning() ? ModItems.glyphid_meat_nether_grilled : ModItems.glyphid_meat_nether;
	}
}
