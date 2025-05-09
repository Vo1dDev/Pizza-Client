package qolskyblockmod.pizzaclient.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.Pathfinding;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.features.player.TPAuraHelper;

public class PathfindCommand extends CommandBase {
   public String func_71517_b() {
      return "pathfind";
   }

   public String func_71518_a(ICommandSender sender) {
      return null;
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      if (args.length >= 3) {
         int x = Integer.parseInt(args[0]);
         int y = Integer.parseInt(args[1]);
         int z = Integer.parseInt(args[2]);
         TPAuraHelper.update();
         if (args.length == 4) {
            Pathfinding.runAStarPathfinder(new BetterBlockPos(x, y, z));
         } else {
            Pathfinding.runPathfinder(new BetterBlockPos(x, y, z));
         }

      }
   }
}
