package qolskyblockmod.pizzaclient.util.graphics.custom;

import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.graphics.FontUtil;
import qolskyblockmod.pizzaclient.util.graphics.custom.names.RainbowString;

public class ModMessageString implements CustomString {
   public ModMessageString() {
   }

   public ModMessageString(String text) {
      RainbowString.rgbList.put(text, this);
   }

   public static void addToList(String text) {
      RainbowString.rgbList.put(text, new ModMessageString());
   }

   public int render(String text, int x, int y, int color, boolean shadow) {
      return PizzaClient.mc.field_71466_p.func_175065_a(text.substring(16), FontUtil.drawRainbowModMessage(x, y), (float)y, color, shadow);
   }
}
