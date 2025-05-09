package qolskyblockmod.pizzaclient.features.player;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.ChestBackgroundDrawnEvent;

public class HarpSolver {
   private static long lastInteractTime;
   public static boolean click;
   private static String harpTag;

   @SubscribeEvent
   public void onBackgroundDrawn(ChestBackgroundDrawnEvent event) {
      if (PizzaClient.config.autoHarpSolver && event.displayName.startsWith("Harp -")) {
         int i;
         if (!click) {
            StringBuilder currentTag = new StringBuilder();

            for(i = 1; i <= 34; ++i) {
               if (event.chestInv.func_70301_a(i) != null) {
                  currentTag.append(event.chestInv.func_70301_a(i).func_77973_b());
               }
            }

            if (!currentTag.toString().equals(harpTag)) {
               harpTag = currentTag.toString();
               lastInteractTime = 0L;
               click = true;
            }
         }

         if (click) {
            if (lastInteractTime == 0L) {
               lastInteractTime = System.currentTimeMillis();
               return;
            }

            if (System.currentTimeMillis() - lastInteractTime >= (long)PizzaClient.config.harpSolverDelay) {
               int woolPos = -1;

               for(i = 28; i <= 34; ++i) {
                  if (event.chestInv.func_70301_a(i) != null && event.chestInv.func_70301_a(i).func_77973_b() == Item.func_150898_a(Blocks.field_150325_L)) {
                     woolPos = i + 9;
                     break;
                  }
               }

               if (woolPos == -1) {
                  lastInteractTime = 0L;
                  click = false;
                  return;
               }

               PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, woolPos, 2, 0, PizzaClient.mc.field_71439_g);
               lastInteractTime = 0L;
               click = false;
            }
         }
      }

   }
}
