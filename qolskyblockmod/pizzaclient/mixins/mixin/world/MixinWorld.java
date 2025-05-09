package qolskyblockmod.pizzaclient.mixins.mixin.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import qolskyblockmod.pizzaclient.PizzaClient;

@Mixin({World.class})
public class MixinWorld {
   @Overwrite
   public MovingObjectPosition func_147447_a(Vec3 vec31, Vec3 vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
      int i = MathHelper.func_76128_c(vec32.field_72450_a);
      int j = MathHelper.func_76128_c(vec32.field_72448_b);
      int k = MathHelper.func_76128_c(vec32.field_72449_c);
      int l = MathHelper.func_76128_c(vec31.field_72450_a);
      int i1 = MathHelper.func_76128_c(vec31.field_72448_b);
      int j1 = MathHelper.func_76128_c(vec31.field_72449_c);
      BlockPos blockpos = new BlockPos(l, i1, j1);
      IBlockState iblockstate = PizzaClient.mc.field_71441_e.func_180495_p(blockpos);
      Block block = iblockstate.func_177230_c();
      MovingObjectPosition movingobjectposition2;
      if ((!ignoreBlockWithoutBoundingBox || block.func_180640_a(PizzaClient.mc.field_71441_e, blockpos, iblockstate) != null) && block.func_176209_a(iblockstate, stopOnLiquid)) {
         movingobjectposition2 = block.func_180636_a(PizzaClient.mc.field_71441_e, blockpos, vec31, vec32);
         if (movingobjectposition2 != null) {
            return movingobjectposition2;
         }
      }

      movingobjectposition2 = null;
      int var16 = 200;

      while(true) {
         EnumFacing enumfacing;
         IBlockState iblockstate1;
         Block block1;
         do {
            if (var16-- < 0) {
               return returnLastUncollidableBlock ? movingobjectposition2 : null;
            }

            if (l == i && i1 == j && j1 == k) {
               return returnLastUncollidableBlock ? movingobjectposition2 : null;
            }

            boolean flag2 = true;
            boolean flag = true;
            boolean flag1 = true;
            double d0 = 999.0D;
            double d1 = 999.0D;
            double d2 = 999.0D;
            if (i > l) {
               d0 = (double)l + 1.0D;
            } else if (i < l) {
               d0 = (double)l + 0.0D;
            } else {
               flag2 = false;
            }

            if (j > i1) {
               d1 = (double)i1 + 1.0D;
            } else if (j < i1) {
               d1 = (double)i1 + 0.0D;
            } else {
               flag = false;
            }

            if (k > j1) {
               d2 = (double)j1 + 1.0D;
            } else if (k < j1) {
               d2 = (double)j1 + 0.0D;
            } else {
               flag1 = false;
            }

            double d3 = 999.0D;
            double d4 = 999.0D;
            double d5 = 999.0D;
            double d6 = vec32.field_72450_a - vec31.field_72450_a;
            double d7 = vec32.field_72448_b - vec31.field_72448_b;
            double d8 = vec32.field_72449_c - vec31.field_72449_c;
            if (flag2) {
               d3 = (d0 - vec31.field_72450_a) / d6;
            }

            if (flag) {
               d4 = (d1 - vec31.field_72448_b) / d7;
            }

            if (flag1) {
               d5 = (d2 - vec31.field_72449_c) / d8;
            }

            if (d3 == -0.0D) {
               d3 = -1.0E-4D;
            }

            if (d4 == -0.0D) {
               d4 = -1.0E-4D;
            }

            if (d5 == -0.0D) {
               d5 = -1.0E-4D;
            }

            if (d3 < d4 && d3 < d5) {
               enumfacing = i > l ? EnumFacing.WEST : EnumFacing.EAST;
               vec31 = new Vec3(d0, vec31.field_72448_b + d7 * d3, vec31.field_72449_c + d8 * d3);
            } else if (d4 < d5) {
               enumfacing = j > i1 ? EnumFacing.DOWN : EnumFacing.UP;
               vec31 = new Vec3(vec31.field_72450_a + d6 * d4, d1, vec31.field_72449_c + d8 * d4);
            } else {
               enumfacing = k > j1 ? EnumFacing.NORTH : EnumFacing.SOUTH;
               vec31 = new Vec3(vec31.field_72450_a + d6 * d5, vec31.field_72448_b + d7 * d5, d2);
            }

            l = MathHelper.func_76128_c(vec31.field_72450_a) - (enumfacing == EnumFacing.EAST ? 1 : 0);
            i1 = MathHelper.func_76128_c(vec31.field_72448_b) - (enumfacing == EnumFacing.UP ? 1 : 0);
            j1 = MathHelper.func_76128_c(vec31.field_72449_c) - (enumfacing == EnumFacing.SOUTH ? 1 : 0);
            blockpos = new BlockPos(l, i1, j1);
            iblockstate1 = PizzaClient.mc.field_71441_e.func_180495_p(blockpos);
            block1 = iblockstate1.func_177230_c();
         } while(ignoreBlockWithoutBoundingBox && block1.func_180640_a(PizzaClient.mc.field_71441_e, blockpos, iblockstate1) == null);

         if (block1.func_176209_a(iblockstate1, stopOnLiquid)) {
            MovingObjectPosition movingobjectposition1 = block1.func_180636_a(PizzaClient.mc.field_71441_e, blockpos, vec31, vec32);
            if (movingobjectposition1 != null) {
               return movingobjectposition1;
            }
         } else {
            movingobjectposition2 = new MovingObjectPosition(MovingObjectType.MISS, vec31, enumfacing, blockpos);
         }
      }
   }
}
