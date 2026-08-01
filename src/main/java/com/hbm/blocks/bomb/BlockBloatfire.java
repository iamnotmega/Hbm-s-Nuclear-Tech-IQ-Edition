package com.hbm.blocks.bomb;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockFire;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBloatfire extends BlockFire {

	private IIcon icon;

	public BlockBloatfire() {
		super();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icon = register.registerIcon(this.getTextureName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getFireIcon(int i) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon;
	}
  // poorly copied from balefire

	// this is actually poorly copied though
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if(world.getGameRules().getGameRuleBooleanValue("doFireTick")) {

			if(!this.canPlaceBlockAt(world, x, y, z)) {
				world.setBlockToAir(x, y, z);
			}

			int meta = world.getBlockMetadata(x, y, z);

			if(meta < 15) world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world) + rand.nextInt(10));

			if(meta < 15) {
				this.tryCatchFire(world, x + 1, y, z, 500, rand, meta, WEST);
				this.tryCatchFire(world, x - 1, y, z, 500, rand, meta, EAST);
				this.tryCatchFire(world, x, y - 1, z, 300, rand, meta, UP);
				this.tryCatchFire(world, x, y + 1, z, 300, rand, meta, DOWN);
				this.tryCatchFire(world, x, y, z - 1, 500, rand, meta, SOUTH);
				this.tryCatchFire(world, x, y, z + 1, 500, rand, meta, NORTH);

				int h = 2;

				for(int ix = x - h; ix <= x + h; ++ix) {
					for(int iz = z - h; iz <= z + h; ++iz) {
						for(int iy = y - 1; iy <= y + 4; ++iy) {

							if(ix != x || iy != y || iz != z) {
								int fireLimit = 100;

								if(iy > y + 1) {
									fireLimit += (iy - (y + 1)) * 100;
								}

								if(world.getBlock(ix, iy, iz) == Blocks.fire && world.getBlockMetadata(ix, iy, iz) > meta + 1) {
									world.setBlock(ix, iy, iz, this, meta + 1, 3);
									continue;
								}

								int neighborFireChance = this.getChanceOfNeighborsEncouragingFire(world, ix, iy, iz);

								if(neighborFireChance > 0) {
									int adjustedFireChance = (neighborFireChance + 40 + world.difficultySetting.getDifficultyId() * 7) / (meta + 30);

									if(adjustedFireChance > 0 && rand.nextInt(fireLimit) <= adjustedFireChance) {
										world.setBlock(ix, iy, iz, this, meta + 1, 3);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private void tryCatchFire(World world, int x, int y, int z, int chance, Random rand, int fireMetadata, ForgeDirection face) {
		int flammability = world.getBlock(x, y, z).getFlammability(world, x, y, z, face);

		if(rand.nextInt(chance) < flammability) {
			boolean flag = world.getBlock(x, y, z) == Blocks.tnt;

			world.setBlock(x, y, z, this, fireMetadata + 1, 3);

			if(flag) {
				Blocks.tnt.onBlockDestroyedByPlayer(world, x, y, z, 1);
			}
		}
	}

	private int getChanceOfNeighborsEncouragingFire(World world, int x, int y, int z) {

		if(!world.isAirBlock(x, y, z)) {
			return 0;
		} else {
			int spread = 0;
			spread = this.getChanceToEncourageFire(world, x + 1, y, z, spread, WEST);
			spread = this.getChanceToEncourageFire(world, x - 1, y, z, spread, EAST);
			spread = this.getChanceToEncourageFire(world, x, y - 1, z, spread, UP);
			spread = this.getChanceToEncourageFire(world, x, y + 1, z, spread, DOWN);
			spread = this.getChanceToEncourageFire(world, x, y, z - 1, spread, SOUTH);
			spread = this.getChanceToEncourageFire(world, x, y, z + 1, spread, NORTH);
			return spread;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, int x, int y, int z) {
		return 0xF7FF00;
	}

	@Override
	public int getRenderType() {
		return 1;
	}
}
