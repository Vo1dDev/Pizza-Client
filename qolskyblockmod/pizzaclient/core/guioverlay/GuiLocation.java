package qolskyblockmod.pizzaclient.core.guioverlay;

import net.minecraft.client.gui.ScaledResolution;
import qolskyblockmod.pizzaclient.PizzaClient;

public class GuiLocation {
   private static final ScaledResolution sr;
   public float x;
   public float y;

   public GuiLocation(int x, int y) {
      this((float)x / (float)sr.func_78328_b(), (float)y / (float)sr.func_78328_b());
   }

   public GuiLocation(float x, float y) {
      this.x = x;
      this.y = y;
   }

   public boolean equals(GuiLocation loc) {
      return loc.x == this.x && loc.y == this.y;
   }

   public String toString() {
      return "{x=" + this.x + ", y=" + this.y + "}";
   }

   static {
      sr = new ScaledResolution(PizzaClient.mc);
   }
}
