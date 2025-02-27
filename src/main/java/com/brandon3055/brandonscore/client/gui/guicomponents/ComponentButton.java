package com.brandon3055.brandonscore.client.gui.guicomponents;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brandon on 1/01/2015.
 */
public class ComponentButton extends ComponentBase {
	protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");


	public final int buttonId;
	public final GUIBase gui;
	public final int xSize;
	public final int ySize;
	public int packedFGColour;
	public final String displayString;
	public String hoverText;

	public ComponentButton(int x, int y, int xSize, int ySize, int id, GUIBase gui, String displayString) {
		super(x, y);
		this.xSize = xSize;
		this.ySize = ySize;
		this.buttonId = id;
		this.gui = gui;
		this.displayString = displayString;
	}

	public ComponentButton(int x, int y, int xSize, int ySize, int id, GUIBase gui, String displayString, String hoverText) {
		this(x, y, xSize, ySize, id, gui, displayString);
		this.hoverText = hoverText;
	}

	@Override
	public int getWidth() {
		return xSize;
	}

	@Override
	public int getHeight() {
		return ySize;
	}

	@Override
	public void renderBackground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {

	}

	@Override
	public void renderForground(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		//FontRenderer fontrenderer = minecraft.fontRenderer;
		minecraft.getTextureManager().bindTexture(buttonTextures);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int k = isMouseOver(mouseX, mouseY) ? 2 : 1;
		GL11.glEnable(GL11.GL_BLEND);
		OpenGlHelper.glBlendFunc(770, 771, 1, 0);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.drawTexturedModalRect(this.x, this.y, 0, 46 + k * 20, this.xSize / 2, this.ySize);
		this.drawTexturedModalRect(this.x + this.xSize / 2, this.y, 200 - this.xSize / 2, 46 + k * 20, this.xSize / 2, this.ySize);
		if (this.ySize < 20){

			this.drawTexturedModalRect(x, y+3, 0, (46 + k * 20)+20-ySize+3, xSize / 2, ySize-3);
			this.drawTexturedModalRect(x + xSize / 2, y+3, 200 - xSize / 2, (46 + k * 20)+20-ySize+3, xSize / 2, ySize-3);
		}
		//this.mouseDragged(minecraft, mouseX, mouseY);
		int l = 14737632;

		if (packedFGColour != 0)
		{
			l = packedFGColour;
		}
		else if (!this.enabled)
		{
			l = 10526880;
		}
		else if (isMouseOver(mouseX, mouseY))
		{
			l = 16777120;
		}
		this.drawCenteredString(fontRendererObj, this.displayString, this.x + this.xSize / 2, this.y + (this.ySize - 8) / 2, l);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

	@Override
	public void renderFinal(Minecraft minecraft, int offsetX, int offsetY, int mouseX, int mouseY) {
		if (isMouseOver(mouseX, mouseY) && !StringUtils.isNullOrEmpty(hoverText)){
			List<String> list = new ArrayList<String>();
			list.add(hoverText);
			drawHoveringText(list, mouseX + offsetX, mouseY + offsetY + 10, fontRendererObj);
		}
	}

	@Override
	public void mouseClicked(int x, int y, int button) {
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
		gui.buttonClicked(buttonId, button);
	}
}
