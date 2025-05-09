package qolskyblockmod.pizzaclient.features.macros.misc;

import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.Macro;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.SnapRotater;

public class ForagingMacro extends Macro {
   private long treeMode;

   public void onTick() {
      if (PizzaClient.location == Locations.PRIVATEISLAND) {
         switch((int)this.treeMode) {
         case 0:
            if (!PlayerUtil.holdingItem(Item.func_150898_a(Blocks.field_150345_g))) {
               PlayerUtil.swapToSlot(Item.func_150898_a(Blocks.field_150345_g));
               return;
            }

            Vec3 furthest = null;
            Vec3 player = PlayerUtil.getPositionEyes();
            Iterator var7 = BlockPos.func_177980_a(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t + 5.0D, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v + 5.0D), new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t - 5.0D, PizzaClient.mc.field_71439_g.field_70163_u - 1.0D, PizzaClient.mc.field_71439_g.field_70161_v - 5.0D)).iterator();

            while(true) {
               Vec3 distance;
               do {
                  BlockPos pos;
                  Block block;
                  do {
                     do {
                        do {
                           if (!var7.hasNext()) {
                              if (furthest != null) {
                                 SnapRotater.snapTo(furthest);
                                 this.treeMode = 1L;
                              } else {
                                 this.treeMode = 2L;
                              }

                              return;
                           }

                           pos = (BlockPos)var7.next();
                        } while(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150346_d && PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() != Blocks.field_150349_c);

                        block = PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n(), pos.func_177956_o() + 1, pos.func_177952_p())).func_177230_c();
                     } while(block instanceof BlockLog);
                  } while(block == Blocks.field_150345_g);

                  distance = new Vec3((double)pos.func_177958_n() + 0.5D, (double)(pos.func_177956_o() + 1), (double)pos.func_177952_p() + 0.5D);
               } while(furthest != null && !(player.func_72436_e(distance) > player.func_72436_e(furthest)));

               furthest = distance;
            }
         case 1:
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
            this.treeMode = 0L;
            break;
         case 2:
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.checkHotBarForItem(Items.field_151100_aR);
            this.treeMode = 3L;
            break;
         case 3:
            this.treeMode = 4L;
            break;
         case 4:
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
            this.treeMode = 5L;
            break;
         case 5:
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.checkHotbarForDisplayName("Treecapitator");
            this.treeMode = 6L;
            break;
         case 6:
            if (PizzaClient.mc.field_71476_x.func_178782_a() != null && PizzaClient.mc.field_71441_e.func_180495_p(PizzaClient.mc.field_71476_x.func_178782_a()).func_177230_c() instanceof BlockLog) {
               this.treeMode = 7L;
            }
            break;
         case 7:
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), true);
            BlockPos pos = PizzaClient.mc.field_71476_x.func_178782_a();
            if (pos != null && !(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() instanceof BlockLog)) {
               this.treeMode = System.currentTimeMillis();
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), false);
            }
            break;
         default:
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), false);
            if (System.currentTimeMillis() - this.treeMode >= (long)PizzaClient.config.foragingMacroDelay) {
               this.treeMode = 0L;
               PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.checkHotBarForItem(Item.func_150898_a(Blocks.field_150345_g));
            }
         }

      }
   }

   public void onToggle(boolean toggled) {
      if (toggled) {
         PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.checkHotBarForItem(Item.func_150898_a(Blocks.field_150345_g));
      }

      this.addToggleMessage("Foraging Macro");
      this.treeMode = 0L;
   }

   public boolean applyFailsafes() {
      return true;
   }

   public boolean applyPositionFailsafe() {
      return true;
   }

   public boolean applyBedrockFailsafe() {
      return true;
   }

   public boolean applyPlayerFailsafes() {
      return false;
   }

   public Locations getLocation() {
      return Locations.PRIVATEISLAND;
   }

   public void warpBack() {
      Locations.PRIVATEISLAND.warpTo();
      this.treeMode = 0L;
   }
}
