package qolskyblockmod.pizzaclient.gui;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiElement;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiManager;
import qolskyblockmod.pizzaclient.gui.button.LocationButton;

public class LocationEditGui extends GuiScreen {
   private float xOffset;
   private float yOffset;
   private GuiElement dragging;
   private final Map<GuiElement, LocationButton> locationButtons = new HashMap();

   public boolean func_73868_f() {
      return false;
   }

   public void func_73866_w_() {
      super.func_73866_w_();
      Iterator var1 = GuiManager.elements.iterator();

      while(var1.hasNext()) {
         GuiElement e = (GuiElement)var1.next();
         LocationButton lb = new LocationButton(e);
         this.field_146292_n.add(lb);
         this.locationButtons.put(e, lb);
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.onMouseMove();
      this.func_146276_q_();
      Iterator var4 = this.field_146292_n.iterator();

      while(var4.hasNext()) {
         GuiButton button = (GuiButton)var4.next();
         if (button instanceof LocationButton) {
            if (((LocationButton)button).element.getToggled()) {
               button.func_146112_a(this.field_146297_k, mouseX, mouseY);
            }
         } else {
            button.func_146112_a(this.field_146297_k, mouseX, mouseY);
         }
      }

   }

   public void func_146284_a(GuiButton button) {
      if (button instanceof LocationButton) {
         LocationButton lb = (LocationButton)button;
         this.dragging = lb.getElement();
         ScaledResolution sr = new ScaledResolution(this.field_146297_k);
         float minecraftScale = (float)sr.func_78325_e();
         float floatMouseX = (float)Mouse.getX() / minecraftScale;
         float floatMouseY = (float)(this.field_146297_k.field_71440_d - Mouse.getY()) / minecraftScale;
         this.xOffset = floatMouseX - this.dragging.getActualX();
         this.yOffset = floatMouseY - this.dragging.getActualY();
      }

   }

   protected void onMouseMove() {
      ScaledResolution sr = new ScaledResolution(this.field_146297_k);
      float minecraftScale = (float)sr.func_78325_e();
      float floatMouseX = (float)Mouse.getX() / minecraftScale;
      float floatMouseY = (float)(Display.getHeight() - Mouse.getY()) / minecraftScale;
      if (this.dragging != null) {
         LocationButton lb = (LocationButton)this.locationButtons.get(this.dragging);
         if (lb == null) {
            return;
         }

         float x = (floatMouseX - this.xOffset) / (float)sr.func_78326_a();
         float y = (floatMouseY - this.yOffset) / (float)sr.func_78328_b();
         this.dragging.setPos(x, y);
      }

   }

   protected void func_146286_b(int mouseX, int mouseY, int state) {
      super.func_146286_b(mouseX, mouseY, state);
      this.dragging = null;
   }

   public void func_146281_b() {
      GuiManager.saveConfig();
   }
}
