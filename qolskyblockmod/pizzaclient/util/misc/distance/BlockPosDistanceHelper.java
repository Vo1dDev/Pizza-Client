package qolskyblockmod.pizzaclient.util.misc.distance;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.PlayerUtil;

public class BlockPosDistanceHelper {
   private final Vec3 pos;
   private double bestDist = 9.99999999E8D;
   public BlockPos closest;

   public BlockPosDistanceHelper() {
      this.pos = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
   }

   public BlockPosDistanceHelper(Vec3 pos) {
      this.pos = pos;
   }

   public BlockPosDistanceHelper(BlockPos lastBlock) {
      this.pos = new Vec3((double)lastBlock.func_177958_n() + 0.5D, (double)lastBlock.func_177956_o() + 0.5D, (double)lastBlock.func_177952_p() + 0.5D);
   }

   public void compare(BlockPos block) {
      Vec3 vecIn = new Vec3((double)block.func_177958_n() + 0.5D, (double)block.func_177956_o() + 0.5D, (double)block.func_177952_p() + 0.5D);
      double dist = this.pos.func_72436_e(vecIn);
      if (dist < this.bestDist) {
         this.bestDist = dist;
         this.closest = block;
      }

   }

   public boolean isNotNull() {
      return this.closest != null;
   }
}
