package qolskyblockmod.pizzaclient.features.macros.mining.dwarven;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.AiMining;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.CustomBlock;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.Refuel;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.Macro;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class MithrilMacro extends Macro {
   public static final Set<Block> mithrilBlocks;
   public static final Set<Block> mineables;
   public static final AiMining miningAi;

   public void onTick() {
      if (PizzaClient.location == Locations.DWARVENMINES) {
         if (!PlayerUtil.holdingMiningTool()) {
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getMiningTool();
         }

         if (miningAi.hittables.size() == 0) {
            miningAi.onMove();
         }

         if (PizzaClient.config.mithrilAutoRefuel == 2 && Refuel.drillNPC == null) {
            Refuel.drillNPC = Refuel.findDrillNPC();
         }

         if (!miningAi.onTick()) {
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), true);
            if (!Rotater.rotating) {
               if (PizzaClient.mc.field_71476_x.field_72308_g != null) {
                  miningAi.currentBlock = null;
               } else if (PizzaClient.mc.field_71476_x.func_178782_a() != null && PizzaClient.mc.field_71476_x.func_178782_a().equals(miningAi.currentBlock)) {
                  if (PizzaClient.mc.field_71441_e.func_180495_p(miningAi.currentBlock).func_177230_c() == Blocks.field_150357_h || PizzaClient.mc.field_71441_e.func_180495_p(PizzaClient.mc.field_71476_x.func_178782_a()).func_177230_c() == Blocks.field_150357_h) {
                     miningAi.currentBlock = null;
                  }
               } else {
                  miningAi.currentBlock = null;
               }

               if (miningAi.currentBlock == null) {
                  if (this.foundBlock(PizzaClient.config.mithrilPriority)) {
                     return;
                  }

                  if (this.foundBlock(PizzaClient.config.mithrilPriority2)) {
                     return;
                  }

                  if (this.foundBlock(PizzaClient.config.mithrilPriority3)) {
                     return;
                  }

                  if (!miningAi.findBlock(mineables, PizzaClient.config.mithrilRotateAmount)) {
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > Found no mithril blocks."));
                  }
               }
            }

         }
      }
   }

   public void onToggle(boolean toggled) {
      if (toggled) {
         KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
      }

      miningAi.onToggle();
      this.addToggleMessage("Mithril Macro");
   }

   public void onDisable() {
      miningAi.disable();
   }

   public void onRender() {
      miningAi.render();
   }

   private boolean foundBlock(int priority) {
      switch(priority) {
      case 0:
         return miningAi.findBlock((Block)Blocks.field_150325_L, (PropertyEnum)BlockColored.field_176581_a, (Enum)EnumDyeColor.LIGHT_BLUE, PizzaClient.config.mithrilRotateAmount, PizzaClient.config.mithrilMaxRotation);
      case 1:
         return miningAi.findBlock(Blocks.field_180397_cI, PizzaClient.config.mithrilRotateAmount, PizzaClient.config.mithrilMaxRotation);
      case 2:
         return miningAi.findBlock(Blocks.field_150406_ce, Blocks.field_150325_L, new CustomBlock(BlockColored.field_176581_a, EnumDyeColor.GRAY), PizzaClient.config.mithrilRotateAmount, PizzaClient.config.mithrilMaxRotation);
      case 3:
         return miningAi.findBlock((Block)Blocks.field_150348_b, (PropertyEnum)BlockStone.field_176247_a, (Enum)EnumType.DIORITE_SMOOTH, PizzaClient.config.mithrilRotateAmount, PizzaClient.config.mithrilMaxRotation);
      case 4:
         return miningAi.findBlock(Blocks.field_150340_R, PizzaClient.config.mithrilRotateAmount, PizzaClient.config.mithrilMaxRotation);
      default:
         return false;
      }
   }

   public void onChat(String unformatted) {
      if (unformatted.equals("Mining Speed Boost is now available!")) {
         PlayerUtil.useAbility();
      } else if (unformatted.startsWith("Your ") && unformatted.endsWith("Refuel it by talking to a Drill Mechanic!")) {
         if (PizzaClient.config.mithrilAutoRefuel == 2) {
            if (Refuel.drillNPC == null) {
               Refuel.drillNPC = Refuel.findDrillNPC();
            }

            if (Refuel.drillNPC != null) {
               this.enqueueThread(Refuel::funnyRefuel);
            }
         } else if (PizzaClient.config.mithrilAutoRefuel == 1) {
            this.enqueueThread(() -> {
               Refuel.legitRefuel();
               MithrilMarkers.run();
            });
         }
      }

   }

   public void onMove() {
      miningAi.hittables.clear();
      int x = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70165_t);
      int z = MathUtil.floor(PizzaClient.mc.field_71439_g.field_70161_v);
      Iterator var3 = PlayerUtil.getPlayerBlocks().iterator();

      while(true) {
         BlockPos pos;
         label40:
         do {
            while(var3.hasNext()) {
               pos = (BlockPos)var3.next();
               IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
               Block block = state.func_177230_c();
               if (miningAi.miningBlocks.contains(block)) {
                  continue label40;
               }

               if (block == Blocks.field_150348_b && state.func_177229_b(BlockStone.field_176247_a) == EnumType.DIORITE_SMOOTH && pos.func_177958_n() != x && pos.func_177952_p() != z && !Utils.isBlockBlocked(pos) && VecUtil.isHittable(pos)) {
                  miningAi.hittables.add(pos);
               }
            }

            return;
         } while(pos.func_177958_n() == x && pos.func_177952_p() == z);

         if (!Utils.isBlockBlocked(pos) && VecUtil.isHittable(pos)) {
            miningAi.hittables.add(pos);
         }
      }
   }

   public void warpBack() {
      MithrilMarkers.run();
      PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getMiningTool();
   }

   public boolean applyFailsafes() {
      return true;
   }

   public Locations getLocation() {
      return Locations.DWARVENMINES;
   }

   public boolean applyPositionFailsafe() {
      return true;
   }

   public boolean applyBedrockFailsafe() {
      return false;
   }

   public boolean applyPlayerFailsafes() {
      return true;
   }

   static {
      mithrilBlocks = new HashSet(Arrays.asList(Blocks.field_150406_ce, Blocks.field_150325_L, Blocks.field_180397_cI, Blocks.field_150357_h, Blocks.field_150340_R));
      mineables = new HashSet(Arrays.asList(Blocks.field_150406_ce, Blocks.field_150325_L, Blocks.field_180397_cI, Blocks.field_150340_R));
      miningAi = new AiMining(mithrilBlocks, new HashMap(Collections.singletonMap(Blocks.field_150348_b, CustomBlock.TITANIUM)));
   }
}
