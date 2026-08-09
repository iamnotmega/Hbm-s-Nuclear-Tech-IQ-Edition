package com.hbm.render.entity.mob;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderBloatwisp extends Render {

	public RenderBloatwisp() {
		this.shadowOpaque = 0.0F;
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f0, float f1) { }

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}
}
