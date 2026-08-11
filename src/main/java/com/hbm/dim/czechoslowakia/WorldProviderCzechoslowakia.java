package com.hbm.dim.czechoslowakia;

import com.hbm.config.SpaceConfig;
import com.hbm.dim.WorldProviderCelestial;
import com.hbm.dim.thatmo.BiomeGenThatmo;
import net.minecraft.world.biome.WorldChunkManagerHell;

public class WorldProviderCzechoslowakia extends WorldProviderCelestial {
	public WorldProviderCzechoslowakia() {}

	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHell(new BiomeGenThatmo(SpaceConfig.thatmoBiome), this.dimensionId);
	}

	@Override
	public String getDimensionName() {
		return "Czechoslowakia";
	}
}
