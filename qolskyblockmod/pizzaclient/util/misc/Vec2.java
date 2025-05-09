package qolskyblockmod.pizzaclient.util.misc;

import net.minecraft.util.Vec3;

public class Vec2 {
   public double x;
   public double y;

   public Vec2(double x, double y) {
      this.x = x;
      this.y = y;
   }

   public double distanceTo(Vec2 vec) {
      double d0 = vec.x - this.x;
      double d1 = vec.y - this.y;
      return Math.sqrt(d0 * d0 + d1 * d1);
   }

   public double squareDistanceTo(Vec2 vec) {
      double d0 = vec.x - this.x;
      double d1 = vec.y - this.y;
      return d0 * d0 + d1 * d1;
   }

   public static Vec2 getXYVec(Vec3 vecIn) {
      return new Vec2(vecIn.field_72450_a, vecIn.field_72448_b);
   }

   public static Vec2 getXZVec(Vec3 vecIn) {
      return new Vec2(vecIn.field_72450_a, vecIn.field_72449_c);
   }
}
