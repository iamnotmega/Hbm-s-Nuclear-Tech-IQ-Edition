package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockNetherGlyphid extends BlockEnumMulti {

	public BlockNetherGlyphid(Material mat) {
		super(mat, EnumNetherGlyphidType.class, true, true);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return null;
	}

	public static enum EnumNetherGlyphidType {
		CREVASSE,
		GRUB,
		OVERTAKEN,
		HIVE,
		BLOATWISP;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(world.getBlockMetadata(x, y, z) == EnumNetherGlyphidType.BLOATWISP.ordinal() && world.getBlock(x, y + 1, z) == Blocks.fire) {
			world.setBlock(x, y + 1, z, ModBlocks.bloatfire, world.getBlockMetadata(x, y + 1, z), 2);
		}
	}
}
