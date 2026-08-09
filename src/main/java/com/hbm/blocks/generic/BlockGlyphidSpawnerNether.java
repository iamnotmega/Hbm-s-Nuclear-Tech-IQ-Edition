package com.hbm.blocks.generic;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import com.hbm.config.MobConfig;
import com.hbm.entity.mob.glyphid.EntityGlyphid;
import com.hbm.entity.mob.glyphid.EntityGlyphidNether;
import com.hbm.entity.mob.glyphid.EntityGlyphidNetherBehemoth;
import com.hbm.entity.mob.glyphid.EntityGlyphidNetherBlaster;
import com.hbm.entity.mob.glyphid.EntityGlyphidNetherBombardier;
import com.hbm.entity.mob.glyphid.EntityGlyphidNetherBrawler;
import com.hbm.entity.mob.glyphid.EntityGlyphidNetherBrenda;
import com.hbm.entity.mob.glyphid.EntityGlyphidNetherDigger;
import com.hbm.entity.mob.glyphid.EntityGlyphidNetherNuclear;
import com.hbm.entity.mob.glyphid.EntityGlyphidNetherScout;
import com.hbm.entity.mob.glyphid.EntityGlyphidScout;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.Tuple.Pair;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockGlyphidSpawnerNether extends BlockGlyphidSpawner {

	public IIcon nestIcon;

	private static final ArrayList<Pair<Function<World, EntityGlyphid>, int[]>> netherSpawnMap = new ArrayList<>();

	static {
		netherSpawnMap.add(new Pair<>(EntityGlyphidNether::new,			MobConfig.netherGlyphidChance));
		netherSpawnMap.add(new Pair<>(EntityGlyphidNetherBombardier::new,	MobConfig.netherBombardierChance));
		netherSpawnMap.add(new Pair<>(EntityGlyphidNetherBrawler::new,	MobConfig.netherBrawlerChance));
		netherSpawnMap.add(new Pair<>(EntityGlyphidNetherDigger::new,		MobConfig.netherDiggerChance));
		netherSpawnMap.add(new Pair<>(EntityGlyphidNetherBlaster::new,	MobConfig.netherBlasterChance));
		netherSpawnMap.add(new Pair<>(EntityGlyphidNetherBehemoth::new,	MobConfig.netherBehemothChance));
		netherSpawnMap.add(new Pair<>(EntityGlyphidNetherBrenda::new,		MobConfig.netherBrendaChance));
		netherSpawnMap.add(new Pair<>(EntityGlyphidNetherNuclear::new,	MobConfig.netherNuclearChance));
	}

	public BlockGlyphidSpawnerNether(Material mat) {
		super(mat);
		this.setCreativeTab(MainRegistry.blockTab);
	}

	@Override
	public Item getItemDropped(int meta, Random rand, int fortune) {
		return ModItems.egg_glyphid_nether;
	}

	@Override
	public int getSubCount() {
		return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return nestIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister reg) {
		nestIcon = reg.registerIcon(RefStrings.MODID + ":nether_glyphid.nest");
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityGlyphidSpawnerNether();
	}

	public static class TileEntityGlyphidSpawnerNether extends BlockGlyphidSpawner.TileEntityGlpyhidSpawner {

		@Override
		protected ArrayList<Pair<Function<World, EntityGlyphid>, int[]>> getSpawnMap() {
			return netherSpawnMap;
		}

		@Override
		public EntityGlyphidScout createScout() {
			return new EntityGlyphidNetherScout(worldObj);
		}
	}
}
