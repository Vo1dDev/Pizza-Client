package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.Path;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.AStarPathfinder;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.BasePathfinder;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathFinder;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.RenderUtil;

public class Pathfinding {
   public static Pathfinding instance;

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (BasePathfinder.path != null && BasePathfinder.path.moves.size() != 0) {
         RenderUtil.drawRainbowPath(BasePathfinder.path.moves, 2.0F);
      } else {
         MinecraftForge.EVENT_BUS.unregister(instance);
      }
   }

   public static void runPathfinder(BetterBlockPos goal) {
      (new Thread(() -> {
         (new PathFinder(new Path(goal))).run();
      })).start();
   }

   public static void runAStarPathfinder(BetterBlockPos goal) {
      (new Thread(() -> {
         (new AStarPathfinder(new Path(goal))).run();
      })).start();
   }

   public static void unregister() {
      MinecraftForge.EVENT_BUS.unregister(instance);
   }

   public static void register() {
      MinecraftForge.EVENT_BUS.register(instance);
   }
}
