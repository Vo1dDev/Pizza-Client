package qolskyblockmod.pizzaclient.features.macros.misc;

import java.awt.Color;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.AntiAFK;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.Macro;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;

public class FishingMacro extends Macro {
   private int fishingMode;
   private BlockPos position;
   private long lastNoHookTime;

   public void onTick() {
      BlockPos pos = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      if (this.position == null) {
         this.position = pos;
      }

      if (!AntiAFK.isMoving()) {
         if (!this.position.equals(pos)) {
            EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
            if (this.position.equals(pos.func_177972_a(Utils.getLeftEnumfacing(facing)))) {
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
            } else if (this.position.equals(pos.func_177972_a(Utils.getRightEnumfacing(facing)))) {
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
            }
         }
      } else {
         Movement.disableMovement();
      }

      if (!this.facingWater()) {
         this.fishingMode = -1;
         MovingObjectPosition rayTrace = VecUtil.rayTrace(100.0F);
         if (rayTrace != null && rayTrace.func_178782_a() != null) {
            BlockPos hit = rayTrace.func_178782_a();
            EnumFacing player = PizzaClient.mc.field_71439_g.func_174811_aO();
            if (PizzaClient.mc.field_71441_e.func_180495_p(hit.func_177972_a(Utils.getLeftEnumfacing(player))).func_177230_c() instanceof BlockLiquid) {
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
            } else if (PizzaClient.mc.field_71441_e.func_180495_p(hit.func_177972_a(Utils.getRightEnumfacing(player))).func_177230_c() instanceof BlockLiquid) {
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
            }
         }

      } else {
         switch(this.fishingMode) {
         case -1:
            if (this.facingWater()) {
               Movement.disableMovement();
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
               this.fishingMode = 0;
               AntiAFK.reset();
            }
            break;
         case 0:
            if (AntiAFK.mode != 0) {
               AntiAFK.check();
               return;
            }

            if (PizzaClient.mc.field_71439_g.field_71104_cf != null) {
               if (this.isInWater()) {
                  this.fishingMode = 1;
                  this.lastNoHookTime = 0L;
               } else if (this.isStandingStill()) {
                  KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
               }
            } else {
               ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
               if (held != null && held.func_77973_b() == Items.field_151112_aM) {
                  KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
                  this.fishingMode = 1;
               } else {
                  PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getFishingRod();
               }
            }
            break;
         case 1:
            AntiAFK.check();
            if (PizzaClient.mc.field_71439_g.field_71104_cf == null) {
               if (this.lastNoHookTime == 0L) {
                  this.lastNoHookTime = System.currentTimeMillis();
                  return;
               }

               if (System.currentTimeMillis() - this.lastNoHookTime >= 1000L) {
                  this.fishingMode = 0;
                  this.lastNoHookTime = 0L;
               }

               return;
            }

            if (!this.isInWater()) {
               this.fishingMode = 0;
               this.lastNoHookTime = 0L;
               return;
            }

            if (this.isStandingStill() && MathUtil.getDecimals(PizzaClient.mc.field_71439_g.field_71104_cf.field_70163_u) >= 0.77D) {
               this.fishingMode = 2;
               this.lastNoHookTime = 0L;
            }
            break;
         case 2:
            AntiAFK.check();
            if (PizzaClient.mc.field_71439_g.field_71104_cf == null) {
               if (this.lastNoHookTime == 0L) {
                  this.lastNoHookTime = System.currentTimeMillis();
                  return;
               }

               if (System.currentTimeMillis() - this.lastNoHookTime >= 1000L) {
                  this.fishingMode = 0;
                  this.lastNoHookTime = 0L;
               }

               return;
            }

            if (!this.isInWater()) {
               this.fishingMode = 0;
               this.lastNoHookTime = 0L;
               return;
            }

            if (MathUtil.getDecimals(PizzaClient.mc.field_71439_g.field_71104_cf.field_70163_u) < 0.77D) {
               KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
               this.fishingMode = 3;
               this.lastNoHookTime = System.currentTimeMillis();
            }
            break;
         case 3:
            AntiAFK.check();
            if (System.currentTimeMillis() - this.lastNoHookTime >= 400L) {
               this.fishingMode = 0;
               this.lastNoHookTime = 0L;
            }
         }

      }
   }

   public void onToggle(boolean toggled) {
      this.addToggleMessage("Fishing Macro");
      AntiAFK.reset();
      this.position = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      this.fishingMode = 0;
   }

   public boolean applyFailsafes() {
      return true;
   }

   private boolean isInWater() {
      return PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(PizzaClient.mc.field_71439_g.field_71104_cf.field_70165_t, PizzaClient.mc.field_71439_g.field_71104_cf.field_70163_u, PizzaClient.mc.field_71439_g.field_71104_cf.field_70161_v)).func_177230_c() instanceof BlockLiquid;
   }

   private boolean facingWater() {
      MovingObjectPosition rayTrace = VecUtil.rayTrace(100.0F);
      return rayTrace != null && PizzaClient.mc.field_71441_e.func_180495_p(rayTrace.func_178782_a()).func_177230_c() instanceof BlockLiquid;
   }

   private boolean isStandingStill() {
      return MathUtil.abs(PizzaClient.mc.field_71439_g.field_71104_cf.field_70159_w) < 0.01D && MathUtil.abs(PizzaClient.mc.field_71439_g.field_71104_cf.field_70179_y) < 0.01D;
   }

   public boolean applyPositionFailsafe() {
      return false;
   }

   public boolean applyBedrockFailsafe() {
      return true;
   }

   public boolean applyPlayerFailsafes() {
      return PizzaClient.config.stopWhenNearPlayer;
   }

   public void onRender() {
      MovingObjectPosition pos = VecUtil.rayTraceStopLiquid(50.0F);
      if (pos != null) {
         RenderUtil.drawFilledEsp(pos.func_178782_a(), Color.CYAN, 0.4F, -0.251F);
      }

   }
}
