package qolskyblockmod.pizzaclient.features.macros.builder.macros;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public abstract class FarmingMacro extends Macro {
   private float yaw;
   private float pitch;
   public static final Set<Block> walkthrough;

   public Locations getLocation() {
      return Locations.PRIVATEISLAND;
   }

   public void warpBack() {
      Locations.PRIVATEISLAND.warpTo();
   }

   public boolean applyPositionFailsafe() {
      return false;
   }

   public boolean applyBedrockFailsafe() {
      return true;
   }

   public boolean applyFailsafes() {
      return true;
   }

   public boolean applyPlayerFailsafes() {
      return false;
   }

   public boolean applyRotationFailsafe() {
      return true;
   }

   public void onTick() {
      if (PizzaClient.config.lockYawAndPitch) {
         float diffYaw = MathUtil.abs(PizzaClient.mc.field_71439_g.field_70177_z - this.yaw);
         if (diffYaw > 5.0F || MathUtil.abs(PizzaClient.mc.field_71439_g.field_70125_A - this.pitch) > 5.0F) {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Fixing rotation..."));
            this.enqueueFailsafeThread(() -> {
               Utils.sleep(300);
               this.disableMovement();
               float goalYaw = MathHelper.func_76142_g(this.yaw - PizzaClient.mc.field_71439_g.field_70177_z);
               float goalPitch = MathHelper.func_76142_g(this.pitch - PizzaClient.mc.field_71439_g.field_70125_A);
               (new Rotater(goalYaw, goalPitch)).antiSus(20.0F).rotate();

               while(Rotater.rotating) {
                  Utils.sleep(1);
               }

               Utils.sleep(100);
               this.onToggle();
            });
         }
      }

      ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
      if (held == null || !(held.func_77973_b() instanceof ItemHoe)) {
         for(int i = 0; i < 8; ++i) {
            ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (stack != null && stack.func_77973_b() instanceof ItemHoe) {
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
               break;
            }
         }
      }

   }

   public void onToggle() {
      this.yaw = PizzaClient.mc.field_71439_g.field_70177_z;
      this.pitch = PizzaClient.mc.field_71439_g.field_70125_A;
   }

   protected void disableMovement() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), false);
   }

   static {
      walkthrough = new HashSet(Arrays.asList(Blocks.field_150350_a, Blocks.field_150355_j, Blocks.field_150358_i, Blocks.field_150415_aT, Blocks.field_180400_cw, Blocks.field_180413_ao, Blocks.field_150454_av));
   }
}
