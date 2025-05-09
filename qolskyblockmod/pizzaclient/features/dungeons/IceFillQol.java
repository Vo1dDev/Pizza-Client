package qolskyblockmod.pizzaclient.features.dungeons;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.BlockChangeEvent;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PacketUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class IceFillQol {
   private BlockPos currentBlock;
   private final List<BlockPos> iceBlocks = new ArrayList();

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (PizzaClient.config.qolIceFill && PizzaClient.location == Locations.DUNGEON) {
         BlockPos player = new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u - 1.0D, PizzaClient.mc.field_71439_g.field_70161_v);
         if (PizzaClient.mc.field_71441_e.func_180495_p(player).func_177230_c() == Blocks.field_150432_aD) {
            if (this.currentBlock == null) {
               this.currentBlock = player;
               PacketUtil.stopPackets = true;
            }

            if (!this.iceBlocks.contains(player)) {
               this.iceBlocks.add(player);
            }
         } else {
            PacketUtil.continueAndSendPackets();
            this.currentBlock = null;
            this.iceBlocks.clear();
         }

      }
   }

   @SubscribeEvent
   public void onBlockChange(BlockChangeEvent event) {
      if (PizzaClient.config.qolIceFill && event.pos.equals(this.currentBlock) && event.currentState.func_177230_c() == Blocks.field_150403_cj && event.oldState.func_177230_c() == Blocks.field_150432_aD) {
         this.iceBlocks.remove(0);
         if (this.iceBlocks.size() == 0) {
            PacketUtil.continueAndSendPackets();
            this.currentBlock = null;
            this.iceBlocks.clear();
            return;
         }

         this.currentBlock = (BlockPos)this.iceBlocks.get(0);
         if (this.currentBlock.equals(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u - 1.0D, PizzaClient.mc.field_71439_g.field_70161_v))) {
            PacketUtil.continueAndSendPackets();
            this.currentBlock = null;
            this.iceBlocks.clear();
            return;
         }

         List<Packet<?>> stopped = new ArrayList();
         boolean wasInsideBox = false;

         Packet packet;
         for(Iterator var4 = PacketUtil.pausedPackets.iterator(); var4.hasNext(); stopped.add(packet)) {
            packet = (Packet)var4.next();
            if (wasInsideBox) {
               stopped.add(packet);
               PacketUtil.sendPackets(stopped);
               return;
            }

            if (packet instanceof C06PacketPlayerPosLook) {
               C06PacketPlayerPosLook pos = (C06PacketPlayerPosLook)packet;
               if (this.currentBlock.equals(new BlockPos(pos.func_149464_c(), (double)this.currentBlock.func_177956_o(), pos.func_149472_e())) && MathUtil.inBetween(pos.func_149464_c(), (double)this.currentBlock.func_177958_n() + 0.15D, (double)this.currentBlock.func_177958_n() + 0.85D) && MathUtil.inBetween(pos.func_149472_e(), (double)this.currentBlock.func_177952_p() + 0.15D, (double)this.currentBlock.func_177952_p() + 0.85D)) {
                  wasInsideBox = true;
               }
            } else if (packet instanceof C04PacketPlayerPosition) {
               C04PacketPlayerPosition pos = (C04PacketPlayerPosition)packet;
               if (this.currentBlock.equals(new BlockPos(pos.func_149464_c(), (double)this.currentBlock.func_177956_o(), pos.func_149472_e())) && MathUtil.inBetween(pos.func_149464_c(), (double)this.currentBlock.func_177958_n() + 0.15D, (double)this.currentBlock.func_177958_n() + 0.85D) && MathUtil.inBetween(pos.func_149472_e(), (double)this.currentBlock.func_177952_p() + 0.15D, (double)this.currentBlock.func_177952_p() + 0.85D)) {
                  wasInsideBox = true;
               }
            }
         }

         PacketUtil.continueAndSendPackets();
      }

   }

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      this.currentBlock = null;
      this.iceBlocks.clear();
   }

   @SubscribeEvent
   public void onRender(RenderWorldLastEvent event) {
      if (this.currentBlock != null) {
         RenderUtil.drawOutlinedEsp(this.currentBlock, Color.CYAN, 3.0F);
         Iterator var2 = this.iceBlocks.iterator();

         while(var2.hasNext()) {
            BlockPos pos = (BlockPos)var2.next();
            RenderUtil.drawOutlinedEsp(pos, Color.GREEN);
         }
      }

   }
}
