package qolskyblockmod.pizzaclient.commands;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.features.keybinds.CustomKeybind;
import qolskyblockmod.pizzaclient.features.keybinds.KeybindAction;
import qolskyblockmod.pizzaclient.features.keybinds.MacroKeybind;

public class ItemMacroCommand extends CommandBase {
   public String func_71517_b() {
      return "itemmacro";
   }

   public String func_71518_a(ICommandSender sender) {
      return EnumChatFormatting.RED + "Usage: /itemmacro to see all the item macros or /itemmacro add [part of the item name] + [delay in ms] + [\"left\" or \"right\"] or /itemmacro remove [name]";
   }

   public List<String> func_71514_a() {
      return Lists.newArrayList(new String[]{"itemmacros", "itemmacrokey", "itemmacrokeys"});
   }

   public void sendMessage(ICommandSender sender) {
      sender.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "Usage: /itemmacro to see all the item macros or /itemmacro add [part of the item name] + [delay in ms] + [\"left\" or \"right\"] or /itemmacro remove [name]"));
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      switch(args.length) {
      case 0:
         sender.func_145747_a(new ChatComponentText(MacroKeybind.itemMacros.toString()));
         break;
      case 1:
         if (args[0].equalsIgnoreCase("list")) {
            sender.func_145747_a(new ChatComponentText(MacroKeybind.itemMacros.toString()));
            return;
         }

         this.sendMessage(sender);
         break;
      default:
         int delay;
         int i;
         StringBuilder sb;
         KeybindAction action;
         if (args[0].equalsIgnoreCase("add")) {
            delay = -1;
            sb = new StringBuilder(args[1]);
            i = 2;

            while(i < 11) {
               try {
                  delay = Integer.parseInt(args[i]);
                  break;
               } catch (Exception var7) {
                  sb.append(" ");
                  sb.append(args[i]);
                  ++i;
               }
            }

            if (delay <= -1) {
               sender.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "The item displayname was either too long or the delay was invalid/non existant."));
            }

            action = KeybindAction.getActionFromString(args[i + 1]);
            if (action == KeybindAction.UNKNOWN) {
               sender.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "The third parameter must equal to left or right."));
               return;
            }

            MacroKeybind.itemMacros.put(sb.toString().toLowerCase(), new CustomKeybind(delay, action));
            sender.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Successfully added " + sb + " to the list."));
            MacroKeybind.saveConfig();
         } else if (args[0].equalsIgnoreCase("remove")) {
            StringBuilder sb = new StringBuilder(args[1]);

            for(i = 2; i < args.length; ++i) {
               sb.append(" ");
               sb.append(args[i]);
            }

            String name = sb.toString().toLowerCase();
            if (MacroKeybind.itemMacros.containsKey(name)) {
               MacroKeybind.itemMacros.remove(name);
               sender.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Successfully removed " + sb + " from the list."));
               MacroKeybind.saveConfig();
            } else {
               sender.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "You need to use the exact name of the item that you want to remove"));
            }
         } else {
            delay = -1;
            sb = new StringBuilder(args[0]);
            i = 1;

            while(i < 10) {
               try {
                  delay = Integer.parseInt(args[i]);
                  break;
               } catch (Exception var8) {
                  sb.append(" ");
                  sb.append(args[i]);
                  ++i;
               }
            }

            if (delay <= -1) {
               sender.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "The item displayname was either too long or the delay was invalid/non existant."));
            }

            action = KeybindAction.getActionFromString(args[i + 1]);
            if (action == KeybindAction.UNKNOWN) {
               sender.func_145747_a(new ChatComponentText(EnumChatFormatting.RED + "The third parameter must equal to left or right."));
               return;
            }

            MacroKeybind.itemMacros.put(sb.toString().toLowerCase(), new CustomKeybind(delay, action));
            sender.func_145747_a(new ChatComponentText(EnumChatFormatting.GREEN + "Successfully added " + sb + " to the list."));
            MacroKeybind.saveConfig();
         }
      }

   }
}
