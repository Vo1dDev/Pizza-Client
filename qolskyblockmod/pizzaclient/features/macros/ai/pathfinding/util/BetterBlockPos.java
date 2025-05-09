package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vec3i;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;

public class BetterBlockPos extends BlockPos {
   public final int field_177962_a;
   public final int field_177960_b;
   public final int field_177961_c;
   public static final List<Vec3> FLAT_DIRECTIONS = new ArrayList(Arrays.asList(new Vec3(0.0D, 0.0D, -1.0D), new Vec3(0.0D, 0.0D, 1.0D), new Vec3(-1.0D, 0.0D, 0.0D), new Vec3(1.0D, 0.0D, 0.0D)));
   public static final List<Vec3> ALL_DIRECTIONS = new ArrayList(Arrays.asList(new Vec3(0.0D, -1.0D, 0.0D), new Vec3(0.0D, 1.0D, 0.0D), new Vec3(0.0D, 0.0D, -1.0D), new Vec3(0.0D, 0.0D, 1.0D), new Vec3(-1.0D, 0.0D, 0.0D), new Vec3(1.0D, 0.0D, 0.0D)));

   public BetterBlockPos(int x, int y, int z) {
      super(x, y, z);
      this.field_177962_a = x;
      this.field_177960_b = y;
      this.field_177961_c = z;
   }

   public BetterBlockPos(double x, double y, double z) {
      this(MathUtil.floor(x), MathUtil.floor(y), MathUtil.floor(z));
   }

   public BetterBlockPos(Vec3 vec) {
      this(MathUtil.floor(vec.field_72450_a), MathUtil.floor(vec.field_72448_b), MathUtil.floor(vec.field_72449_c));
   }

   public BetterBlockPos(BlockPos pos) {
      this(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
   }

   public static BetterBlockPos getPlayer() {
      return new BetterBlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
   }

   public boolean isSameXandZ(BetterBlockPos pos) {
      return pos.field_177962_a == this.field_177962_a && pos.field_177961_c == this.field_177961_c;
   }

   public Vec3 toVec() {
      return new Vec3((double)this.field_177962_a + 0.5D, (double)this.field_177960_b + 0.5D, (double)this.field_177961_c + 0.5D);
   }

   public Vec3 toRawVec() {
      return new Vec3((double)this.field_177962_a, (double)this.field_177960_b, (double)this.field_177961_c);
   }

   public boolean equals(BetterBlockPos pos) {
      return pos.field_177962_a == this.field_177962_a && pos.field_177960_b == this.field_177960_b && pos.field_177961_c == this.field_177961_c;
   }

   public boolean equals(BlockPos pos) {
      return pos.func_177958_n() == this.field_177962_a && pos.func_177956_o() == this.field_177960_b && pos.func_177952_p() == this.field_177961_c;
   }

   public double distanceTo(BetterBlockPos pos) {
      double d0 = (double)(pos.field_177962_a - this.field_177962_a);
      double d1 = (double)(pos.field_177960_b - this.field_177960_b);
      double d2 = (double)(pos.field_177961_c - this.field_177961_c);
      return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
   }

   public double distanceToSq(BetterBlockPos pos) {
      double d0 = (double)(pos.field_177962_a - this.field_177962_a);
      double d1 = (double)(pos.field_177960_b - this.field_177960_b);
      double d2 = (double)(pos.field_177961_c - this.field_177961_c);
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double distanceTo(Vec3 pos) {
      double d0 = pos.field_72450_a - (double)this.field_177962_a;
      double d1 = pos.field_72448_b - (double)this.field_177960_b;
      double d2 = pos.field_72449_c - (double)this.field_177961_c;
      return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
   }

   public double distanceToSq(Vec3 pos) {
      double d0 = pos.field_72450_a - (double)this.field_177962_a;
      double d1 = pos.field_72448_b - (double)this.field_177960_b;
      double d2 = pos.field_72449_c - (double)this.field_177961_c;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double getXDistance(double x) {
      return MathUtil.abs(x - (double)this.field_177962_a);
   }

   public double getYDistance(double y) {
      return MathUtil.abs(y - (double)this.field_177960_b);
   }

   public double getZDistance(double z) {
      return MathUtil.abs(z - (double)this.field_177961_c);
   }

   public double getXZDistance(double x, double z) {
      double diffX = x - (double)this.field_177962_a;
      double diffZ = z - (double)this.field_177961_c;
      return Math.sqrt(diffX * diffX + diffZ * diffZ);
   }

   public double getCost(BetterBlockPos pos) {
      return (double)(MathUtil.abs(pos.field_177962_a - this.field_177962_a) + MathUtil.abs(pos.field_177960_b - this.field_177960_b) + MathUtil.abs(pos.field_177961_c - this.field_177961_c));
   }

   public double getXZDistanceSq(double x, double z) {
      double diffX = x - (double)this.field_177962_a;
      double diffZ = z - (double)this.field_177961_c;
      return diffX * diffX + diffZ * diffZ;
   }

   public boolean isInsideAABB(AxisAlignedBB aabb) {
      return (double)this.field_177962_a > aabb.field_72340_a && (double)this.field_177962_a < aabb.field_72336_d && (double)this.field_177960_b > aabb.field_72338_b && (double)this.field_177960_b < aabb.field_72337_e && (double)this.field_177961_c > aabb.field_72339_c && (double)this.field_177961_c < aabb.field_72334_f;
   }

   public BetterBlockPos add(BetterBlockPos pos) {
      return new BetterBlockPos(this.field_177962_a + pos.field_177962_a, this.field_177960_b + pos.field_177960_b, this.field_177961_c + pos.field_177961_c);
   }

   public List<BetterBlockPos> getAdjacents() {
      return new ArrayList(Arrays.asList(new BetterBlockPos(this.field_177962_a, this.field_177960_b, this.field_177961_c - 1), new BetterBlockPos(this.field_177962_a, this.field_177960_b, this.field_177961_c + 1), new BetterBlockPos(this.field_177962_a - 1, this.field_177960_b, this.field_177961_c), new BetterBlockPos(this.field_177962_a + 1, this.field_177960_b, this.field_177961_c)));
   }

   public boolean isBlockLoaded() {
      return this.field_177962_a >= -30000000 && this.field_177961_c >= -30000000 && this.field_177962_a < 30000000 && this.field_177961_c < 30000000 && this.field_177960_b >= 0 && this.field_177960_b < 256 && this.isChunkLoaded(this.field_177962_a >> 4, this.field_177961_c >> 4);
   }

   public Block getBlock() {
      return PizzaClient.mc.field_71441_e.func_180495_p(this).func_177230_c();
   }

   public IBlockState getBlockState() {
      return PizzaClient.mc.field_71441_e.func_180495_p(this);
   }

   private boolean isChunkLoaded(int x, int z) {
      return PizzaClient.mc.field_71441_e.func_72863_F().func_73149_a(x, z) && !PizzaClient.mc.field_71441_e.func_72863_F().func_73154_d(x, z).func_76621_g();
   }

   public Vec3 getPositionEyes() {
      return new Vec3((double)this.field_177962_a + 0.5D, (double)((float)this.field_177960_b + 1.62F), (double)this.field_177961_c + 0.5D);
   }

   public BetterBlockPos up() {
      return new BetterBlockPos(this.field_177962_a, this.field_177960_b + 1, this.field_177961_c);
   }

   public BetterBlockPos up(int amt) {
      return amt == 0 ? this : new BetterBlockPos(this.field_177962_a, this.field_177960_b + amt, this.field_177961_c);
   }

   public BetterBlockPos down() {
      return new BetterBlockPos(this.field_177962_a, this.field_177960_b - 1, this.field_177961_c);
   }

   public BetterBlockPos down(int amt) {
      return amt == 0 ? this : new BetterBlockPos(this.field_177962_a, this.field_177960_b - amt, this.field_177961_c);
   }

   public BetterBlockPos offset(EnumFacing dir) {
      Vec3i vec = dir.func_176730_m();
      return new BetterBlockPos(this.field_177962_a + dir.func_82601_c(), this.field_177960_b + vec.func_177956_o(), this.field_177961_c + vec.func_177952_p());
   }

   public BetterBlockPos offset(EnumFacing dir, int dist) {
      if (dist == 0) {
         return this;
      } else {
         Vec3i vec = dir.func_176730_m();
         return new BetterBlockPos(this.field_177962_a + vec.func_177958_n() * dist, this.field_177960_b + vec.func_177956_o() * dist, this.field_177961_c + vec.func_177952_p() * dist);
      }
   }

   public BetterBlockPos north() {
      return new BetterBlockPos(this.field_177962_a, this.field_177960_b, this.field_177961_c - 1);
   }

   public BetterBlockPos north(int amt) {
      return amt == 0 ? this : new BetterBlockPos(this.field_177962_a, this.field_177960_b, this.field_177961_c - amt);
   }

   public BetterBlockPos south() {
      return new BetterBlockPos(this.field_177962_a, this.field_177960_b, this.field_177961_c + 1);
   }

   public BetterBlockPos south(int amt) {
      return amt == 0 ? this : new BetterBlockPos(this.field_177962_a, this.field_177960_b, this.field_177961_c + amt);
   }

   public BetterBlockPos east() {
      return new BetterBlockPos(this.field_177962_a + 1, this.field_177960_b, this.field_177961_c);
   }

   public BetterBlockPos east(int amt) {
      return amt == 0 ? this : new BetterBlockPos(this.field_177962_a + amt, this.field_177960_b, this.field_177961_c);
   }

   public BetterBlockPos west() {
      return new BetterBlockPos(this.field_177962_a - 1, this.field_177960_b, this.field_177961_c);
   }

   public BetterBlockPos west(int amt) {
      return amt == 0 ? this : new BetterBlockPos(this.field_177962_a - amt, this.field_177960_b, this.field_177961_c);
   }

   public BetterBlockPos add(int x, int y, int z) {
      return new BetterBlockPos(this.field_177962_a + x, this.field_177960_b + y, this.field_177961_c + z);
   }

   public BetterBlockPos add(Vec3 vecIn) {
      return new BetterBlockPos((double)this.field_177962_a + vecIn.field_72450_a, (double)this.field_177960_b + vecIn.field_72448_b, (double)this.field_177961_c + vecIn.field_72449_c);
   }

   public String toString() {
      return "{x=" + this.field_177962_a + ", y=" + this.field_177960_b + ", z=" + this.field_177961_c + "}";
   }

   public int hashCode() {
      return (int)(482263L * (751913L * (long)this.field_177962_a + (long)this.field_177960_b) + (long)this.field_177961_c);
   }

   public int func_177958_n() {
      return this.field_177962_a;
   }

   public int func_177956_o() {
      return this.field_177960_b;
   }

   public int func_177952_p() {
      return this.field_177961_c;
   }
}
