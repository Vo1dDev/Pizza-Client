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

public class SShapedMacro extends FarmingMacro {
   private boolean forward;
   private boolean left;
   private boolean right;

   public void onTick() {
      super.onTick();
      if (PlayerUtil.isStandingStill() || !this.right && !this.left && !this.forward) {
         this.changeKeys();
      } else {
         BlockPos pos = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)MathUtil.round(PizzaClient.mc.field_71439_g.field_70163_u), PizzaClient.mc.field_71439_g.field_70161_v);
         double moving = Utils.formatDouble(MathUtil.abs(PlayerUtil.getMovingDirection()) % 1.0D);
         if (moving == 0.0D) {
            EnumFacing player = PizzaClient.mc.field_71439_g.func_174811_aO();
            EnumFacing f;
            if (this.left) {
               f = Utils.getLeftEnumfacing(player);
               if (!this.isValid(f, pos)) {
                  this.changeKeys();
               }
            }

            if (this.right) {
               f = Utils.getRightEnumfacing(player);
               if (!this.isValid(f, pos)) {
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

      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), true);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), this.forward);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), this.left);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), this.right);
   }

   public void onToggle(boolean toggled) {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "S Shaped Farm Macro is now " + Utils.getColouredBoolean(toggled)));
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
      this.disableMovement();
      this.left = this.right = this.forward = false;
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Changing keys..."));
      EnumFacing player = PizzaClient.mc.field_71439_g.func_174811_aO();
      BlockPos pos = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)MathUtil.round(PizzaClient.mc.field_71439_g.field_70163_u), PizzaClient.mc.field_71439_g.field_70161_v);
      if (this.isValid(player, pos)) {
         this.forward = true;
      } else {
         EnumFacing facing = Utils.getLeftEnumfacing(player);
         if (this.isValid(facing, pos)) {
            this.left = true;
         } else {
            facing = Utils.getRightEnumfacing(player);
            this.right = this.isValid(facing, pos);
         }
      }
   }

   public boolean isValid(EnumFacing f, BlockPos pos) {
      BlockPos offset = pos.func_177972_a(f);
      return walkthrough.contains(PizzaClient.mc.field_71441_e.func_180495_p(offset).func_177230_c()) && walkthrough.contains(PizzaClient.mc.field_71441_e.func_180495_p(offset.func_177984_a()).func_177230_c()) && PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, (double)(MathUtil.roundUp(PizzaClient.mc.field_71439_g.field_70163_u) - 1), PizzaClient.mc.field_71439_g.field_70161_v)).func_177230_c() != Blocks.field_150350_a;
   }
}
