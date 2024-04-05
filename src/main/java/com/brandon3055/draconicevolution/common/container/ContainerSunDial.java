package com.brandon3055.draconicevolution.common.container;

import com.brandon3055.draconicevolution.common.tileentities.TileSunDial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerSunDial extends Container {

    private final TileSunDial tile;

    public ContainerSunDial(InventoryPlayer invPlayer, TileSunDial tile) {
        this.tile = tile;

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.isUseableByPlayer(player);
    }


    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        return null;
    }

    public TileSunDial getTile() {
        return tile;
    }

}
