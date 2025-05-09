package qolskyblockmod.pizzaclient.features.player;

import java.util.List;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.Pathfinding;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.TPAuraPath;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.BasePathfinder;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathFinderNoMovement;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class TPAuraHelper {
   public static final int FLY_DURATION = 1300;
   public static TPAuraPath path;
   private static long lastFlyDisable;

   public static boolean isFlyActive() {
      return System.currentTimeMillis() - lastFlyDisable < 1300L;
   }

   public static void update() {
      lastFlyDisable = System.currentTimeMillis();
   }

   public static void teleport() {
      if (path.moves.size() == 0) {
         path.onEnd();
         disable();
      } else {
         List<BetterBlockPos> subList = path.moves.subList(0, MathUtil.min(path.moves.size(), path.getSpeed() - 1));
         BetterBlockPos pos = (BetterBlockPos)subList.get(subList.size() - 1);
         PizzaClient.mc.field_71439_g.func_70107_b((double)pos.field_177962_a + 0.5D, (double)pos.field_177960_b, (double)pos.field_177961_c + 0.5D);
         subList.clear();
      }
   }

   public static void runPathfinder(BetterBlockPos pos) {
      (new Thread(() -> {
         path = (TPAuraPath)(new PathFinderNoMovement(new TPAuraPath(Utils.getClosestInRange(pos)))).calculateAndGetPath();
      })).start();
   }

   public static void runPathfinder(BetterBlockPos pos, Runnable runnable) {
      (new Thread(() -> {
         path = (TPAuraPath)(new PathFinderNoMovement((new TPAuraPath(Utils.getClosestInRange(pos))).setRunnable(runnable))).calculateAndGetPath();
      })).start();
   }

   public static void disable() {
      Pathfinding.unregister();
      BasePathfinder.path = null;
      path = null;
      PizzaClient.mc.field_71439_g.field_70159_w = PizzaClient.mc.field_71439_g.field_70179_y = 0.0D;
   }
}
