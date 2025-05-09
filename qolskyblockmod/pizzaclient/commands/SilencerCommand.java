package qolskyblockmod.pizzaclient.commands;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;

public class SilencerCommand extends CommandBase implements ICommand {
   public static Set<String> silencedSounds = new HashSet();
   private static File saveFile;

   public SilencerCommand() {
      saveFile = new File(PizzaClient.modDir, "soundsilencer.json");
      reloadSave();
   }

   public String func_71517_b() {
      return "silencer";
   }

   public List<String> func_71514_a() {
      return Lists.newArrayList(new String[]{"soundsilencer", "silencesound", "silence"});
   }

   public String func_71518_a(ICommandSender sender) {
      return EnumChatFormatting.RED + "Usages: /silencer + add + [sound]\n" + EnumChatFormatting.RED + "/silencer + remove + all or [name]\n" + EnumChatFormatting.RED + "/silencer list";
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      EntityPlayerSP player = (EntityPlayerSP)sender;
      switch(args.length) {
      case 1:
         if (args[0].equalsIgnoreCase("list")) {
            player.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Silenced sounds: \n" + EnumChatFormatting.GOLD + silencedSounds));
            return;
         }

         player.func_145747_a(new ChatComponentText(this.func_71518_a(sender)));
         break;
      case 2:
         String var4 = args[0].toLowerCase();
         byte var5 = -1;
         switch(var4.hashCode()) {
         case -934610812:
            if (var4.equals("remove")) {
               var5 = 1;
            }
            break;
         case 96417:
            if (var4.equals("add")) {
               var5 = 0;
            }
         }

         switch(var5) {
         case 0:
            if (args[1] != null) {
               String lowerCase = args[1].toLowerCase(Locale.ROOT);
               if (!silencedSounds.contains(lowerCase)) {
                  silencedSounds.add(lowerCase);
                  writeSave();
                  player.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Successfully added " + EnumChatFormatting.GOLD + args[1].toLowerCase() + EnumChatFormatting.GREEN + "."));
                  return;
               }

               player.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + args[1] + " is already added!"));
               return;
            }

            player.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "Usages: /silencer + add + [sound]"));
            return;
         case 1:
            if (args[1].equalsIgnoreCase("all")) {
               silencedSounds.clear();
               writeSave();
               player.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Cleared all silencers."));
               return;
            }

            if (silencedSounds.contains(args[1])) {
               silencedSounds.remove(args[1]);
               writeSave();
               player.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Successfully removed the sound \"" + args[1] + "\" from the silencer!"));
               return;
            }

            player.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "the sound \"" + args[1] + "\" does not exist in the silencer!"));
            return;
         default:
            player.func_145747_a(new ChatComponentText(this.func_71518_a(sender)));
            return;
         }
      default:
         player.func_145747_a(new ChatComponentText(this.func_71518_a(sender)));
      }

   }

   public static void reloadSave() {
      silencedSounds.clear();

      JsonArray dataArray;
      try {
         FileReader in = new FileReader(saveFile);
         Throwable var35 = null;

         try {
            dataArray = (JsonArray)PizzaClient.gson.fromJson(in, JsonArray.class);
            silencedSounds.addAll(Arrays.asList(Utils.getStringArrayFromJsonArray(dataArray)));
         } catch (Throwable var30) {
            var35 = var30;
            throw var30;
         } finally {
            if (in != null) {
               if (var35 != null) {
                  try {
                     in.close();
                  } catch (Throwable var29) {
                     var35.addSuppressed(var29);
                  }
               } else {
                  in.close();
               }
            }

         }
      } catch (Exception var34) {
         dataArray = new JsonArray();

         try {
            FileWriter writer = new FileWriter(saveFile);
            Throwable var3 = null;

            try {
               PizzaClient.gson.toJson(dataArray, writer);
            } catch (Throwable var28) {
               var3 = var28;
               throw var28;
            } finally {
               if (writer != null) {
                  if (var3 != null) {
                     try {
                        writer.close();
                     } catch (Throwable var27) {
                        var3.addSuppressed(var27);
                     }
                  } else {
                     writer.close();
                  }
               }

            }
         } catch (Exception var32) {
         }
      }

   }

   public static void writeSave() {
      try {
         FileWriter writer = new FileWriter(saveFile);
         Throwable var1 = null;

         try {
            JsonArray arr = new JsonArray();
            Iterator var3 = silencedSounds.iterator();

            while(var3.hasNext()) {
               String itemId = (String)var3.next();
               arr.add(new JsonPrimitive(itemId));
            }

            PizzaClient.gson.toJson(arr, writer);
         } catch (Throwable var13) {
            var1 = var13;
            throw var13;
         } finally {
            if (writer != null) {
               if (var1 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var12) {
                     var1.addSuppressed(var12);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (Exception var15) {
      }

   }
}
