package qolskyblockmod.pizzaclient.features.misc;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class MonolithESP {
   private final List<BlockPos> spawns = new ArrayList(Arrays.asList(new BlockPos(-15, 236, -92), new BlockPos(49, 202, -162), new BlockPos(56, 214, -25), new BlockPos(0, 170, 0), new BlockPos(150, 196, 190), new BlockPos(-64, 206, -63), new BlockPos(-93, 221, -53), new BlockPos(-94, 201, -30), new BlockPos(-9, 162, 109), new BlockPos(1, 183, 23), new BlockPos(61, 204, 181), new BlockPos(77, 160, 162), new BlockPos(92, 187, 131), new BlockPos(128, 187, 58)));
   private BlockPos monolithPos;

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (PizzaClient.config.monolithEsp && PizzaClient.location == Locations.DWARVENMINES) {
         if (this.monolithPos == null) {
            Iterator var2 = this.spawns.iterator();

            while(var2.hasNext()) {
               BlockPos p = (BlockPos)var2.next();
               Iterator var4 = BlockPos.func_177980_a(new BlockPos(p.func_177958_n() - 4, p.func_177956_o() - 2, p.func_177952_p() - 4), new BlockPos(p.func_177958_n() + 4, p.func_177956_o() + 2, p.func_177952_p() + 4)).iterator();

               while(var4.hasNext()) {
                  BlockPos pos = (BlockPos)var4.next();
                  if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150380_bt) {
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Monolith!"));
                     this.monolithPos = pos;
                  }
               }
            }
         } else {
            if (PizzaClient.mc.field_71441_e.func_180495_p(this.monolithPos).func_177230_c() != Blocks.field_150380_bt && PizzaClient.mc.field_71441_e.func_175668_a(this.monolithPos, false)) {
               this.monolithPos = null;
               return;
            }

            BlockPos pos = this.monolithPos;
            RenderUtil.drawOutlinedEsp(new AxisAlignedBB((double)(pos.func_177958_n() - 1), (double)(pos.func_177956_o() - 1), (double)(pos.func_177952_p() - 1), (double)(pos.func_177958_n() + 2), (double)(pos.func_177956_o() + 2), (double)(pos.func_177952_p() + 2)), Color.MAGENTA, 7.5F);
            RenderUtil.drawFilledEsp(pos, Color.MAGENTA);
         }
      }

   }
}
