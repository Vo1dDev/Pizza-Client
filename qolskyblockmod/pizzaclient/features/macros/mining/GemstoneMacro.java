package qolskyblockmod.pizzaclient.features.macros.mining;

import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.Macro;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class GemstoneMacro extends Macro {
   public void onTick() {
      if (PizzaClient.location == Locations.CHOLLOWS) {
      }

   }

   public void onToggle(boolean toggled) {
      this.addToggleMessage("Gemstone Macro");
   }

   public boolean applyFailsafes() {
      return true;
   }

   public boolean applyPositionFailsafe() {
      return true;
   }

   public boolean applyBedrockFailsafe() {
      return false;
   }

   public boolean applyPlayerFailsafes() {
      return PizzaClient.config.stopWhenNearPlayer;
   }
}
