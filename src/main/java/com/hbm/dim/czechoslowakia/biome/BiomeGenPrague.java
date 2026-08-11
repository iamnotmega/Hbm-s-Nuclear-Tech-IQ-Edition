package com.hbm.dim.czechoslowakia.biome;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.BiomeGenBaseCelestial;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.Random;

public class BiomeGenPrague extends BiomeGenBaseCelestial {
	public static final Height height = new Height(0.125F, 0.125F);
	public BiomeGenPrague(int id) {
		super(id);
		this.setBiomeName("Prague");
		this.theBiomeDecorator.generateLakes = false;
		this.setHeight(height);
		this.topBlock = ModBlocks.concrete;
		this.fillerBlock = ModBlocks.concrete;
	}

	@Override
	public void genTerrainBlocks(World world, Random random, Block[] blocks, byte[] metadata, int x, int z, double noise) {

	}
}
