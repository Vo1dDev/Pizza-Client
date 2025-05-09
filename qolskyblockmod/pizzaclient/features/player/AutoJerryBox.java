package qolskyblockmod.pizzaclient.features.player;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.ChestBackgroundDrawnEvent;

public class AutoJerryBox {
   private static boolean clickBox = true;
   private static boolean clickedSlot;
   private static long lastInteractTime;

   @SubscribeEvent
   public void onRenderGui(ChestBackgroundDrawnEvent event) {
      if (PizzaClient.config.autoJerryBox && event.displayName.equals("Open a Jerry Box")) {
         clickBox = true;
         if (lastInteractTime == 0L) {
            lastInteractTime = System.currentTimeMillis();
            return;
         }

         if (System.currentTimeMillis() - lastInteractTime >= (long)(400 + PizzaClient.config.autoJerryBoxDelay + PizzaClient.config.autoJerryBoxClosingDelay)) {
            PizzaClient.mc.field_71439_g.func_71053_j();
            lastInteractTime = 0L;
            clickedSlot = false;
         } else if (System.currentTimeMillis() - lastInteractTime >= (long)(300 + PizzaClient.config.autoJerryBoxDelay) && !clickedSlot) {
            PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 22, 0, 0, PizzaClient.mc.field_71439_g);
            clickedSlot = true;
         }
      }

   }

   public static void useJerryBox() {
      if (clickBox) {
         clickedSlot = false;
         if (lastInteractTime == 0L) {
            lastInteractTime = System.currentTimeMillis();
            return;
         }

         if (System.currentTimeMillis() - lastInteractTime >= (long)(PizzaClient.config.autoJerryBoxOpenDelay + 100)) {
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
            clickBox = false;
            lastInteractTime = 0L;
         }
      }

   }

   public static void swapToBox() {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_82833_r().contains("Jerry Box")) {
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
            break;
         }
      }

   }
}
