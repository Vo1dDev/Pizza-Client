package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util;

import java.util.Comparator;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.PathBase;

public class PathComparator implements Comparator<PathBase> {
   public int compare(PathBase o1, PathBase o2) {
      return (int)(o1.currentPos.distanceToSq(o1.goalPos) - o2.currentPos.distanceToSq(o2.goalPos));
   }
}
