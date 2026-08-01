package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.deco.TileEntityCyst;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCyst extends BlockGeysir {

	@SideOnly(Side.CLIENT)
	private IIcon iconTop;

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		this.iconTop = iconRegister.registerIcon(RefStrings.MODID + ":boil_top");
		this.blockIcon = iconRegister.registerIcon(RefStrings.MODID + ":glyphid_base");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return side == 1 ? this.iconTop : this.blockIcon;
	}

	public BlockCyst(Material material) {
		super(material);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCyst();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
		// the base blood thing appart from the big blodo effect
		if(rand.nextInt(3) == 0) {
			world.spawnParticle("reddust", x + 0.3 + rand.nextDouble() * 0.4, y + 1.0625F, z + 0.3 + rand.nextDouble() * 0.4, 0.7, 0.05, 0.05);
		}
	}
}
