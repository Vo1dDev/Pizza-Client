package qolskyblockmod.pizzaclient.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.graphics.custom.names.RainbowString;

public class ReloadNamesCommand extends CommandBase {
   public String func_71517_b() {
      return "reloadnames";
   }

   public String func_71518_a(ICommandSender arg0) {
      return "/" + this.func_71517_b();
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      RainbowString.updateList();
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Reloading names!"));
   }
}
