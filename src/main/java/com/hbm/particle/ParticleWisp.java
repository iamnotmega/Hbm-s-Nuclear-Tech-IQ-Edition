package com.hbm.particle;

import java.awt.Color;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class ParticleWisp extends EntitySmokeFX {

	private static final Random RAND = new Random();

	public ParticleWisp(World world, double x, double y, double z) {
		super(world, x, y, z, 0, 0, 0, 4.5F + RAND.nextFloat() * 2.0F);
		this.particleScale = 4.5F + rand.nextFloat() * 2.0F;
		this.noClip = true;
		this.particleMaxAge = 30 + rand.nextInt(13);
		this.motionX = (rand.nextDouble() - 0.5) * 0.02D;
		this.motionY = 0.03D + rand.nextDouble() * 0.04D;
		this.motionZ = (rand.nextDouble() - 0.5) * 0.02D;
		updateColor();
	}

	@Override
	public void onUpdate() {
		double prevMo = this.motionY;
		super.onUpdate();
		this.motionY = prevMo;
		this.motionX *= 0.75D;
		this.motionY += 0.005D;
		this.motionZ *= 0.75D;
		updateColor();
	}

	protected void updateColor() {
		float time = (float) this.particleAge / (float) this.particleMaxAge;

		Color color = Color.getHSBColor(Math.max((68 - time * 30) / 360F, 0.0F), 1 - time * 0.25F, 1 - time * 0.5F);

		this.particleRed = color.getRed() / 255F;
		this.particleGreen = color.getGreen() / 255F;
		this.particleBlue = color.getBlue() / 255F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_) {
		return 15728880;
	}
}
