package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.finder.BlockFinder;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.NukerBase;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.SimpleNuker;
import qolskyblockmod.pizzaclient.util.VecUtil;

public abstract class AdvancedMiningBot extends MiningBot {
   private Vec3 vec;
   private boolean isPathFree;

   public AdvancedMiningBot(Vec3 from, Vec3 to) {
      super(from, to);
   }

   public AdvancedMiningBot(BetterBlockPos from, BetterBlockPos to) {
      super(from, to);
   }

   public AdvancedMiningBot(BetterBlockPos to) {
      super(to);
   }

   public AdvancedMiningBot(Vec3 to) {
      super(to);
   }

   public abstract BlockFinder getFinder();

   public boolean addBlock(PathNode node) {
      return false;
   }

   public void move() {
      if (this.moves.size() == 1) {
         this.moves.clear();
         this.mineTargetBlock();
      } else if (this.isPathFree) {
         if (((BetterBlockPos)this.moves.get(0)).equals(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v))) {
            this.isPathFree = false;
            this.moves.remove(0);
         } else {
            this.useDefaultMovement();
         }
      } else {
         if (NukerBase.nuker == null) {
            NukerBase.nuker = this;
         }

      }
   }

   public void mineTargetBlock() {
      BlockFinder finder = this.getFinder();

      while(true) {
         while(NukerBase.nuker != null) {
         }

         if (!SimpleNuker.hasBlock(finder)) {
            this.shutdown();
            return;
         }

         (new SimpleNuker(finder)).enqueue();
      }
   }

   public Block getBlockToMine() {
      return PizzaClient.mc.field_71441_e.func_180495_p(this.goalPos).func_177230_c();
   }

   public boolean isBlockValid(BlockPos pos) {
      return this.getFinder().isValid(pos);
   }

   public boolean nuke(boolean noSwing) {
      return this.nuke(this.vec, noSwing);
   }

   public boolean isVecValid() {
      if (this.vec == null) {
         return false;
      } else {
         BlockPos pos = new BlockPos(this.vec);
         return VecUtil.canReachBlock(pos) && PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a ? this.getFinder().isValid(pos) : false;
      }
   }

   public boolean findVec() {
      if (this.moves.size() == 0) {
         return false;
      } else {
         BetterBlockPos player = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
         BetterBlockPos next = (BetterBlockPos)this.moves.get(0);
         if (next.equals(player)) {
            this.moves.remove(0);
            if (this.moves.size() == 0) {
               return false;
            }

            next = (BetterBlockPos)this.moves.get(0);
         }

         int i;
         BetterBlockPos pos;
         if (player.isSameXandZ(next)) {
            for(i = 0; i < 5; ++i) {
               pos = next.down(i);
               if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a && VecUtil.canReachBlock(pos)) {
                  this.vec = this.getRandomAABBPoint(pos);
                  return true;
               }
            }

            return false;
         } else if (next.field_177960_b > player.field_177960_b) {
            for(i = 0; i < 2; ++i) {
               pos = next.up(i);
               if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
                  this.vec = this.getRandomAABBPoint(pos);
                  return true;
               }
            }

            BlockPos pos = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + 2.0D, PizzaClient.mc.field_71439_g.field_70161_v);
            if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
               this.vec = this.getRandomAABBPoint(pos);
               return true;
            } else {
               return false;
            }
         } else if (next.field_177960_b == player.field_177960_b) {
            for(i = 0; i < 2; ++i) {
               pos = next.up(i);
               if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
                  this.vec = this.getRandomAABBPoint(pos);
                  return true;
               }
            }

            return false;
         } else {
            for(i = 0; i < 3; ++i) {
               pos = next.down(i);
               if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
                  this.vec = this.getRandomAABBPoint(pos);
                  return true;
               }
            }

            return false;
         }
      }
   }

   public void onNoVecAvailable() {
      this.isPathFree = true;
   }

   private boolean isMineable(Block block) {
      return true;
   }
}
