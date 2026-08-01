package com.hbm.tileentity.deco;

import com.hbm.handler.threading.PacketThreading;
import com.hbm.packet.toclient.AuxParticlePacketNT;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityCyst extends TileEntityGeysir {

	@Override
	protected void perform() {
		blood();
	}

	private void blood() {

		int range = 32;
		if(worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, xCoord + 0.5, yCoord + 0.5, zCoord + 0.5).expand(range, range, range)).isEmpty())
			return;

		if(timer % 2 == 0) {
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "blood");
			data.setDouble("mX", worldObj.rand.nextGaussian() * 0.05);
			data.setDouble("mY", 0.2);
			data.setDouble("mZ", worldObj.rand.nextGaussian() * 0.05);
			PacketThreading.createAllAroundThreadedPacket(new AuxParticlePacketNT(data, this.xCoord + 0.5F, this.yCoord + 1.1F, this.zCoord + 0.5F), new TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 75));
		}
	}

	@Override
	protected int getDelay() {
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		return meta == 0 ? (worldObj.rand.nextBoolean() ? 300 : 450) : 80 + worldObj.rand.nextInt(60);
	}
}
