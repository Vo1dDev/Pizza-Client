package qolskyblockmod.pizzaclient.features.player;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.ItemUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class AutoBookCombine {
   private static final Map<String, Integer> books = new ConcurrentHashMap();
   private static boolean threadRunning;

   @SubscribeEvent
   public void onOpenGui(GuiOpenEvent event) {
      books.clear();
      threadRunning = false;
   }

   public static void combineBooks(List<Slot> invSlots) {
      if (!threadRunning) {
         for(int i = 54; i <= 89; ++i) {
            Slot slot = (Slot)invSlots.get(i);
            if (slot.func_75211_c() != null && slot.func_75211_c().func_77973_b() == Items.field_151134_bR) {
               NBTTagCompound extraAttr = ItemUtil.getExtraAttributes(slot.func_75211_c());
               NBTTagCompound enchantments = extraAttr.func_74775_l("enchantments");
               if (enchantments.func_150296_c().size() == 1) {
                  if (books.containsKey(enchantments.toString()) && (Integer)books.get(enchantments.toString()) != i) {
                     if (((Slot)invSlots.get((Integer)books.get(enchantments.toString()))).func_75211_c() != null) {
                        AtomicInteger click = new AtomicInteger(i);
                        threadRunning = true;
                        (new Thread(() -> {
                           Utils.sleep(150 + PizzaClient.config.autoBookCombineDelay);
                           String pair = enchantments.toString();
                           if (PizzaClient.mc.field_71462_r != null) {
                              PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, (Integer)books.get(pair), 0, 1, PizzaClient.mc.field_71439_g);
                           }

                           books.remove(pair);
                           Utils.sleep(300 + PizzaClient.config.autoBookCombineDelay);
                           if (((Slot)invSlots.get(click.get())).func_75211_c() == null) {
                              click.set(fix(pair, invSlots));
                           }

                           if (PizzaClient.mc.field_71462_r != null) {
                              PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, click.get(), 0, 1, PizzaClient.mc.field_71439_g);
                           }

                           do {
                              if (((Slot)invSlots.get(13)).func_75211_c().func_77973_b() == Items.field_151134_bR) {
                                 Utils.sleep(50);
                                 if (PizzaClient.mc.field_71462_r != null) {
                                    PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, PizzaClient.mc.field_71439_g);
                                 }

                                 Utils.sleep(250 + PizzaClient.config.autoBookCombineDelay);
                                 if (PizzaClient.mc.field_71462_r != null) {
                                    PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 13, 0, 1, PizzaClient.mc.field_71439_g);
                                 }

                                 Utils.sleep(50);
                                 threadRunning = false;
                                 return;
                              }
                           } while(PizzaClient.mc.field_71462_r != null);

                        })).start();
                        return;
                     }

                     books.remove(enchantments.toString());
                  } else {
                     int value;
                     try {
                        value = Integer.parseInt(String.valueOf(enchantments.toString().charAt(enchantments.toString().indexOf(":") + 2)));
                     } catch (Exception var7) {
                        value = Integer.parseInt(String.valueOf(enchantments.toString().charAt(enchantments.toString().indexOf(":") + 1)));
                     }

                     if (!enchantments.toString().contains("feather_falling") && !enchantments.toString().contains("infinite_quiver")) {
                        if (value >= 5) {
                           continue;
                        }
                     } else if (value >= 10) {
                        continue;
                     }

                     books.put(enchantments.toString(), i);
                  }
               }
            }
         }

      }
   }

   private static int fix(String name, List<Slot> invSlots) {
      for(int i = 54; i <= 89; ++i) {
         Slot slot = (Slot)invSlots.get(i);
         if (slot.func_75211_c() != null && slot.func_75211_c().func_77973_b() == Items.field_151134_bR) {
            NBTTagCompound extraAttr = ItemUtil.getExtraAttributes(slot.func_75211_c());
            NBTTagCompound enchantments = extraAttr.func_74775_l("enchantments");
            if (enchantments.func_150296_c().size() == 1 && enchantments.toString().equals(name)) {
               return i;
            }
         }
      }

      return 0;
   }

   public static void salvage(List<Slot> invSlots) {
      if (!threadRunning) {
         for(int i = 54; i <= 89; ++i) {
            Slot slot = (Slot)invSlots.get(i);
            if (((Slot)invSlots.get(i)).func_75211_c() != null) {
               NBTTagCompound extraAttr = ItemUtil.getExtraAttributes(slot.func_75211_c());
               String sbId = ItemUtil.getSkyBlockItemID(slot.func_75211_c());
               if (!sbId.equals("ICE_SPRAY_WAND") && extraAttr != null && extraAttr.func_74764_b("baseStatBoostPercentage") && !extraAttr.func_74764_b("dungeon_item_level")) {
                  threadRunning = true;
                  (new Thread(() -> {
                     Utils.sleep(335 + PizzaClient.config.autoSalvageDelay);
                     PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, 1, PizzaClient.mc.field_71439_g);

                     do {
                        if (((Slot)invSlots.get(13)).func_75211_c() != null) {
                           Utils.sleep(200 + PizzaClient.config.autoSalvageDelay);
                           PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, PizzaClient.mc.field_71439_g);
                           threadRunning = false;
                           return;
                        }
                     } while(PizzaClient.mc.field_71462_r != null);

                  })).start();
               }
            }
         }

      }
   }
}
