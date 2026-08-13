package com.hbm.inventory.container;

import com.hbm.inventory.SlotNonRetarded;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;

public class ContainerMachineSludgeProcessor extends ContainerBase {
	public ContainerMachineSludgeProcessor(InventoryPlayer inv, IInventory te) {
		super(inv, te);

		this.addSlotToContainer(new SlotNonRetarded(te, 0, 152, 81));
		this.addSlotToContainer(new SlotNonRetarded(te, 1, 35, 126));
		this.addSlots(te, 2, 152, 108, 2, 1);
		this.addSlotToContainer(new SlotNonRetarded(te, 4, 8, 90));
		this.addOutputSlots(inv.player, te, 5, 62, 90, 1, 4);

		this.playerInv(inv, 8, 174);
	}
}
