package qolskyblockmod.pizzaclient.util;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;

public class VecUtil {
   public static Vec3 scaleVec(Vec3 vec, float scale) {
      return new Vec3(vec.field_72450_a * (double)scale, vec.field_72448_b * (double)scale, vec.field_72449_c * (double)scale);
   }

   public static Vec3 scaleVec(Vec3 vec, double scale) {
      return new Vec3(vec.field_72450_a * scale, vec.field_72448_b * scale, vec.field_72449_c * scale);
   }

   public static Vec3 divideVec(Vec3 vec, float scale) {
      return new Vec3(vec.field_72450_a / (double)scale, vec.field_72448_b / (double)scale, vec.field_72449_c / (double)scale);
   }

   public static Vec3 divideVec(Vec3 vec, double scale) {
      return new Vec3(vec.field_72450_a / scale, vec.field_72448_b / scale, vec.field_72449_c / scale);
   }

   public static Vec3 offsetVec(Vec3 original, double offset) {
      return new Vec3(original.field_72450_a + offset, original.field_72448_b + offset, original.field_72449_c + offset);
   }

   public static Vec3 offsetVec(Vec3 original, double xOffset, double yOffset, double zOffset) {
      return new Vec3(original.field_72450_a + xOffset, original.field_72448_b + yOffset, original.field_72449_c + zOffset);
   }

   public static MovingObjectPosition rayTrace(float range) {
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      Vec3 vec31 = PlayerUtil.getLook();
      return fastRayTrace(vec3, vec3.func_72441_c(vec31.field_72450_a * (double)range, vec31.field_72448_b * (double)range, vec31.field_72449_c * (double)range));
   }

   public static MovingObjectPosition rayTraceStopLiquid(float range) {
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      Vec3 vec31 = PlayerUtil.getLook();
      return fastRayTrace(vec3, vec3.func_72441_c(vec31.field_72450_a * (double)range, vec31.field_72448_b * (double)range, vec31.field_72449_c * (double)range), false);
   }

   public static MovingObjectPosition rayTraceLook(Vec3 target, float range) {
      Vec3 pos = PlayerUtil.getPositionEyes();
      return pos.func_72436_e(target) > (double)(range * range) ? null : fastRayTrace(pos, target);
   }

   public static MovingObjectPosition rayTraceLookStopLiquid(Vec3 target, float range) {
      Vec3 pos = PlayerUtil.getPositionEyes();
      return pos.func_72436_e(target) > (double)(range * range) ? null : fastRayTrace(pos, target, false);
   }

   public static MovingObjectPosition rayTraceLook(Vec3 position, Vec3 target, float range) {
      return position.func_72436_e(target) > (double)(range * range) ? null : fastRayTrace(position, target);
   }

   public static MovingObjectPosition rayTraceLook(Vec3 position, Vec3 target) {
      return fastRayTrace(position, target);
   }

   public static MovingObjectPosition rayTraceLook(Vec3 target) {
      return fastRayTrace(PlayerUtil.getPositionEyes(), target);
   }

   public static boolean isRayTraceableLook(Vec3 target, BlockPos goal, float range) {
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      return vec3.func_72436_e(target) > (double)(range * range) ? false : rayTraceable(vec3, target, goal);
   }

   public static boolean isRayTraceableLook(BlockPos goal, float range) {
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      Vec3 target = Utils.getMiddleOfAABB(goal);
      return vec3.func_72436_e(target) > (double)(range * range) ? false : rayTraceable(vec3, target, goal);
   }

   public static boolean isRayTraceableLook(Vec3 position, Vec3 target, BlockPos goal, float range) {
      return position.func_72436_e(target) > (double)(range * range) ? false : rayTraceable(position, target, goal);
   }

   public static boolean isRayTraceableLook(Vec3 position, Vec3 target, BlockPos goal) {
      return rayTraceable(position, target, goal);
   }

   public static boolean isRayTraceableLook(Vec3 target, BlockPos goal) {
      return rayTraceable(PlayerUtil.getPositionEyes(), target, goal);
   }

   public static MovingObjectPosition rayTraceTopLayer(BlockPos pos) {
      Vec3 vec = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
      MovingObjectPosition position = rayTraceLook(vec, 4.5F);
      if (position != null && position.func_178782_a().equals(pos)) {
         return position;
      } else {
         for(int x = 1; x < 6; ++x) {
            for(int z = 1; z < 6; ++z) {
               vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 5.0F) - 0.1D, (double)pos.func_177956_o() + 0.99D, (double)((float)pos.func_177952_p() + (float)z / 5.0F) - 0.1D);
               position = rayTraceLook(vec, 4.5F);
               if (position != null && position.func_178782_a().equals(pos)) {
                  return position;
               }
            }
         }

         return null;
      }
   }

   public static Vec3 getHittableHitVec(BlockPos pos) {
      Vec3 vec = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
      MovingObjectPosition position = rayTraceLook(vec, 4.5F);
      if (position != null && position.func_178782_a().equals(pos)) {
         return position.field_72307_f;
      } else {
         for(int x = 1; x < 5; ++x) {
            for(int y = 1; y < 5; ++y) {
               for(int z = 1; z < 5; ++z) {
                  vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 4.0F) - 0.125D, (double)((float)pos.func_177956_o() + (float)y / 4.0F) - 0.125D, (double)((float)pos.func_177952_p() + (float)z / 4.0F) - 0.125D);
                  position = rayTraceLook(vec, 4.5F);
                  if (position != null && position.func_178782_a().equals(pos)) {
                     return position.field_72307_f;
                  }
               }
            }
         }

         return null;
      }
   }

   public static Vec3 getVeryAccurateHittableHitVec(BlockPos pos) {
      Vec3 vec = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
      MovingObjectPosition position = rayTraceLook(vec);
      if (position != null && position.func_178782_a().equals(pos)) {
         return position.field_72307_f;
      } else {
         for(int x = 1; x < 9; ++x) {
            for(int y = 1; y < 9; ++y) {
               for(int z = 1; z < 9; ++z) {
                  vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 8.0F) - 0.0625D, (double)((float)pos.func_177956_o() + (float)y / 8.0F) - 0.0625D, (double)((float)pos.func_177952_p() + (float)z / 8.0F) - 0.0625D);
                  position = rayTraceLook(vec);
                  if (position != null && position.func_178782_a().equals(pos)) {
                     return position.field_72307_f;
                  }
               }
            }
         }

         return null;
      }
   }

   public static MovingObjectPosition getHittableMovingObjectPosition(BlockPos pos) {
      Vec3 vec = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
      MovingObjectPosition position = rayTraceLook(vec, 4.5F);
      if (position != null && position.func_178782_a().equals(pos)) {
         return position;
      } else {
         for(int x = 1; x < 5; ++x) {
            for(int y = 1; y < 5; ++y) {
               for(int z = 1; z < 5; ++z) {
                  vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 4.0F) - 0.125D, (double)((float)pos.func_177956_o() + (float)y / 4.0F) - 0.125D, (double)((float)pos.func_177952_p() + (float)z / 4.0F) - 0.125D);
                  position = rayTraceLook(vec, 4.5F);
                  if (position != null && position.func_178782_a().equals(pos)) {
                     return position;
                  }
               }
            }
         }

         return null;
      }
   }

   public static Vec3 getClosestHittablePosition(BlockPos pos, Vec3 goal) {
      double bestDist = 9.9999999E7D;
      Vec3 bestHit = null;

      for(int x = 1; x < 5; ++x) {
         for(int y = 1; y < 5; ++y) {
            for(int z = 1; z < 5; ++z) {
               Vec3 vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 4.0F) - 0.125D, (double)((float)pos.func_177956_o() + (float)y / 4.0F) - 0.125D, (double)((float)pos.func_177952_p() + (float)z / 4.0F) - 0.125D);
               MovingObjectPosition position = rayTraceLook(vec, 4.5F);
               if (position != null && position.func_178782_a().equals(pos)) {
                  double dist = position.field_72307_f.func_72436_e(goal);
                  if (dist < bestDist) {
                     bestDist = dist;
                     bestHit = position.field_72307_f;
                  }
               }
            }
         }
      }

      return bestHit;
   }

   public static Vec3 getClosestHittableToMiddle(BlockPos pos) {
      Vec3 middle = new Vec3((double)pos.func_177958_n() + 0.5D + (double)(MathUtil.randomFloat() / 4.0F), (double)pos.func_177956_o() + 0.5D + (double)(MathUtil.randomFloat() / 4.0F), (double)pos.func_177952_p() + 0.5D + (double)(MathUtil.randomFloat() / 4.0F));
      double bestDist = 9.9999999E7D;
      Vec3 bestHit = null;

      for(int x = 1; x < 6; ++x) {
         for(int y = 1; y < 6; ++y) {
            for(int z = 1; z < 6; ++z) {
               Vec3 vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 5.0F - 0.1234F), (double)((float)pos.func_177956_o() + (float)y / 5.0F - 0.1234F), (double)((float)pos.func_177952_p() + (float)z / 5.0F - 0.1234F));
               MovingObjectPosition position = rayTraceLook(vec, 4.5F);
               if (position != null && position.func_178782_a().equals(pos)) {
                  double dist = position.field_72307_f.func_72436_e(middle);
                  if (dist < bestDist) {
                     bestDist = dist;
                     bestHit = position.field_72307_f;
                  }
               }
            }
         }
      }

      return bestHit;
   }

   public static Vec3 getRandomHittable(BlockPos pos) {
      List<Vec3> vecs = new ArrayList();

      for(int x = 1; x < 6; ++x) {
         for(int y = 1; y < 6; ++y) {
            for(int z = 1; z < 6; ++z) {
               Vec3 vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 5.0F) - 0.1D, (double)((float)pos.func_177956_o() + (float)y / 5.0F) - 0.1D, (double)((float)pos.func_177952_p() + (float)z / 5.0F) - 0.1D);
               MovingObjectPosition position = rayTraceLook(vec, 4.5F);
               if (position != null && position.func_178782_a().equals(pos)) {
                  vecs.add(position.field_72307_f);
               }
            }
         }
      }

      return vecs.size() == 0 ? null : (Vec3)vecs.get(Utils.random.nextInt(vecs.size()));
   }

   public static Vec3 getRandomHittable(BlockPos pos, AxisAlignedBB aabb) {
      List<Vec3> vecs = new ArrayList();

      for(int x = 1; x < 6; ++x) {
         for(int y = 1; y < 6; ++y) {
            for(int z = 1; z < 6; ++z) {
               Vec3 vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 5.0F) - 0.1D, (double)((float)pos.func_177956_o() + (float)y / 5.0F) - 0.1D, (double)((float)pos.func_177952_p() + (float)z / 5.0F) - 0.1D);
               if (aabb.func_72318_a(vec)) {
                  MovingObjectPosition position = rayTraceLook(vec, 4.5F);
                  if (position != null && position.func_178782_a().equals(pos)) {
                     vecs.add(position.field_72307_f);
                  }
               }
            }
         }
      }

      return vecs.size() == 0 ? null : (Vec3)vecs.get(Utils.random.nextInt(vecs.size()));
   }

   public static Vec3 getRandomHittableStopLiquid(BlockPos pos, AxisAlignedBB aabb) {
      List<Vec3> vecs = new ArrayList();

      for(int x = 1; x < 6; ++x) {
         for(int y = 1; y < 6; ++y) {
            for(int z = 1; z < 6; ++z) {
               Vec3 vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 5.0F) - 0.1D, (double)((float)pos.func_177956_o() + (float)y / 5.0F) - 0.1D, (double)((float)pos.func_177952_p() + (float)z / 5.0F) - 0.1D);
               if (aabb.func_72318_a(vec)) {
                  MovingObjectPosition position = rayTraceLookStopLiquid(vec, 100.0F);
                  if (position != null && position.func_178782_a().equals(pos)) {
                     vecs.add(position.field_72307_f);
                  }
               }
            }
         }
      }

      return vecs.size() == 0 ? null : (Vec3)vecs.get(Utils.random.nextInt(vecs.size()));
   }

   public static List<Vec3> getAllHittablePosition(BlockPos pos) {
      List<Vec3> hittables = new ArrayList();

      for(int x = 1; x < 5; ++x) {
         for(int y = 1; y < 5; ++y) {
            for(int z = 1; z < 5; ++z) {
               Vec3 vec = new Vec3((double)((float)pos.func_177958_n() + (float)x / 4.0F) - 0.125D, (double)((float)pos.func_177956_o() + (float)y / 4.0F) - 0.125D, (double)((float)pos.func_177952_p() + (float)z / 4.0F) - 0.125D);
               MovingObjectPosition position = rayTraceLook(vec, 4.5F);
               if (position != null && position.func_178782_a().equals(pos)) {
                  hittables.add(vec);
               }
            }
         }
      }

      return hittables;
   }

   public static boolean isHittable(BlockPos pos) {
      if (isRayTraceableLook(new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D), pos, 4.5F)) {
         return true;
      } else {
         for(int x = 1; x < 6; ++x) {
            for(int y = 1; y < 6; ++y) {
               for(int z = 1; z < 6; ++z) {
                  if (isRayTraceableLook(new Vec3((double)((float)pos.func_177958_n() + (float)x / 5.0F) - 0.1D, (double)((float)pos.func_177956_o() + (float)y / 5.0F) - 0.1D, (double)((float)pos.func_177952_p() + (float)z / 5.0F) - 0.1D), pos, 4.5F)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public static MovingObjectPosition calculateInterceptAABBLook(AxisAlignedBB aabb, Vec3 look) {
      return aabb.func_72327_a(PlayerUtil.getPositionEyes(), look);
   }

   public static MovingObjectPosition calculateInterceptAABBLook(AxisAlignedBB aabb, Vec3 look, float range) {
      Vec3 position = PlayerUtil.getPositionEyes();
      return position.func_72436_e(look) > (double)(range * range) ? null : aabb.func_72327_a(position, look);
   }

   public static MovingObjectPosition calculateInterceptAABB(AxisAlignedBB aabb, float range) {
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      Vec3 vec31 = PlayerUtil.getLook();
      return aabb.func_72327_a(vec3, vec3.func_72441_c(vec31.field_72450_a * (double)range, vec31.field_72448_b * (double)range, vec31.field_72449_c * (double)range));
   }

   public static MovingObjectPosition calculateInterceptAABB(AxisAlignedBB aabb) {
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      Vec3 vec31 = PlayerUtil.getLook();
      return aabb.func_72327_a(vec3, vec3.func_72441_c(vec31.field_72450_a * 100.0D, vec31.field_72448_b * 100.0D, vec31.field_72449_c * 100.0D));
   }

   public static MovingObjectPosition calculateIntercept(BlockPos pos, float range) {
      AxisAlignedBB aabb = Utils.getBlockAABB(pos);
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      Vec3 vec31 = PlayerUtil.getLook();
      return aabb.func_72327_a(vec3, vec3.func_72441_c(vec31.field_72450_a * (double)range, vec31.field_72448_b * (double)range, vec31.field_72449_c * (double)range));
   }

   public static MovingObjectPosition calculateInterceptLook(BlockPos pos, Vec3 look, float range) {
      AxisAlignedBB aabb = Utils.getBlockAABB(pos);
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      return vec3.func_72436_e(look) > (double)(range * range) ? null : aabb.func_72327_a(vec3, look);
   }

   public static MovingObjectPosition calculateInterceptLook(BlockPos pos, float range) {
      AxisAlignedBB aabb = Utils.getBlockAABB(pos);
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      Vec3 look = Utils.getMiddleOfAABB(aabb);
      return vec3.func_72436_e(look) > (double)(range * range) ? null : aabb.func_72327_a(vec3, look);
   }

   public static MovingObjectPosition calculateInterceptLook(Vec3 look, float range) {
      AxisAlignedBB aabb = Utils.getBlockAABB(new BlockPos(look));
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      return vec3.func_72436_e(look) > (double)(range * range) ? null : aabb.func_72327_a(vec3, look);
   }

   public static MovingObjectPosition calculateIntercept(Vec3 vec, float range) {
      return calculateInterceptLook(new BlockPos(vec), vec, range);
   }

   public static EnumFacing calculateEnumfacingLook(AxisAlignedBB aabb, Vec3 look) {
      MovingObjectPosition position = calculateInterceptAABBLook(aabb, look);
      return position != null ? position.field_178784_b : null;
   }

   public static EnumFacing calculateEnumfacingLook(Vec3 look) {
      MovingObjectPosition position = calculateInterceptAABBLook(Utils.getBlockAABB(new BlockPos(look)), look);
      return position != null ? position.field_178784_b : PizzaClient.mc.field_71439_g.func_174811_aO().func_176734_d();
   }

   public static EnumFacing calculateEnumfacingLook(BlockPos look) {
      MovingObjectPosition position = calculateInterceptAABBLook(Utils.getBlockAABB(look), new Vec3((double)look.func_177958_n() + 0.5D, (double)look.func_177956_o() + 0.5D, (double)look.func_177952_p() + 0.5D));
      return position != null ? position.field_178784_b : PizzaClient.mc.field_71439_g.func_174811_aO().func_176734_d();
   }

   public static EnumFacing calculateEnumfacingLook(BlockPos pos, Vec3 look) {
      MovingObjectPosition position = calculateInterceptAABBLook(Utils.getBlockAABB(pos), look);
      return position != null ? position.field_178784_b : null;
   }

   public static EnumFacing calculateEnumfacing(AxisAlignedBB aabb) {
      MovingObjectPosition position = calculateInterceptAABB(aabb);
      return position != null ? position.field_178784_b : null;
   }

   public static boolean isFacingAABB(AxisAlignedBB aabb, float range) {
      return isInterceptable(aabb, range);
   }

   public static boolean isFacingAABBLook(AxisAlignedBB aabb, Vec3 target, float range) {
      return isInterceptable(PlayerUtil.getPositionEyes(), target, aabb, range);
   }

   public static boolean facingBlock(BlockPos block, float range) {
      AxisAlignedBB aabb = Utils.getBlockAABB(block);
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      Vec3 vec31 = PlayerUtil.getLook();
      return isInterceptable(vec3, vec3.func_72441_c(vec31.field_72450_a * (double)range, vec31.field_72448_b * (double)range, vec31.field_72449_c * (double)range), aabb);
   }

   public static boolean canReachBlock(BlockPos pos, float range) {
      AxisAlignedBB aabb = Utils.getBlockAABB(pos);
      return isInterceptable(PlayerUtil.getPositionEyes(), Utils.getMiddleOfAABB(aabb), aabb, range);
   }

   public static boolean canReachBlock(BlockPos pos) {
      AxisAlignedBB aabb = Utils.getBlockAABB(pos);
      Vec3 vec3 = PlayerUtil.getPositionEyes();
      return isInterceptable(vec3, Utils.getMiddleOfAABB(aabb), aabb, 4.5F);
   }

   private static MovingObjectPosition fastRayTrace(Vec3 vec31, Vec3 vec32) {
      return fastRayTrace(vec31, vec32, true);
   }

   private static MovingObjectPosition fastRayTrace(Vec3 vec31, Vec3 vec32, boolean hitLiquid) {
      int i = MathHelper.func_76128_c(vec32.field_72450_a);
      int j = MathHelper.func_76128_c(vec32.field_72448_b);
      int k = MathHelper.func_76128_c(vec32.field_72449_c);
      int l = MathHelper.func_76128_c(vec31.field_72450_a);
      int i1 = MathHelper.func_76128_c(vec31.field_72448_b);
      int j1 = MathHelper.func_76128_c(vec31.field_72449_c);
      BlockPos blockpos = new BlockPos(l, i1, j1);
      IBlockState iblockstate = PizzaClient.mc.field_71441_e.func_180495_p(blockpos);
      Block block = iblockstate.func_177230_c();
      if (block.func_176209_a(iblockstate, hitLiquid)) {
         MovingObjectPosition movingobjectposition = block.func_180636_a(PizzaClient.mc.field_71441_e, blockpos, vec31, vec32);
         if (movingobjectposition != null) {
            return movingobjectposition;
         }
      }

      int var37 = 200;

      while(var37-- >= 0) {
         if (l == i && i1 == j && j1 == k) {
            return null;
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
            vec31 = new Vec3(d0, vec31.field_72448_b + d7 * d3, vec31.field_72449_c + d8 * d3);
            if (i > l) {
               l = MathHelper.func_76128_c(vec31.field_72450_a);
            } else {
               l = MathHelper.func_76128_c(vec31.field_72450_a) - 1;
            }

            i1 = MathHelper.func_76128_c(vec31.field_72448_b);
            j1 = MathHelper.func_76128_c(vec31.field_72449_c);
         } else if (d4 < d5) {
            vec31 = new Vec3(vec31.field_72450_a + d6 * d4, d1, vec31.field_72449_c + d8 * d4);
            if (j > i1) {
               i1 = MathHelper.func_76128_c(vec31.field_72448_b);
            } else {
               i1 = MathHelper.func_76128_c(vec31.field_72448_b) - 1;
            }

            l = MathHelper.func_76128_c(vec31.field_72450_a);
            j1 = MathHelper.func_76128_c(vec31.field_72449_c);
         } else {
            vec31 = new Vec3(vec31.field_72450_a + d6 * d5, vec31.field_72448_b + d7 * d5, d2);
            if (k > j1) {
               j1 = MathHelper.func_76128_c(vec31.field_72449_c);
            } else {
               j1 = MathHelper.func_76128_c(vec31.field_72449_c) - 1;
            }

            l = MathHelper.func_76128_c(vec31.field_72450_a);
            i1 = MathHelper.func_76128_c(vec31.field_72448_b);
         }

         blockpos = new BlockPos(l, i1, j1);
         IBlockState iblockstate1 = PizzaClient.mc.field_71441_e.func_180495_p(blockpos);
         Block block1 = iblockstate1.func_177230_c();
         if (block1.func_176209_a(iblockstate1, hitLiquid)) {
            MovingObjectPosition movingobjectposition1 = block1.func_180636_a(PizzaClient.mc.field_71441_e, blockpos, vec31, vec32);
            if (movingobjectposition1 != null) {
               return movingobjectposition1;
            }
         }
      }

      return null;
   }

   private static boolean rayTraceable(Vec3 vec31, Vec3 vec32, BlockPos goal) {
      int i = MathHelper.func_76128_c(vec32.field_72450_a);
      int j = MathHelper.func_76128_c(vec32.field_72448_b);
      int k = MathHelper.func_76128_c(vec32.field_72449_c);
      int l = MathHelper.func_76128_c(vec31.field_72450_a);
      int i1 = MathHelper.func_76128_c(vec31.field_72448_b);
      int j1 = MathHelper.func_76128_c(vec31.field_72449_c);
      BlockPos blockpos = new BlockPos(l, i1, j1);
      IBlockState iblockstate = PizzaClient.mc.field_71441_e.func_180495_p(blockpos);
      Block block = iblockstate.func_177230_c();
      if (block.func_176209_a(iblockstate, false)) {
         MovingObjectPosition movingobjectposition = block.func_180636_a(PizzaClient.mc.field_71441_e, blockpos, vec31, vec32);
         if (movingobjectposition != null) {
            return blockpos == goal || l == goal.func_177958_n() && i1 == goal.func_177956_o() && j1 == goal.func_177952_p();
         }
      }

      int var38 = 200;

      while(var38-- >= 0) {
         if (l == i && i1 == j && j1 == k) {
            return false;
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

         EnumFacing enumfacing;
         if (d3 < d4 && d3 < d5) {
            vec31 = new Vec3(d0, vec31.field_72448_b + d7 * d3, vec31.field_72449_c + d8 * d3);
            if (i > l) {
               enumfacing = EnumFacing.WEST;
               l = MathHelper.func_76128_c(vec31.field_72450_a);
            } else {
               enumfacing = EnumFacing.EAST;
               l = MathHelper.func_76128_c(vec31.field_72450_a) - 1;
            }

            i1 = MathHelper.func_76128_c(vec31.field_72448_b);
            j1 = MathHelper.func_76128_c(vec31.field_72449_c);
         } else if (d4 < d5) {
            vec31 = new Vec3(vec31.field_72450_a + d6 * d4, d1, vec31.field_72449_c + d8 * d4);
            if (j > i1) {
               enumfacing = EnumFacing.DOWN;
               i1 = MathHelper.func_76128_c(vec31.field_72448_b);
            } else {
               enumfacing = EnumFacing.UP;
               i1 = MathHelper.func_76128_c(vec31.field_72448_b) - 1;
            }

            l = MathHelper.func_76128_c(vec31.field_72450_a);
            j1 = MathHelper.func_76128_c(vec31.field_72449_c);
         } else {
            vec31 = new Vec3(vec31.field_72450_a + d6 * d5, vec31.field_72448_b + d7 * d5, d2);
            if (k > j1) {
               enumfacing = EnumFacing.NORTH;
               j1 = MathHelper.func_76128_c(vec31.field_72449_c);
            } else {
               enumfacing = EnumFacing.SOUTH;
               j1 = MathHelper.func_76128_c(vec31.field_72449_c) - 1;
            }

            l = MathHelper.func_76128_c(vec31.field_72450_a);
            i1 = MathHelper.func_76128_c(vec31.field_72448_b);
         }

         blockpos = new BlockPos(l, i1, j1);
         IBlockState iblockstate1 = PizzaClient.mc.field_71441_e.func_180495_p(blockpos);
         Block block1 = iblockstate1.func_177230_c();
         if (block1.func_176209_a(iblockstate1, false)) {
            MovingObjectPosition movingobjectposition1 = block1.func_180636_a(PizzaClient.mc.field_71441_e, blockpos, vec31, vec32);
            if (movingobjectposition1 != null) {
               return (blockpos == goal || l == goal.func_177958_n() && i1 == goal.func_177956_o() && j1 == goal.func_177952_p()) && PizzaClient.mc.field_71441_e.func_180495_p(blockpos.func_177972_a(enumfacing)).func_177230_c() == Blocks.field_150350_a;
            }
         }
      }

      return false;
   }

   private static boolean rayTraceable(Vec3 vec31, Vec3 vec32) {
      return rayTraceable(vec31, vec32, new BlockPos(vec32));
   }

   public static boolean isInterceptable(Vec3 start, Vec3 goal, AxisAlignedBB aabb) {
      return isVecInYZ(start.func_72429_b(goal, aabb.field_72340_a), aabb) || isVecInYZ(start.func_72429_b(goal, aabb.field_72336_d), aabb) || isVecInXZ(start.func_72435_c(goal, aabb.field_72338_b), aabb) || isVecInXZ(start.func_72435_c(goal, aabb.field_72337_e), aabb) || isVecInXY(start.func_72434_d(goal, aabb.field_72339_c), aabb) || isVecInXY(start.func_72434_d(goal, aabb.field_72334_f), aabb);
   }

   public static boolean isInterceptable(Vec3 start, Vec3 goal, AxisAlignedBB aabb, float range) {
      return start.func_72436_e(goal) > (double)(range * range) ? false : isInterceptable(start, goal, aabb);
   }

   public static boolean isInterceptable(Vec3 goal, AxisAlignedBB aabb, float range) {
      Vec3 position = PlayerUtil.getPositionEyes();
      return position.func_72436_e(goal) > (double)(range * range) ? false : isInterceptable(position, goal, aabb);
   }

   public static boolean isInterceptable(AxisAlignedBB aabb, float range) {
      Vec3 position = PlayerUtil.getPositionEyes();
      Vec3 look = PlayerUtil.getLook();
      return isInterceptable(position, position.func_72441_c(look.field_72450_a * (double)range, look.field_72448_b * (double)range, look.field_72449_c * (double)range), aabb);
   }

   public static boolean isInterceptable(Vec3 goal, AxisAlignedBB aabb) {
      Vec3 position = PlayerUtil.getPositionEyes();
      return isInterceptable(position, goal, aabb);
   }

   public static boolean isVecInYZ(Vec3 vec, AxisAlignedBB aabb) {
      return vec != null && vec.field_72448_b >= aabb.field_72338_b && vec.field_72448_b <= aabb.field_72337_e && vec.field_72449_c >= aabb.field_72339_c && vec.field_72449_c <= aabb.field_72334_f;
   }

   public static boolean isVecInXZ(Vec3 vec, AxisAlignedBB aabb) {
      return vec != null && vec.field_72450_a >= aabb.field_72340_a && vec.field_72450_a <= aabb.field_72336_d && vec.field_72449_c >= aabb.field_72339_c && vec.field_72449_c <= aabb.field_72334_f;
   }

   public static boolean isVecInXY(Vec3 vec, AxisAlignedBB aabb) {
      return vec != null && vec.field_72450_a >= aabb.field_72340_a && vec.field_72450_a <= aabb.field_72336_d && vec.field_72448_b >= aabb.field_72338_b && vec.field_72448_b <= aabb.field_72337_e;
   }

   public static boolean entityRayTrace(Vec3 start, Vec3 end, AxisAlignedBB aabb) {
      aabb = aabb.func_72314_b(1.0D, 1.0D, 1.0D);
      return true;
   }
}
