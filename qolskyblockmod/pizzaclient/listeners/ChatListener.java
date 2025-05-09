package qolskyblockmod.pizzaclient.listeners;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.dungeons.AutoSpiritLeap;
import qolskyblockmod.pizzaclient.features.dungeons.DungeonFeatures;
import qolskyblockmod.pizzaclient.features.dungeons.QuizAura;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class ChatListener {
   private final Pattern ANTI_FURRY_PATTERN = Pattern.compile("[oqua>]w[oqua<]|nya|meow", 2);

   @SubscribeEvent(
      priority = EventPriority.HIGHEST,
      receiveCanceled = true
   )
   public void onChatMessage(ClientChatReceivedEvent event) {
      if (PizzaClient.config.antiFurry) {
         Matcher matcher = this.ANTI_FURRY_PATTERN.matcher(event.message.func_150260_c());
         if (matcher.find()) {
            event.message = new ChatComponentText(matcher.replaceAll(PizzaClient.config.antiFurryCensor));
         }
      }

      if (event.type != 2) {
         String unformatted = StringUtils.func_76338_a(event.message.func_150260_c());
         if (PizzaClient.config.autoPickonimbus && unformatted.equals("Oh no! Your Pickonimbus 2000 broke!")) {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > Swapping to pickonimbus..."));
            PlayerUtil.findPickonimbus();
         } else if (unformatted.startsWith("Dungeon Finder > BennettArthur joined the dungeon group!")) {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Shitters out"));
            PizzaClient.mc.field_71439_g.func_71165_d("/p kick BennettArthur");
         } else {
            if (PizzaClient.location == Locations.DUNGEON) {
               if (unformatted.startsWith("[BOSS] ")) {
                  if (unformatted.equals("[BOSS] Livid: I respect you for making it to here, but I'll be your undoing.")) {
                     DungeonFeatures.shouldLividsSpawn = true;
                     return;
                  }

                  if (unformatted.startsWith("[BOSS] Necron: FINE! LET'S MOVE TO SOMEWHERE ELSE") && PizzaClient.config.autoClipGhostblocks) {
                     DungeonFeatures.clipGhostBlocks();
                  }

                  return;
               }

               if (PizzaClient.config.quizAura) {
                  if (!QuizAura.isQuizActive) {
                     if (unformatted.equals("[STATUE] Oruo the Omniscient: Answer incorrectly, and your moment of ineptitude will live on for generations.")) {
                        QuizAura.isQuizActive = true;
                        return;
                     }
                  } else {
                     QuizAura.onChat(unformatted);
                  }
               }

               if (PizzaClient.config.funnyDungeonFail) {
                  if (unformatted.startsWith("PUZZLE FAIL! " + PizzaClient.username)) {
                     (new Thread(() -> {
                        try {
                           PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.getJson(new URL("https://api.myip.com/")).getAsJsonObject().get("ip").getAsString()));
                        } catch (Exception var1) {
                           var1.printStackTrace();
                           PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to add the ip to chat :("));
                        }

                     })).start();
                  }

                  return;
               }

               if (PizzaClient.config.autoLeapDoor && unformatted.contains(" opened a WITHER")) {
                  AutoSpiritLeap.leapName = unformatted.substring(0, unformatted.indexOf(" "));
               }
            } else {
               if (unformatted.startsWith("Your pass to the Crystal Hollows will expire ")) {
                  PizzaClient.mc.field_71439_g.func_71165_d("/purchasecrystallhollowspass");
                  return;
               }

               if (MacroBuilder.toggled) {
                  if (unformatted.startsWith("You died")) {
                     MacroBuilder.currentMacro.onDeath();
                     return;
                  }

                  MacroBuilder.currentMacro.onChat(unformatted);
               }
            }

         }
      }
   }
}
