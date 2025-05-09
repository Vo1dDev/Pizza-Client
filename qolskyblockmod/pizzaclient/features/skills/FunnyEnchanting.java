package qolskyblockmod.pizzaclient.features.skills;

import java.awt.Color;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.ChestBackgroundDrawnEvent;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class FunnyEnchanting {
   private long lastInteractTime;
   private boolean getNextChronomatronClick;
   private int[] pattern;
   private int clickCount;
   private int nextSlot;

   @SubscribeEvent
   public void onRenderGui(ChestBackgroundDrawnEvent event) {
      if (PizzaClient.config.funnyEnchanting && PizzaClient.location == Locations.PRIVATEISLAND && ((Slot)event.chest.field_75151_b.get(49)).func_75211_c() != null) {
         List<Slot> invSlots = event.chest.field_75151_b;
         Slot slot;
         int i;
         if (event.displayName.startsWith("Ultrasequencer (")) {
            if (PizzaClient.config.funnyEnchantingCloseChest && ((Slot)invSlots.get(4)).func_75211_c().field_77994_a >= 10) {
               PizzaClient.mc.field_71439_g.func_71053_j();
               return;
            }

            if (((Slot)invSlots.get(49)).func_75211_c().func_77973_b() == Items.field_151113_aN) {
               if (this.pattern[this.clickCount] != 0) {
                  slot = (Slot)event.slots.get(this.pattern[this.clickCount]);
                  RenderUtil.drawOnSlot(slot, Color.GREEN.getRGB());
                  if (System.currentTimeMillis() - this.lastInteractTime >= (long)PizzaClient.config.funnyEnchantingDelay) {
                     PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, this.pattern[this.clickCount], 2, 0, PizzaClient.mc.field_71439_g);
                     this.lastInteractTime = System.currentTimeMillis();
                     this.pattern[this.clickCount] = 0;
                     ++this.clickCount;
                  }
               }
            } else if (((Slot)invSlots.get(49)).func_75211_c().func_77973_b() == Item.func_150898_a(Blocks.field_150426_aN)) {
               for(i = 9; i <= 44; ++i) {
                  if (((Slot)invSlots.get(i)).func_75211_c() != null && this.pattern[((Slot)invSlots.get(i)).func_75211_c().field_77994_a - 1] == 0 && !((Slot)invSlots.get(i)).func_75211_c().func_82833_r().startsWith(" ")) {
                     this.pattern[((Slot)invSlots.get(i)).func_75211_c().field_77994_a - 1] = i;
                  }
               }

               this.clickCount = 0;
            }
         } else if (event.displayName.startsWith("Chronomatron (")) {
            if (PizzaClient.config.funnyEnchantingCloseChest && ((Slot)invSlots.get(4)).func_75211_c().field_77994_a >= 13) {
               PizzaClient.mc.field_71439_g.func_71053_j();
               return;
            }

            if (((Slot)invSlots.get(49)).func_75211_c().func_77973_b() == Items.field_151113_aN) {
               if (this.getNextChronomatronClick) {
                  for(i = 11; i <= 33; ++i) {
                     if (((Slot)invSlots.get(i)).func_75211_c() != null && ((Slot)invSlots.get(i)).func_75211_c().func_77973_b() == Item.func_150898_a(Blocks.field_150406_ce) && this.pattern[this.nextSlot] == 0) {
                        this.getNextChronomatronClick = false;
                        this.pattern[this.nextSlot] = i;
                        ++this.nextSlot;
                        break;
                     }
                  }
               }

               if (this.pattern[this.clickCount] != 0) {
                  slot = (Slot)event.slots.get(this.pattern[this.clickCount]);
                  RenderUtil.drawOnSlot(slot, Color.GREEN.getRGB());
                  if (System.currentTimeMillis() - this.lastInteractTime >= (long)PizzaClient.config.funnyEnchantingDelay) {
                     PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, this.pattern[this.clickCount], 2, 0, PizzaClient.mc.field_71439_g);
                     this.lastInteractTime = System.currentTimeMillis();
                     ++this.clickCount;
                  }
               }
            } else if (((Slot)invSlots.get(49)).func_75211_c().func_77973_b() == Item.func_150898_a(Blocks.field_150426_aN)) {
               this.clickCount = 0;
               this.getNextChronomatronClick = true;
            }
         }

      }
   }

   @SubscribeEvent
   public void onOpenGui(GuiOpenEvent event) {
      this.clickCount = 0;
      this.pattern = new int[66];
      this.nextSlot = 0;
   }
}
