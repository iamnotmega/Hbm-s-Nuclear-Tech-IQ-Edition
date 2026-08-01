package com.hbm.blocks.generic;

import java.util.Locale;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockFleshPlant extends BlockEnumMulti {

	public BlockFleshPlant() {
		super(Material.plants, EnumFleshPlantType.class, true, true);
	}

	public static enum EnumFleshPlantType {
		TENDON,
		PEEPING;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
	}

	protected boolean canPlaceBlockOn(Block block) {
		return block == Blocks.netherrack || block == Blocks.soul_sand || block == ModBlocks.glyphid_base || block == ModBlocks.nether_glyphid;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		super.onNeighborBlockChange(world, x, y, z, block);
		this.checkAndDropBlock(world, x, y, z);
	}

	protected void checkAndDropBlock(World world, int x, int y, int z) {
		if(!this.canBlockStay(world, x, y, z)) {
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlock(x, y, z, Blocks.air, 0, 2);
		}
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		return canPlaceBlockOn(world.getBlock(x, y - 1, z));
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return null;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return 1;
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return super.getItemDropped(meta, rand, fortune);
	}

	@Override
	public String getTextureMultiName(Enum num) {
		switch((EnumFleshPlantType) num) {
		case TENDON:  return RefStrings.MODID + ":tendonweed";
		case PEEPING: return RefStrings.MODID + ":tendonweed_eye";
		}
		return super.getTextureMultiName(num);
	}

	@Override
	public String getUnlocalizedMultiName(Enum num) {
		return super.getUnlocalizedName() + "." + num.name().toLowerCase(Locale.US);
	}
}
