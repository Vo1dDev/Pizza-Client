package qolskyblockmod.pizzaclient.util.misc;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C69PacketSex implements Packet<INetHandlerPlayServer> {
   protected boolean cum;
   protected EntityPlayer sender;
   protected EntityPlayer receiver;

   public C69PacketSex(EntityPlayer senderIn, EntityPlayer receiverIn, boolean cum) {
      this.sender = senderIn;
      this.receiver = receiverIn;
      this.cum = cum;
   }

   public void func_148837_a(PacketBuffer buf) throws IOException {
   }

   public void func_148840_b(PacketBuffer buf) throws IOException {
   }

   public void processPacket(INetHandlerPlayServer handler) {
   }
}
