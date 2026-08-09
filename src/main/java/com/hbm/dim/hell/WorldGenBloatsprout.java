package com.hbm.dim.hell;

import java.util.Random;

import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenBloatsprout extends WorldGenAbstractTree {

	int offset;
	int smallest;
	int tallest;
	int xz;
	int y;
	Block logBlock;
	Block leavBlock;

	public WorldGenBloatsprout(boolean notify, int offset, int smallest, int tallest, int xz, int y, Block log, Block leaf) {
		super(notify);
		this.offset = offset;
		this.smallest = smallest;
		this.tallest = tallest;
		this.xz = xz;
		this.y = y;
		this.logBlock = log;
		this.leavBlock = leaf;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z) {
		int height = rand.nextInt(smallest) + tallest;

		if (y < 1 || y + height + 1 > 256) {
			return false;
		}

		Block blockBelow = world.getBlock(x, y - 1, z);
		if (blockBelow != Blocks.netherrack && blockBelow != Blocks.soul_sand && blockBelow != ModBlocks.glyphid_base && blockBelow != ModBlocks.nether_glyphid) {
			return false;
		}

		for (int i = 0; i < height; i++) {
			world.setBlock(x, y + i, z, logBlock, 0, 2);
		}

		int bulbStartY = y + height - offset;
		int bulbRadiusXz = xz;
		int bulbRadiusY = this.y;

		for (int dy = -bulbRadiusY; dy <= bulbRadiusY; dy++) {
			for (int dx = -bulbRadiusXz; dx <= bulbRadiusXz; dx++) {
				for (int dz = -bulbRadiusXz; dz <= bulbRadiusXz; dz++) {

					if (Math.pow(dx / (double)bulbRadiusXz, 2) + Math.pow(dy / (double)bulbRadiusY, 2) + Math.pow(dz / (double)bulbRadiusXz, 2) <= 1) {
						Block block = world.getBlock(x + dx, bulbStartY + dy, z + dz);
						if (block.isAir(world, x + dx, bulbStartY + dy, z + dz)) {
							world.setBlock(x + dx, bulbStartY + dy, z + dz, leavBlock, 8, 2);
						}
					}
				}
			}
		}

		return true;
	}

}
