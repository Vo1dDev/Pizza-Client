package qolskyblockmod.pizzaclient.features.dungeons;

import java.util.Iterator;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;

public class SuperboomAura {
   private long lastInteractTime;

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (PizzaClient.config.superboomAura && PizzaClient.location == Locations.DUNGEON && System.currentTimeMillis() - this.lastInteractTime >= 1500L) {
         for(int x = (int)(PizzaClient.mc.field_71439_g.field_70165_t - 6.0D); (double)x < PizzaClient.mc.field_71439_g.field_70165_t + 6.0D; ++x) {
            for(int y = (int)(PizzaClient.mc.field_71439_g.field_70163_u - 6.0D); (double)y < PizzaClient.mc.field_71439_g.field_70163_u + 6.0D; ++y) {
               for(int z = (int)(PizzaClient.mc.field_71439_g.field_70161_v - 6.0D); (double)z < PizzaClient.mc.field_71439_g.field_70161_v + 6.0D; ++z) {
                  BlockPos pos = new BlockPos(x, y, z);
                  IBlockState block = PizzaClient.mc.field_71441_e.func_180495_p(pos);
                  if (block.func_177230_c() != Blocks.field_150417_aV) {
                     if (block.func_177230_c() == Blocks.field_180389_cP) {
                     }
                  } else if (Blocks.field_150417_aV.func_176201_c(block) == BlockStoneBrick.field_176251_N && VecUtil.canReachBlock(pos) && VecUtil.isHittable(pos)) {
                     int count = 0;
                     Iterator var8 = BlockPos.func_177980_a(new BlockPos(x - 1, y - 1, z - 1), new BlockPos(x + 1, y + 1, z + 1)).iterator();

                     while(var8.hasNext()) {
                        BlockPos adjacent = (BlockPos)var8.next();
                        IBlockState state = PizzaClient.mc.field_71441_e.func_180495_p(adjacent);
                        if (state.func_177230_c() == Blocks.field_150417_aV && Blocks.field_150417_aV.func_176201_c(state) == BlockStoneBrick.field_176251_N) {
                           ++count;
                        }
                     }

                     if (count >= 5) {
                        MovingObjectPosition hit = VecUtil.getHittableMovingObjectPosition(pos);
                        if (hit != null) {
                           FakeRotation.rotationVec = hit.field_72307_f;
                           FakeRotation.hitPos = hit.func_178782_a();
                           FakeRotation.itemSlot = getBoom();
                           FakeRotation.rightClick = 1;
                           this.lastInteractTime = System.currentTimeMillis();
                           return;
                        }
                     }
                  }
               }
            }
         }
      }

   }

   private static int getBoom() {
      int superboom = 0;
      Item tnt = Item.func_150898_a(Blocks.field_150335_W);

      for(int i = 0; i < 8; ++i) {
         ItemStack stack = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (stack != null && stack.func_77973_b() == tnt) {
            String displayName = stack.func_82833_r();
            if (displayName.contains("Infinityboom")) {
               return i;
            }

            if (displayName.contains("Superboom")) {
               superboom = i;
            }
         }
      }

      return superboom;
   }
}
