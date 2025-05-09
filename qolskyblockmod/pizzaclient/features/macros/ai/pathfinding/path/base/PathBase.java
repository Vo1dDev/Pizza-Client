package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.MovementType;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.Pathfinding;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathFinder;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;
import qolskyblockmod.pizzaclient.util.rotation.RotationUtil;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.SnapRotater;

public abstract class PathBase implements IPath {
   public BetterBlockPos goalPos;
   public BetterBlockPos currentPos;
   public boolean finished;
   public Set<BetterBlockPos> checked = new HashSet();
   public List<BetterBlockPos> moves = new ArrayList();

   public PathBase(Vec3 from, Vec3 to) {
      this.currentPos = new BetterBlockPos(from);
      this.goalPos = new BetterBlockPos(to);
   }

   public PathBase(BetterBlockPos from, BetterBlockPos to) {
      this.currentPos = from;
      this.goalPos = to;
   }

   public PathBase(BetterBlockPos to) {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.goalPos = to;
   }

   public PathBase(Vec3 to) {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.goalPos = new BetterBlockPos(to);
   }

   public void shutdown() {
      PathFinder.path = null;
      this.finished = true;
      Pathfinding.unregister();
      PizzaClient.rotater = null;
   }

   public final void addMovementJumping(BetterBlockPos current) {
      double nextTickX = PizzaClient.mc.field_71439_g.field_70165_t;
      double nextTickZ = PizzaClient.mc.field_71439_g.field_70161_v;
      double nextX = PizzaClient.mc.field_71439_g.field_70165_t + PizzaClient.mc.field_71439_g.field_70159_w;
      double nextZ = PizzaClient.mc.field_71439_g.field_70161_v + PizzaClient.mc.field_71439_g.field_70179_y;
      if (MathUtil.inBetween(nextX, (double)current.field_177962_a + 0.35D, (double)current.field_177962_a + 0.65D) && MathUtil.inBetween(nextZ, (double)current.field_177961_c + 0.35D, (double)current.field_177961_c + 0.65D)) {
         Movement.disableMovement();
      } else {
         EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
         if (!MathUtil.inBetween(nextTickZ, (double)current.field_177961_c + 0.35D, (double)current.field_177961_c + 0.65D)) {
            if (nextTickZ <= (double)current.field_177961_c + 0.35D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.BACKWARDS);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.FORWARDS);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.LEFT);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.RIGHT);
               }
            } else if (nextTickZ >= (double)current.field_177961_c + 0.65D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.FORWARDS);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.BACKWARDS);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.RIGHT);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.LEFT);
               }
            }
         }

         if (!MathUtil.inBetween(nextTickX, (double)current.field_177962_a + 0.35D, (double)current.field_177962_a + 0.65D)) {
            if (nextTickX <= (double)current.field_177962_a + 0.35D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.RIGHT);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.LEFT);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.BACKWARDS);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.FORWARDS);
               }
            } else if (nextTickX >= (double)current.field_177962_a + 0.65D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.LEFT);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.RIGHT);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.FORWARDS);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.BACKWARDS);
               }
            }
         }

         Movement.endMovement();
      }
   }

   public final void addMovement(BetterBlockPos current) {
      double nextTickX = PizzaClient.mc.field_71439_g.field_70165_t;
      double nextTickZ = PizzaClient.mc.field_71439_g.field_70161_v;
      if (!PizzaClient.mc.field_71439_g.field_70122_E) {
         this.addMovementJumping(current);
      } else {
         Movement.endMovement();
         EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
         if (!MathUtil.inBetween(nextTickZ, (double)current.field_177961_c + 0.35D, (double)current.field_177961_c + 0.65D)) {
            if (nextTickZ <= (double)current.field_177961_c + 0.35D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.BACKWARDS);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.FORWARDS);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.LEFT);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.RIGHT);
               }
            } else if (nextTickZ >= (double)current.field_177961_c + 0.65D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.FORWARDS);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.BACKWARDS);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.RIGHT);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.LEFT);
               }
            }
         }

         if (!MathUtil.inBetween(nextTickX, (double)current.field_177962_a + 0.35D, (double)current.field_177962_a + 0.65D)) {
            if (nextTickX <= (double)current.field_177962_a + 0.35D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.RIGHT);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.LEFT);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.BACKWARDS);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.FORWARDS);
               }
            } else if (nextTickX >= (double)current.field_177962_a + 0.65D) {
               switch(facing) {
               case NORTH:
                  Movement.addMovement(MovementType.LEFT);
                  break;
               case SOUTH:
                  Movement.addMovement(MovementType.RIGHT);
                  break;
               case WEST:
                  Movement.addMovement(MovementType.FORWARDS);
                  break;
               case EAST:
                  Movement.addMovement(MovementType.BACKWARDS);
               }
            }
         }

      }
   }

   public final void rotateIfNeeded() {
      if (this.moves.size() >= 2) {
         BetterBlockPos current = (BetterBlockPos)this.moves.get(0);
         if (PizzaClient.mc.field_71439_g.field_70122_E || !MathUtil.inBetween(PizzaClient.mc.field_71439_g.field_70165_t, (double)current.field_177962_a + 0.3D, (double)current.field_177962_a + 0.7D) || !MathUtil.inBetween(PizzaClient.mc.field_71439_g.field_70161_v, (double)current.field_177961_c + 0.3D, (double)current.field_177961_c + 0.7D)) {
            BetterBlockPos next = (BetterBlockPos)this.moves.get(1);
            EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
            if (!current.offset(facing).isSameXandZ(next) && !this.moves.contains(next.offset(facing)) && !this.moves.contains(next.up().offset(facing)) && !this.moves.contains(next.down().offset(facing))) {
               this.rotateToBlock(next);
            }
         }
      }

   }

   public final boolean isVerticalPassable(AxisAlignedBB aabb) {
      Vec3 position = PlayerUtil.getPositionEyes();
      Vec3 middle = Utils.getMiddleOfAABB(aabb);
      Vec3 look = PlayerUtil.getLook(middle);
      look = VecUtil.scaleVec(look, 0.10000000149011612D / look.func_72433_c());
      BetterBlockPos last = new BetterBlockPos(position);

      for(int i = 0; i < MathUtil.floor(position.func_72438_d(middle) / 0.10000000149011612D); ++i) {
         BetterBlockPos pos = new BetterBlockPos(position);
         if (pos.equals(last)) {
            position = position.func_178787_e(look);
         } else {
            if (aabb.func_72318_a(position)) {
               return true;
            }

            BetterBlockPos calculated = this.calculatePos(pos);
            if (PizzaClient.mc.field_71441_e.func_180495_p(calculated).func_177230_c() != Blocks.field_150350_a) {
               return false;
            }

            BetterBlockPos collidable = calculated.down();
            Block block = PizzaClient.mc.field_71441_e.func_180495_p(collidable).func_177230_c();
            Block lastBlock = PizzaClient.mc.field_71441_e.func_180495_p(last).func_177230_c();
            if (block.func_180646_a(PizzaClient.mc.field_71441_e, collidable).field_72337_e > lastBlock.func_180646_a(PizzaClient.mc.field_71441_e, last).field_72337_e + 0.5D) {
               return false;
            }

            last = collidable;
            position = position.func_178787_e(look);
         }
      }

      return true;
   }

   public final BetterBlockPos calculatePos(BetterBlockPos pos) {
      BetterBlockPos down = pos.down();
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(down).func_177230_c();
      if (!Utils.uncollidables.contains(block)) {
         return pos;
      } else if (block instanceof BlockLiquid) {
         return pos.down();
      } else {
         while(Utils.uncollidables.contains(block = PizzaClient.mc.field_71441_e.func_180495_p(down = pos.down()).func_177230_c())) {
            if (!down.isBlockLoaded()) {
               return down;
            }

            if (block instanceof BlockLiquid) {
               return down.down();
            }

            pos = down;
         }

         return pos;
      }
   }

   public final void useDefaultMovement() {
      BetterBlockPos player = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      BetterBlockPos current = (BetterBlockPos)this.moves.get(0);
      BetterBlockPos pos = new BetterBlockPos((double)player.field_177962_a, PizzaClient.mc.field_71439_g.field_70163_u, (double)player.field_177961_c);
      if (!this.moves.contains(pos) && !this.moves.contains(pos.up()) && !this.moves.contains(pos.down())) {
         this.addMovement(current);
         pos = current.down();
         Block block = pos.getBlock();
         block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
         if (PizzaClient.mc.field_71441_e.func_180495_p(player).func_177230_c() instanceof BlockLiquid) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), true);
            Movement.addMovement(player, current);
         } else if (PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u - 0.4D, PizzaClient.mc.field_71439_g.field_70161_v)).func_177230_c() instanceof BlockLiquid) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), true);
            Movement.addMovement(player, current);
         } else if (block.func_180646_a(PizzaClient.mc.field_71441_e, pos).field_72337_e > PizzaClient.mc.field_71439_g.field_70163_u + 0.6D && PizzaClient.mc.field_71439_g.field_70122_E && !Utils.uncollidables.contains(block)) {
            double lastMotion = MathUtil.abs(PizzaClient.mc.field_71439_g.field_70159_w) + MathUtil.abs(PizzaClient.mc.field_71439_g.field_70179_y) + 1.0E-9D;
            Movement.disableMovement();

            while(lastMotion <= MathUtil.abs(PizzaClient.mc.field_71439_g.field_70159_w) + MathUtil.abs(PizzaClient.mc.field_71439_g.field_70179_y) && PizzaClient.mc.field_71439_g.field_70159_w + PizzaClient.mc.field_71439_g.field_70179_y != 0.0D) {
               Movement.disableMovement();
            }

            Utils.sleep(10);
            double posY = PizzaClient.mc.field_71439_g.field_70163_u;
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), true);

            while(posY == PizzaClient.mc.field_71439_g.field_70163_u) {
               Utils.sleep(1);
            }

         } else {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
            Movement.addMovement(player, current);
         }
      } else {
         while(!((BetterBlockPos)this.moves.get(0)).isSameXandZ(player)) {
            this.moves.remove(0);
         }

         this.moves.remove(0);
         Movement.disableMovement();
      }
   }

   public void update() {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.checked.clear();
      this.moves.clear();
   }

   public void update(BetterBlockPos goalPos) {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.goalPos = goalPos;
      this.checked.clear();
      this.moves.clear();
   }

   public final boolean getDefaultAddBlock(PathNode node) {
      Iterator var3 = BetterBlockPos.FLAT_DIRECTIONS.iterator();

      while(true) {
         BetterBlockPos pos;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            Vec3 directions = (Vec3)var3.next();
            pos = new BetterBlockPos((double)node.currentPos.field_177962_a + directions.field_72450_a, (double)node.currentPos.field_177960_b + directions.field_72448_b, (double)node.currentPos.field_177961_c + directions.field_72449_c);
         } while(this.checked.contains(pos));

         this.checked.add(pos);
         BetterBlockPos posUp = pos.up();
         if (this.goalPos.isSameXandZ(pos) && (this.goalPos.equals(pos) || this.goalPos.equals(posUp) || this.goalPos.equals(pos.down()))) {
            this.moves = new ArrayList(node.moves);
            this.moves.add(this.goalPos);
            this.finished = true;
            return true;
         }

         if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(posUp).func_177230_c())) {
            if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c())) {
               PathFinder.nodes.add(new PathNode(this.calculatePos(pos), node.moves));
            } else if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(node.currentPos.field_177962_a, node.currentPos.field_177960_b + 2, node.currentPos.field_177961_c)).func_177230_c())) {
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.field_177962_a, pos.field_177960_b + 2, pos.field_177961_c)).func_177230_c();
               if (Utils.uncollidables.contains(block) && block.func_149669_A() <= 1.0D) {
                  PathFinder.nodes.add(new PathNode(posUp, node.moves));
               }
            }
         }
      }
   }

   public final boolean addBlockForAllDirections(PathNode node) {
      Iterator var3 = BetterBlockPos.ALL_DIRECTIONS.iterator();

      while(true) {
         BetterBlockPos pos;
         do {
            if (!var3.hasNext()) {
               return false;
            }

            Vec3 directions = (Vec3)var3.next();
            pos = new BetterBlockPos((double)node.currentPos.field_177962_a + directions.field_72450_a, (double)node.currentPos.field_177960_b + directions.field_72448_b, (double)node.currentPos.field_177961_c + directions.field_72449_c);
         } while(this.checked.contains(pos));

         this.checked.add(pos);
         BetterBlockPos posUp = pos.up();
         if (this.goalPos.isSameXandZ(pos) && (this.goalPos.equals(pos) || this.goalPos.equals(posUp) || this.goalPos.equals(pos.down()))) {
            this.moves = new ArrayList(node.moves);
            this.moves.add(this.goalPos);
            this.finished = true;
            return true;
         }

         if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(posUp).func_177230_c())) {
            if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c())) {
               PathFinder.nodes.add(new PathNode(pos, node.moves));
            } else if (Utils.uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(node.currentPos.field_177962_a, node.currentPos.field_177960_b + 2, node.currentPos.field_177961_c)).func_177230_c())) {
               Block block = PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.field_177962_a, pos.field_177960_b + 2, pos.field_177961_c)).func_177230_c();
               if (Utils.uncollidables.contains(block) && block.func_149669_A() <= 1.0D) {
                  PathFinder.nodes.add(new PathNode(posUp, node.moves));
               }
            }
         }
      }
   }

   public void initRotater() {
      (new Rotater(RotationUtil.getYawClosestTo90Degrees(Rotation.getRotation(((BetterBlockPos)this.moves.get(0)).toVec()).yaw) - PizzaClient.mc.field_71439_g.field_70177_z, 0.0F)).randomPitch().setRotationAmount(17).rotate();

      while(PizzaClient.rotater != null) {
         Utils.sleep(5);
      }

      Utils.sleep(100);
   }

   public final void rotateToBlock(BetterBlockPos next) {
      SnapRotater.snapTo(RotationUtil.getYawClosestTo90Degrees(Rotation.getRotationDifference(next.toVec()).yaw + PizzaClient.mc.field_71439_g.field_70177_z), MathUtil.randomFloat(5.0F));
   }

   public final void rotateToBlock() {
      SnapRotater.snapTo(RotationUtil.getYawClosestTo90Degrees(Rotation.getRotationDifference(((BetterBlockPos)this.moves.get(1)).toVec()).yaw + PizzaClient.mc.field_71439_g.field_70177_z), MathUtil.randomFloat(5.0F));
   }

   public final void closeScreenIfOpen() {
      while(PizzaClient.mc.field_71462_r != null) {
         Movement.disableMovement();
         PizzaClient.mc.field_71439_g.func_71053_j();
         Utils.sleep(600);
      }

   }

   public final void normalizeMotion() {
   }
}
