package qolskyblockmod.pizzaclient.features.player;

import java.awt.Color;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.features.dungeons.ChestESP;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.PowderMacro;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;

public class AutoPowderChest {
   public static Vec3 particleVec;
   private static BlockPos chestPos;

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (particleVec != null) {
         RenderUtil.drawFilledEsp(new AxisAlignedBB(particleVec.field_72450_a - 0.1D, particleVec.field_72448_b - 0.1D, particleVec.field_72449_c - 0.1D, particleVec.field_72450_a + 0.1D, particleVec.field_72448_b + 0.1D, particleVec.field_72449_c + 0.1D), new Color(90, 0, 255));
         if (chestPos != null) {
            RenderUtil.drawFilledEsp(chestPos, Color.CYAN, 0.5F);
         }
      }

   }

   public static void onParticle(S2APacketParticles packet) {
      if (PizzaClient.config.powderChestMacro && PizzaClient.location == Locations.CHOLLOWS && PizzaClient.mc.field_71462_r == null) {
         if (chestPos != null) {
            if (Utils.getXandZDistanceSquared(new Vec3((double)chestPos.func_177958_n() + 0.5D, (double)chestPos.func_177956_o() + 0.5D, (double)chestPos.func_177952_p() + 0.5D)) >= 49.0D) {
               chestPos = null;
               particleVec = null;
            } else if (!chestPos.equals(getBlockPos(packet))) {
               return;
            }
         }

         if (packet.func_179749_a() == EnumParticleTypes.CRIT && isPosValid(packet)) {
            Vec3 vec = new Vec3(packet.func_149220_d(), packet.func_149226_e(), packet.func_149225_f());
            if (Utils.getXandZDistanceSquared(vec) < 36.0D) {
               particleVec = vec;
               chestPos = getBlockPos(packet);
               ChestESP.clickedBlocks.add(chestPos);
            }
         }

      }
   }

   @SubscribeEvent
   public void onOpenGui(GuiOpenEvent event) {
      particleVec = null;
      PowderMacro.clicked.clear();
      chestPos = null;
   }

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (particleVec != null) {
         FakeRotation.rotationVec = particleVec;
      }

   }

   public static BlockPos getBlockPos(S2APacketParticles packet) {
      double posX = packet.func_149220_d();
      double posZ = packet.func_149225_f();
      float x = formatFloat((float)(MathUtil.abs(packet.func_149220_d()) % 1.0D));
      float z = formatFloat((float)(MathUtil.abs(packet.func_149225_f()) % 1.0D));
      if (x == 0.9F) {
         ++posX;
      }

      if (x == 0.1F) {
         --posX;
      }

      if (z == 0.9F) {
         ++posZ;
      }

      if (z == 0.1F) {
         --posZ;
      }

      return new BlockPos(posX, packet.func_149226_e(), posZ);
   }

   private static boolean isPosValid(S2APacketParticles packet) {
      float x = formatFloat((float)(MathUtil.abs(packet.func_149220_d()) % 1.0D));
      float z = formatFloat((float)(MathUtil.abs(packet.func_149225_f()) % 1.0D));
      return x == 0.1F || x == 0.9F || z == 0.1F || z == 0.9F;
   }

   private static float formatFloat(float value) {
      return (float)MathUtil.round(value * 10000.0F) / 10000.0F;
   }
}
