package com.hbm.dim.hell;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

// @author iris-lgtm
// how the fuck do you do this
// helper methods for nether biomes
public class NetherBiomeHelper {

	public static int getFloorHeight(World world, int x, int z) {
		for(int y = 100; y > 0; y--) {
			Block b = world.getBlock(x, y, z);
			if(b == ModBlocks.bloatsprout_leaves) continue;
			if(b != Blocks.air)
				return y + 1;
		}
		return -1;
	}

	public static boolean isSolidFloor(World world, int x, int z) {
		int y = getFloorHeight(world, x, z);
		if(y <= 0) return false;
		Block b = world.getBlock(x, y - 1, z);
		return b == Blocks.netherrack || b == Blocks.soul_sand || b == ModBlocks.glyphid_base || b == ModBlocks.nether_glyphid;
	}

	// used for things which do stack on caps
	public static boolean isSolidMass(World world, int x, int y, int z) {
		return !world.isAirBlock(x, y, z) && !world.isAirBlock(x, y + 1, z);
	}

	// used for things that dont stack on things like caps
	public static boolean isThickFloor(World world, int x, int z) {
		int y = getFloorHeight(world, x, z);
		if(y <= 2) return false;
		for(int dy = 1; dy <= 3; dy++) {
			Block b = world.getBlock(x, y - dy, z);
			if(b != Blocks.netherrack && b != Blocks.soul_sand && b != ModBlocks.glyphid_base && b != ModBlocks.nether_glyphid) return false;
		}
		return true;
	}

	public static boolean isLavaNearby(World world, int x, int y, int z) {
		for(int dy = 1; dy <= 5; dy++) {
			Block b = world.getBlock(x, y - dy, z);
			if(b == Blocks.lava) return true;
			if(b != Blocks.netherrack && b != ModBlocks.glyphid_base && b != ModBlocks.nether_glyphid && b != Blocks.soul_sand) return false;
		}
		return false;
	}

	public static boolean isFlatArea(World world, int x, int z, int r) {
		int h = getFloorHeight(world, x, z);
		for(int dx = -r; dx <= r; dx++)
			for(int dz = -r; dz <= r; dz++)
				if(Math.abs(getFloorHeight(world, x + dx, z + dz) - h) > 1)
					return false;
		return true;
	}
}
