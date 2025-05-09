package qolskyblockmod.pizzaclient.commands;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.PizzaClient;

public class CustomToggleSprintCommand extends CommandBase implements ICommand {
   public String func_71517_b() {
      return "togglesprintmessage";
   }

   public String func_71518_a(ICommandSender sender) {
      return EnumChatFormatting.RED + "Usage: /" + this.func_71517_b() + " [Message] or /" + this.func_71517_b() + " reset";
   }

   public List<String> func_71514_a() {
      return Lists.newArrayList(new String[]{"customtogglesprint"});
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      EntityPlayerSP player = (EntityPlayerSP)sender;
      if (args.length == 0) {
         player.func_145747_a(new ChatComponentText(this.func_71518_a(sender)));
      } else if (args[0].equalsIgnoreCase("reset")) {
         PizzaClient.config.customToggleSprintName = "ToggleSprint (Toggled)";
         player.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Reset ToggleSprint message back to " + EnumChatFormatting.GOLD + PizzaClient.config.customToggleSprintName));
      } else {
         StringBuilder customMessage = new StringBuilder();
         String[] var5 = args;
         int var6 = args.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            String arg = var5[var7];
            customMessage.append(arg).append(" ");
         }

         PizzaClient.config.customToggleSprintName = customMessage.toString();
         player.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "ToggleSprint message is now set to " + EnumChatFormatting.GOLD + PizzaClient.config.customToggleSprintName));
      }
   }
}
