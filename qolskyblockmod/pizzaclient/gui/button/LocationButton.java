package qolskyblockmod.pizzaclient.gui.button;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.GuiButton;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiElement;
import qolskyblockmod.pizzaclient.util.graphics.FontUtil;

public class LocationButton extends GuiButton {
   private float x;
   private float y;
   private float x2;
   private float y2;
   public GuiElement element;

   public LocationButton(GuiElement element) {
      super(-1, 0, 0, (String)null);
      this.element = element;
      this.x = this.element.getActualX() - 5.0F;
      this.y = this.element.getActualY() - 5.0F;
      this.x2 = this.x + (float)this.element.getWidth() + 5.0F;
      this.y2 = this.y + (float)this.element.getHeight() + 5.0F;
   }

   private void refreshLocations() {
      this.x = this.element.getActualX() - 2.0F;
      this.y = this.element.getActualY() - 2.0F;
      this.x2 = this.x + (float)this.element.getWidth() + 4.0F;
      this.y2 = this.y + (float)this.element.getHeight() + 4.0F;
   }

   public void func_146112_a(Minecraft mc, int mouseX, int mouseY) {
      this.refreshLocations();
      this.field_146123_n = (float)mouseX >= this.x && (float)mouseY >= this.y && (float)mouseX < this.x2 && (float)mouseY < this.y2;
      Color c = new Color(250, 250, 250, this.field_146123_n ? 120 : 60);
      FontUtil.drawRect((double)this.x, (double)this.y, (double)this.x2, (double)this.y2, c.getRGB());
      this.element.demoRender();
   }

   public boolean func_146116_c(Minecraft mc, int mouseX, int mouseY) {
      return this.field_146124_l && this.field_146125_m && this.field_146123_n;
   }

   public void func_146113_a(SoundHandler soundHandlerIn) {
   }

   public GuiElement getElement() {
      return this.element;
   }
}
