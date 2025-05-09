package qolskyblockmod.pizzaclient.util.misc.distance;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;

public class VecDistanceHelper {
   private final Vec3 pos;
   private double bestDist = 9.99999999E8D;
   public Vec3 closest;

   public VecDistanceHelper() {
      this.pos = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
   }

   public VecDistanceHelper(Vec3 pos) {
      this.pos = pos;
   }

   public void compare(Vec3 vecIn) {
      double dist = this.pos.func_72436_e(vecIn);
      if (dist < this.bestDist) {
         this.bestDist = dist;
         this.closest = vecIn;
      }

   }

   public void compare(Entity entity) {
      Vec3 vecIn = new Vec3(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v);
      double dist = this.pos.func_72436_e(vecIn);
      if (dist < this.bestDist) {
         this.bestDist = dist;
         this.closest = vecIn;
      }

   }

   public void compare(MovingObjectPosition mop) {
      double dist = this.pos.func_72436_e(mop.field_72307_f);
      if (dist < this.bestDist) {
         this.bestDist = dist;
         this.closest = mop.field_72307_f;
      }

   }

   public void compare(BlockPos block) {
      Vec3 vecIn = new Vec3((double)block.func_177958_n() + 0.5D, (double)block.func_177956_o() + 0.5D, (double)block.func_177952_p() + 0.5D);
      double dist = this.pos.func_72436_e(vecIn);
      if (dist < this.bestDist) {
         this.bestDist = dist;
         this.closest = vecIn;
      }

   }

   public boolean isNotNull() {
      return this.closest != null;
   }
}
