package qolskyblockmod.pizzaclient.util;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class MathUtil {
   public static final float PI = 3.1415927F;
   public static final float PI_180 = 0.017453292F;
   public static final double ROTATION_NUMBER = 57.29577951308232D;
   public static final float ROTATION_NUMBER_F = 57.29578F;
   public static final float PLAYER_REACH_SQ = 20.25F;

   public static int floor(float value) {
      int i = (int)value;
      return value < (float)i ? i - 1 : i;
   }

   public static int floor(double value) {
      int i = (int)value;
      return value < (double)i ? i - 1 : i;
   }

   public static long floor_long(double value) {
      long i = (long)value;
      return value < (double)i ? i - 1L : i;
   }

   public static int round(float value) {
      int floor = floor(value);
      return (double)value >= (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int round(double value) {
      int floor = floor(value);
      return value >= (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int roundUp(float value) {
      int floor = floor(value);
      return (double)value >= (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int roundUp(double value) {
      int floor = floor(value);
      return value >= (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int roundDown(float value) {
      int floor = floor(value);
      return (double)value > (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int roundDown(double value) {
      int floor = floor(value);
      return value > (double)floor + 0.5D ? floor + 1 : floor;
   }

   public static int ceil(float value) {
      int i = (int)value;
      return value > (float)i ? i + 1 : i;
   }

   public static int ceil(double value) {
      int i = (int)value;
      return value > (double)i ? i + 1 : i;
   }

   public static int abs(int value) {
      return value >= 0 ? value : -value;
   }

   public static float abs(float value) {
      return value >= 0.0F ? value : -value;
   }

   public static double abs(double value) {
      return value >= 0.0D ? value : -value;
   }

   public static double randomDouble() {
      return System.currentTimeMillis() % 2L == 0L ? Utils.random.nextDouble() : -Utils.random.nextDouble();
   }

   public static float randomFloat() {
      return System.currentTimeMillis() % 2L == 0L ? Utils.random.nextFloat() : -Utils.random.nextFloat();
   }

   public static float positiveFloat() {
      return Utils.random.nextFloat();
   }

   public static float negativeFloat() {
      return -Utils.random.nextFloat();
   }

   public static float randomFloat(float multiplier) {
      return System.currentTimeMillis() % 2L == 0L ? Utils.random.nextFloat() * multiplier : -Utils.random.nextFloat() * multiplier;
   }

   public static float positiveFloat(float multiplier) {
      return Utils.random.nextFloat() * multiplier;
   }

   public static float negativeFloat(float multiplier) {
      return -Utils.random.nextFloat() * multiplier;
   }

   public static int randomNumber(int bound) {
      return System.currentTimeMillis() % 2L == 0L ? Utils.random.nextInt(bound) : -Utils.random.nextInt(bound);
   }

   public static boolean inBetween(float value, float i, float i2) {
      return max(i, i2) >= value && min(i, i2) <= value;
   }

   public static boolean inBetween(double value, float i, float i2) {
      return (double)max(i, i2) >= value && (double)min(i, i2) <= value;
   }

   public static boolean inBetween(double value, double i, double i2) {
      return max(i, i2) >= value && min(i, i2) <= value;
   }

   public static Vec3 randomVec() {
      return new Vec3(Utils.random.nextDouble(), Utils.random.nextDouble(), Utils.random.nextDouble());
   }

   public static double min(double a, double b) {
      return a < b ? a : b;
   }

   public static float min(float a, float b) {
      return a < b ? a : b;
   }

   public static long min(long a, long b) {
      return a < b ? a : b;
   }

   public static int min(int a, int b) {
      return a < b ? a : b;
   }

   public static double max(double a, double b) {
      return a >= b ? a : b;
   }

   public static float max(float a, float b) {
      return a >= b ? a : b;
   }

   public static int max(int a, int b) {
      return a >= b ? a : b;
   }

   public static double max(Vec3 vec) {
      return max(max(vec.field_72450_a, vec.field_72448_b), vec.field_72449_c);
   }

   public static double min(Vec3 vec) {
      return min(min(vec.field_72450_a, vec.field_72448_b), vec.field_72449_c);
   }

   public static Vec3 randomAABBPoint(AxisAlignedBB aabb) {
      return new Vec3(getRandomInBetween(aabb.field_72340_a, aabb.field_72336_d), getRandomInBetween(aabb.field_72338_b, aabb.field_72337_e), getRandomInBetween(aabb.field_72339_c, aabb.field_72334_f));
   }

   public static Vec3 randomAABBPoint(BlockPos pos) {
      AxisAlignedBB aabb = Utils.getBlockAABB(pos);
      return new Vec3(getRandomInBetween(aabb.field_72340_a, aabb.field_72336_d), getRandomInBetween(aabb.field_72338_b, aabb.field_72337_e), getRandomInBetween(aabb.field_72339_c, aabb.field_72334_f));
   }

   public static Vec3 randomAABBPoint(Vec3 pos) {
      AxisAlignedBB aabb = Utils.getBlockAABB(pos);
      return new Vec3(getRandomInBetween(aabb.field_72340_a, aabb.field_72336_d), getRandomInBetween(aabb.field_72338_b, aabb.field_72337_e), getRandomInBetween(aabb.field_72339_c, aabb.field_72334_f));
   }

   public static float getRandomInBetween(float i, float i2) {
      float min = min(i, i2);
      return Utils.random.nextFloat() * (max(i, i2) - min) + min;
   }

   public static double getRandomInBetween(double i, double i2) {
      double min = min(i, i2);
      return Utils.random.nextDouble() * (max(i, i2) - min) + min;
   }

   public static float randomNegative(float numb) {
      return System.currentTimeMillis() % 2L == 0L ? numb : -numb;
   }

   public static double randomNegative(double numb) {
      return System.currentTimeMillis() % 2L == 0L ? numb : -numb;
   }

   public static float clampPitch(float pitch) {
      return pitch < -90.0F ? -90.0F : (pitch > 90.0F ? 90.0F : pitch);
   }

   public static float clampPitch(double pitch) {
      return pitch < -90.0D ? -90.0F : (float)(pitch > 90.0D ? 90.0D : pitch);
   }

   public static float getDecimals(float num) {
      return num - (float)floor(num);
   }

   public static double getDecimals(double num) {
      return num - (double)floor(num);
   }

   public static int getPositveOrZero(int num) {
      return num > 0 ? num : 0;
   }

   public static long getPositveOrZero(long num) {
      return num > 0L ? num : 0L;
   }

   public static float getPositveOrZero(float num) {
      return num > 0.0F ? num : 0.0F;
   }

   public static double getPositveOrZero(double num) {
      return num > 0.0D ? num : 0.0D;
   }

   public static boolean isEven(int param0) {
      // $FF: Couldn't be decompiled
   }
}
