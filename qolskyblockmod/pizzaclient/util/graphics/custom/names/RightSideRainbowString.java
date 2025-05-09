package qolskyblockmod.pizzaclient.util.graphics.custom.names;

import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.graphics.FontUtil;
import qolskyblockmod.pizzaclient.util.graphics.custom.CustomString;

public class RightSideRainbowString implements CustomString {
   private char[] rainbowChars;
   private int index;

   public RightSideRainbowString(String text, String rainbow, String lowerCase) {
      this.rainbowChars = (char[])RainbowString.rgbNames.get(lowerCase);
      if (this.rainbowChars[0] == 167) {
         new NonRGBString(text, rainbow, lowerCase);
      } else {
         RainbowString.rgbList.put(text, this);
         this.index = text.indexOf(rainbow);
         if (this.rainbowChars[1] == '[') {
            if (text.indexOf(91) != -1 && text.indexOf(93) != -1) {
               if (this.index > 2 && text.charAt(this.index - 2) == ']') {
                  this.index = text.indexOf(91);
               }
            } else {
               for(int i = 2; i < this.rainbowChars.length; ++i) {
                  char ch = this.rainbowChars[i];
                  if (ch == ']') {
                     int from = i + 2;
                     int newLength = this.rainbowChars.length - from;
                     char[] copy = new char[newLength + 1];
                     System.arraycopy(this.rainbowChars, from, copy, 1, newLength);
                     copy[0] = this.rainbowChars[0];
                     this.rainbowChars = copy;
                     break;
                  }
               }
            }
         }

      }
   }

   public int render(String text, int x, int y, int color, boolean shadow) {
      FontUtil.drawRainbowName(this.rainbowChars, PizzaClient.mc.field_71466_p.func_175065_a(text.substring(0, this.index), (float)x, (float)y, color, shadow), y, shadow);
      return FontUtil.posX;
   }
}
