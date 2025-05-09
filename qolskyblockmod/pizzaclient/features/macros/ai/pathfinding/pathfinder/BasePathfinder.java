package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder;

import java.util.List;
import net.minecraftforge.common.MinecraftForge;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.Pathfinding;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.PathBase;

public abstract class BasePathfinder {
   public static List<PathNode> nodes;
   public static PathBase path;

   public BasePathfinder(PathBase path) {
      BasePathfinder.path = path;
      MinecraftForge.EVENT_BUS.unregister(Pathfinding.instance);
      nodes = null;
   }

   public void run() {
      this.run(true);
   }

   public void runNewThread() {
      (new Thread(this::run)).start();
   }

   public void shutdown() {
      path = null;
      Pathfinding.unregister();
   }

   public abstract boolean run(boolean var1);
}
