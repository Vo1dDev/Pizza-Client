package qolskyblockmod.pizzaclient.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.Packet;
import qolskyblockmod.pizzaclient.PizzaClient;

public class PacketUtil {
   public static List<Packet<?>> pausedPackets = new ArrayList();
   public static boolean stopPackets;

   public static void sendPackets(List<Packet<?>> packets) {
      boolean bool = stopPackets;
      stopPackets = false;
      Iterator var2 = packets.iterator();

      while(var2.hasNext()) {
         Packet<?> packet = (Packet)var2.next();
         pausedPackets.remove(packet);
         PizzaClient.mc.field_71439_g.field_71174_a.func_147298_b().func_179290_a(packet);
      }

      stopPackets = bool;
   }

   public static void continueAndSendPackets() {
      stopPackets = false;
      Iterator var0 = pausedPackets.iterator();

      while(var0.hasNext()) {
         Packet<?> packet = (Packet)var0.next();
         PizzaClient.mc.field_71439_g.field_71174_a.func_147298_b().func_179290_a(packet);
      }

      pausedPackets.clear();
   }
}
