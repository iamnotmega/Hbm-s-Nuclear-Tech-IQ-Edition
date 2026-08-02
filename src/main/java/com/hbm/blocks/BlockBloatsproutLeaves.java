package com.hbm.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockLeaves;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockBloatsproutLeaves extends BlockLeaves {

	public BlockBloatsproutLeaves() {
		super();
		this.field_150121_P = true;
		this.setStepSound(ModSoundType.mod("plantsquish", 1.0F, 1.0F));
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.bloatsprout_sapling);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	protected boolean canSilkHarvest() {
		return false;
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance, int fortune) {
		super.dropBlockAsItemWithChance(world, x, y, z, metadata, chance, fortune);

		if(!world.isRemote) {
			if(world.rand.nextFloat() < 0.5F) {
				this.dropBlockAsItem(world, x, y, z, new ItemStack(ModBlocks.bloatsprout_sapling));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor() {
		return 0xFFFFFF;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return 0xFFFFFF;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return 0xFFFFFF;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return this.blockIcon;
	}

	@Override
	public String[] func_150125_e() {
		return null;
	}
}