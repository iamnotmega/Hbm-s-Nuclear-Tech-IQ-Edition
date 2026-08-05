package com.hbm.world.feature;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.BlockFleshPlant.EnumFleshPlantType;
import com.hbm.blocks.generic.BlockNetherGlyphid.EnumNetherGlyphidType;
import com.hbm.util.LootGenerator;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.world.World;

public class GlyphidHiveNether extends GlyphidHive {


	public static final int[][][] schematicLarge = new int[][][] {
		{
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,4,1,0,0,0,0,0},
			{0,0,0,0,1,1,1,1,0,0,0,0},
			{0,0,0,0,1,1,1,1,0,0,0,0},
			{0,0,0,0,0,1,4,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
		},
		{
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,1,1,1,0,0,0,0},
			{0,0,0,1,4,4,0,1,1,0,0,0},
			{0,0,0,1,0,0,0,0,1,0,0,0},
			{0,0,0,1,0,0,0,0,1,0,0,0},
			{0,0,0,1,1,0,4,4,1,0,0,0},
			{0,0,0,0,1,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
		},
		{
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,2,1,1,0,0,0,0},
			{0,0,0,2,1,0,0,1,2,0,0,0},
			{0,0,1,1,0,0,0,0,1,1,0,0},
			{0,0,2,0,0,0,0,0,0,2,0,0},
			{0,0,1,0,0,0,0,0,0,1,0,0},
			{0,0,1,1,0,0,0,0,1,1,0,0},
			{0,0,0,2,1,0,0,1,2,0,0,0},
			{0,0,0,0,1,2,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
		},
		{
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,0,1,1,1,1,0,0,0,0},
			{0,0,2,1,2,0,0,2,1,2,0,0},
			{0,0,1,0,0,0,0,0,0,1,0,0},
			{0,1,1,0,0,0,0,0,0,1,1,0},
			{0,1,0,0,0,0,0,0,0,0,1,0},
			{0,1,0,0,0,0,0,0,0,0,1,0},
			{0,1,1,0,0,0,0,0,0,1,1,0},
			{0,0,1,0,0,0,0,0,0,1,0,0},
			{0,0,2,1,2,0,0,2,1,2,0,0},
			{0,0,0,0,1,1,1,1,0,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
		},
		{
			{0,0,0,0,0,0,0,0,0,0,0,0},
			{0,0,0,1,1,1,1,1,1,0,0,0},
			{0,0,1,1,3,3,3,3,1,1,0,0},
			{0,1,1,3,3,3,3,3,3,1,1,0},
			{0,1,3,3,3,3,3,3,3,3,1,0},
			{0,1,3,3,3,3,3,3,3,3,1,0},
			{0,1,3,3,3,3,3,3,3,3,1,0},
			{0,1,3,3,3,3,3,3,3,3,1,0},
			{0,1,1,3,3,3,3,3,3,1,1,0},
			{0,0,1,1,3,3,3,3,1,1,0,0},
			{0,0,0,1,1,1,1,1,1,0,0,0},
			{0,0,0,0,0,0,0,0,0,0,0,0},
		},
		{
			{0,0,0,1,1,1,1,1,1,0,0,0},
			{0,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,0},
			{1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1},
			{1,1,1,1,1,1,1,1,1,1,1,1},
			{0,1,1,1,1,1,1,1,1,1,1,0},
			{0,1,1,1,1,1,1,1,1,1,1,0},
			{0,0,0,1,1,1,1,1,1,0,0,0},
		},
	};

	public static void generateLarge(World world, int x, int y, int z, Random rand) {

		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 6; j++) {
				for(int k = 0; k < 12; k++) {

					int block = schematicLarge[5 - j][i][k];
					int iX = x + i - 6;
					int iY = y + j - 2;
					int iZ = z + k - 6;

					switch(block) {
					case 1:
						placeWall(world, iX, iY, iZ, rand);
						break;
					case 2:
						if(rand.nextInt(3) == 0) {
							world.setBlock(iX, iY, iZ, ModBlocks.glyphid_spawner_nether, 0, 2);
						} else {
							placeWall(world, iX, iY, iZ, rand);
						}
						break;
					case 3:
						int r = rand.nextInt(3);
						if(r == 0) {
							world.setBlock(iX, iY, iZ, Blocks.skull, 1, 3);
							TileEntitySkull skull = (TileEntitySkull) world.getTileEntity(iX, iY, iZ);
							if(skull != null) skull.func_145903_a(rand.nextInt(16));
						} else if(r == 1) {
							world.setBlock(iX, iY, iZ, ModBlocks.deco_loot, 0, 2);
							LootGenerator.lootBones(world, iX, iY, iZ);
						} else {
							world.setBlock(iX, iY, iZ, ModBlocks.deco_loot, 0, 2);
							LootGenerator.lootNetherHive(world, iX, iY, iZ);
						}
						break;
					case 4:
						world.setBlock(iX, iY, iZ, ModBlocks.cyst, 0, 2);
						break;
					}
				}
			}
		}

		for(int i = 0; i < 12; i++) {
			for(int j = 0; j < 6; j++) {
				for(int k = 0; k < 12; k++) {
					if(schematicLarge[5 - j][i][k] != 0 && rand.nextInt(4) == 0) {
						int iX = x + i - 6;
						int iY = y + j - 2;
						int iZ = z + k - 6;
						Block below = world.getBlock(iX, iY, iZ);
						if((below == ModBlocks.nether_glyphid || below == ModBlocks.glyphid_base) && world.getBlock(iX, iY + 1, iZ).isAir(world, iX, iY + 1, iZ)) {
							world.setBlock(iX, iY + 1, iZ, ModBlocks.plant_flesh, rand.nextInt(EnumFleshPlantType.values().length), 2);
						}
					}
				}
			}
		}
	}

	private static void placeWall(World world, int x, int y, int z, Random rand) {

		int r = rand.nextInt(60);
		if(r < 25) {
			world.setBlock(x, y, z, ModBlocks.nether_glyphid, EnumNetherGlyphidType.HIVE.ordinal(), 2);
		} else if(r < 40) {
			world.setBlock(x, y, z, ModBlocks.nether_glyphid, EnumNetherGlyphidType.OVERTAKEN.ordinal(), 2);
		} else if(r < 50) {
			world.setBlock(x, y, z, ModBlocks.nether_glyphid, EnumNetherGlyphidType.CREVASSE.ordinal(), 2);
		} else {
			world.setBlock(x, y, z, ModBlocks.nether_glyphid, EnumNetherGlyphidType.GRUB.ordinal(), 2);
		}
	}
}
