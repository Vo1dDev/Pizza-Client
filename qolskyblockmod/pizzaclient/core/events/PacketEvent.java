package qolskyblockmod.pizzaclient.core.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketEvent extends Event {
   public final Packet<?> packet;

   public PacketEvent(Packet<?> packet) {
      this.packet = packet;
   }

   public static class SendEvent extends PacketEvent {
      public SendEvent(Packet<?> packet) {
         super(packet);
      }
   }

   public static class ReceiveEvent extends PacketEvent {
      public ReceiveEvent(Packet<?> packet) {
         super(packet);
      }
   }
}
