package com.hbm.tileentity.machine;

import api.hbm.energymk2.IEnergyReceiverMK2;
import api.hbm.fluidmk2.IFluidStandardTransceiverMK2;
import api.hbm.redstoneoverradio.IRORValueProvider;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.UpgradeManagerNT;
import com.hbm.inventory.container.ContainerMachineSludgeProcessor;
import com.hbm.inventory.fluid.Fluids;
import com.hbm.inventory.fluid.tank.FluidTank;
import com.hbm.inventory.gui.GUIMachineSludgeProcessor;
import com.hbm.inventory.recipes.loader.GenericRecipe;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.Library;
import com.hbm.module.machine.ModuleMachineSludgeProcessor;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.IUpgradeInfoProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.BobMathUtil;
import com.hbm.util.fauxpointtwelve.DirPos;
import com.hbm.util.i18n.I18nUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.HashMap;
import java.util.List;

public class TileEntityMachineSludgeProcessor extends TileEntityMachineBase implements IEnergyReceiverMK2, IFluidStandardTransceiverMK2, IUpgradeInfoProvider, IControlReceiver, IGUIProvider, IRORValueProvider {
	public FluidTank[] inputTanks;
	public FluidTank[] outputTanks;

	public long power;
	public long maxPower = 1_000_000;
	public boolean didProcess = false;

	public int anim;
	public int prevAnim;

	public ModuleMachineSludgeProcessor sludgeProcessorModule;
	public UpgradeManagerNT upgradeManager = new UpgradeManagerNT(this);

	public TileEntityMachineSludgeProcessor() {
		super(9);

		this.inputTanks = new FluidTank[]{
			new FluidTank(Fluids.NONE, 64_000),
		};
		this.outputTanks = new FluidTank[]{
			new FluidTank(Fluids.NONE, 64_000),
			new FluidTank(Fluids.NONE, 64_000),
			new FluidTank(Fluids.NONE, 64_000),
			new FluidTank(Fluids.NONE, 64_000),
		};

		this.sludgeProcessorModule = new ModuleMachineSludgeProcessor(0, this, this.slots)
			.itemInput(4).itemOutput(5)
			.fluidInput(this.inputTanks[0]).fluidOutput(this.outputTanks[0], this.outputTanks[1], this.outputTanks[2], this.outputTanks[3]);
	}

	@Override
	public String getName() {
		return "container.machineSludgeProcessor";
	}

	@Override
	public void updateEntity() {
		if (this.maxPower <= 0) this.maxPower = 1_000_000L;

		if (!this.worldObj.isRemote) {
			GenericRecipe recipe = this.sludgeProcessorModule.getRecipe();
			if (recipe != null) {
				this.maxPower = recipe.power * 100;
			}
			this.maxPower = BobMathUtil.max(this.power, this.maxPower, 1_000_000);
			this.power = Library.chargeTEFromItems(this.slots, 0, this.power, this.maxPower);
			this.upgradeManager.checkSlots(this.slots, 1, 2);

			for (DirPos pos : this.getConPos()) {
				this.trySubscribe(this.worldObj, pos);
				for (FluidTank tank : this.inputTanks) {
					if (tank.getTankType() != Fluids.NONE) this.trySubscribe(tank.getTankType(), this.worldObj, pos);
				}
				for (FluidTank tank : this.outputTanks) {
					if (tank.getTankType() != Fluids.NONE && tank.getFill() > 0) this.tryProvide(tank, this.worldObj, pos);
				}
			}

			double speed = 1.0;
			double pow = 1.0;

			speed += Math.min(this.upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.SPEED), 3) / 3.0;
			speed += Math.min(this.upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.OVERDRIVE), 3);

			pow -= Math.min(this.upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.POWER), 3) * 0.25;
			pow += Math.min(this.upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.SPEED), 3);
			pow += Math.min(this.upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.OVERDRIVE), 3) * 10.0 / 3.0;

			this.sludgeProcessorModule.update(speed, pow, true, this.slots[1]);
			this.didProcess = this.sludgeProcessorModule.didProcess;
			if (this.sludgeProcessorModule.markDirty) this.markDirty();
			this.networkPackNT(100);
		} else {
			this.prevAnim = this.anim;
			if (this.didProcess) this.anim++;
		}
	}

	public DirPos[] getConPos() {
		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);

		switch (dir) {
			case EAST:
				return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z),
					new DirPos(this.xCoord + 1, this.yCoord, this.zCoord - 1, Library.POS_X),
					new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
					new DirPos(this.xCoord + 1, this.yCoord, this.zCoord + 2, Library.POS_X),
					new DirPos(this.xCoord - 4, this.yCoord, this.zCoord - 2, Library.NEG_Z),
					new DirPos(this.xCoord - 5, this.yCoord, this.zCoord - 1, Library.NEG_X),
					new DirPos(this.xCoord - 4, this.yCoord, this.zCoord + 3, Library.POS_Z),
					new DirPos(this.xCoord - 5, this.yCoord, this.zCoord + 2, Library.NEG_X),
				};
			case WEST:
				return new DirPos[] {
					new DirPos(this.xCoord, this.yCoord, this.zCoord - 2, Library.NEG_Z),
					new DirPos(this.xCoord - 1, this.yCoord, this.zCoord - 1, Library.NEG_X),
					new DirPos(this.xCoord, this.yCoord, this.zCoord + 2, Library.POS_Z),
					new DirPos(this.xCoord - 1, this.yCoord, this.zCoord + 2, Library.NEG_X),
					new DirPos(this.xCoord + 4, this.yCoord, this.zCoord - 2, Library.NEG_Z),
					new DirPos(this.xCoord + 5, this.yCoord, this.zCoord - 1, Library.POS_X),
					new DirPos(this.xCoord + 4, this.yCoord, this.zCoord + 3, Library.POS_Z),
					new DirPos(this.xCoord + 5, this.yCoord, this.zCoord + 2, Library.POS_X),
				};
			case SOUTH:
				return new DirPos[] {
					new DirPos(this.xCoord - 3, this.yCoord, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord - 2, this.yCoord, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord + 2, this.yCoord, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord + 1, this.yCoord, this.zCoord + 1, Library.POS_Z),
					new DirPos(this.xCoord - 3, this.yCoord, this.zCoord - 4, Library.NEG_X),
					new DirPos(this.xCoord - 2, this.yCoord, this.zCoord - 5, Library.NEG_Z),
					new DirPos(this.xCoord + 2, this.yCoord, this.zCoord - 4, Library.POS_X),
					new DirPos(this.xCoord + 1, this.yCoord, this.zCoord - 5, Library.NEG_Z),
				};
			case NORTH:
				return new DirPos[] {
					new DirPos(this.xCoord - 2, this.yCoord, this.zCoord, Library.NEG_X),
					new DirPos(this.xCoord - 1, this.yCoord, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord + 3, this.yCoord, this.zCoord, Library.POS_X),
					new DirPos(this.xCoord + 2, this.yCoord, this.zCoord - 1, Library.NEG_Z),
					new DirPos(this.xCoord - 2, this.yCoord, this.zCoord + 4, Library.NEG_X),
					new DirPos(this.xCoord - 1, this.yCoord, this.zCoord + 5, Library.POS_Z),
					new DirPos(this.xCoord + 2, this.yCoord, this.zCoord + 4, Library.POS_X),
					new DirPos(this.xCoord + 1, this.yCoord, this.zCoord + 5, Library.POS_Z),
				};
		}

		return new DirPos[0];
	}

	@Override
	public void serialize(ByteBuf buf) {
		super.serialize(buf);
		for (FluidTank tank : this.inputTanks) tank.serialize(buf);
		for (FluidTank tank : this.outputTanks) tank.serialize(buf);
		buf.writeLong(this.power);
		buf.writeLong(this.maxPower);
		buf.writeBoolean(this.didProcess);
		this.sludgeProcessorModule.serialize(buf);
	}

	@Override
	public void deserialize(ByteBuf buf) {
		super.deserialize(buf);
		for (FluidTank tank : this.inputTanks) tank.deserialize(buf);
		for (FluidTank tank : this.outputTanks) tank.deserialize(buf);
		this.power = buf.readLong();
		this.maxPower = buf.readLong();
		this.didProcess = buf.readBoolean();
		this.sludgeProcessorModule.deserialize(buf);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.inputTanks[0].readFromNBT(nbt, "i" + 0);
		this.outputTanks[0].readFromNBT(nbt, "o" + 0);
		this.outputTanks[1].readFromNBT(nbt, "o" + 1);
		this.outputTanks[2].readFromNBT(nbt, "o" + 2);
		this.outputTanks[3].readFromNBT(nbt, "o" + 3);

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.sludgeProcessorModule.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		this.inputTanks[0].writeToNBT(nbt, "i" + 0);
		this.outputTanks[0].writeToNBT(nbt, "o" + 0);
		this.outputTanks[1].writeToNBT(nbt, "o" + 1);
		this.outputTanks[2].writeToNBT(nbt, "o" + 2);
		this.outputTanks[3].writeToNBT(nbt, "o" + 3);

		nbt.setLong("power", this.power);
		nbt.setLong("maxPower", this.maxPower);
		this.sludgeProcessorModule.writeToNBT(nbt);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
		if (slot == 0) return true;
		if (slot == 1 && itemStack.getItem() == ModItems.blueprints) return true;
		if (slot >= 2 && slot <= 3 && itemStack.getItem() instanceof ItemMachineUpgrade) return true;
		return this.sludgeProcessorModule.isItemValid(slot, itemStack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int side) {
		return (slot >= 4 && slot <= 8) || this.sludgeProcessorModule.isSlotClogged(slot);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{4,5,6,7,8};
	}

	@Override
	public long getPower() {
		return this.power;
	}
	@Override
	public void setPower(long power) {
		this.power = power;
	}
	@Override
	public long getMaxPower() {
		return this.maxPower;
	}

	@Override
	public FluidTank[] getReceivingTanks() {
		return this.inputTanks;
	}
	@Override
	public FluidTank[] getSendingTanks() {
		return this.outputTanks;
	}
	@Override
	public FluidTank[] getAllTanks() {
		return new FluidTank[]{this.inputTanks[0], this.outputTanks[0], this.outputTanks[1], this.outputTanks[2], this.outputTanks[3]};
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineSludgeProcessor(player.inventory, this);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public Object provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineSludgeProcessor(player.inventory, this);
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return this.isUseableByPlayer(player);
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		if (data.hasKey("index") && data.hasKey("selection")) {
			int index = data.getInteger("index");
			String selection = data.getString("selection");
			if (index == 0) {
				this.sludgeProcessorModule.setRecipe(selection, false);
				this.markChanged();
			}
		}
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if (this.bb == null) this.bb = AxisAlignedBB.getBoundingBox(this.xCoord - 2, this.yCoord, this.zCoord - 2, this.xCoord + 3, this.yCoord + 5, this.zCoord + 3);
		return this.bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean canProvideInfo(ItemMachineUpgrade.UpgradeType type, int level, boolean extendedInfo) {
		return type == ItemMachineUpgrade.UpgradeType.SPEED || type == ItemMachineUpgrade.UpgradeType.POWER || type == ItemMachineUpgrade.UpgradeType.OVERDRIVE;
	}
	@Override
	public void provideInfo(ItemMachineUpgrade.UpgradeType type, int level, List<String> info, boolean extendedInfo) {
		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_sludge_processor));
		if(type == ItemMachineUpgrade.UpgradeType.SPEED) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(KEY_SPEED, "+" + (level * 100 / 3) + "%"));
			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(KEY_CONSUMPTION, "+" + (level * 50) + "%"));
		}
		if(type == ItemMachineUpgrade.UpgradeType.POWER) {
			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(KEY_CONSUMPTION, "-" + (level * 25) + "%"));
		}
		if(type == ItemMachineUpgrade.UpgradeType.OVERDRIVE) {
			info.add((BobMathUtil.getBlink() ? EnumChatFormatting.RED : EnumChatFormatting.DARK_GRAY) + "YES");
		}
	}
	@Override
	public HashMap<ItemMachineUpgrade.UpgradeType, Integer> getValidUpgrades() {
		HashMap<ItemMachineUpgrade.UpgradeType, Integer> upgrades = new HashMap<>();
		upgrades.put(ItemMachineUpgrade.UpgradeType.SPEED, 3);
		upgrades.put(ItemMachineUpgrade.UpgradeType.POWER, 3);
		upgrades.put(ItemMachineUpgrade.UpgradeType.OVERDRIVE, 3);
		return upgrades;
	}

	@Override
	public String[] getFunctionInfo() {
		return new String[] {
			PREFIX_VALUE + "progress",
			PREFIX_VALUE + "recipe",
			PREFIX_VALUE + "active",
		};
	}

	@Override
	public String provideRORValue(String name) {
		if((PREFIX_VALUE + "progress").equals(name)) return "" + (int) Math.round(this.sludgeProcessorModule.progress * 100);
		if((PREFIX_VALUE + "recipe").equals(name)) return this.sludgeProcessorModule.getRecipeName();
		if((PREFIX_VALUE + "active").equals(name)) return "" + (this.didProcess ? 1 : 0);
		return null;
	}
}
