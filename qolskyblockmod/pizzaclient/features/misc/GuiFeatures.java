package qolskyblockmod.pizzaclient.features.misc;

import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.ChestBackgroundDrawnEvent;
import qolskyblockmod.pizzaclient.features.dungeons.AutoSpiritLeap;
import qolskyblockmod.pizzaclient.features.player.AutoBookCombine;
import qolskyblockmod.pizzaclient.features.player.HarpSolver;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class GuiFeatures {
   private static long lastClick;
   private static boolean clicked;

   @SubscribeEvent
   public void onContainerBackground(ChestBackgroundDrawnEvent event) {
      String var2 = event.displayName;
      byte var3 = -1;
      switch(var2.hashCode()) {
      case -1564935600:
         if (var2.equals("Treasure Chest")) {
            var3 = 3;
         }
         break;
      case -1293521353:
         if (var2.equals("Spirit Leap")) {
            var3 = 5;
         }
         break;
      case 63422636:
         if (var2.equals("Anvil")) {
            var3 = 1;
         }
         break;
      case 65074913:
         if (var2.equals("Chest")) {
            var3 = 0;
         }
         break;
      case 216731529:
         if (var2.equals("Loot Chest")) {
            var3 = 2;
         }
         break;
      case 257928946:
         if (var2.equals("Salvage Dungeon Item")) {
            var3 = 4;
         }
         break;
      case 1188878083:
         if (var2.equals("Start Dungeon?")) {
            var3 = 6;
         }
      }

      switch(var3) {
      case 0:
         if (PizzaClient.location == Locations.DUNGEON && PizzaClient.config.autoCloseDungChest) {
            closeChest();
         }
         break;
      case 1:
         if (PizzaClient.config.autoBookCombine) {
            AutoBookCombine.combineBooks(event.chest.field_75151_b);
         }
         break;
      case 2:
         if (PizzaClient.config.autoCloseLootChest && PizzaClient.location == Locations.CHOLLOWS) {
            closeChest();
         }
         break;
      case 3:
         if (PizzaClient.config.autoCloseLootChest && PizzaClient.location == Locations.CHOLLOWS) {
            closeChest();
         }
         break;
      case 4:
         if (PizzaClient.config.autoSalvage) {
            AutoBookCombine.salvage(event.chest.field_75151_b);
         }
         break;
      case 5:
         if (PizzaClient.config.autoLeapDoor) {
            AutoSpiritLeap.onRenderLeap(event.slots);
         }
         break;
      case 6:
         if (PizzaClient.config.autoReady) {
            if (lastClick == 0L) {
               lastClick = System.currentTimeMillis();
               return;
            }

            if (System.currentTimeMillis() - lastClick >= 125L) {
               PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 13, 2, 0, PizzaClient.mc.field_71439_g);
               lastClick = 0L;
            }
         }
         break;
      default:
         if (PizzaClient.config.autoReady && !clicked) {
            if (event.displayName.startsWith("Catacombs - ")) {
               if (lastClick == 0L) {
                  lastClick = System.currentTimeMillis();
                  return;
               }

               if (System.currentTimeMillis() - lastClick >= 150L) {
                  for(int i = 2; i < 7; ++i) {
                     if (((Slot)event.slots.get(i)).func_75211_c() != null && ((Slot)event.slots.get(i)).func_75211_c().func_82833_r().contains(PizzaClient.username)) {
                        ItemStack below = ((Slot)event.slots.get(i + 9)).func_75211_c();
                        if (below == null || below.func_77952_i() == 14) {
                           PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i + 9, 2, 0, PizzaClient.mc.field_71439_g);
                           lastClick = 0L;
                           clicked = true;
                           return;
                        }
                     }
                  }
               }
            }

            return;
         }
      }

   }

   private static void closeChest() {
      if (lastClick == 0L) {
         lastClick = System.currentTimeMillis();
      } else if (System.currentTimeMillis() - lastClick >= 40L) {
         PizzaClient.mc.field_71439_g.func_71053_j();
         lastClick = 0L;
      }

   }

   @SubscribeEvent
   public void onOpenGui(GuiOpenEvent event) {
      HarpSolver.click = false;
   }

   @SubscribeEvent
   public void onWorldChange(Load event) {
      clicked = false;
   }
}
