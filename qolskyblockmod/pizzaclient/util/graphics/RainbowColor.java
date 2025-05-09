package qolskyblockmod.pizzaclient.util.graphics;

import java.awt.Color;

public class RainbowColor extends Color {
   private final int alpha;

   public RainbowColor(int alpha) {
      super(alpha);
      this.alpha = alpha;
   }

   public int getRGB() {
      return Color.HSBtoRGB((float)(System.currentTimeMillis() % 3000L) / 3000.0F, 1.0F, (float)this.alpha);
   }
}
