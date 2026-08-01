package com.hbm.dim.hell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class WorldChunkManagerHbmHell extends WorldChunkManager {
// tam iq
	// this is MOSTLY vanilla code
	// or thats atleast what big bop wants yout o think, little twink..


	private GenLayer genBiomes;
	private GenLayer biomeIndexLayer;
	private BiomeCache biomeCache;
	private List<BiomeGenBase> biomesToSpawnIn;

	protected WorldChunkManagerHbmHell() {
		this.biomeCache = new BiomeCache(this);
		this.biomesToSpawnIn = new ArrayList<>();
	}

	public WorldChunkManagerHbmHell(long seed, WorldType worldType) {
		this();
		GenLayer[] layers = GenLayerHbmHell.makeLayers(seed, worldType);
		this.genBiomes = layers[0];
		this.biomeIndexLayer = layers[1];
	}

	public WorldChunkManagerHbmHell(World world) {
		this(world.getSeed(), world.getWorldInfo().getTerrainType());
	}

	@Override
	public List<BiomeGenBase> getBiomesToSpawnIn() {
		return this.biomesToSpawnIn;
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int x, int z) {
		return this.biomeCache.getBiomeGenAt(x, z);
	}

	@Override
	public float[] getRainfall(float[] reuse, int x, int z, int w, int h) {
		IntCache.resetIntCache();
		if(reuse == null || reuse.length < w * h) reuse = new float[w * h];
		int[] ids = this.biomeIndexLayer.getInts(x, z, w, h);
		for(int i = 0; i < w * h; i++) {
			float r = BiomeGenBase.getBiomeGenArray()[ids[i]].getIntRainfall() / 65536.0F;
			if(r > 1.0F) r = 1.0F;
			reuse[i] = r;
		}
		return reuse;
	}

	@Override
	public float getTemperatureAtHeight(float temperature, int y) {
		return temperature;
	}

	public float[] getTemperatures(float[] reuse, int x, int z, int w, int h) {
		IntCache.resetIntCache();
		if(reuse == null || reuse.length < w * h) reuse = new float[w * h];
		int[] ids = this.biomeIndexLayer.getInts(x, z, w, h);
		for(int i = 0; i < w * h; i++) {
			float t = BiomeGenBase.getBiomeGenArray()[ids[i]].temperature / 65536.0F;
			if(t > 1.0F) t = 1.0F;
			reuse[i] = t;
		}
		return reuse;
	}

	@Override
	public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] reuse, int x, int z, int w, int h) {
		IntCache.resetIntCache();
		if(reuse == null || reuse.length < w * h) reuse = new BiomeGenBase[w * h];
		int[] ids = this.genBiomes.getInts(x, z, w, h);
		for(int i = 0; i < w * h; i++) reuse[i] = BiomeGenBase.getBiomeGenArray()[ids[i]];
		return reuse;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] reuse, int x, int z, int w, int h) {
		return this.getBiomeGenAt(reuse, x, z, w, h, true);
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] reuse, int x, int z, int w, int h, boolean cache) {
		IntCache.resetIntCache();
		if(reuse == null || reuse.length < w * h) reuse = new BiomeGenBase[w * h];
		if(cache && w == 16 && h == 16 && (x & 15) == 0 && (z & 15) == 0) {
			BiomeGenBase[] cached = this.biomeCache.getCachedBiomes(x, z);
			System.arraycopy(cached, 0, reuse, 0, w * h);
			return reuse;
		}
		int[] ids = this.biomeIndexLayer.getInts(x, z, w, h);
			for(int i = 0; i < w * h; i++) reuse[i] = BiomeGenBase.getBiomeGenArray()[ids[i]];
			return reuse;
		}
    // Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.
	// Called a nugget, behaves like 125mB of liquid metal. Fine.

	@Override
	@SuppressWarnings("rawtypes")
	public boolean areBiomesViable(int x, int z, int radius, List allowed) {
		IntCache.resetIntCache();
		int minX = x - radius >> 2;
		int minZ = z - radius >> 2;
		int maxX = x + radius >> 2;
		int maxZ = z + radius >> 2;
		int w = maxX - minX + 1;
		int h = maxZ - minZ + 1;
		int[] ids = this.genBiomes.getInts(minX, minZ, w, h);
		for(int i = 0; i < w * h; i++) if(!allowed.contains(BiomeGenBase.getBiomeGenArray()[ids[i]])) return false;
		return true;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public ChunkPosition findBiomePosition(int x, int z, int radius, List allowed, Random rand) {
		IntCache.resetIntCache();
		int minX = x - radius >> 2;
		int minZ = z - radius >> 2;
		int maxX = x + radius >> 2;
		int maxZ = z + radius >> 2;
		int w = maxX - minX + 1;
		int h = maxZ - minZ + 1;
		int[] ids = this.genBiomes.getInts(minX, minZ, w, h);
		ChunkPosition pos = null;
		int count = 0;
		for(int i = 0; i < w * h; i++) {
			int bx = minX + i % w << 2;
			int bz = minZ + i / w << 2;
			if(allowed.contains(BiomeGenBase.getBiomeGenArray()[ids[i]]) && (pos == null || rand.nextInt(count + 1) == 0)) {
				pos = new ChunkPosition(bx, 0, bz);
				count++;
			}
		}
		return pos;
	}

	@Override
	public void cleanupCache() {
		this.biomeCache.cleanupCache();
	}
}
