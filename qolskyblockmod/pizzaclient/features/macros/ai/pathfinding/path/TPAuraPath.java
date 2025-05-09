package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path;

import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.PathBase;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;

public final class TPAuraPath extends PathBase {
   private Runnable runnable;

   public TPAuraPath(Vec3 from, Vec3 to) {
      super(from, to);
   }

   public TPAuraPath(BetterBlockPos from, BetterBlockPos to) {
      super(from, to);
   }

   public TPAuraPath(BetterBlockPos to) {
      super(to);
   }

   public TPAuraPath(Vec3 to) {
      super(to);
   }

   public TPAuraPath setRunnable(Runnable runnable) {
      this.runnable = runnable;
      return this;
   }

   public void onEnd() {
      if (this.runnable != null) {
         this.runnable.run();
      }

   }

   public int getSpeed() {
      return 3;
   }

   public boolean addBlock(PathNode node) {
      return this.addBlockForAllDirections(node);
   }

   public void move() {
   }
}
