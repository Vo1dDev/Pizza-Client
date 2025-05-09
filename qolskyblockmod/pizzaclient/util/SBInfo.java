package qolskyblockmod.pizzaclient.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.Iterator;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.SendChatMessageEvent;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.util.handler.ScoreboardHandler;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class SBInfo {
   private long lastManualLocRaw;
   private long lastLocRaw;
   private long joinedWorld;
   private String mode;
   private int ticks = 1;
   public static boolean bossSpawned;
   public static boolean inSkyblock;

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      inSkyblock = false;
      this.lastLocRaw = 0L;
      this.mode = null;
      this.joinedWorld = System.currentTimeMillis();
      this.ticks = 1;
   }

   @SubscribeEvent
   public void onSendChatMessage(SendChatMessageEvent event) {
      if (event.message.trim().equals("/locraw")) {
         this.lastManualLocRaw = System.currentTimeMillis();
      }

   }

   @SubscribeEvent(
      receiveCanceled = true
   )
   public void onRecieveChat(ClientChatReceivedEvent event) {
      if (event.type != 2) {
         String unformatted = event.message.func_150260_c();
         if (unformatted.startsWith("{") && unformatted.endsWith("}")) {
            try {
               JsonObject obj = (JsonObject)(new Gson()).fromJson(unformatted, JsonObject.class);
               if (obj.has("server")) {
                  this.mode = "notnull";
                  if (System.currentTimeMillis() - this.lastManualLocRaw >= 2000L) {
                     event.setCanceled(true);
                  }

                  if (obj.has("gametype") && obj.get("gametype").getAsString().equals("SKYBLOCK")) {
                     inSkyblock = true;
                     String mode = obj.get("mode").getAsString();
                     this.mode = mode;
                     if (mode != null) {
                        byte var6 = -1;
                        switch(mode.hashCode()) {
                        case -1573913147:
                           if (mode.equals("foraging_1")) {
                              var6 = 4;
                           }
                           break;
                        case -1359040508:
                           if (mode.equals("mining_3")) {
                              var6 = 2;
                           }
                           break;
                        case -612688472:
                           if (mode.equals("combat_3")) {
                              var6 = 5;
                           }
                           break;
                        case -413973157:
                           if (mode.equals("crystal_hollows")) {
                              var6 = 1;
                           }
                           break;
                        case 2010421946:
                           if (mode.equals("dungeon")) {
                              var6 = 3;
                           }
                           break;
                        case 2124767295:
                           if (mode.equals("dynamic")) {
                              var6 = 0;
                           }
                        }

                        switch(var6) {
                        case 0:
                           PizzaClient.location = Locations.PRIVATEISLAND;
                           break;
                        case 1:
                           PizzaClient.location = Locations.CHOLLOWS;
                           break;
                        case 2:
                           PizzaClient.location = Locations.DWARVENMINES;
                           return;
                        case 3:
                           PizzaClient.location = Locations.DUNGEON;
                           break;
                        case 4:
                           PizzaClient.location = Locations.PARK;
                           break;
                        case 5:
                           PizzaClient.location = Locations.END;
                           break;
                        default:
                           PizzaClient.location = Locations.NOTNULL;
                        }
                     } else {
                        PizzaClient.location = Locations.NULL;
                     }
                  } else {
                     inSkyblock = false;
                  }
               }
            } catch (Exception var7) {
               inSkyblock = false;
               PizzaClient.location = Locations.NULL;
               var7.printStackTrace();
            }
         }

      }
   }

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (PizzaClient.mc.field_71441_e != null) {
         if (this.mode == null && System.currentTimeMillis() - this.joinedWorld >= 1500L && System.currentTimeMillis() - this.lastLocRaw >= 5000L && isOnHypixel()) {
            this.lastLocRaw = System.currentTimeMillis();
            PizzaClient.mc.field_71439_g.func_71165_d("/locraw");
         }

         if (this.ticks % 20 == 0) {
            if (isOnHypixel()) {
               isInSkyblock();
               checkForSlayerBoss();
               getLocation();
            }

            this.ticks = 0;
         }

         ++this.ticks;
      }
   }

   public static boolean isInSkyblock() {
      ScoreObjective scoreboardObj = PizzaClient.mc.field_71441_e.func_96441_U().func_96539_a(1);
      if (scoreboardObj != null) {
         if (ScoreboardHandler.cleanSB(scoreboardObj.func_96678_d()).startsWith("SKYBLOCK")) {
            inSkyblock = true;
            return true;
         } else {
            inSkyblock = false;
            PizzaClient.location = Locations.NULL;
            return false;
         }
      } else {
         inSkyblock = false;
         return false;
      }
   }

   public static void getLocation() {
      if (inSkyblock) {
         Iterator var0 = ScoreboardHandler.getSidebarLines().iterator();

         while(var0.hasNext()) {
            String s = (String)var0.next();
            if (s.startsWith(" §7⏣")) {
               String sCleaned = ScoreboardHandler.cleanSB(s);
               String var3 = sCleaned.substring(sCleaned.indexOf("") + 2);
               byte var4 = -1;
               switch(var3.hashCode()) {
               case -2064978723:
                  if (var3.equals("Jungle")) {
                     var4 = 5;
                  }
                  break;
               case -1027402425:
                  if (var3.equals("Precursor Remnants")) {
                     var4 = 7;
                  }
                  break;
               case -725141854:
                  if (var3.equals("Your Island")) {
                     var4 = 2;
                  }
                  break;
               case 641884637:
                  if (var3.equals("Void Sepulture")) {
                     var4 = 0;
                  }
                  break;
               case 1143013776:
                  if (var3.equals("Mithril Deposits")) {
                     var4 = 4;
                  }
                  break;
               case 1178975842:
                  if (var3.equals("Dark Thicket")) {
                     var4 = 3;
                  }
                  break;
               case 1506266854:
                  if (var3.equals("Goblin Holdout")) {
                     var4 = 6;
                  }
                  break;
               case 2018322424:
                  if (var3.equals("The Catacombs")) {
                     var4 = 1;
                  }
                  break;
               case 2101343758:
                  if (var3.equals("Divan's Gateway")) {
                     var4 = 8;
                  }
               }

               switch(var4) {
               case 0:
                  PizzaClient.location = Locations.END;
                  return;
               case 1:
                  PizzaClient.location = Locations.DUNGEON;
                  return;
               case 2:
                  PizzaClient.location = Locations.PRIVATEISLAND;
                  return;
               case 3:
                  PizzaClient.location = Locations.PARK;
                  return;
               case 4:
                  PizzaClient.location = Locations.CHOLLOWS;
                  return;
               case 5:
                  PizzaClient.location = Locations.CHOLLOWS;
                  return;
               case 6:
                  PizzaClient.location = Locations.CHOLLOWS;
                  return;
               case 7:
                  PizzaClient.location = Locations.CHOLLOWS;
                  return;
               case 8:
                  PizzaClient.location = Locations.DWARVENMINES;
                  return;
               default:
                  return;
               }
            }
         }
      }

   }

   public static void checkForSlayerBoss() {
      if (inSkyblock) {
         Iterator var0 = ScoreboardHandler.getSidebarLines().iterator();

         while(var0.hasNext()) {
            String s = (String)var0.next();
            if (ScoreboardHandler.cleanSB(s).contains("Slay the boss!")) {
               bossSpawned = true;
               return;
            }
         }
      }

      bossSpawned = false;
   }

   public static boolean isOnHypixel() {
      if (!PizzaClient.mc.func_71356_B()) {
         if (PizzaClient.mc.field_71439_g.func_142021_k() != null && PizzaClient.mc.field_71439_g.func_142021_k().toLowerCase().contains("hypixel")) {
            return true;
         } else {
            return PizzaClient.mc.func_147104_D() != null && PizzaClient.mc.func_147104_D().field_78845_b.toLowerCase().contains("hypixel");
         }
      } else {
         return false;
      }
   }
}
