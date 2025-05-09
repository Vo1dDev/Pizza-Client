package qolskyblockmod.pizzaclient.features.dungeons.f7;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.ChestBackgroundDrawnEvent;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class FunnyTerminals {
   private static int lastSlot;
   private static int mazeId;
   private static long lastInteractTime;
   private static int[] chest = new int[54];
   protected static FunnyTerminals.Terminals terminals;
   private static final Map<Integer, Long> terminalFix;
   public static final EnumDyeColor[] LEFT_COLORS;
   public static EnumDyeColor bestColor;

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (PizzaClient.config.funnyTerminals && PizzaClient.location == Locations.DUNGEON && PizzaClient.mc.field_71462_r == null) {
         terminals = FunnyTerminals.Terminals.NULL;
      }

   }

   @SubscribeEvent
   public void onRenderGui(ChestBackgroundDrawnEvent event) {
      if (PizzaClient.config.funnyTerminals && PizzaClient.location == Locations.DUNGEON) {
         List<Slot> invSlots = event.slots;
         String displayName = event.displayName;
         byte var6 = -1;
         switch(displayName.hashCode()) {
         case -1976344926:
            if (displayName.equals("Change all to same color!")) {
               var6 = 3;
            }
            break;
         case -890308604:
            if (displayName.equals("Navigate the maze!")) {
               var6 = 0;
            }
            break;
         case -377745862:
            if (displayName.equals("Click the button on time!")) {
               var6 = 4;
            }
            break;
         case -230942598:
            if (displayName.equals("Correct all the panes!")) {
               var6 = 1;
            }
            break;
         case 866971958:
            if (displayName.equals("Click in order!")) {
               var6 = 2;
            }
         }

         FunnyTerminals.Terminals currentTerminal;
         switch(var6) {
         case 0:
            currentTerminal = FunnyTerminals.Terminals.MAZE;
            break;
         case 1:
            currentTerminal = FunnyTerminals.Terminals.PANELS;
            break;
         case 2:
            currentTerminal = FunnyTerminals.Terminals.NUMBERS;
            break;
         case 3:
            currentTerminal = FunnyTerminals.Terminals.SAME_COLOR;
            break;
         case 4:
            currentTerminal = FunnyTerminals.Terminals.TIMING;
            break;
         default:
            if (displayName.startsWith("What starts with:")) {
               currentTerminal = FunnyTerminals.Terminals.LETTER;
            } else if (displayName.startsWith("Select all the")) {
               currentTerminal = FunnyTerminals.Terminals.COLOR;
            } else {
               currentTerminal = FunnyTerminals.Terminals.NULL;
            }
         }

         if (currentTerminal != terminals) {
            this.updateValues();
            terminals = currentTerminal;
         }

         if (mazeId < PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c) {
            mazeId = PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c;
         }

         Iterator iterator = terminalFix.entrySet().iterator();

         int i;
         while(iterator.hasNext()) {
            Entry<Integer, Long> map = (Entry)iterator.next();
            i = (Integer)map.getKey();
            long value = (Long)map.getValue();
            if (terminals != FunnyTerminals.Terminals.NUMBERS && terminals != FunnyTerminals.Terminals.PANELS) {
               if (terminals == FunnyTerminals.Terminals.MAZE) {
                  if (((Slot)invSlots.get(i)).func_75211_c() == null) {
                     fixTerminals(value, invSlots);
                     break;
                  }

                  if (((Slot)invSlots.get(i)).func_75211_c().func_77952_i() == 0) {
                     fixTerminals(value, invSlots);
                     break;
                  }

                  iterator.remove();
               } else if (terminals != FunnyTerminals.Terminals.COLOR && terminals != FunnyTerminals.Terminals.LETTER) {
                  if (terminals == FunnyTerminals.Terminals.SAME_COLOR && ((Slot)invSlots.get(i)).func_75211_c() == null) {
                     fixTerminals(value, invSlots);
                     break;
                  }
               } else {
                  if (((Slot)invSlots.get(i)).func_75211_c() == null) {
                     fixTerminals(value, invSlots);
                     break;
                  }

                  if (!((Slot)invSlots.get(i)).func_75211_c().func_77948_v()) {
                     fixTerminals(value, invSlots);
                     break;
                  }

                  iterator.remove();
               }
            } else {
               if (((Slot)invSlots.get(i)).func_75211_c() == null) {
                  fixTerminals(value, invSlots);
                  break;
               }

               if (((Slot)invSlots.get(i)).func_75211_c().func_77952_i() == 14) {
                  fixTerminals(value, invSlots);
                  break;
               }

               iterator.remove();
            }
         }

         Slot slot;
         ItemStack stack;
         int greenSlot;
         int itemDamage;
         int i;
         ItemStack item;
         ItemStack item;
         int clickSlot;
         switch(terminals) {
         case MAZE:
            if (System.currentTimeMillis() - lastInteractTime >= (long)sleepTime()) {
               if (lastSlot == -1) {
                  for(greenSlot = 0; greenSlot <= 53; ++greenSlot) {
                     item = ((Slot)invSlots.get(greenSlot)).func_75211_c();
                     if (item != null) {
                        itemDamage = item.func_77952_i();
                        if (itemDamage == 0) {
                           chest[greenSlot] = 1;
                        } else if (itemDamage == 5) {
                           lastSlot = greenSlot;
                           chest[greenSlot] = 2;
                        }
                     } else {
                        chest[greenSlot] = 1;
                     }
                  }
               }

               greenSlot = lastSlot + 1;
               i = lastSlot + 9;
               itemDamage = lastSlot - 1;
               i = lastSlot - 9;
               clickSlot = -1;
               if (greenSlot % 9 != 0 && greenSlot <= 53 && chest[greenSlot] == 1) {
                  clickSlot = greenSlot;
               } else if (i <= 53 && chest[i] == 1) {
                  clickSlot = i;
               } else if (itemDamage % 9 != 8 && itemDamage >= 0 && itemDamage <= 53 && chest[itemDamage] == 1) {
                  clickSlot = itemDamage;
               } else if (i >= 0 && chest[i] == 1) {
                  clickSlot = i;
               }

               if (clickSlot != -1) {
                  chest[clickSlot] = 0;
                  lastSlot = clickSlot;
                  PizzaClient.mc.field_71442_b.func_78753_a(mazeId, clickSlot, PizzaClient.config.terminalClickMode, 0, PizzaClient.mc.field_71439_g);
                  terminalFix.put(clickSlot, System.currentTimeMillis());
                  ++mazeId;
                  lastInteractTime = System.currentTimeMillis();
               }
            }
            break;
         case NUMBERS:
            if (chest[13] == 0) {
               for(greenSlot = 10; greenSlot < 26; ++greenSlot) {
                  if (greenSlot != 17 && greenSlot != 18) {
                     item = ((Slot)invSlots.get(greenSlot)).func_75211_c();
                     if (item != null) {
                        chest[item.field_77994_a - 1] = greenSlot;
                     }
                  }
               }
            }

            if (System.currentTimeMillis() - lastInteractTime >= (long)sleepTime()) {
               for(greenSlot = 0; greenSlot < 14; ++greenSlot) {
                  if (((Slot)invSlots.get(chest[greenSlot])).func_75211_c() != null && greenSlot > lastSlot && ((Slot)invSlots.get(chest[greenSlot])).func_75211_c().func_77952_i() == 14) {
                     PizzaClient.mc.field_71442_b.func_78753_a(mazeId, chest[greenSlot], PizzaClient.config.terminalClickMode, 0, PizzaClient.mc.field_71439_g);
                     terminalFix.put(chest[greenSlot], System.currentTimeMillis());
                     ++mazeId;
                     lastSlot = greenSlot;
                     lastInteractTime = System.currentTimeMillis();
                     return;
                  }
               }
            }
            break;
         case PANELS:
            if (System.currentTimeMillis() - lastInteractTime >= (long)sleepTime()) {
               for(greenSlot = 11; greenSlot < 34; ++greenSlot) {
                  if (((Slot)invSlots.get(greenSlot)).getSlotIndex() > lastSlot) {
                     item = ((Slot)invSlots.get(greenSlot)).func_75211_c();
                     if (item != null && item.func_77952_i() == 14) {
                        PizzaClient.mc.field_71442_b.func_78753_a(mazeId, greenSlot, PizzaClient.config.terminalClickMode, 0, PizzaClient.mc.field_71439_g);
                        terminalFix.put(greenSlot, System.currentTimeMillis());
                        ++mazeId;
                        lastSlot = greenSlot;
                        lastInteractTime = System.currentTimeMillis();
                        return;
                     }
                  }
               }

               for(greenSlot = 11; greenSlot < 34; ++greenSlot) {
                  item = ((Slot)invSlots.get(greenSlot)).func_75211_c();
                  if (item != null && item.func_77952_i() == 14) {
                     PizzaClient.mc.field_71442_b.func_78753_a(mazeId, greenSlot, PizzaClient.config.terminalClickMode, 0, PizzaClient.mc.field_71439_g);
                     terminalFix.put(greenSlot, System.currentTimeMillis());
                     ++mazeId;
                     lastSlot = greenSlot;
                     lastInteractTime = System.currentTimeMillis();
                     return;
                  }
               }
            }
            break;
         case LETTER:
            if (System.currentTimeMillis() - lastInteractTime >= (long)sleepTime()) {
               char letter = displayName.charAt(displayName.indexOf("'") + 1);

               for(i = 10; i <= 43; ++i) {
                  slot = (Slot)invSlots.get(i);
                  if (slot.field_75222_d > lastSlot && slot.getSlotIndex() > lastSlot) {
                     item = slot.func_75211_c();
                     if (item != null && !item.func_77948_v() && StringUtils.func_76338_a(item.func_82833_r()).charAt(0) == letter) {
                        PizzaClient.mc.field_71442_b.func_78753_a(mazeId, i, PizzaClient.config.terminalClickMode, 0, PizzaClient.mc.field_71439_g);
                        terminalFix.put(i, System.currentTimeMillis());
                        ++mazeId;
                        lastSlot = slot.getSlotIndex();
                        lastInteractTime = System.currentTimeMillis();
                        break;
                     }
                  }
               }
            }
            break;
         case COLOR:
            if (System.currentTimeMillis() - lastInteractTime >= (long)sleepTime()) {
               String colour = displayName.split(" ")[3];

               for(i = 10; i <= 43; ++i) {
                  slot = (Slot)invSlots.get(i);
                  if (slot.field_75222_d > lastSlot && slot.getSlotIndex() > lastSlot) {
                     item = slot.func_75211_c();
                     if (item != null && !item.func_77948_v()) {
                        String itemName = StringUtils.func_76338_a(item.func_82833_r()).toUpperCase();
                        if (item.func_82833_r().toUpperCase().contains(colour) || colour.equals("SILVER") && itemName.contains("LIGHT GRAY") || colour.equals("WHITE") && itemName.equals("WOOL") || colour.equals("BLACK") && itemName.contains("INK") || colour.equals("BROWN") && itemName.contains("COCOA") || colour.equals("BLUE") && itemName.contains("LAPIS") || colour.equals("WHITE") && itemName.contains("BONE")) {
                           lastInteractTime = System.currentTimeMillis();
                           PizzaClient.mc.field_71442_b.func_78753_a(mazeId, i, PizzaClient.config.terminalClickMode, 0, PizzaClient.mc.field_71439_g);
                           terminalFix.put(i, System.currentTimeMillis());
                           lastSlot = i;
                           ++mazeId;
                           break;
                        }
                     }
                  }
               }
            }
            break;
         case TIMING:
            if (System.currentTimeMillis() - lastInteractTime >= (long)sleepTime()) {
               greenSlot = -1;
               i = -1;
               itemDamage = 0;

               for(i = 1; i < 51; ++i) {
                  stack = ((Slot)invSlots.get(i)).func_75211_c();
                  if (stack != null) {
                     EnumDyeColor color = EnumDyeColor.func_176764_b(stack.func_77952_i());
                     switch(color) {
                     case PURPLE:
                        if (i == -1) {
                           i = i % 9;
                        }
                        break;
                     case LIME:
                        Item item = stack.func_77973_b();
                        if (item == Item.func_150898_a(Blocks.field_150397_co)) {
                           if (greenSlot == -1) {
                              greenSlot = i % 9;
                           }
                        } else if (item == Item.func_150898_a(Blocks.field_150406_ce)) {
                           itemDamage = i;
                        }
                     }
                  }
               }

               if (i != -1 && itemDamage != 0 && greenSlot == i) {
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, itemDamage, PizzaClient.config.terminalClickMode, 0, PizzaClient.mc.field_71439_g);
                  lastInteractTime = System.currentTimeMillis();
               }
            }
            break;
         case SAME_COLOR:
            if (System.currentTimeMillis() - lastInteractTime >= (long)sleepTime()) {
               greenSlot = 0;
               Slot[] itemStacks = new Slot[9];

               for(itemDamage = 12; itemDamage < 34; ++itemDamage) {
                  Slot slot1 = (Slot)invSlots.get(itemDamage);
                  stack = slot1.func_75211_c();
                  if (stack != null && stack.func_77952_i() != 15 && stack.func_77952_i() != 0) {
                     itemStacks[greenSlot] = slot1;
                     ++greenSlot;
                  }
               }

               if (bestColor == null) {
                  bestColor = this.getBestColor(itemStacks);
                  if (bestColor == null) {
                     return;
                  }
               }

               Slot[] var21 = itemStacks;
               i = itemStacks.length;

               for(clickSlot = 0; clickSlot < i; ++clickSlot) {
                  Slot stack = var21[clickSlot];
                  if (stack != null && stack.func_75211_c() != null && lastSlot != stack.getSlotIndex()) {
                     EnumDyeColor color = EnumDyeColor.func_176764_b(stack.func_75211_c().func_77952_i());
                     if (color != bestColor) {
                        int clicks = this.getClicks(color, bestColor);
                        if (clicks != 0) {
                           if (clicks < 0) {
                              PizzaClient.mc.field_71442_b.func_78753_a(mazeId, stack.getSlotIndex(), 1, 0, PizzaClient.mc.field_71439_g);
                           } else {
                              PizzaClient.mc.field_71442_b.func_78753_a(mazeId, stack.getSlotIndex(), 0, 0, PizzaClient.mc.field_71439_g);
                           }

                           lastSlot = stack.getSlotIndex();
                           ++mazeId;
                           lastInteractTime = System.currentTimeMillis();
                           terminalFix.put(stack.getSlotIndex(), System.currentTimeMillis());
                           return;
                        }
                     }
                  }
               }

               lastSlot = 0;
            }
         }

      }
   }

   private EnumDyeColor getBestColor(Slot[] itemStacks) {
      EnumDyeColor bestColor = null;
      int clickCount = 9999;

      for(int i = 0; i < 5; ++i) {
         EnumDyeColor color = LEFT_COLORS[i];
         int clicks = 0;
         Slot[] var7 = itemStacks;
         int var8 = itemStacks.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            Slot stack = var7[var9];
            if (stack != null && stack.func_75211_c() != null) {
               int click = this.getClicks(EnumDyeColor.func_176764_b(stack.func_75211_c().func_77952_i()), i);
               if (click < 0) {
                  click = -click;
               }

               clicks += click;
            }
         }

         if (clicks != 0 && clicks < clickCount) {
            clickCount = clicks;
            bestColor = color;
         }
      }

      return bestColor;
   }

   private int getClicks(EnumDyeColor itemStackColor, EnumDyeColor goalColor) {
      if (itemStackColor == goalColor) {
         return 0;
      } else {
         int itemStackClicks = 0;
         int goalColorClicks = 0;

         int i;
         for(i = 0; i < LEFT_COLORS.length; ++i) {
            EnumDyeColor color = LEFT_COLORS[i];
            if (color == itemStackColor) {
               itemStackClicks = i;
            } else if (color == goalColor) {
               goalColorClicks = i;
            }
         }

         i = goalColorClicks - itemStackClicks;
         if (i < 0) {
            int otherClicks = i + 5;
            if (otherClicks < -i) {
               return otherClicks;
            }
         }

         return i;
      }
   }

   private int getClicks(EnumDyeColor itemStackColor, int goalColor) {
      if (itemStackColor == LEFT_COLORS[goalColor]) {
         return 0;
      } else {
         int itemStackClicks = 0;

         int clicks;
         for(clicks = 0; clicks < LEFT_COLORS.length; ++clicks) {
            if (LEFT_COLORS[clicks] == itemStackColor) {
               itemStackClicks = clicks;
            }
         }

         clicks = goalColor - itemStackClicks;
         if (clicks < 0) {
            int otherClicks = clicks + 5;
            if (otherClicks < -clicks) {
               return otherClicks;
            }
         }

         return clicks;
      }
   }

   private static void fixTerminals(long value, List<Slot> invSlots) {
      if (System.currentTimeMillis() - value >= (long)PizzaClient.config.terminalFixTime) {
         lastInteractTime = 0L;
         int i;
         int bottom;
         int itemDamage;
         switch(terminals) {
         case MAZE:
            chest = new int[54];
            i = 0;

            ItemStack item;
            for(; i < 54; ++i) {
               item = ((Slot)invSlots.get(i)).func_75211_c();
               if (item != null) {
                  switch(item.func_77952_i()) {
                  case 0:
                     chest[i] = 1;
                     break;
                  case 5:
                     chest[i] = 2;
                     break;
                  case 14:
                     lastSlot = i;
                     break;
                  default:
                     chest[i] = 0;
                  }
               } else {
                  chest[i] = 1;
               }
            }

            while(true) {
               i = lastSlot + 1;
               bottom = lastSlot + 9;
               itemDamage = lastSlot - 1;
               int top = lastSlot - 9;
               if (i % 9 != 0 && i <= 53 && chest[i] != 0) {
                  if (!fixMaze(i)) {
                     continue;
                  }
                  break;
               } else if (bottom <= 53 && chest[bottom] != 0) {
                  if (!fixMaze(bottom)) {
                     continue;
                  }
                  break;
               } else if (itemDamage % 9 != 8 && itemDamage >= 0 && itemDamage <= 53 && chest[itemDamage] != 0) {
                  if (!fixMaze(itemDamage)) {
                     continue;
                  }
                  break;
               } else {
                  if (top < 0 || chest[top] == 0) {
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Could not fix maze! lastSlot: " + lastSlot));
                     return;
                  }

                  if (fixMaze(top)) {
                     break;
                  }
               }
            }

            chest = new int[54];

            for(i = 0; i < 54; ++i) {
               item = ((Slot)invSlots.get(i)).func_75211_c();
               if (item != null) {
                  itemDamage = item.func_77952_i();
                  if (itemDamage == 0) {
                     chest[i] = 1;
                  } else if (itemDamage == 5) {
                     chest[i] = 2;
                  }
               }
            }

            mazeId = PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c;
            terminalFix.clear();
            break;
         case NUMBERS:
            bottom = 0;

            for(itemDamage = 10; itemDamage < 26; ++itemDamage) {
               if (itemDamage != 17 && itemDamage != 18) {
                  ItemStack item = ((Slot)invSlots.get(itemDamage)).func_75211_c();
                  if (item != null && item.func_77952_i() == 5 && item.field_77994_a > bottom) {
                     bottom = item.field_77994_a;
                  }
               }
            }

            lastSlot = bottom - 1;
            mazeId = PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c;
            terminalFix.clear();
            break;
         case PANELS:
            i = 0;

            for(bottom = 11; bottom <= 33; ++bottom) {
               ItemStack item = ((Slot)invSlots.get(bottom)).func_75211_c();
               if (item != null) {
                  if (item.func_77952_i() == 14) {
                     i = bottom - 1;
                     break;
                  }
               } else {
                  i = bottom - 1;
               }
            }

            lastSlot = i;
            mazeId = PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c;
            terminalFix.clear();
            break;
         case LETTER:
            lastSlot = -1;
            mazeId = PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c;
            terminalFix.clear();
            break;
         case COLOR:
            lastSlot = -1;
            mazeId = PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c;
            terminalFix.clear();
            break;
         case TIMING:
            mazeId = PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c;
            lastSlot = -1;
            terminalFix.clear();
            break;
         case SAME_COLOR:
            mazeId = PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c;
            terminalFix.clear();
         }
      }

   }

   private static int sleepTime() {
      return PizzaClient.config.terminalSleepAmount;
   }

   private static boolean fixMaze(int clickSlot) {
      if (chest[clickSlot] == 1) {
         chest[clickSlot] = 0;
         lastSlot = clickSlot;
      } else if (chest[clickSlot] == 2) {
         lastSlot = clickSlot;
         return true;
      }

      return false;
   }

   private void updateValues() {
      bestColor = null;
      lastSlot = -1;
      mazeId = -1;
      chest = new int[54];
      terminalFix.clear();
      lastInteractTime = 0L;
   }

   static {
      terminals = FunnyTerminals.Terminals.NULL;
      terminalFix = new HashMap();
      LEFT_COLORS = new EnumDyeColor[]{EnumDyeColor.ORANGE, EnumDyeColor.YELLOW, EnumDyeColor.GREEN, EnumDyeColor.BLUE, EnumDyeColor.RED};
   }

   protected static enum Terminals {
      MAZE,
      NUMBERS,
      COLOR,
      LETTER,
      PANELS,
      TIMING,
      SAME_COLOR,
      NULL;
   }
}
