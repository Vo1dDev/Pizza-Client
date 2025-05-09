package qolskyblockmod.pizzaclient.features.macros.farming;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.FarmingMacro;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.RunnableRotater;

public class CocoaBeanMacro extends FarmingMacro {
   private boolean forward;
   private boolean left;
   private boolean right;
   private int lastX;
   private int lastZ;

   public void onTick() {
      if (!Rotater.rotating) {
         super.onTick();
         if (PizzaClient.mc.field_71439_g.field_70122_E) {
            if (PlayerUtil.isStandingStill() && !this.right && !this.left && !this.forward) {
               this.changeKeys();
            } else {
               BlockPos pos = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)MathUtil.round(PizzaClient.mc.field_71439_g.field_70163_u), PizzaClient.mc.field_71439_g.field_70161_v);
               double moving = Utils.formatDouble(MathUtil.abs(PlayerUtil.getMovingDirection()) % 1.0D);
               if (moving == 0.0D) {
                  EnumFacing player = PizzaClient.mc.field_71439_g.func_174811_aO();
                  EnumFacing f;
                  if (this.left) {
                     f = Utils.getLeftEnumfacing(player);
                     if (!this.isValid(f, pos) || this.shouldRotateBack()) {
                        this.changeKeys();
                     }
                  }

                  if (this.right) {
                     f = Utils.getRightEnumfacing(player);
                     if (!this.isValid(f, pos) || this.shouldRotateBack()) {
                        this.changeKeys();
                     }
                  }

                  if (this.forward && !this.isValid(player, pos)) {
                     this.changeKeys();
                  }
               }
            }

            if (this.left && this.right) {
               this.left = false;
            }
         }

         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), true);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), this.forward);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), this.left);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), this.right);
      }

   }

   public void onToggle(boolean toggled) {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Cocoa Bean Macro is now " + Utils.getColouredBoolean(toggled)));
      this.left = this.right = this.forward = false;
      super.onToggle();
   }

   public boolean applyPositionFailsafe() {
      return false;
   }

   public boolean applyBedrockFailsafe() {
      return true;
   }

   private void changeKeys() {
      boolean movedRight = this.right;
      boolean movedLeft = this.left;
      this.disableMovement();
      this.left = this.right = this.forward = false;
      this.lastX = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t);
      this.lastZ = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v);
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Changing keys..."));
      EnumFacing player = PizzaClient.mc.field_71439_g.func_174811_aO();
      BlockPos pos = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)MathUtil.round(PizzaClient.mc.field_71439_g.field_70163_u), PizzaClient.mc.field_71439_g.field_70161_v);
      if (this.isValid(player, pos)) {
         this.forward = true;
      } else {
         EnumFacing facing;
         if (!movedRight) {
            facing = Utils.getLeftEnumfacing(player);
            if (this.isValid(facing, pos)) {
               this.left = true;
               return;
            }
         }

         if (!movedLeft) {
            facing = Utils.getRightEnumfacing(player);
            if (this.isValid(facing, pos)) {
               this.right = true;
               return;
            }
         }

         if (this.isValid(player.func_176734_d(), pos)) {
            (new RunnableRotater(180.0F, 0.0F, () -> {
               this.forward = true;
               this.onToggle();
            })).setRotationAmount(40).rotate();
         }

      }
   }

   public boolean isValid(EnumFacing f, BlockPos pos) {
      BlockPos offset = pos.func_177972_a(f);
      return walkthrough.contains(PizzaClient.mc.field_71441_e.func_180495_p(offset).func_177230_c()) && walkthrough.contains(PizzaClient.mc.field_71441_e.func_180495_p(offset.func_177984_a()).func_177230_c()) && PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)(MathUtil.roundUp(PizzaClient.mc.field_71439_g.field_70163_u) - 1), PizzaClient.mc.field_71439_g.field_70161_v)).func_177230_c() != Blocks.field_150350_a;
   }

   private boolean shouldRotateBack() {
      if (this.lastX == MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t) && this.lastZ == MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v)) {
         return false;
      } else {
         BlockPos offset = (new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)).func_177972_a(PizzaClient.mc.field_71439_g.func_174811_aO().func_176734_d());
         return PizzaClient.mc.field_71441_e.func_180495_p(offset).func_177230_c() == Blocks.field_150350_a;
      }
   }
}
