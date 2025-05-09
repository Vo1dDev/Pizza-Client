package qolskyblockmod.pizzaclient.features.macros.mining.nuker;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.finder.BlockFinder;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.helper.BlockPosFakeRotationHelper;

public class SimpleNuker implements INuker {
   private final BlockFinder finder;
   public Vec3 vec;

   public SimpleNuker(BlockFinder finder) {
      this.finder = finder;
   }

   public boolean isBlockValid(BlockPos pos) {
      return this.finder.isValid(pos);
   }

   public boolean nuke(boolean noSwing) {
      return this.nuke(this.vec, noSwing);
   }

   public boolean isVecValid() {
      return this.finder.isValid(new BlockPos(this.vec));
   }

   public boolean findVec() {
      BlockPosFakeRotationHelper helper = new BlockPosFakeRotationHelper();

      int y;
      int x;
      int z;
      BlockPos pos;
      for(y = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u); (double)y < PizzaClient.mc.field_71439_g.field_70163_u + 2.0D; ++y) {
         for(x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D); (double)x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0D; ++x) {
            for(z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0D); (double)z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0D; ++z) {
               pos = new BlockPos(x, y, z);
               if (this.finder.isValid(pos) && VecUtil.canReachBlock(pos)) {
                  helper.compare(pos);
               }
            }
         }
      }

      if (helper.isNotNull()) {
         this.vec = MathUtil.randomAABBPoint(helper.bestPos);
         return true;
      } else {
         for(y = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight() - 5.0D); (double)y < PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight() + 5.0D; ++y) {
            for(x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D); (double)x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0D; ++x) {
               for(z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0D); (double)z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0D; ++z) {
                  pos = new BlockPos(x, y, z);
                  if (this.finder.isValid(pos) && VecUtil.canReachBlock(pos)) {
                     helper.compare(pos);
                  }
               }
            }
         }

         if (helper.isNotNull()) {
            this.vec = MathUtil.randomAABBPoint(helper.bestPos);
            return true;
         } else {
            return false;
         }
      }
   }

   public static boolean hasBlock(BlockFinder finder) {
      for(int y = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight() - 5.0D); (double)y < PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight() + 5.0D; ++y) {
         for(int x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D); (double)x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0D; ++x) {
            for(int z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0D); (double)z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0D; ++z) {
               BlockPos pos = new BlockPos(x, y, z);
               if (finder.isValid(pos) && VecUtil.canReachBlock(pos)) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
