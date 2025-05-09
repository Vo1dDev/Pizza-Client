package qolskyblockmod.pizzaclient.features.macros.farming;

import java.awt.Color;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.Macro;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;
import qolskyblockmod.pizzaclient.util.rotation.helper.MOPFakeRotationHelper;

@SideOnly(Side.CLIENT)
public class CropAura extends Macro {
   private float i;
   private Vec3 lastRotation;
   private BlockPos renderBlock;

   public void onTick() {
      if (this.renderBlock != null) {
         if (!VecUtil.canReachBlock(this.renderBlock)) {
            this.renderBlock = null;
         } else if (PizzaClient.config.cropAuraMode == 3) {
            MovingObjectPosition position;
            if (PizzaClient.config.placeThroughBlocks) {
               position = VecUtil.calculateInterceptLook(this.renderBlock, new Vec3((double)this.renderBlock.func_177958_n() + 0.5D, (double)this.renderBlock.func_177956_o() + 0.5D, (double)this.renderBlock.func_177952_p() + 0.5D), 4.5F);
            } else {
               position = this.rayTraceCocoaBean(this.renderBlock);
            }

            if (position != null) {
               if (PizzaClient.mc.field_71441_e.func_180495_p(this.renderBlock.func_177972_a(position.field_178784_b)).func_177230_c() != Blocks.field_150350_a) {
                  this.renderBlock = null;
               }
            } else {
               this.renderBlock = null;
            }
         } else if (PizzaClient.mc.field_71441_e.func_180495_p(this.renderBlock).func_177230_c() != Blocks.field_150350_a) {
            this.renderBlock = null;
         }
      }

      FakeRotation.preventSnap = true;
      ItemStack held = PizzaClient.mc.field_71439_g.func_70694_bm();
      int metadata;
      int i;
      ItemStack stackAt;
      ItemStack stackAt;
      label162:
      switch(PizzaClient.config.cropAuraMode) {
      case 1:
         if (held == null || held.func_77973_b() != Items.field_151120_aE) {
            metadata = 0;

            while(true) {
               if (metadata >= 8) {
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Found no sugar cane in your hotbar."));
                  return;
               }

               stackAt = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[metadata];
               if (stackAt != null && stackAt.func_77973_b() != null && stackAt.func_77973_b() == Items.field_151120_aE) {
                  PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = metadata;
                  break;
               }

               ++metadata;
            }
         }
         break;
      case 2:
         if (held == null || held.func_77973_b() != Item.func_150898_a(Blocks.field_150434_aF)) {
            metadata = 0;

            while(true) {
               if (metadata >= 8) {
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Found no cactus in your hotbar."));
                  return;
               }

               stackAt = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[metadata];
               if (stackAt != null && stackAt.func_77973_b() != null && stackAt.func_77973_b() == Item.func_150898_a(Blocks.field_150434_aF)) {
                  PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = metadata;
                  break;
               }

               ++metadata;
            }
         }
         break;
      case 3:
         metadata = EnumDyeColor.BROWN.func_176767_b();
         if (held == null) {
            for(i = 0; i < 8; ++i) {
               stackAt = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
               if (stackAt != null && stackAt.func_77973_b() != null && stackAt.func_77973_b() == Items.field_151100_aR && stackAt.func_77952_i() == metadata) {
                  PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
                  break label162;
               }
            }
         } else {
            if (held.func_77973_b() == Items.field_151100_aR && held.func_77952_i() == metadata) {
               break;
            }

            if (held.func_77973_b() == Items.field_151100_aR) {
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumDyeColor.func_176766_a(held.func_77960_j()).toString()));
            }

            for(i = 0; i < 8; ++i) {
               stackAt = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
               if (stackAt != null && stackAt.func_77973_b() != null && stackAt.func_77973_b() == Items.field_151100_aR && stackAt.func_77952_i() == metadata) {
                  PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
                  break label162;
               }
            }
         }

         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Found no cocoa beans in your hotbar."));
         break;
      case 4:
         if (held == null || held.func_77973_b() != Items.field_151075_bm) {
            i = 0;

            while(true) {
               if (i >= 8) {
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Found no nether wart in your hotbar."));
                  return;
               }

               stackAt = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
               if (stackAt != null && stackAt.func_77973_b() != null && stackAt.func_77973_b() == Items.field_151075_bm) {
                  PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
                  break;
               }

               ++i;
            }
         }
      }

      if (!this.run()) {
         this.renderBlock = null;
         this.i = 0.0F;
      } else {
         if (PizzaClient.config.cropAuraBPS != 0) {
            this.i += (float)PizzaClient.config.cropAuraBPS;
            if (this.i >= 20.0F) {
               this.extraBlockPlace();
               this.i %= 20.0F;
            }
         }

      }
   }

   private boolean run() {
      if (FakeRotation.smoothRotate) {
         return true;
      } else {
         if (this.lastRotation == null) {
            this.lastRotation = VecUtil.rayTrace(50.0F).field_72307_f;
         }

         MovingObjectPosition position = this.searchBlock();
         if (position == null) {
            return false;
         } else {
            this.lastRotation = position.field_72307_f;
            if (PizzaClient.config.placeThroughBlocks) {
               FakeRotation.smoothRotateToIntercept(position);
            } else {
               FakeRotation.smoothRotateTo(position);
            }

            FakeRotation.rightClick = 1;
            return true;
         }
      }
   }

   private void extraBlockPlace() {
      if (!FakeRotation.smoothRotate) {
         if (this.lastRotation == null) {
            this.lastRotation = VecUtil.rayTrace(50.0F).field_72307_f;
         }

         MovingObjectPosition closest = this.searchBlock();
         if (closest != null && PizzaClient.mc.field_71442_b.func_178890_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g(), Utils.getBlockFromHit(closest), closest.field_178784_b, closest.field_72307_f)) {
            PizzaClient.mc.field_71439_g.func_71038_i();
         }

      }
   }

   private MovingObjectPosition searchBlock() {
      MOPFakeRotationHelper helper = new MOPFakeRotationHelper();
      Iterator var2;
      BlockPos pos;
      Block blockAt;
      MovingObjectPosition position;
      Vec3 vec;
      switch(PizzaClient.config.cropAuraMode) {
      case 0:
         var2 = BlockPos.func_177980_a(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D, PizzaClient.mc.field_71439_g.field_70163_u - 4.0D, PizzaClient.mc.field_71439_g.field_70161_v - 5.0D), new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t + 5.0D, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v + 5.0D)).iterator();

         while(var2.hasNext()) {
            pos = (BlockPos)var2.next();
            blockAt = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
            if (blockAt == Blocks.field_150458_ak) {
               if (PizzaClient.config.placeThroughBlocks) {
                  vec = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.99D, (double)pos.func_177952_p() + 0.5D);
                  position = VecUtil.calculateInterceptLook(pos, vec, 4.5F);
               } else {
                  position = this.rayTrace(pos);
               }

               if (position != null) {
                  helper.compare(position);
               }
            }
         }

         if (helper.isNotNull()) {
            this.renderBlock = PizzaClient.config.placeThroughBlocks ? Utils.getBlockFromHit(helper.bestPos).func_177984_a() : helper.bestPos.func_178782_a().func_177984_a();
         }
         break;
      case 1:
         var2 = BlockPos.func_177980_a(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D, PizzaClient.mc.field_71439_g.field_70163_u - 4.0D, PizzaClient.mc.field_71439_g.field_70161_v - 5.0D), new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t + 5.0D, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v + 5.0D)).iterator();

         while(true) {
            do {
               if (!var2.hasNext()) {
                  if (helper.isNotNull()) {
                     this.renderBlock = PizzaClient.config.placeThroughBlocks ? Utils.getBlockFromHit(helper.bestPos).func_177984_a() : helper.bestPos.func_178782_a().func_177984_a();
                  }

                  return helper.bestPos;
               }

               pos = (BlockPos)var2.next();
               blockAt = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
            } while(blockAt != Blocks.field_150346_d && blockAt != Blocks.field_150349_c && blockAt != Blocks.field_150354_m);

            if (PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c() == Blocks.field_150350_a && Utils.isAdjacentToWater(pos)) {
               if (PizzaClient.config.placeThroughBlocks) {
                  position = VecUtil.calculateInterceptLook(pos, new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.99D, (double)pos.func_177952_p() + 0.5D), 4.5F);
               } else {
                  position = this.rayTrace(pos);
               }

               if (position != null) {
                  helper.compare(position);
               }
            }
         }
      case 2:
         var2 = BlockPos.func_177980_a(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D, PizzaClient.mc.field_71439_g.field_70163_u - 4.0D, PizzaClient.mc.field_71439_g.field_70161_v - 5.0D), new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t + 5.0D, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v + 5.0D)).iterator();

         while(var2.hasNext()) {
            pos = (BlockPos)var2.next();
            blockAt = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
            BlockPos up = pos.func_177984_a();
            if (blockAt == Blocks.field_150354_m && PizzaClient.mc.field_71441_e.func_180495_p(up).func_177230_c() == Blocks.field_150350_a && !Utils.isAdjacentToUncollidable(up)) {
               MovingObjectPosition position;
               if (PizzaClient.config.placeThroughBlocks) {
                  position = VecUtil.calculateInterceptLook(pos, new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.99D, (double)pos.func_177952_p() + 0.5D), 4.5F);
               } else {
                  position = this.rayTrace(pos);
               }

               if (position != null) {
                  helper.compare(position);
               }
            }
         }

         if (helper.isNotNull()) {
            this.renderBlock = PizzaClient.config.placeThroughBlocks ? Utils.getBlockFromHit(helper.bestPos).func_177984_a() : helper.bestPos.func_178782_a().func_177984_a();
         }
         break;
      case 3:
         var2 = BlockPos.func_177980_a(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D, PizzaClient.mc.field_71439_g.field_70163_u - 4.0D, PizzaClient.mc.field_71439_g.field_70161_v - 5.0D), new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t + 5.0D, PizzaClient.mc.field_71439_g.field_70163_u + 6.0D, PizzaClient.mc.field_71439_g.field_70161_v + 5.0D)).iterator();

         while(var2.hasNext()) {
            pos = (BlockPos)var2.next();
            IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
            if (state.func_177230_c() instanceof BlockLog && state.func_177229_b(BlockPlanks.field_176383_a) == EnumType.JUNGLE) {
               if (PizzaClient.config.placeThroughBlocks) {
                  position = VecUtil.calculateInterceptLook(pos, new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D), 4.5F);
               } else {
                  position = this.rayTraceCocoaBean(pos);
               }

               if (position != null && PizzaClient.mc.field_71441_e.func_180495_p(Utils.getBlockFromHit(position).func_177972_a(position.field_178784_b)).func_177230_c() == Blocks.field_150350_a) {
                  helper.compare(position);
               }
            }
         }

         if (helper.isNotNull()) {
            this.renderBlock = PizzaClient.config.placeThroughBlocks ? Utils.getBlockFromHit(helper.bestPos) : helper.bestPos.func_178782_a();
         }
         break;
      case 4:
         var2 = BlockPos.func_177980_a(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D, PizzaClient.mc.field_71439_g.field_70163_u - 4.0D, PizzaClient.mc.field_71439_g.field_70161_v - 5.0D), new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t + 5.0D, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v + 5.0D)).iterator();

         while(var2.hasNext()) {
            pos = (BlockPos)var2.next();
            blockAt = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
            if (blockAt == Blocks.field_150425_aM) {
               if (PizzaClient.config.placeThroughBlocks) {
                  vec = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.99D, (double)pos.func_177952_p() + 0.5D);
                  position = VecUtil.calculateInterceptLook(pos, vec, 4.5F);
               } else {
                  position = this.rayTrace(pos);
               }

               if (position != null && position.field_178784_b == EnumFacing.UP) {
                  helper.compare(position);
               }
            }
         }

         if (helper.isNotNull()) {
            this.renderBlock = PizzaClient.config.placeThroughBlocks ? Utils.getBlockFromHit(helper.bestPos).func_177984_a() : helper.bestPos.func_178782_a().func_177984_a();
         }
      }

      return helper.bestPos;
   }

   public void onToggle(boolean toggled) {
      this.renderBlock = null;
      this.addToggleMessage("Crop Place Aura");
   }

   public void onRender() {
      if (this.renderBlock != null) {
         RenderUtil.drawFilledEsp(this.renderBlock, Color.CYAN, 0.5F);
      }

   }

   public boolean applyFailsafes() {
      return false;
   }

   public boolean applyPositionFailsafe() {
      return false;
   }

   public boolean applyBedrockFailsafe() {
      return false;
   }

   public boolean applyPlayerFailsafes() {
      return false;
   }

   private MovingObjectPosition rayTrace(BlockPos pos) {
      Vec3 vec = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
      MovingObjectPosition position = VecUtil.rayTraceLook(vec, 4.5F);
      if (position != null && position.func_178782_a().equals(pos)) {
         return position;
      } else {
         for(int x = 1; x < 5; ++x) {
            for(int y = 1; y < 5; ++y) {
               for(int z = 1; z < 5; ++z) {
                  vec = new Vec3((double)pos.func_177958_n() + (double)x / 4.0D - 0.125D, (double)pos.func_177956_o() + (double)y / 4.0D - 0.125D, (double)pos.func_177952_p() + (double)z / 4.0D - 0.125D);
                  position = VecUtil.rayTraceLook(vec, 4.5F);
                  if (position != null && position.func_178782_a().equals(pos) && position.field_178784_b == EnumFacing.UP) {
                     return position;
                  }
               }
            }
         }

         return null;
      }
   }

   private MovingObjectPosition rayTraceCocoaBean(BlockPos pos) {
      Vec3 vec = new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
      MovingObjectPosition position = VecUtil.rayTraceLook(vec, 4.5F);
      if (position != null && position.func_178782_a().equals(pos)) {
         return position;
      } else {
         for(int x = 1; x < 5; ++x) {
            for(int y = 1; y < 5; ++y) {
               for(int z = 1; z < 5; ++z) {
                  vec = new Vec3((double)pos.func_177958_n() + (double)x / 4.0D - 0.125D, (double)pos.func_177956_o() + (double)y / 4.0D - 0.125D, (double)pos.func_177952_p() + (double)z / 4.0D - 0.125D);
                  position = VecUtil.rayTraceLook(vec, 4.5F);
                  if (position != null && position.func_178782_a().equals(pos) && position.field_178784_b != EnumFacing.UP && position.field_178784_b != EnumFacing.DOWN && PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177972_a(PlayerUtil.getHorizontalFacing(position.field_72307_f).func_176734_d())).func_177230_c() == Blocks.field_150350_a) {
                     return position;
                  }
               }
            }
         }

         return null;
      }
   }
}
