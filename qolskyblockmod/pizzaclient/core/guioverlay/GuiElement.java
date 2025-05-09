package qolskyblockmod.pizzaclient.core.guioverlay;

import net.minecraft.client.gui.ScaledResolution;
import qolskyblockmod.pizzaclient.PizzaClient;

public abstract class GuiElement {
   public String name;
   public float scale;
   public GuiLocation pos;

   public GuiElement(String name, GuiLocation location) {
      this(name, 1.0F, location);
   }

   public GuiElement(String name, float scale, GuiLocation location) {
      this.name = name;
      this.scale = scale;
      this.pos = (GuiLocation)GuiManager.GUIPOSITIONS.getOrDefault(name, location);
   }

   public abstract void render();

   public abstract void demoRender();

   public abstract boolean getToggled();

   public void setPos(float x, float y) {
      this.pos.x = x;
      this.pos.y = y;
   }

   public float getActualX() {
      int maxX = (new ScaledResolution(PizzaClient.mc)).func_78326_a();
      return (float)maxX * this.pos.x;
   }

   public float getActualY() {
      int maxY = (new ScaledResolution(PizzaClient.mc)).func_78328_b();
      return (float)maxY * this.pos.y;
   }

   public abstract int getHeight();

   public abstract int getWidth();
}
