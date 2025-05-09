package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder;

import java.util.ArrayList;
import java.util.List;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.MathUtil;

public class PathNode {
   public final List<BetterBlockPos> moves;
   public final BetterBlockPos currentPos;
   public int cost;

   public PathNode(BetterBlockPos pos, List<BetterBlockPos> moves) {
      this.currentPos = pos;
      this.moves = new ArrayList(moves);
      this.moves.add(this.currentPos);
      this.cost = MathUtil.abs(this.currentPos.field_177962_a - PathFinder.path.goalPos.field_177962_a) + MathUtil.abs(this.currentPos.field_177961_c - PathFinder.path.goalPos.field_177961_c);
   }

   public PathNode() {
      this.currentPos = PathFinder.path.currentPos;
      this.moves = new ArrayList();
      PathFinder.path.checked.add(this.currentPos);
   }

   public int getCost() {
      return MathUtil.abs(this.currentPos.field_177962_a - PathFinder.path.goalPos.field_177962_a) + MathUtil.abs(this.currentPos.field_177961_c - PathFinder.path.goalPos.field_177961_c);
   }
}
