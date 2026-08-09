package com.hbm.blocks.generic;

import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.render.block.RenderBlockMultipass;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class BlockArteryOil extends BlockOreFluid {

	@SideOnly(Side.CLIENT)
	private IIcon bedrockIcon;

	public BlockArteryOil(Material mat, Block empty, ReserveType type) {
		super(mat, empty, type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		this.bedrockIcon = reg.registerIcon("bedrock");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		if(RenderBlockMultipass.currentPass == 0) {
			return bedrockIcon;
		}
		return blockIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(RenderBlockMultipass.currentPass == 0) {
			return bedrockIcon;
		}
		return blockIcon;
	}

	@Override
	public int getSubCount() {
		return 1;
	}

	@Override
	public FluidType getPrimaryFluid(int meta) {
		return Fluids.SATANS_BLOOD;
	}

	@Override
	public FluidType getSecondaryFluid(int meta) {
		return Fluids.ARGENT_BLOAT_PUS;
	}

	@Override
	public int getPrimaryFluidAmount(int meta) {
		return 10_000;
	}

	@Override
	public int getSecondaryFluidAmount(int meta) {
		return 5_000;
	}

	@Override
	public int getBlockFluidAmount(int meta) {
		return 250;
	}

}
