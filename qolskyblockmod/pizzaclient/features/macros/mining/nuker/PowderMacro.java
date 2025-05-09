package qolskyblockmod.pizzaclient.features.macros.mining.nuker;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.AiMining;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.NukerMacro;
import qolskyblockmod.pizzaclient.features.player.AutoPowderChest;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.RotationUtil;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class PowderMacro extends NukerMacro {
   private static int ticks;
   public static final Set<BlockPos> clicked = new HashSet();

   public void onTick() {
      if (PizzaClient.location == Locations.CHOLLOWS) {
         if (AutoPowderChest.particleVec == null) {
            NukerBase.nuker = this;
            if (vec != null && VecUtil.calculateInterceptLook(vec, 4.5F) == null) {
               vec = null;
            }

            if (PizzaClient.mc.field_71476_x.func_178782_a() != null) {
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), AutoPowderChest.particleVec == null);
               if (PizzaClient.mc.field_71462_r == null) {
                  Block mouse = PizzaClient.mc.field_71441_e.func_180495_p(PizzaClient.mc.field_71476_x.func_178782_a()).func_177230_c();
                  if (mouse == Blocks.field_150486_ae || mouse == Blocks.field_150357_h && PizzaClient.mc.field_71476_x.func_178782_a().func_177956_o() != 30 || AiMining.isBlockUnmineable(mouse)) {
                     (new Rotater(RotationUtil.getYawDifference(RotationUtil.getYawClosestTo90Degrees()) + 90.0F, RotationUtil.getPitchDifference(5.0F))).setRotationAmount(16).rotate();
                  }
               }
            }
         } else {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         }
      }

   }

   public void onToggle(boolean toggled) {
      clicked.clear();
      if (!PizzaClient.config.nuker) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "You need to enable nuker in order for the hardstone macro to work!"));
      } else {
         if (toggled) {
            PizzaClient.config.nukerRotationPackets = true;
            (new Rotater(RotationUtil.getYawDifference(RotationUtil.getYawClosestTo90Degrees()), RotationUtil.getPitchDifference(5.0F))).setRotationAmount(16).rotate();
         }

         this.addToggleMessage("Powder Macro");
      }
   }

   public void onDisable() {
      vec = null;
   }

   public void onNukePre() {
      if (vec != null) {
         BlockPos pos = new BlockPos(vec);
         if (AiMining.ores.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c())) {
            if (ticks >= PizzaClient.config.powderMacroTicks) {
               clicked.add(pos);
               vec = null;
               ticks = 0;
            } else {
               ++ticks;
            }
         }
      }

   }

   public boolean isBlockValid(BlockPos pos) {
      if ((double)pos.func_177956_o() <= PizzaClient.mc.field_71439_g.field_70163_u - 1.0D) {
         return false;
      } else {
         Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
         return !avoid.contains(block) && !clicked.contains(pos) && !AiMining.isBlockUnmineable(block);
      }
   }

   public void onNoVecAvailable() {
      clicked.clear();
   }

   public Locations getLocation() {
      return Locations.CHOLLOWS;
   }

   public void warpBack() {
      Locations.CHOLLOWS.warpTo();
   }
}
