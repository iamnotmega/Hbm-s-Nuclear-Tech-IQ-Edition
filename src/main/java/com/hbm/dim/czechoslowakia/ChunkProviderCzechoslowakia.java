package com.hbm.dim.czechoslowakia;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class ChunkProviderCzechoslowakia extends ChunkProviderCelestial {
	public ChunkProviderCzechoslowakia(World world, long seed) {
		super(world, seed);

		this.stoneBlock = Blocks.stone;
	}

	@Override
	protected BlockMetaBuffer getChunkPrimer(int x, int z) {
		BlockMetaBuffer buf = new BlockMetaBuffer();
		this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, x * 16, z * 16, 16, 16);
		this.generateBlocks(x, z, buf.blocks);
		return buf;
	}

	@Override
	protected void generateBlocks(int x, int z, Block[] blocks) {
		for (int i = 0; i < 256; i++) {
			blocks[i * 256] = Blocks.bedrock;
			for (int j = 1; j < 64; j++) {
				blocks[i * 256 + j] = ModBlocks.concrete_smooth;
			}
		}

		//TODO: procedurally generate commieblocks
	}
}
