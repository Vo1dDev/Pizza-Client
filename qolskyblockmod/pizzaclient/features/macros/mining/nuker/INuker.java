package qolskyblockmod.pizzaclient.features.macros.mining.nuker;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;

public interface INuker {
   boolean isBlockValid(BlockPos var1);

   boolean nuke(boolean var1);

   boolean isVecValid();

   boolean findVec();

   default void onNoVecAvailable() {
   }

   default void onNukePre() {
   }

   default void enqueue() {
      NukerBase.nuker = this;
   }

   default boolean nukeSilent(Vec3 vec) {
      if (vec != null) {
         BlockPos block = new BlockPos(vec);
         if (!PizzaClient.mc.field_71439_g.func_71039_bw()) {
            if (PizzaClient.mc.field_71462_r == null) {
               EnumFacing facing = VecUtil.calculateEnumfacingLook(vec);
               if (PizzaClient.mc.field_71442_b.func_180512_c(block, facing)) {
                  PizzaClient.mc.field_71452_i.func_180532_a(block, facing);
                  return true;
               }
            } else {
               PizzaClient.mc.field_71442_b.func_78767_c();
            }
         }
      }

      return false;
   }

   default boolean nuke(Vec3 vec, boolean noSwing) {
      if (vec != null) {
         FakeRotation.rotationVec = vec;
         BlockPos block = new BlockPos(vec);
         if (!PizzaClient.mc.field_71439_g.func_71039_bw()) {
            if (PizzaClient.mc.field_71462_r == null) {
               EnumFacing facing = VecUtil.calculateEnumfacingLook(vec);
               if (PizzaClient.mc.field_71442_b.func_180512_c(block, facing)) {
                  PizzaClient.mc.field_71452_i.func_180532_a(block, facing);
                  if (!noSwing) {
                     PizzaClient.mc.field_71439_g.func_71038_i();
                  }

                  return true;
               }
            } else {
               PizzaClient.mc.field_71442_b.func_78767_c();
            }
         }
      }

      return false;
   }

   default boolean nuke(BlockPos pos, boolean noSwing) {
      Vec3 vec = this.getRandomAABBPoint(pos);
      if (vec != null) {
         FakeRotation.rotationVec = vec;
         BlockPos block = new BlockPos(vec);
         if (!PizzaClient.mc.field_71439_g.func_71039_bw()) {
            if (PizzaClient.mc.field_71462_r == null) {
               EnumFacing facing = VecUtil.calculateEnumfacingLook(vec);
               if (PizzaClient.mc.field_71442_b.func_180512_c(block, facing)) {
                  PizzaClient.mc.field_71452_i.func_180532_a(block, facing);
                  if (!noSwing) {
                     PizzaClient.mc.field_71439_g.func_71038_i();
                  }

                  return true;
               }
            } else {
               PizzaClient.mc.field_71442_b.func_78767_c();
            }
         }
      }

      return false;
   }

   default Vec3 getRandomAABBPoint(BlockPos pos) {
      return MathUtil.randomAABBPoint(pos);
   }

   default boolean invalidate() {
      return true;
   }
}
