package qolskyblockmod.pizzaclient.util.rotation.fake;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;

public class FakeRotation {
   private static float actualYaw;
   private static float actualPitch;
   public static Vec3 rotationVec;
   public static BlockPos hitPos;
   public static float lastYaw;
   public static float lastPitch = 69420.0F;
   public static boolean leftClick;
   public static int rightClick;
   public static boolean smoothRotate;
   public static boolean preventSnap;
   public static int itemSlot;
   private static boolean finishedSmoothRotate;

   public static void onPlayerMovePre() {
      Rotation rotation;
      if (smoothRotate) {
         rotation = smoothRotateToVec();
         actualYaw = PizzaClient.mc.field_71439_g.field_70177_z;
         actualPitch = PizzaClient.mc.field_71439_g.field_70125_A;
         PizzaClient.mc.field_71439_g.field_70177_z = rotation.yaw;
         PizzaClient.mc.field_71439_g.field_70125_A = rotation.pitch;
         if (smoothRotate) {
            return;
         }
      } else {
         rotation = Rotation.getRotation(rotationVec);
         actualYaw = PizzaClient.mc.field_71439_g.field_70177_z;
         actualPitch = PizzaClient.mc.field_71439_g.field_70125_A;
         PizzaClient.mc.field_71439_g.field_70177_z = rotation.yaw;
         PizzaClient.mc.field_71439_g.field_70125_A = rotation.pitch;
      }

      if (rightClick != 0) {
         BlockPos pos;
         if (hitPos != null) {
            pos = hitPos;
            hitPos = null;
         } else {
            pos = new BlockPos(rotationVec);
         }

         MovingObjectPosition position = VecUtil.calculateInterceptLook(pos, rotationVec, 4.5F);
         if (position != null) {
            int lastSlot;
            if (itemSlot == 0) {
               for(lastSlot = 0; lastSlot < rightClick; ++lastSlot) {
                  if (PizzaClient.mc.field_71442_b.func_178890_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g(), pos, position.field_178784_b, position.field_72307_f)) {
                     PizzaClient.mc.field_71439_g.func_71038_i();
                  }
               }
            } else {
               lastSlot = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = itemSlot;

               for(int i = 0; i < rightClick; ++i) {
                  if (PizzaClient.mc.field_71442_b.func_178890_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g(), pos, position.field_178784_b, position.field_72307_f)) {
                     PizzaClient.mc.field_71439_g.func_71038_i();
                  }
               }

               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = lastSlot;
               itemSlot = 0;
            }

            rightClick = 0;
         }
      }

   }

   public static void onPlayerMovePost() {
      lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
      lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      PizzaClient.mc.field_71439_g.field_70125_A = actualPitch;
      PizzaClient.mc.field_71439_g.field_70177_z = actualYaw;
      if (leftClick) {
         KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i());
         leftClick = false;
      } else {
         if (!smoothRotate) {
            if (finishedSmoothRotate) {
               finishedSmoothRotate = false;
               return;
            }

            if (preventSnap) {
               smoothRotate = true;
               rotationVec = PlayerUtil.getPositionEyes().func_178787_e(PlayerUtil.getLook(10.0F));
               preventSnap = false;
               return;
            }

            rotationVec = null;
         }

      }
   }

   public static void smoothRotateTo(Vec3 target) {
      smoothRotate = true;
      rotationVec = target;
      if (lastPitch == 69420.0F) {
         lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
         lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      }

   }

   public static void smoothRotateTo(MovingObjectPosition pos) {
      smoothRotate = true;
      rotationVec = pos.field_72307_f;
      hitPos = pos.func_178782_a();
      if (lastPitch == 69420.0F) {
         lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
         lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      }

   }

   public static void smoothRotateToIntercept(MovingObjectPosition pos) {
      smoothRotate = true;
      rotationVec = pos.field_72307_f;
      hitPos = Utils.getBlockFromHit(pos);
      if (lastPitch == 69420.0F) {
         lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
         lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      }

   }

   private static Rotation smoothRotateToVec() {
      Rotation rotation = Rotation.getRotationDifference(rotationVec, lastYaw, lastPitch);
      if (MathUtil.abs(rotation.yaw) < (float)PizzaClient.config.cropAuraSmoothRotation && MathUtil.abs(rotation.pitch) < (float)PizzaClient.config.cropAuraSmoothRotation) {
         smoothRotate = false;
         finishedSmoothRotate = true;
         return new Rotation(lastYaw + rotation.yaw, lastPitch + rotation.pitch);
      } else {
         float rotateYaw;
         float rotatePitch;
         if (MathUtil.abs(rotation.yaw) > MathUtil.abs(rotation.pitch)) {
            rotateYaw = (float)PizzaClient.config.cropAuraSmoothRotation + MathUtil.randomFloat();
            rotatePitch = rotateYaw * MathUtil.abs(rotation.pitch / rotation.yaw);
         } else {
            rotatePitch = (float)PizzaClient.config.cropAuraSmoothRotation + MathUtil.randomFloat();
            rotateYaw = rotatePitch * MathUtil.abs(rotation.yaw / rotation.pitch);
         }

         if (rotation.yaw < 0.0F) {
            rotateYaw = -rotateYaw;
         }

         if (rotation.pitch < 0.0F) {
            rotatePitch = -rotatePitch;
         }

         return new Rotation(lastYaw + rotateYaw, lastPitch + rotatePitch);
      }
   }

   public static void setToDefault() {
      lastPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      lastYaw = PizzaClient.mc.field_71439_g.field_70177_z;
   }

   public static void disable() {
      rotationVec = null;
      leftClick = false;
      rightClick = 0;
      smoothRotate = false;
      hitPos = null;
   }
}
