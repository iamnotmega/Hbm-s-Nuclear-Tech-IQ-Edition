package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;

import com.hbm.blocks.BlockEnumMulti;
import com.hbm.blocks.BlockEnums;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ItemEnums.EnumChunkType;
import com.hbm.items.ModItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockResourceStone extends BlockEnumMulti {

	public BlockResourceStone() {
		super(Material.rock, BlockEnums.EnumStoneType.class, true, true);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float chance, int fortune) {

		if(meta == BlockEnums.EnumStoneType.ASBESTOS.ordinal()) {
			world.setBlock(x, y, z, ModBlocks.gas_asbestos);
		}

		if(meta == BlockEnums.EnumStoneType.ANTHRACITE.ordinal() || meta == BlockEnums.EnumStoneType.FLAMING_ANTHRACITE.ordinal()) {
			world.setBlock(x, y, z, ModBlocks.gas_coal);
		}

		super.dropBlockAsItemWithChance(world, x, y, z, meta, chance, fortune);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int meta, int fortune) {

		if(meta == BlockEnums.EnumStoneType.MALACHITE.ordinal()) {
			ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
			ret.add(DictFrame.fromOne(ModItems.chunk_ore, EnumChunkType.MALACHITE, 3 + fortune + world.rand.nextInt(fortune + 2)));
			return ret;
		}

		if(meta == BlockEnums.EnumStoneType.ANTHRACITE.ordinal()) {
			ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
			ret.add(DictFrame.fromOne(ModItems.chunk_ore, EnumChunkType.ANTHRACITE, 1 + world.rand.nextInt(2 + fortune)));
			return ret;
		}

		if(meta == BlockEnums.EnumStoneType.FLAMING_ANTHRACITE.ordinal()) {
			ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
			ret.add(DictFrame.fromOne(ModItems.chunk_ore, EnumChunkType.FLAMING_ANTHRACITE, 1 + world.rand.nextInt(2 + fortune)));
			return ret;
		}

		if(meta == BlockEnums.EnumStoneType.COBALTITE.ordinal()) {
			ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
			ret.add(new ItemStack(ModItems.fragment_cobalt, 2 + world.rand.nextInt(2 + fortune)));
			return ret;
		}

		return super.getDrops(world, x, y, z, meta, fortune);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == BlockEnums.EnumStoneType.FLAMING_ANTHRACITE.ordinal()) return 10;
		return super.getLightValue(world, x, y, z);
	}

	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		if(world.getBlockMetadata(x, y, z) == BlockEnums.EnumStoneType.FLAMING_ANTHRACITE.ordinal()) {
			entity.setFire(3);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		super.randomDisplayTick(world, x, y, z, rand);

		if(world.getBlockMetadata(x, y, z) != BlockEnums.EnumStoneType.FLAMING_ANTHRACITE.ordinal()) return;

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {

			if(dir == ForgeDirection.DOWN)
				continue;

			if(world.getBlock(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ).getMaterial() == Material.air) {

				double ix = x + 0.5F + dir.offsetX + rand.nextDouble() - 0.5D;
				double iy = y + 0.5F + dir.offsetY + rand.nextDouble() - 0.5D;
				double iz = z + 0.5F + dir.offsetZ + rand.nextDouble() - 0.5D;

				if(dir.offsetX != 0)
					ix = x + 0.5F + dir.offsetX * 0.5 + rand.nextDouble() * 0.125 * dir.offsetX;
				if(dir.offsetY != 0)
					iy = y + 0.5F + dir.offsetY * 0.5 + rand.nextDouble() * 0.125 * dir.offsetY;
				if(dir.offsetZ != 0)
					iz = z + 0.5F + dir.offsetZ * 0.5 + rand.nextDouble() * 0.125 * dir.offsetZ;

				world.spawnParticle("flame", ix, iy, iz, 0.0, 0.0, 0.0);
				world.spawnParticle("smoke", ix, iy, iz, 0.0, 0.0, 0.0);
				world.spawnParticle("smoke", ix, iy, iz, 0.0, 0.1, 0.0);
			}
		}
	}
}
