package qolskyblockmod.pizzaclient.commands;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import qolskyblockmod.pizzaclient.core.config.ConfigFile;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.MithrilMarkers;
import qolskyblockmod.pizzaclient.util.Utils;

public class AotvTestCommand extends CommandBase {
   public String func_71517_b() {
      return "aotvtest";
   }

   public String func_71518_a(ICommandSender sender) {
      return null;
   }

   public List<String> func_71514_a() {
      return Lists.newArrayList(new String[]{"markertest"});
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      if (args.length == 1 && args[0].equals("clear")) {
         MithrilMarkers.markers.clear();
         ConfigFile.write(new JsonObject(), MithrilMarkers.configFile);
         sender.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Cleared all the aotv marks!"));
      } else {
         sender.func_145747_a(new ChatComponentText("Current list of markers: \n" + MithrilMarkers.markers));
         (new Thread(MithrilMarkers::run)).start();
      }
   }

   public int func_82362_a() {
      return 0;
   }
}
