package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path;

import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.PathBase;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathFinder;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class Path extends PathBase {
   public boolean teleport;
   public double lastUnloadedChunkDistance = 9.99999999E8D;
   public PathNode unloaded;

   public Path(Vec3 from, Vec3 to) {
      super(from, to);
   }

   public Path(BetterBlockPos from, BetterBlockPos to) {
      super(from, to);
      this.teleport = PlayerUtil.hotbarHasEtherwarp();
   }

   public Path(BetterBlockPos to) {
      super(to);
   }

   public Path(Vec3 to) {
      super(to);
   }

   public void update() {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.checked.clear();
      this.moves = new ArrayList(Collections.singletonList(new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)));
      this.teleport = PlayerUtil.hotbarHasEtherwarp();
      this.unloaded = null;
   }

   public void update(BetterBlockPos goalPos) {
      this.currentPos = new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.goalPos = goalPos;
      this.checked.clear();
      this.moves = new ArrayList(Collections.singletonList(new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)));
      this.teleport = PlayerUtil.hotbarHasEtherwarp();
      this.unloaded = null;
   }

   public boolean addBlock(PathNode node) {
      if (this.teleport) {
         MovingObjectPosition position = VecUtil.rayTraceLook(node.currentPos.getPositionEyes(), this.goalPos.toVec(), 57.0F);
         if (position != null && position.func_178782_a().equals(this.goalPos)) {
            this.moves = new ArrayList(node.moves);
            this.finished = true;
            return true;
         }
      }

      if (!node.currentPos.isBlockLoaded()) {
         double dist = node.currentPos.distanceToSq(this.goalPos);
         if (dist < this.lastUnloadedChunkDistance) {
            this.lastUnloadedChunkDistance = dist;
            this.unloaded = node;
            return true;
         }
      }

      return this.getDefaultAddBlock(node);
   }

   public void moveUntilLoaded() {
      this.moves = new ArrayList(this.unloaded.moves);
      this.onBeginMove();

      while(!this.unloaded.currentPos.isBlockLoaded()) {
         this.closeScreenIfOpen();
         this.moveTo();
      }

      this.onEndMove();
      Movement.disableMovement();
      Utils.sleep(50);
      this.update();
   }

   public void onTick() {
      if (this.unloaded != null) {
         this.moveUntilLoaded();
         PathFinder.nodes = new ArrayList(Collections.singletonList(new PathNode()));
         this.unloaded = null;
         Movement.disableMovement();
      }

   }

   public void moveTo() {
      this.useDefaultMovement();
      this.rotateIfNeeded();
   }

   public void move() {
      this.moveTo();
   }

   public void onBeginMove() {
      this.initRotater();
   }

   public void onEndMove() {
      this.teleport();
   }

   public void teleport() {
      if (this.teleport) {
         BetterBlockPos player = new BetterBlockPos(PizzaClient.mc.field_71439_g.func_174791_d());
         if (!this.goalPos.equals(player) && !this.goalPos.equals(player.up()) && !this.goalPos.equals(player.down())) {
            Vec3 hit = VecUtil.getVeryAccurateHittableHitVec(this.goalPos);
            Utils.sleep(200);
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
            (new Rotater(hit != null ? hit : this.goalPos.toVec())).rotate();
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
            Utils.sleep(250);
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
         }
      }

   }

   public void shutdown() {
      this.finished = true;
      PizzaClient.rotater = null;
   }
}
