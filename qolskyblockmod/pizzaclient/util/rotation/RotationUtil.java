package qolskyblockmod.pizzaclient.util.rotation;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;

public class RotationUtil {
   public static float getYawDifference(float yaw) {
      return (float)MathHelper.func_76138_g((double)(yaw - PizzaClient.mc.field_71439_g.field_70177_z));
   }

   public static float getPitchDifference(float pitch) {
      return (float)MathHelper.func_76138_g((double)(pitch - PizzaClient.mc.field_71439_g.field_70125_A));
   }

   public static float getYawClosestTo90Degrees() {
      float yaw;
      if (PizzaClient.mc.field_71439_g.field_70177_z > 0.0F) {
         yaw = PizzaClient.mc.field_71439_g.field_70177_z % 90.0F;
         return yaw > 45.0F ? PizzaClient.mc.field_71439_g.field_70177_z + (90.0F - yaw) : PizzaClient.mc.field_71439_g.field_70177_z - yaw;
      } else {
         yaw = -PizzaClient.mc.field_71439_g.field_70177_z;
         float i = yaw % 90.0F;
         return -(i > 45.0F ? yaw + (90.0F - i) : yaw - i);
      }
   }

   public static float getYawClosestTo90Degrees(float yawIn) {
      float yaw;
      if (yawIn > 0.0F) {
         yaw = yawIn % 90.0F;
         return yaw > 45.0F ? yawIn + (90.0F - yaw) : yawIn - yaw;
      } else {
         yaw = -yawIn;
         float i = yaw % 90.0F;
         return -(i > 45.0F ? yaw + (90.0F - i) : yaw - i);
      }
   }

   public static float getYawDifference(Vec3 target) {
      double yaw = MathHelper.func_181159_b(target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v, target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t) * 57.29577951308232D - 90.0D;
      return (float)MathHelper.func_76138_g(yaw - (double)PizzaClient.mc.field_71439_g.field_70177_z);
   }

   public static float getPitchDifference(Vec3 target) {
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      double pitch = -(MathHelper.func_181159_b(target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PizzaClient.mc.field_71439_g.func_70047_e()), (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D);
      return (float)MathHelper.func_76138_g(pitch - (double)PizzaClient.mc.field_71439_g.field_70125_A);
   }
}
