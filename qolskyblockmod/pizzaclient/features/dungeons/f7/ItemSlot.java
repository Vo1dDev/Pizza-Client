package qolskyblockmod.pizzaclient.features.dungeons.f7;

import net.minecraft.item.ItemStack;

public class ItemSlot {
   public final ItemStack stack;
   public final int slot;

   public ItemSlot(ItemStack stackIn, int slotIn) {
      this.stack = stackIn;
      this.slot = slotIn;
   }
}
