package qolskyblockmod.pizzaclient.util.rotation.helper;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;

public class BlockPosRotationHelper {
   public BlockPos bestPos;
   private double bestRotation = 9.9999999E7D;
   private final float maxDist;
   private final Vec3 player;

   public BlockPosRotationHelper() {
      this.maxDist = 20.25F;
      this.player = PlayerUtil.getPositionEyes();
   }

   public BlockPosRotationHelper(float dist) {
      this.maxDist = dist * dist;
      this.player = PlayerUtil.getPositionEyes();
   }

   public void compare(Vec3 vecIn) {
      if (this.player.func_72436_e(vecIn) <= (double)this.maxDist) {
         double dist = (double)Rotation.getRotationDifference(vecIn).sum();
         if (dist < this.bestRotation) {
            this.bestPos = new BlockPos(vecIn);
            this.bestRotation = dist;
         }
      }

   }

   public void compare(MovingObjectPosition pos) {
      if (this.player.func_72436_e(pos.field_72307_f) <= (double)this.maxDist) {
         double dist = (double)Rotation.getRotationDifference(pos.field_72307_f).sum();
         if (dist < this.bestRotation) {
            this.bestPos = pos.func_178782_a();
            this.bestRotation = dist;
         }
      }

   }

   public void compare(BlockPos pos) {
      Vec3 vecIn = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
      if (this.player.func_72436_e(vecIn) <= (double)this.maxDist) {
         double dist = (double)Rotation.getRotationDifference(vecIn).sum();
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
