package com.hbm.render.util;

import java.util.LinkedHashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.util.ResourceLocation;


@SideOnly(Side.CLIENT)
public class SuperSecretShader {
//SUPER SECRET DIGMA SHADER

	// ok but no its just a utility class that helps me use vanilla super secret shaders in stuff

	private static final Map<String, Integer> refCounts = new LinkedHashMap<String, Integer>();

	public static void apply(String shaderPath) {
		Integer count = refCounts.get(shaderPath);
		refCounts.put(shaderPath, (count == null ? 0 : count) + 1);
		reloadShader();
	}

	public static void remove(String shaderPath) {
		Integer count = refCounts.get(shaderPath);
		if(count != null) {
			if(count <= 1) refCounts.remove(shaderPath);
			else refCounts.put(shaderPath, count - 1);
		}
		reloadShader();
	}

	private static void reloadShader() {
		Minecraft mc = Minecraft.getMinecraft();
		if(mc.entityRenderer == null) return;

		if(mc.entityRenderer.theShaderGroup != null) {
			mc.entityRenderer.theShaderGroup.deleteShaderGroup();
			mc.entityRenderer.theShaderGroup = null;
		}

		if(!refCounts.isEmpty() && OpenGlHelper.shadersSupported) {
			String lastPath = null;
			for(String key : refCounts.keySet()) lastPath = key;

			try {
				mc.entityRenderer.theShaderGroup = new ShaderGroup(
					mc.getTextureManager(), mc.getResourceManager(),
					mc.getFramebuffer(), new ResourceLocation(lastPath)
				);
				mc.entityRenderer.theShaderGroup.createBindFramebuffers(
					mc.displayWidth, mc.displayHeight
				);
			} catch(Exception e) { }
		}
	}
}
