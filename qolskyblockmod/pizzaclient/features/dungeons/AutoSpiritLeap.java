package qolskyblockmod.pizzaclient.features.dungeons;

import java.util.List;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;

public class AutoSpiritLeap {
   public static String leapName;
   public static boolean leapToDoor;

   public static void onRenderLeap(List<Slot> invSlots) {
      if (leapToDoor && leapName != null) {
         for(int i = 11; i < 16; ++i) {
            ItemStack stack = ((Slot)invSlots.get(i)).func_75211_c();
            if (stack != null && StringUtils.func_76338_a(stack.func_82833_r()).contains(leapName)) {
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Leaping to " + leapName + "!"));
               PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, 0, PizzaClient.mc.field_71439_g);
               leapToDoor = false;
               break;
            }
         }
      }

   }
}
