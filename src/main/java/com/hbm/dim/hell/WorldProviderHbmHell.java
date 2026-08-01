package com.hbm.dim.hell;

import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderHbmHell extends WorldProviderHell {
// bro this song is so fire
	@Override
	public void registerWorldChunkManager() {
		this.worldChunkMgr = new WorldChunkManagerHbmHell(this.worldObj);
		this.isHellWorld = true;
		this.hasNoSky = true;
		this.dimensionId = -1;
	}

	// literally almost the same as bop
	// if we want end in the future
	// it ownt fucking HAPEN
	// CAUSE IT DOESNT UKFICNG/
	//ERRJEXNRKWNJKNWJLKQNRJKNJKQWELXNLJKWNXJRLKNKLJQNEJLKRJWKL WORK!
	// i hope this doesnt break anyhting thign

	@Override
	public IChunkProvider createChunkGenerator() {
		return new ChunkProviderHbmHell(this.worldObj, this.worldObj.getSeed());
	}
}
