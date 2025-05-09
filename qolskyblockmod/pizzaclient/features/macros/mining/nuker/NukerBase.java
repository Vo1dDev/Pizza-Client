package qolskyblockmod.pizzaclient.features.macros.mining.nuker;

import qolskyblockmod.pizzaclient.PizzaClient;

public class NukerBase {
   public static INuker nuker;
   private static float extraPacket;

   public static boolean nukedThisTick() {
      if (nuker != null) {
         nuker.onNukePre();
         if (!nuker.isVecValid() && !nuker.findVec()) {
            nuker.onNoVecAvailable();
            nuker = null;
            extraPacket = 0.0F;
            return false;
         }

         if (nuker.nuke(false)) {
            if (PizzaClient.config.nukerExtraPackets != 0) {
               extraPacket += (float)PizzaClient.config.nukerExtraPackets;

               for(int i = 0; (float)i < extraPacket / 20.0F; ++i) {
                  nuker.onNukePre();
                  if (!nuker.isVecValid() && !nuker.findVec()) {
                     nuker.onNoVecAvailable();
                     nuker = null;
                     extraPacket = 0.0F;
                     return true;
                  }

                  if (!nuker.nuke(true)) {
                     extraPacket = 0.0F;
                  }
               }

               extraPacket %= 20.0F;
            }

            if (nuker.invalidate()) {
               nuker = null;
            }

            return true;
         }

         nuker = null;
      }

      return false;
   }
}
