package qolskyblockmod.pizzaclient.features.macros.ai.mining;

import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.finder.BlockFinder;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.misc.distance.BlockPosDistanceHelper;

public class InfiniteReachHelper {
   private static long lastMineTime;

   public static void update() {
      lastMineTime = System.currentTimeMillis();
   }

   public static void reset() {
      lastMineTime = 0L;
   }

   public static boolean isValid() {
      return System.currentTimeMillis() - lastMineTime <= 2200L;
   }

   public static boolean mineClosest() {
      return System.currentTimeMillis() - lastMineTime > 2200L;
   }

   public static Vec3 getClosest(BlockFinder finder) {
      BlockPosDistanceHelper helper = new BlockPosDistanceHelper();
      int height = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());

      int y;
      int x;
      int z;
      BlockPos pos;
      for(y = height; y < height + 5; ++y) {
         for(x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D); (double)x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0D; ++x) {
            for(z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0D); (double)z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0D; ++z) {
               pos = new BlockPos(x, y, z);
               if (finder.isValid(pos)) {
                  helper.compare(pos);
               }
            }
         }
      }

      if (helper.isNotNull()) {
         return MathUtil.randomAABBPoint(helper.closest);
      } else {
         for(y = height - 5; y < height + 1; ++y) {
            for(x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D); (double)x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0D; ++x) {
               for(z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0D); (double)z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0D; ++z) {
                  pos = new BlockPos(x, y, z);
                  if (finder.isValid(pos)) {
                     helper.compare(pos);
                  }
               }
            }
         }

         if (helper.isNotNull()) {
            return MathUtil.randomAABBPoint(helper.closest);
         } else {
            return null;
         }
      }
   }
}
