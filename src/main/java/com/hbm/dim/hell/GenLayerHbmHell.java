package com.hbm.dim.hell;

import java.util.ArrayList;
import java.util.List;

import com.hbm.world.ModBiomes;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerFuzzyZoom;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.IntCache;
import net.minecraftforge.common.BiomeManager.BiomeEntry;

public abstract class GenLayerHbmHell extends GenLayer {

	private GenLayerHbmHell(long seed) {
		super(seed);
	}

	public static GenLayer[] makeLayers(long seed, WorldType worldType) {
		int biomeSize = 2;

		GenLayer layer = new HellNoise(1L);
		layer = new GenLayerFuzzyZoom(2000L, layer);
		for(int i = 0; i < 3; i++) layer = new GenLayerZoom(2001L + i, layer);
		layer = new GenLayerZoom(2001L, layer);
		layer = new HellBiomes(200L, layer);
		for(int i = 0; i < biomeSize; i++) layer = new GenLayerZoom(2002L + i, layer);
		GenLayer voronoi = new GenLayerVoronoiZoom(10L, layer);

		layer.initWorldGenSeed(seed);
		voronoi.initWorldGenSeed(seed);

		return new GenLayer[] { layer, voronoi };
	}

	 // noise from mc source, i would sya its poorly copied but heh im better than bob and have higher self esteem (tamiq)
	private static class HellNoise extends GenLayerHbmHell {
		public HellNoise(long seed) { super(seed); }
		@Override
		public int[] getInts(int x, int z, int w, int h) {
			int[] result = IntCache.getIntCache(w * h);
			for(int i = 0; i < w * h; i++) result[i] = this.nextInt(Short.MAX_VALUE);
			return result;
		}
	}

          // biome map
	private static class HellBiomes extends GenLayerHbmHell {
		private List<BiomeEntry> allowedBiomes;

		public HellBiomes(long seed, GenLayer parent) {
			super(seed);
			this.parent = parent;
			this.allowedBiomes = new ArrayList<>();
			this.allowedBiomes.add(new BiomeEntry(BiomeGenBase.hell, 10));
			this.allowedBiomes.addAll(ModBiomes.netherBiomes);
		}

		@Override
		public int[] getInts(int x, int z, int w, int h) {
			int[] parentVals = this.parent.getInts(x, z, w, h);
			int[] result = IntCache.getIntCache(w * h);
			for(int i = 0; i < w * h; i++) {
				this.initChunkSeed(x + (i % w), z + (i / w));
				int totalWeight = WeightedRandom.getTotalWeight(allowedBiomes);
				result[i] = ((BiomeEntry) WeightedRandom.getItem(allowedBiomes, this.nextInt(totalWeight))).biome.biomeID;
			}
			return result;
		}
	}
}
