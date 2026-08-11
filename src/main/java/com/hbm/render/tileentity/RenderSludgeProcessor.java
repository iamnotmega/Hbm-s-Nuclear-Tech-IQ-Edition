package com.hbm.render.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.hbm.tileentity.machine.TileEntityMachineSludgeProcessor;
import com.hbm.util.BobMathUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderSludgeProcessor extends TileEntitySpecialRenderer implements IItemRendererProvider {
	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float dt) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y, z + 0.5);
		GL11.glRotated(90, 0, 1, 0);
		GL11.glShadeModel(GL11.GL_SMOOTH);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
			case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}

		TileEntityMachineSludgeProcessor processor = (TileEntityMachineSludgeProcessor) te;
		float anim = processor.prevAnim + (processor.anim - processor.prevAnim) * dt;

		this.bindTexture(ResourceManager.sludge_processor_tex);
		ResourceManager.sludge_processor.renderPart("Base");
		ResourceManager.sludge_processor.renderPart("Base1");
		ResourceManager.sludge_processor.renderPart("Base2");
		ResourceManager.sludge_processor.renderPart("Base3");

		GL11.glPushMatrix();
		GL11.glTranslated(1.5, 1.25, 0);
		GL11.glRotated(anim * 45, 0, 0, 1);
		GL11.glTranslated(-1.5, -1.25, 0);
		ResourceManager.sludge_processor.renderPart("Fan");
		GL11.glPopMatrix();

		GL11.glShadeModel(GL11.GL_FLAT);
		GL11.glPopMatrix();
	}

	@Override
	public Item getItemForRenderer() {
		return Item.getItemFromBlock(ModBlocks.machine_sludge_processor);
	}

	@Override
	public IItemRenderer getRenderer() {
		return new ItemRenderBase() {
			@Override
			public void renderInventory() {
				GL11.glTranslated(0, -2.5, 0);
				GL11.glScaled(2.5, 2.5, 2.5);
			}

			@Override
			public void renderCommonWithStack(ItemStack item) {
				GL11.glScaled(0.75, 0.75, 0.75);
				GL11.glShadeModel(GL11.GL_SMOOTH);
				bindTexture(ResourceManager.sludge_processor_tex);
				ResourceManager.sludge_processor.renderPart("Base");
				ResourceManager.sludge_processor.renderPart("Base1");
				ResourceManager.sludge_processor.renderPart("Base2");
				ResourceManager.sludge_processor.renderPart("Base3");
				ResourceManager.sludge_processor.renderPart("Fan");
				GL11.glShadeModel(GL11.GL_FLAT);
			}
		};
	}
}
