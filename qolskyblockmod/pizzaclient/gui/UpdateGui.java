package qolskyblockmod.pizzaclient.gui;

import com.google.gson.JsonObject;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.gui.button.BetterButton;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.graphics.FontUtil;

public class UpdateGui extends GuiScreen {
   private static String[] splitString;
   private static String newVersion;
   private static boolean urlIssue;
   private static String link;
   public static boolean shownGui;

   public boolean func_73868_f() {
      return false;
   }

   public void func_73866_w_() {
      ScaledResolution sr = new ScaledResolution(this.field_146297_k);
      float height = (float)sr.func_78328_b();
      super.func_73866_w_();
      this.field_146292_n.add(new BetterButton(1, this.field_146294_l / 2 - 95, (int)((double)height * 0.8D), 190, 20, "Download"));
      this.field_146292_n.add(new BetterButton(0, this.field_146294_l / 2 - 95, (int)((double)height * 0.85D), 190, 20, "Close"));
   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      this.func_146276_q_();
      if (splitString != null) {
         ((GuiButton)this.field_146292_n.get(1)).func_146112_a(this.field_146297_k, mouseX, mouseY);
      }

      ((GuiButton)this.field_146292_n.get(0)).func_146112_a(this.field_146297_k, mouseX, mouseY);
      float scale = 3.0F;
      GlStateManager.func_179152_a(scale, scale, 0.0F);
      FontUtil.drawCenteredString("Changes in the new Update (" + newVersion + ") :", (float)this.field_146294_l / 2.0F / scale, ((float)this.field_146295_m / 4.0F - 75.0F) / scale, 65535);
      GlStateManager.func_179152_a(1.0F / scale, 1.0F / scale, 0.0F);
      if (splitString != null && !urlIssue) {
         int y = 12;
         scale = 1.25F;
         String[] var6 = splitString;
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String s = var6[var8];
            GlStateManager.func_179152_a(scale, scale, 0.0F);
            FontUtil.drawString(s, (float)this.field_146294_l / 5.0F / scale, ((float)this.field_146295_m / 4.0F + (float)y) / scale, 16777215);
            GlStateManager.func_179152_a(1.0F / scale, 1.0F / scale, 0.0F);
            y += 15;
         }

         super.func_73863_a(mouseX, mouseY, partialTicks);
      } else {
         GlStateManager.func_179152_a(scale, scale, 0.0F);
         FontUtil.drawCenteredString("Something went wrong!", (float)this.field_146294_l / 2.0F / scale, ((float)this.field_146295_m / 4.0F + 110.0F) / scale, 16711680);
         GlStateManager.func_179152_a(1.0F / scale, 1.0F / scale, 0.0F);
      }
   }

   protected void func_146284_a(GuiButton button) {
      switch(button.field_146127_k) {
      case 0:
         shownGui = true;
         PizzaClient.displayScreen = new GuiMainMenu();
         break;
      case 1:
         try {
            Utils.openUrl(link);
         } catch (Exception var3) {
            var3.printStackTrace();
            urlIssue = true;
            return;
         }

         shownGui = true;
         PizzaClient.displayScreen = new GuiMainMenu();
      }

   }

   public static void checkForUpdates() {
      if (PizzaClient.response != null) {
         try {
            JsonObject obj = PizzaClient.response.get("updater").getAsJsonObject();
            newVersion = obj.get("version").getAsString();
            if (!"1.1.3".equalsIgnoreCase(newVersion)) {
               link = obj.get("url").getAsString();
               String featureString = obj.get("new").getAsString();
               splitString = featureString.split("\n");

               for(int i = 0; i < splitString.length; ++i) {
                  splitString[i] = splitString[i].replace("\n", "").replace("\r", "").replace("+ ", "§a+ ").replace("= ", "§f= ").replace("- ", "§c- ");
               }

               PizzaClient.displayScreen = new UpdateGui();
            }
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

   }
}
