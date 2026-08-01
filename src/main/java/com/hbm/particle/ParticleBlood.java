package com.hbm.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;

public class ParticleBlood extends EntitySmokeFX {

	float colorMod = 1.0F;

	public ParticleBlood(World world, double x, double y, double z, double mX, double mY, double mZ) {
		super(world, x, y, z, mX, mY, mZ, 6.5F);
		this.particleRed = 0.7F;
		this.particleGreen = 0.02F;
		this.particleBlue = 0.02F;
		this.colorMod = 0.8F + rand.nextFloat() * 0.2F;
		this.particleRed *= colorMod;
		this.particleGreen *= colorMod;
		this.particleBlue *= colorMod;
		this.noClip = true;
		this.particleMaxAge = 30 + rand.nextInt(13);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}
}
