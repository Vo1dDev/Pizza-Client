package qolskyblockmod.pizzaclient.features.keybinds;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StringUtils;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.PlayerUtil;

public class AnyItemWithAnything {
   private static long lastInteract;

   public static void use() {
      if (System.currentTimeMillis() - lastInteract >= (long)PizzaClient.config.anyItemWithAnythingDelay) {
         lastInteract = System.currentTimeMillis();
         String name = PizzaClient.config.anyItemWithAnythingName.toLowerCase();

         for(int i = 0; i < 8; ++i) {
            ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (item != null && StringUtils.func_76338_a(item.func_82833_r().toLowerCase()).contains(name)) {
               int currentSlot = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
               if (PizzaClient.config.anyItemWithAnythingAction == 0) {
                  PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, item);
               } else {
                  PlayerUtil.forceUpdateController();
                  PlayerUtil.leftClick();
               }

               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentSlot;
               break;
            }
         }
      }

   }
}
