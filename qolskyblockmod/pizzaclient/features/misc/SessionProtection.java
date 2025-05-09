package qolskyblockmod.pizzaclient.features.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.util.Iterator;
import java.util.Map.Entry;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.config.ConfigFile;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.exceptions.RatException;

public class SessionProtection {
   public static String changed;
   public static boolean changedToken;

   public SessionProtection(SessionProtection a) {
      throw new RatException();
   }

   public static void changeLauncherAccounts() {
      File file = new File(PizzaClient.mc.field_71412_D, "launcher_accounts.json");
      if (file.exists()) {
         try {
            JsonObject json = new JsonObject();
            Iterator var2 = Utils.getJson(file).getAsJsonObject().get("accounts").getAsJsonObject().entrySet().iterator();

            while(true) {
               while(true) {
                  Entry entry;
                  JsonElement e;
                  do {
                     if (!var2.hasNext()) {
                        JsonObject content = new JsonObject();
                        content.add("accounts", json);
                        ConfigFile.write(content, file);
                        return;
                     }

                     entry = (Entry)var2.next();
                     e = (JsonElement)entry.getValue();
                  } while(!(e instanceof JsonObject));

                  JsonObject object = e.getAsJsonObject();
                  if (object.has("accessToken")) {
                     JsonObject sub = new JsonObject();
                     Iterator var7 = object.entrySet().iterator();

                     while(var7.hasNext()) {
                        Entry<String, JsonElement> entry1 = (Entry)var7.next();
                        String s = (String)entry1.getKey();
                        if (s.equals("accessToken")) {
                           sub.addProperty(s, "");
                        } else {
                           sub.add(s, object.get(s));
                        }
                     }

                     json.add((String)entry.getKey(), sub);
                  } else {
                     json.add((String)entry.getKey(), object);
                  }
               }
            }
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }
   }
}
