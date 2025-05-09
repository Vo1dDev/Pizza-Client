package qolskyblockmod.pizzaclient.commands;

import java.util.Collections;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import qolskyblockmod.pizzaclient.PizzaClient;

public class AutoPetCommand extends CommandBase {
   public static String clickSlot;

   public String func_71517_b() {
      return "autopet";
   }

   public List<String> func_71514_a() {
      return Collections.singletonList("autopets");
   }

   public void func_71515_b(ICommandSender sender, String[] args) throws CommandException {
      PizzaClient.mc.field_71439_g.func_71165_d("/pets");
      StringBuilder sb = new StringBuilder();
      String[] var4 = args;
      int var5 = args.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String s = var4[var6];
         sb.append(s.toLowerCase()).append(" ");
      }

      clickSlot = sb.substring(0, sb.toString().length() - 1);
   }

   public String func_71518_a(ICommandSender sender) {
      return null;
   }

   public int func_82362_a() {
      return 0;
   }
}
