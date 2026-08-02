package com.hbm.dim.hell;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import java.util.Random;

public class NetherBiomeBase extends BiomeGenBase {
    public NetherBiomeBase(int id) {
        super(id);
        field_150604_aj = field_76754_C = 0;
        this.setDisableRain();
        this.setTemperatureRainfall(2.0F, 0.0F);
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.addAll(BiomeGenBase.hell.getSpawnableList(net.minecraft.entity.EnumCreatureType.monster));
        BiomeDictionary.registerBiomeType(this, BiomeDictionary.Type.NETHER);
    }

    @Override
    public void decorate(World world, Random rand, int x, int z) {
		// to be filledin
    }
}
