package com.hbm.blocks;

import java.util.List;

import com.hbm.dim.hell.WorldGenBloatsprout;
import com.hbm.lib.RefStrings;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockBloatsproutSapling extends BlockSapling implements IBlockMulti {

	private IIcon[] textures;

	public BlockBloatsproutSapling() {
		super();
		float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.setStepSound(soundTypeGrass);
	}

	@Override
	protected boolean canPlaceBlockOn(Block block) {
		return block == Blocks.netherrack || block == Blocks.soul_sand || block == ModBlocks.glyphid_base || block == ModBlocks.nether_glyphid;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		textures = new IIcon[1];
		textures[0] = iconRegister.registerIcon(RefStrings.MODID + ":bloatsprout_sapling");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return textures[0];
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		list.add(new ItemStack(item, 1, 0));
	}

	@Override
	public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side) {
		return canPlaceBlockOn(world.getBlock(x, y - 1, z));
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		Block soil = world.getBlock(x, y - 1, z);
		return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && soil != null && canPlaceBlockOn(soil);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, java.util.Random random) {
		if(!world.isRemote) {
			if(world.getBlockLightValue(x, y + 1, z) >= 9 && random.nextInt(7) == 0) {
				this.func_149878_d(world, x, y, z, random);
			}
		}
	}

	@Override
	public void func_149878_d(World world, int x, int y, int z, java.util.Random rand) {
		WorldGenAbstractTree treeGen = new WorldGenBloatsprout(true, 2, 5, 7, 4, 3, ModBlocks.bloatsprout_log, ModBlocks.bloatsprout_leaves);

		if(treeGen != null) {
			world.setBlockToAir(x, y, z);
			if(!treeGen.generate(world, rand, x, y, z)) {
				world.setBlock(x, y, z, this, 0, 2);
			}
		}
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public int getDamageValue(World world, int x, int y, int z) {
		return world.getBlockMetadata(x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		world.setBlockMetadataWithNotify(x, y, z, stack.getItemDamage(), 2);
	}

	@Override
	public void func_149853_b(World world, java.util.Random random, int x, int y, int z) {
		this.func_149878_d(world, x, y, z, random);
	}

	@Override
	public int getSubCount() {
		return 1;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName();
	}
}