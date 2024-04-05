package com.brandon3055.draconicevolution.client.render.block;

import com.brandon3055.draconicevolution.common.tileentities.TileEnergyInfuser;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class RenderEnergyInfuser implements IItemRenderer {
    private final TileEnergyInfuser tile;

    public RenderEnergyInfuser() {
        this.tile = new TileEnergyInfuser();
        this.tile.rotation = 0f;
        this.tile.running = false;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (type == ItemRenderType.ENTITY) GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
        TileEntityRendererDispatcher.instance.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
    }
}
