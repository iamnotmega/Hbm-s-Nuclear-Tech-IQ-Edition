package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;

import com.hbm.tileentity.machine.TileEntityDriveRack;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class MachineDriveRack extends BlockDummyable {

	public MachineDriveRack(Material mat) { super(mat); }

	public TileEntityDriveRack createNewTileEntity(World world, int meta) {
		return new TileEntityDriveRack();
	}

	@Override
	public int[] getDimensions() { return new int[] {1, 0, 0, 0, 0, 1}; }

	@Override
	public int getOffset() { return 0; }
}
