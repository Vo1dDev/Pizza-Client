package qolskyblockmod.pizzaclient.util.rotation.helper;

import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;

public class MOPRotationHelper {
   public MovingObjectPosition bestPos;
   private double bestRotation = 9.9999999E7D;
   private final float maxDist;
   private final Vec3 player;

   public MOPRotationHelper() {
      this.maxDist = 20.25F;
      this.player = PlayerUtil.getPositionEyes();
   }

   public MOPRotationHelper(float dist) {
      this.maxDist = dist * dist;
      this.player = PlayerUtil.getPositionEyes();
   }

   public void compare(MovingObjectPosition pos) {
      if (this.player.func_72436_e(pos.field_72307_f) <= (double)this.maxDist) {
         double dist = (double)Rotation.getRotationDifference(pos.field_72307_f).sum();
         if (dist < this.bestRotation) {
            this.bestPos = pos;
            this.bestRotation = dist;
         }
      }

   }

   public boolean isNotNull() {
      return this.bestPos != null;
   }
}
