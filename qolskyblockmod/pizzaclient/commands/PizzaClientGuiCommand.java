package qolskyblockmod.pizzaclient.commands;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.gui.LocationEditGui;
import qolskyblockmod.pizzaclient.gui.StartGui;
import qolskyblockmod.pizzaclient.util.Utils;

public class PizzaClientGuiCommand extends CommandBase {
   public String func_71517_b() {
      return "pizzaclient";
   }

   public List<String> func_71514_a() {
      return Lists.newArrayList(new String[]{"pizza"});
   }

   public String func_71518_a(ICommandSender arg0) {
      return "/" + this.func_71517_b();
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length == 0) {
         PizzaClient.displayScreen = new StartGui();
      } else {
         if (args.length == 1) {
            String var3 = args[0];
            byte var4 = -1;
            switch(var3.hashCode()) {
            case -1764813676:
               if (var3.equals("editlocations")) {
                  var4 = 4;
               }
               break;
            case -1354792126:
               if (var3.equals("config")) {
                  var4 = 0;
               }
               break;
            case -602535288:
               if (var3.equals("commands")) {
                  var4 = 3;
               }
               break;
            case -57791681:
               if (var3.equals("locationedit")) {
                  var4 = 5;
               }
               break;
            case 3002386:
               if (var3.equals("arab")) {
                  var4 = 1;
               }
               break;
            case 679453368:
               if (var3.equals("arabfunny")) {
                  var4 = 2;
               }
               break;
            case 1671380268:
               if (var3.equals("discord")) {
                  var4 = 6;
               }
            }

            switch(var4) {
            case 0:
               PizzaClient.displayScreen = new StartGui();
               break;
            case 1:
               Utils.openUrl((String)ArabFunnyCommand.arabfunny.get(Utils.random.nextInt(ArabFunnyCommand.arabfunny.size())));
               break;
            case 2:
               Utils.openUrl((String)ArabFunnyCommand.arabfunny.get(Utils.random.nextInt(ArabFunnyCommand.arabfunny.size())));
               break;
            case 3:
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + PizzaClient.config.commandsList));
               PizzaClient.mc.field_71439_g.func_71053_j();
               break;
            case 4:
               PizzaClient.mc.func_147108_a(new LocationEditGui());
               break;
            case 5:
               PizzaClient.mc.func_147108_a(new LocationEditGui());
               break;
            case 6:
               Utils.openUrl("https://discord.gg/NWeacCr3B8");
            }
         }

      }
   }
}
