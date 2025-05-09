package qolskyblockmod.pizzaclient.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.keybinds.KeybindFeatures;
import qolskyblockmod.pizzaclient.util.handler.ScoreboardHandler;
import qolskyblockmod.pizzaclient.util.misc.MethodReflectionHelper;
import qolskyblockmod.pizzaclient.util.misc.distance.EntityDistanceHelper;
import qolskyblockmod.pizzaclient.util.rotation.Rotation;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class PlayerUtil {
   public static final Set<Block> secretBlocks;
   private static final float STEP_SIZE = 0.1F;
   private static final MethodReflectionHelper RIGHT_CLICK;
   private static final MethodReflectionHelper LEFT_CLICK;
   private static final MethodReflectionHelper CONTROLLER;
   public static final float EYE_HEIGHT = 1.62F;

   public static void rightClick() {
      RIGHT_CLICK.invokeMc();
   }

   public static void rightClick(int clickamount) {
      RIGHT_CLICK.invokeMc(clickamount);
   }

   public static void leftClick() {
      LEFT_CLICK.invokeMc();
   }

   public static void leftClick(int clickAmount) {
      LEFT_CLICK.invokeMc(clickAmount);
   }

   public static void forceUpdateController() {
      CONTROLLER.invoke(PizzaClient.mc.field_71442_b);
   }

   public static void updatePlayer() {
      try {
         Method method = EntityPlayerSP.class.getDeclaredMethod("func_175161_p");
         method.setAccessible(true);
         method.invoke(PizzaClient.mc);
      } catch (Exception var1) {
      }

   }

   public static void ghostBlock(float dist) {
      Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + (double)fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
      Vec3 look = getLook();
      look = VecUtil.scaleVec(look, 0.10000000149011612D / look.func_72433_c());

      for(int i = 0; i < MathUtil.floor(dist / 0.1F) - 2; ++i) {
         BlockPos blockAtPos = new BlockPos(position);
         if (!KeybindFeatures.notGhostBlockable.contains(PizzaClient.mc.field_71441_e.func_180495_p(blockAtPos).func_177230_c())) {
            if (PizzaClient.mc.field_71441_e.func_180495_p(blockAtPos).func_177230_c() == Blocks.field_150465_bP && Utils.isWitherEssence((TileEntitySkull)PizzaClient.mc.field_71441_e.func_175625_s(blockAtPos))) {
               return;
            }

            PizzaClient.mc.field_71441_e.func_175698_g(blockAtPos);
         }

         position = position.func_178787_e(look);
      }

   }

   public static void ghostBlockIgnoreInteractables(float dist) {
      Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + (double)fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
      Vec3 look = getLook();
      look = Utils.scaleVec(look, 0.10000000149011612D / look.func_72433_c());

      for(int i = 0; i < MathUtil.floor(dist / 0.1F) - 2; ++i) {
         BlockPos blockAtPos = new BlockPos(position);
         if (!secretBlocks.contains(PizzaClient.mc.field_71441_e.func_180495_p(blockAtPos).func_177230_c())) {
            PizzaClient.mc.field_71441_e.func_175698_g(blockAtPos);
         }

         position = position.func_178787_e(look);
      }

   }

   public static HashSet<BlockPos> getAllFacingBlockPos(float range) {
      Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + (double)fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
      Vec3 look = getLook();
      look = Utils.scaleVec(look, 0.10000000149011612D / look.func_72433_c());
      HashSet<BlockPos> facingBlocks = new HashSet();

      for(int i = 0; i < MathUtil.floor(range / 0.1F) - 2; ++i) {
         BlockPos blockAtPos = new BlockPos(position);
         facingBlocks.add(blockAtPos);
         position = position.func_178787_e(look);
      }

      return facingBlocks;
   }

   public static int checkHotBarForItem(Item item) {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() == item) {
            return i;
         }
      }

      return 0;
   }

   public static int checkHotBarForEtherwarp() {
      int diamondSword = 0;

      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null) {
            Item item = stack.func_77973_b();
            if (item == Items.field_151047_v) {
               return i;
            }

            if (item == Items.field_151048_u) {
               diamondSword = i;
            }
         }
      }

      return diamondSword;
   }

   public static boolean hotbarHasEtherwarp() {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && (stack.func_77973_b() == Items.field_151047_v || stack.func_77973_b() == Items.field_151048_u)) {
            return true;
         }
      }

      return false;
   }

   public static int checkHotbarForDisplayName(String displayName) {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && StringUtils.func_76338_a(stack.func_82833_r()).contains(displayName)) {
            return i;
         }
      }

      return 0;
   }

   public static boolean hotbarHasItem(Item item) {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() == item) {
            return true;
         }
      }

      return false;
   }

   public static boolean hotbarHasDisplayName(String displayName) {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && StringUtils.func_76338_a(PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i].func_82833_r()).contains(displayName)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isHoldingAotv() {
      ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
      return held != null && held.func_77973_b() == Items.field_151047_v;
   }

   private static Vec3 getVectorForRotation(float pitch, float yaw) {
      float f2 = -MathHelper.func_76134_b(-pitch * 0.017453292F);
      return new Vec3((double)(MathHelper.func_76126_a(-yaw * 0.017453292F - 3.1415927F) * f2), (double)MathHelper.func_76126_a(-pitch * 0.017453292F), (double)(MathHelper.func_76134_b(-yaw * 0.017453292F - 3.1415927F) * f2));
   }

   private static Vec3 getVectorForRotation() {
      float f2 = -MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70125_A * 0.017453292F);
      return new Vec3((double)(MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292F - 3.1415927F) * f2), (double)MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70125_A * 0.017453292F), (double)(MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292F - 3.1415927F) * f2));
   }

   private static Vec3 getVectorForRotation2D(float yaw) {
      return new Vec3((double)MathHelper.func_76126_a(-yaw * 0.017453292F - 3.1415927F), 0.0D, (double)MathHelper.func_76134_b(-yaw * 0.017453292F - 3.1415927F));
   }

   private static Vec3 getVectorForRotation2D() {
      return new Vec3((double)MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292F - 3.1415927F), 0.0D, (double)MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292F - 3.1415927F));
   }

   public static Vec3 getLook(Vec3 vec) {
      double diffX = vec.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffY = vec.field_72448_b - (PizzaClient.mc.field_71439_g.field_70163_u + (double)fastEyeHeight());
      double diffZ = vec.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      float yaw = (float)(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D);
      float pitch = (float)(-(MathHelper.func_181159_b(diffY, (double)MathHelper.func_76133_a(diffX * diffX + diffZ * diffZ)) * 57.29577951308232D));
      return getVectorForRotation(pitch, yaw);
   }

   public static Vec3 getLook(float yaw, float pitch) {
      return getVectorForRotation(pitch, yaw);
   }

   public static Vec3 getLook() {
      return getVectorForRotation();
   }

   public static Vec3 getLook(float multiplier) {
      float f2 = -MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70125_A * 0.017453292F);
      return new Vec3((double)(MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292F - 3.1415927F) * f2 * multiplier), (double)(MathHelper.func_76126_a(-PizzaClient.mc.field_71439_g.field_70125_A * 0.017453292F) * multiplier), (double)(MathHelper.func_76134_b(-PizzaClient.mc.field_71439_g.field_70177_z * 0.017453292F - 3.1415927F) * f2 * multiplier));
   }

   public static Vec3 get2DLook(Vec3 vec) {
      double diffX = vec.field_72450_a - PizzaClient.mc.field_71439_g.field_70165_t;
      double diffZ = vec.field_72449_c - PizzaClient.mc.field_71439_g.field_70161_v;
      return getVectorForRotation2D((float)(MathHelper.func_181159_b(diffZ, diffX) * 57.29577951308232D - 90.0D) + PizzaClient.mc.field_71439_g.field_70177_z);
   }

   public static Vec3 get2DLook(float yaw) {
      return getVectorForRotation2D(yaw);
   }

   public static Vec3 get2DLook() {
      return getVectorForRotation2D();
   }

   public static Set<BlockPos> getBlocksInBetweenBlocks(Vec3 endVec) {
      Vec3 position = getPositionEyes();
      Vec3 look = getLook(endVec);
      look = Utils.scaleVec(look, 0.10000000149011612D / look.func_72433_c());
      HashSet<BlockPos> facingBlocks = new HashSet();

      for(int i = 0; i < 400; ++i) {
         BlockPos blockAtPos = new BlockPos(position);
         facingBlocks.add(blockAtPos);
         if (blockAtPos.equals(new BlockPos(endVec.field_72450_a, endVec.field_72448_b, endVec.field_72449_c))) {
            return facingBlocks;
         }

         position = position.func_178787_e(look);
      }

      return facingBlocks;
   }

   public static boolean facingSameXAndZ(BlockPos pos, float range) {
      Vec3 position = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + (double)fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
      Vec3 look = getLook();
      look = Utils.scaleVec(look, 0.10000000149011612D / look.func_72433_c());

      for(int i = 0; i < MathUtil.floor(range / 0.1F) - 2; ++i) {
         BlockPos blockAtPos = new BlockPos(position);
         if (blockAtPos.func_177958_n() == pos.func_177958_n() && blockAtPos.func_177952_p() == pos.func_177952_p()) {
            return true;
         }

         position = position.func_178787_e(look);
      }

      return false;
   }

   public static void sendSlotRightClickPacket(int slot) {
      int currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
      PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = slot;
      PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[slot]);
      PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
   }

   public static Iterable<BlockPos> getPlayerBlocks() {
      return BlockPos.func_177980_a(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D, PizzaClient.mc.field_71439_g.field_70163_u + (double)fastEyeHeight() - 5.0D, PizzaClient.mc.field_71439_g.field_70161_v - 5.0D), new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t + 5.0D, PizzaClient.mc.field_71439_g.field_70163_u + (double)fastEyeHeight() + 5.0D, PizzaClient.mc.field_71439_g.field_70161_v + 5.0D));
   }

   public static float fastEyeHeight() {
      return PizzaClient.mc.field_71439_g.func_70093_af() ? 1.54F : 1.62F;
   }

   public static float fastEyeHeight(EntityPlayer playerIn) {
      return playerIn.func_70093_af() ? 1.54F : 1.62F;
   }

   public static Vec3 getPositionEyes() {
      return new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + (double)fastEyeHeight(), PizzaClient.mc.field_71439_g.field_70161_v);
   }

   public static EnumFacing getHorizontalFacing(float yaw) {
      return EnumFacing.func_176731_b(MathUtil.floor((double)(yaw * 4.0F / 360.0F) + 0.5D) & 3);
   }

   public static EnumFacing getHorizontalFacing(Vec3 look) {
      return EnumFacing.func_176731_b(MathUtil.floor((double)(Rotation.getRotation(look).yaw * 4.0F / 360.0F) + 0.5D) & 3);
   }

   public static boolean isStandingStill() {
      return PizzaClient.mc.field_71439_g.field_70159_w == 0.0D && PizzaClient.mc.field_71439_g.field_70179_y == 0.0D;
   }

   public static boolean isStandingStill(Entity entity) {
      return entity.field_70159_w == 0.0D && entity.field_70179_y == 0.0D;
   }

   public static int getAdjacentHotbarSlot() {
      if (PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c == 0) {
         return 1;
      } else if (PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c > 6) {
         return PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c - 1;
      } else {
         return System.currentTimeMillis() % 2L == 0L ? PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c - 1 : PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c + 1;
      }
   }

   public static int getMiningTool() {
      int gauntlet = -1;

      int i;
      ItemStack stack;
      for(i = 0; i < 8; ++i) {
         stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null) {
            String displayName = StringUtils.func_76338_a(stack.func_82833_r());
            if (stack.func_77973_b() == Items.field_179562_cC) {
               if (displayName.contains("Drill")) {
                  return i;
               }
            } else if (displayName.contains("Gemstone Gauntlet")) {
               gauntlet = i;
            }
         }
      }

      if (gauntlet != -1) {
         return gauntlet;
      } else {
         for(i = 0; i < 8; ++i) {
            stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() instanceof ItemPickaxe) {
               return i;
            }
         }

         return 0;
      }
   }

   public static int getPickaxe() {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() instanceof ItemPickaxe) {
            return i;
         }
      }

      return -1;
   }

   public static boolean holdingMiningTool() {
      ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
      if (stack == null) {
         return false;
      } else {
         Item item = stack.func_77973_b();
         return item == Items.field_179562_cC || item instanceof ItemPickaxe || StringUtils.func_76338_a(stack.func_82833_r()).contains("Gemstone Gauntlet");
      }
   }

   public static boolean isInLimbo() {
      return !SBInfo.inSkyblock && PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g() == null && ScoreboardHandler.isScoreboardEmpty() && SBInfo.isOnHypixel();
   }

   public static boolean isInLobby() {
      ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[0];
      return held != null ? held.func_82833_r().equals("§aGame Menu §7(Right Click)") : false;
   }

   public static boolean isInSkyblock() {
      return SBInfo.inSkyblock || SBInfo.isInSkyblock();
   }

   public static boolean warpToSkyblock() {
      return isInLimbo() || isInLobby() || !SBInfo.isInSkyblock();
   }

   public static Entity findClosestEntity(Class<? extends Entity> clazz) {
      EntityDistanceHelper helper = new EntityDistanceHelper();
      Iterator var2 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      while(var2.hasNext()) {
         Entity entity = (Entity)var2.next();
         if (entity.getClass() == clazz) {
            helper.compare(entity);
         }
      }

      return helper.closest;
   }

   public static void findPickonimbus() {
      int i;
      ItemStack stack;
      for(i = 0; i < 8; ++i) {
         stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() == Items.field_151046_w && stack.func_82833_r().contains("Pickonimbus 2000")) {
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
            return;
         }
      }

      for(i = 0; i < PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a.length; ++i) {
         stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() == Items.field_151046_w && stack.func_82833_r().contains("Pickonimbus 2000")) {
            (new Thread(() -> {
               KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_151445_Q.func_151463_i());
               Utils.sleep(500);

               while(!(PizzaClient.mc.field_71462_r instanceof GuiInventory)) {
                  if (PizzaClient.mc.field_71462_r != null) {
                     PizzaClient.mc.field_71439_g.func_71053_j();
                     Utils.sleep(250);
                  }

                  KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_151445_Q.func_151463_i());
                  Utils.sleep(1000);
               }

               if (PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g() == null) {
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, 1, PizzaClient.mc.field_71439_g);
                  Utils.sleep(500);
                  PizzaClient.mc.field_71439_g.func_71053_j();
               } else {
                  for(int j = 0; j < 8; ++j) {
                     if (PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[j] == null) {
                        PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, 1, PizzaClient.mc.field_71439_g);
                        Utils.sleep(500);
                        PizzaClient.mc.field_71439_g.func_71053_j();
                        return;
                     }
                  }

                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, 0, PizzaClient.mc.field_71439_g);
                  Utils.sleep(500);
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 36, 0, 0, PizzaClient.mc.field_71439_g);
                  Utils.sleep(500);
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, i, 0, 0, PizzaClient.mc.field_71439_g);
                  Utils.sleep(500);
                  PizzaClient.mc.field_71439_g.func_71053_j();
               }
            })).start();
         }
      }

   }

   public static boolean isPassable(AxisAlignedBB aabb) {
      return isPassable(aabb, getPositionEyes()) ? true : isPassable(aabb, new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u + 0.6D, PizzaClient.mc.field_71439_g.field_70161_v));
   }

   public static boolean isPassable(AxisAlignedBB aabb, Vec3 position) {
      Vec3 middle = Utils.getMiddleOfAABB(aabb);
      Vec3 look = getLook(middle);
      look = VecUtil.scaleVec(look, 0.10000000149011612D / look.func_72433_c());

      for(int i = 0; i < MathUtil.floor(position.func_72438_d(middle) / 0.10000000149011612D) - 2; ++i) {
         BlockPos pos = new BlockPos(position);
         if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150350_a) {
            return false;
         }

         if (aabb.func_72318_a(position)) {
            return true;
         }

         position = position.func_178787_e(look);
      }

      return true;
   }

   public static void useAbility() {
      if (PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g())) {
         PizzaClient.mc.field_71460_t.field_78516_c.func_78445_c();
      }

   }

   public static boolean switchDirection() {
      double posX = Utils.formatDouble(MathUtil.abs(PizzaClient.mc.field_71439_g.field_70165_t));
      double posZ = Utils.formatDouble(MathUtil.abs(PizzaClient.mc.field_71439_g.field_70161_v));
      double x = Utils.formatDouble(posX - (double)MathUtil.floor(posX));
      if (x != 0.3D && x != 0.7D) {
         double z = Utils.formatDouble(posZ - (double)MathUtil.floor(posZ));
         return z == 0.3D || z == 0.7D;
      } else {
         return true;
      }
   }

   public static double getMovingDirection(EnumFacing facing) {
      switch(facing) {
      case NORTH:
         return PizzaClient.mc.field_71439_g.field_70161_v;
      case SOUTH:
         return PizzaClient.mc.field_71439_g.field_70161_v;
      default:
         return PizzaClient.mc.field_71439_g.field_70165_t;
      }
   }

   public static double getMovingDirection() {
      float yaw = PizzaClient.mc.field_71439_g.field_70177_z;
      if (PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d() || PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d()) {
         yaw += 90.0F;
      }

      switch(getHorizontalFacing(yaw)) {
      case NORTH:
         return PizzaClient.mc.field_71439_g.field_70179_y;
      case SOUTH:
         return PizzaClient.mc.field_71439_g.field_70179_y;
      default:
         return PizzaClient.mc.field_71439_g.field_70159_w;
      }
   }

   public static void windowClick(int slot) {
      PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, slot, 0, 0, PizzaClient.mc.field_71439_g);
   }

   public static void windowClick(int slot, int button, int mode) {
      PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, slot, button, mode, PizzaClient.mc.field_71439_g);
   }

   public static void windowClick(int extraId, int slot, int button, int mode) {
      PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c + extraId, slot, button, mode, PizzaClient.mc.field_71439_g);
   }

   public static int getFishingRod() {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() == Items.field_151112_aM) {
            return i;
         }
      }

      return 0;
   }

   public static int getRandomMovement() {
      return Utils.random.nextInt(2) == 0 ? PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i() : PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i();
   }

   public static void moveOpposite() {
      if (PizzaClient.mc.field_71474_y.field_74351_w.func_151470_d()) {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
      } else if (PizzaClient.mc.field_71474_y.field_74368_y.func_151470_d()) {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
      }

      if (PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d()) {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
      } else if (PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d()) {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
      }

   }

   public static void rotateToClosestHitttable(BlockPos currentBlock) {
      float rand = MathUtil.randomFloat(3.0F);
      float pitch = MathUtil.randomFloat(3.0F);
      Vec3 hittable = Utils.getHittableAdjacent(currentBlock);
      if (hittable != null) {
         (new Rotater(hittable)).rotate();

         while(Rotater.rotating) {
            Utils.sleep(1);
         }

         Utils.sleep(50 + Utils.random.nextInt(50));
         (new Rotater(VecUtil.getClosestHittableToMiddle(currentBlock))).rotate();
      } else {
         (new Rotater(60.0F + rand, pitch)).rotate();

         while(Rotater.rotating) {
            Utils.sleep(1);
         }

         Utils.sleep(50 + Utils.random.nextInt(50));
         (new Rotater(-(60.0F + rand), -pitch)).rotate();
      }

   }

   public static void rotateToClosestHitttable() {
      rotateToClosestHitttable(PizzaClient.mc.field_71476_x.func_178782_a());
   }

   public static void warpToChollows() {
      while(PizzaClient.mc.field_71462_r == null) {
         PizzaClient.mc.field_71439_g.func_71165_d("/sbmenu");
         Utils.sleep(1000);
      }

      Utils.sleep(300);
      if (PizzaClient.mc.field_71439_g.field_71070_bA instanceof ContainerChest && Utils.getContainerName().contains("SkyBlock Menu")) {
         while(!Utils.getContainerNameTrimmed().equals("Enter the Crystal Hollows?")) {
            if (!Utils.getContainerName().contains("SkyBlock Menu")) {
               PizzaClient.mc.field_71439_g.func_71053_j();
               Utils.sleep(500);

               while(PizzaClient.mc.field_71462_r == null) {
                  PizzaClient.mc.field_71439_g.func_71165_d("/sbmenu");
                  Utils.sleep(1000);
               }
            }

            PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 16, 0, 0, PizzaClient.mc.field_71439_g);
            Utils.sleep(1000);
         }

         Utils.sleep(500);
         PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 11, 0, 0, PizzaClient.mc.field_71439_g);
         Utils.sleep(2000);
         Utils.sleep(1000);
         if (PizzaClient.mc.field_71462_r != null) {
            PizzaClient.mc.field_71439_g.func_71053_j();
         }

      } else {
         PizzaClient.mc.field_71439_g.func_71053_j();
         Utils.sleep(400);
         warpToChollows();
      }
   }

   public static boolean holdingItem(Item item) {
      ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
      return held != null && held.func_77973_b() == item;
   }

   public static void swapToSlot(Item item) {
      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() == item) {
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
            break;
         }
      }

   }

   public static void closeScreen() {
      while(PizzaClient.mc.field_71462_r != null) {
         PizzaClient.mc.field_71439_g.func_71053_j();
         Utils.sleep(500);
      }

   }

   static {
      secretBlocks = new HashSet(Arrays.asList(Blocks.field_150486_ae, Blocks.field_150442_at, Blocks.field_150465_bP, Blocks.field_150447_bR));
      RIGHT_CLICK = new MethodReflectionHelper(Minecraft.class, "func_147121_ag", new Class[0]);
      LEFT_CLICK = new MethodReflectionHelper(Minecraft.class, "func_147116_af", new Class[0]);
      CONTROLLER = new MethodReflectionHelper(PlayerControllerMP.class, "func_78750_j", new Class[0]);
   }
}
