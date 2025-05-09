package qolskyblockmod.pizzaclient.mixins.mixin.server;

import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.util.PacketUtil;

@Mixin(
   value = {NetworkManager.class},
   priority = 2000
)
public abstract class MixinNetworkManager extends SimpleChannelInboundHandler<Packet<?>> {
   @Inject(
      method = {"sendPacket(Lnet/minecraft/network/Packet;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSendPacket(Packet<?> packetIn, CallbackInfo ci) {
      if (PacketUtil.stopPackets) {
         PacketUtil.pausedPackets.add(packetIn);
         ci.cancel();
      }

   }
}
