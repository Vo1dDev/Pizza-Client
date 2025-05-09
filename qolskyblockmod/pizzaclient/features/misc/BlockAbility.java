package qolskyblockmod.pizzaclient.features.misc;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.dungeons.ChestESP;
import qolskyblockmod.pizzaclient.features.keybinds.KeybindFeatures;
import qolskyblockmod.pizzaclient.util.ItemUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class BlockAbility {
   private static File saveFile;
   public static Set<String> blockedItems = new HashSet();

   public BlockAbility() {
      saveFile = new File(PizzaClient.modDir, "blockability.json");
      reloadSave();
   }

   public static boolean blockAbility(ItemStack item) {
      String extraAttr = ItemUtil.getSkyBlockItemID(item);
      if (extraAttr.equals("GYROKINETIC_WAND")) {
         return PizzaClient.config.blockAlignment;
      } else {
         return PizzaClient.mc.field_71439_g.func_110143_aJ() >= PizzaClient.mc.field_71439_g.func_110138_aP() && extraAttr.contains("ZOMBIE_SWORD") ? PizzaClient.config.blockUselessZombieSword : blockedItems.contains(extraAttr);
      }
   }

   @SubscribeEvent
   public void onInteract(PlayerInteractEvent event) {
      if (event.entityPlayer == PizzaClient.mc.field_71439_g) {
         ItemStack item;
         switch(event.action) {
         case RIGHT_CLICK_BLOCK:
            Block block = event.world.func_180495_p(event.pos).func_177230_c();
            if (block == Blocks.field_150486_ae) {
               ChestESP.clickedBlocks.add(event.pos);
               item = event.entityPlayer.field_71071_by.func_70448_g();
               if (item != null && !KeybindFeatures.notGhostBlockable.contains(block)) {
                  event.setCanceled(blockAbility(item));
               }
            }
            break;
         case RIGHT_CLICK_AIR:
            item = event.entityPlayer.field_71071_by.func_70448_g();
            if (item != null) {
               event.setCanceled(blockAbility(item));
            }
         }

      }
   }

   public static void reloadSave() {
      blockedItems.clear();

      JsonArray dataArray;
      try {
         FileReader in = new FileReader(saveFile);
         Throwable var35 = null;

         try {
            dataArray = (JsonArray)PizzaClient.gson.fromJson(in, JsonArray.class);
            blockedItems.addAll(Arrays.asList(Utils.getStringArrayFromJsonArray(dataArray)));
         } catch (Throwable var30) {
            var35 = var30;
            throw var30;
         } finally {
            if (in != null) {
               if (var35 != null) {
                  try {
                     in.close();
                  } catch (Throwable var29) {
                     var35.addSuppressed(var29);
                  }
               } else {
                  in.close();
               }
            }

         }
      } catch (Exception var34) {
         dataArray = new JsonArray();

         try {
            FileWriter writer = new FileWriter(saveFile);
            Throwable var3 = null;

            try {
               PizzaClient.gson.toJson(dataArray, writer);
            } catch (Throwable var28) {
               var3 = var28;
               throw var28;
            } finally {
               if (writer != null) {
                  if (var3 != null) {
                     try {
                        writer.close();
                     } catch (Throwable var27) {
                        var3.addSuppressed(var27);
                     }
                  } else {
                     writer.close();
                  }
               }

            }
         } catch (Exception var32) {
         }
      }

   }

   public static void writeSave() {
      try {
         FileWriter writer = new FileWriter(saveFile);
         Throwable var1 = null;

         try {
            JsonArray arr = new JsonArray();
            Iterator var3 = blockedItems.iterator();

            while(var3.hasNext()) {
               String itemId = (String)var3.next();
               arr.add(new JsonPrimitive(itemId));
            }

            PizzaClient.gson.toJson(arr, writer);
         } catch (Throwable var13) {
            var1 = var13;
            throw var13;
         } finally {
            if (writer != null) {
               if (var1 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var12) {
                     var1.addSuppressed(var12);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (Exception var15) {
      }

   }
}
