package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util;

import java.util.Comparator;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathNode;

public class NodeCostComparator implements Comparator<PathNode> {
   public int compare(PathNode o1, PathNode o2) {
      return o1.cost - o2.cost;
   }
}
