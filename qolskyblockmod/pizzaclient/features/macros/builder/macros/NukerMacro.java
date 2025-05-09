package qolskyblockmod.pizzaclient.features.macros.builder.macros;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.INuker;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.NukerBase;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;
import qolskyblockmod.pizzaclient.util.rotation.helper.BlockPosFakeRotationHelper;

public abstract class NukerMacro extends Macro implements INuker {
   public static Vec3 vec;
   public static final Set<Block> avoid;

   public abstract boolean isBlockValid(BlockPos var1);

   public boolean nuke(boolean noSwing) {
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

   public boolean nukeSilent() {
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

   public void onNukePre() {
   }

   public boolean findVec() {
      BlockPosFakeRotationHelper helper = new BlockPosFakeRotationHelper();

      int y;
      int x;
      int z;
      BlockPos pos;
      for(y = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u); (double)y < PizzaClient.mc.field_71439_g.field_70163_u + 2.0D; ++y) {
         for(x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D); (double)x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0D; ++x) {
            for(z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0D); (double)z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0D; ++z) {
               pos = new BlockPos(x, y, z);
               if (NukerBase.nuker.isBlockValid(pos)) {
                  if (PizzaClient.config.nukeReachableBlocks) {
                     if (!VecUtil.isHittable(pos)) {
                        continue;
                     }
                  } else if (!VecUtil.canReachBlock(pos)) {
                     continue;
                  }

                  helper.compare(pos);
               }
            }
         }
      }

      if (helper.isNotNull()) {
         vec = MathUtil.randomAABBPoint(helper.bestPos);
         return true;
      } else {
         for(y = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight() - 5.0D); (double)y < PizzaClient.mc.field_71439_g.field_70163_u + (double)PlayerUtil.fastEyeHeight() + 5.0D; ++y) {
            for(x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D); (double)x < PizzaClient.mc.field_71439_g.field_70165_t + 5.0D; ++x) {
               for(z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v - 5.0D); (double)z < PizzaClient.mc.field_71439_g.field_70161_v + 5.0D; ++z) {
                  pos = new BlockPos(x, y, z);
                  if (NukerBase.nuker.isBlockValid(pos)) {
                     if (PizzaClient.config.nukeReachableBlocks) {
                        if (!VecUtil.isHittable(pos)) {
                           continue;
                        }
                     } else if (!VecUtil.canReachBlock(pos)) {
                        continue;
                     }

                     helper.compare(pos);
                  }
               }
            }
         }

         if (helper.isNotNull()) {
            vec = MathUtil.randomAABBPoint(helper.bestPos);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean isVecValid() {
      if (vec == null) {
         return false;
      } else {
         BlockPos pos = new BlockPos(vec);
         return !VecUtil.canReachBlock(pos) ? false : this.isBlockValid(pos);
      }
   }

   public void drawCurrentBlock() {
      if (vec != null) {
         RenderUtil.drawFilledEsp(new BlockPos(vec), Color.CYAN, 0.5F);
      }

   }

   public void drawCurrentBlock(Color c) {
      if (vec != null) {
         RenderUtil.drawFilledEsp(new BlockPos(vec), c, 0.5F);
      }

   }

   public void setPoint(Vec3 vecIn) {
      vec = MathUtil.randomAABBPoint(vecIn);
   }

   public void setPoint(BlockPos pos) {
      vec = MathUtil.randomAABBPoint(pos);
   }

   public void swapToTool() {
      if (!PlayerUtil.holdingMiningTool()) {
         PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getMiningTool();
      }

   }

   public void onChat(String unformatted) {
      if (unformatted.equals("Mining Speed Boost is now available!")) {
         PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g());
      }

   }

   public boolean applyPositionFailsafe() {
      return false;
   }

   public boolean applyBedrockFailsafe() {
      return false;
   }

   public void onRender() {
      this.drawCurrentBlock();
   }

   public boolean applyFailsafes() {
      return false;
   }

   public boolean applyPlayerFailsafes() {
      return PizzaClient.config.stopWhenNearPlayer;
   }

   static {
      avoid = new HashSet(Arrays.asList(Blocks.field_150350_a, Blocks.field_150357_h, Blocks.field_150486_ae));
   }
}
