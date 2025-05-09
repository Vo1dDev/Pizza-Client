package qolskyblockmod.pizzaclient.util.rotation;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;

public class Rotation {
   public final float yaw;
   public final float pitch;

   public Rotation(float yawIn, float pitchIn) {
      this.yaw = yawIn;
      this.pitch = pitchIn;
   }

   public static Rotation getRotationDifference(Vec3 target) {
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      double dist = (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
      return new Rotation((float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z), (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, dist) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A));
   }

   public static Rotation getRotationDifference(Vec3 target, float playerYaw, float playerPitch) {
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      double dist = (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
      return new Rotation((float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)playerYaw), (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, dist) * 57.29577951308232D) - (double)playerPitch));
   }

   public static Rotation getRotation(Vec3 target) {
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      double dist = (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ);
      return new Rotation((float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z) + PizzaClient.mc.field_71439_g.field_70177_z, (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, dist) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A) + PizzaClient.mc.field_71439_g.field_70125_A);
   }

   public static float getYaw(Vec3 target) {
      return (float)MathHelper.func_76138_g(MathHelper.func_181159_b(target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v, target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z) + PizzaClient.mc.field_71439_g.field_70177_z;
   }

   public static float getYawDifference(Vec3 target) {
      return (float)MathHelper.func_76138_g(MathHelper.func_181159_b(target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v, target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z);
   }

   public static Rotation getLastReportedRotation() {
      return FakeRotation.lastPitch != 69420.0F ? new Rotation(FakeRotation.lastYaw, FakeRotation.lastPitch) : new Rotation(PizzaClient.mc.field_71439_g.field_70177_z, PizzaClient.mc.field_71439_g.field_70125_A);
   }

   public float max() {
      return MathUtil.max(MathUtil.abs(this.yaw), MathUtil.abs(this.pitch));
   }

   public float min() {
      return MathUtil.min(MathUtil.abs(this.yaw), MathUtil.abs(this.pitch));
   }

   public float sum() {
      return MathUtil.abs(this.yaw) + MathUtil.abs(this.pitch);
   }

   public String toString() {
      return "{yaw=" + this.yaw + ", pitch=" + this.pitch + "}";
   }
}
