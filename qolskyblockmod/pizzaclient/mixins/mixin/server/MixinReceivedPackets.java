package qolskyblockmod.pizzaclient.mixins.mixin.server;

import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S08PacketPlayerPosLook.EnumFlags;
import net.minecraft.util.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.Failsafes;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroState;
import qolskyblockmod.pizzaclient.features.player.AutoPowderChest;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;

@Mixin(
   value = {NetHandlerPlayClient.class},
   priority = 2000
)
public abstract class MixinReceivedPackets {
   @Inject(
      method = {"handleParticles"},
      at = {@At("HEAD")}
   )
   private void onParticle(S2APacketParticles packetIn, CallbackInfo ci) {
      AutoPowderChest.onParticle(packetIn);
   }

   @Inject(
      method = {"handlePlayerPosLook"},
      at = {@At("HEAD")}
   )
   private void onPacketPosLook(S08PacketPlayerPosLook packetIn, CallbackInfo ci) {
      if (MacroBuilder.toggled && PizzaClient.location != Locations.NULL && MacroBuilder.state == MacroState.ACTIVE) {
         float yaw = packetIn.func_148931_f();
         if (packetIn.func_179834_f().contains(EnumFlags.Y_ROT)) {
            yaw += PizzaClient.mc.field_71439_g.field_70177_z;
         }

         if (PlayerUtil.getPositionEyes().func_72436_e(new Vec3(packetIn.func_148932_c(), packetIn.func_148928_d(), packetIn.func_148933_e())) < 16.0D) {
            if (PizzaClient.config.rotationFailsafe) {
               float diffYaw = MathUtil.abs(PizzaClient.mc.field_71439_g.field_70177_z - yaw);
               if (diffYaw > 15.0F) {
                  Failsafes.onPacketPosLook(diffYaw);
               }
            }
         } else {
            MacroBuilder.updatePosition();
            Failsafes.onChangePosition();
         }
      }

   }
}
