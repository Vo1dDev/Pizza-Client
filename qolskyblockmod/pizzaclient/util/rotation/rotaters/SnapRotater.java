package qolskyblockmod.pizzaclient.util.rotation.rotaters;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.PlayerUtil;

public class SnapRotater {
   public static void snapTo(float yaw, float pitch) {
      PizzaClient.mc.field_71439_g.field_70177_z = yaw;
      PizzaClient.mc.field_71439_g.field_70125_A = pitch;
   }

   public static void addRotation(float yaw, float pitch) {
      EntityPlayerSP var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70177_z += yaw;
      var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70125_A += pitch;
   }

   public static void snapTo(Vec3 target) {
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      EntityPlayerSP var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70177_z += (float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z);
      var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70125_A += (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A);
   }

   public static void snapTo(Entity entity) {
      Vec3 target = new Vec3(entity.field_70165_t, entity.field_70163_u + (double)entity.func_70047_e(), entity.field_70161_v);
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      EntityPlayerSP var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70177_z += (float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z);
      var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70125_A += (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A);
   }

   public static void snapTo(float x, float y, float z) {
      double diffX = (double)x - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = (double)y - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = (double)z - PizzaClient.mc.field_71439_g.field_70161_v;
      EntityPlayerSP var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70177_z += (float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z);
      var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70125_A += (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A);
   }

   public static void snapTo(double x, double y, double z) {
      double diffX = x - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = y - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = z - PizzaClient.mc.field_71439_g.field_70161_v;
      EntityPlayerSP var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70177_z += (float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z);
      var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70125_A += (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A);
   }

   public static void snapTo(int x, int y, int z) {
      double diffX = (double)x - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = (double)y - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = (double)z - PizzaClient.mc.field_71439_g.field_70161_v;
      EntityPlayerSP var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70177_z += (float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z);
      var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70125_A += (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A);
   }

   public static void snapToLeftClick(Vec3 target) {
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      EntityPlayerSP var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70177_z += (float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z);
      var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70125_A += (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A);
      KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i());
   }

   public static void snapToRightClick(Vec3 target) {
      double diffX = target.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = target.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight());
      double diffZ = target.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      EntityPlayerSP var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70177_z += (float)MathHelper.func_76138_g(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D - (double)PizzaClient.mc.field_71439_g.field_70177_z);
      var10000 = PizzaClient.mc.field_71439_g;
      var10000.field_70125_A += (float)MathHelper.func_76138_g(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D) - (double)PizzaClient.mc.field_71439_g.field_70125_A);
      KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
   }
}
