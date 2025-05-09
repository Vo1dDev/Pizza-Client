package qolskyblockmod.pizzaclient.core.guioverlay;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.config.ConfigFile;
import qolskyblockmod.pizzaclient.gui.LocationEditGui;

public class GuiManager extends ConfigFile {
   public static final Map<String, GuiLocation> GUIPOSITIONS = new HashMap();
   public static final List<GuiElement> elements = new ArrayList();
   public static final File configFile;

   public GuiManager() {
      super(configFile);
   }

   public static void registerElement(GuiElement e) {
      elements.add(e);
   }

   public void read() {
      try {
         FileReader in = new FileReader(configFile);
         Throwable var2 = null;

         try {
            JsonObject file = (JsonObject)PizzaClient.gson.fromJson(in, JsonObject.class);
            Iterator var4 = file.entrySet().iterator();

            while(var4.hasNext()) {
               Entry<String, JsonElement> e = (Entry)var4.next();
               GUIPOSITIONS.put(e.getKey(), new GuiLocation(((JsonElement)e.getValue()).getAsJsonObject().get("x").getAsFloat(), ((JsonElement)e.getValue()).getAsJsonObject().get("y").getAsFloat()));
            }
         } catch (Throwable var14) {
            var2 = var14;
            throw var14;
         } finally {
            if (in != null) {
               if (var2 != null) {
                  try {
                     in.close();
                  } catch (Throwable var13) {
                     var2.addSuppressed(var13);
                  }
               } else {
                  in.close();
               }
            }

         }
      } catch (Exception var16) {
         var16.printStackTrace();
      }

   }

   public static void saveConfig() {
      Iterator var0 = elements.iterator();

      while(var0.hasNext()) {
         GuiElement e = (GuiElement)var0.next();
         GUIPOSITIONS.put(e.name, e.pos);
      }

      write(GUIPOSITIONS, configFile);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST,
      receiveCanceled = true
   )
   public void renderPlayerInfo(Post event) {
      if (PizzaClient.mc.field_71441_e != null && PizzaClient.mc.field_71456_v instanceof GuiIngameForge && !(PizzaClient.mc.field_71462_r instanceof LocationEditGui) && event.type == ElementType.HOTBAR) {
         Iterator var2 = elements.iterator();

         while(var2.hasNext()) {
            GuiElement e = (GuiElement)var2.next();
            e.render();
         }

      }
   }

   static {
      configFile = new File(PizzaClient.modDir, "guipositions.json");
   }
}
