package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base;

import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.INuker;

public abstract class MiningBot extends PathBase implements INuker {
   public MiningBot(Vec3 from, Vec3 to) {
      super(from, to);
   }

   public MiningBot(BetterBlockPos from, BetterBlockPos to) {
      super(from, to);
   }

   public MiningBot(BetterBlockPos to) {
      super(to);
   }

   public MiningBot(Vec3 to) {
      super(to);
   }
}
