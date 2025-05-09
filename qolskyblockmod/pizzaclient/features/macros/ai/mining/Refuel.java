package qolskyblockmod.pizzaclient.features.macros.ai.mining;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.Failsafes;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.AOTVMovement;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.CommissionMacro;
import qolskyblockmod.pizzaclient.util.ItemUtil;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class Refuel {
   public static EntityArmorStand drillNPC;

   public static EntityArmorStand findDrillNPC() {
      Iterator var0 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      Entity entity;
      do {
         if (!var0.hasNext()) {
            return null;
         }

         entity = (Entity)var0.next();
      } while(!(entity instanceof EntityArmorStand) || !entity.func_145748_c_().func_150260_c().contains("DRILL MECHANIC"));

      return (EntityArmorStand)entity;
   }

   public static void refuel() {
      if (PizzaClient.mc.field_71439_g.field_71070_bA instanceof ContainerChest) {
         Failsafes.writeToWebhook("Refuel");
         Utils.sleep(500);
         Slot drillSlot = null;
         Slot fuelSlot = null;
         float fuelPer = 0.0F;
         Iterator var3 = PizzaClient.mc.field_71439_g.field_71070_bA.field_75151_b.iterator();

         while(true) {
            String displayName;
            if (!var3.hasNext()) {
               if (drillSlot == null || fuelSlot == null) {
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Wasn't able to auto refuel, disabling."));
                  PizzaClient.mc.field_71439_g.func_71053_j();
                  return;
               }

               int uses = 1;
               Iterator var8 = ItemUtil.getItemLore(drillSlot.func_75211_c()).iterator();

               while(var8.hasNext()) {
                  displayName = (String)var8.next();
                  if (displayName.contains("Fuel:")) {
                     float fuel = (float)Integer.parseInt(displayName.split("/")[1].split("k")[0]);
                     uses = MathUtil.ceil(fuel / fuelPer);
                     break;
                  }
               }

               PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, fuelSlot.field_75222_d, 0, 0, PizzaClient.mc.field_71439_g);
               Utils.sleep(500);
               PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 33, 0, 0, PizzaClient.mc.field_71439_g);
               Utils.sleep(1000);

               for(int i = 0; i < uses; ++i) {
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, drillSlot.field_75222_d, 0, 1, PizzaClient.mc.field_71439_g);
                  Utils.sleep(500);
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, PizzaClient.mc.field_71439_g);
                  Utils.sleep(500);
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 13, 0, 0, PizzaClient.mc.field_71439_g);
                  Utils.sleep(500);
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, drillSlot.field_75222_d, 0, 0, PizzaClient.mc.field_71439_g);
                  Utils.sleep(1000);
               }

               Utils.sleep(2500);
               PizzaClient.mc.field_71439_g.func_71053_j();
               Utils.sleep(1000);
               break;
            }

            Slot slot = (Slot)var3.next();
            if (slot.func_75211_c() != null) {
               displayName = StringUtils.func_76338_a(slot.func_75211_c().func_82833_r());
               if (displayName.contains("Drill")) {
                  if (slot.func_75211_c().func_77973_b() == Items.field_179562_cC) {
                     drillSlot = slot;
                  }
               } else if (!displayName.contains("Volta") && !displayName.contains("Oil Barrel")) {
                  if (displayName.contains("Biofuel")) {
                     fuelSlot = slot;
                     fuelPer = 3.0F;
                  }
               } else {
                  fuelSlot = slot;
                  fuelPer = 10.0F;
               }
            }
         }
      }

   }

   public static void legitRefuel() {
      Utils.sleep(500);
      PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
      BlockPos forge = new BlockPos(0, 149, -69);

      while(!(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)).equals(forge)) {
         Utils.sleep(1000);
         PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
      }

      Utils.sleep(500);
      AOTVMovement.run((Runnable)null, (BlockPos[])(CommissionMacro.CRUCIBLE, new BlockPos(-7, 144, -22)));
      EntityArmorStand npc = findDrillNPC();
      if (npc == null) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to find the drill npc to refuel, report this."));
      } else {
         (new Rotater(npc.func_174824_e(1.0F))).rotate();
         Utils.sleep(400);
         PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, npc);
         Utils.sleep(3000);
         refuel();
         Utils.sleep(1000);
      }
   }

   public static void funnyRefuel() {
      Utils.sleep(500);
      PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, drillNPC);
      Utils.sleep(3000);
      refuel();
      Utils.sleep(2000);
   }

   public static enum RefuelState {
   }
}
