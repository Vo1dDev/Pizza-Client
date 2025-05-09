package qolskyblockmod.pizzaclient.features.macros.farming;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.FarmingMacro;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class AutoF11Macro extends FarmingMacro {
   public void onTick() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), PizzaClient.config.holdLeftClick);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), PizzaClient.config.holdW);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), PizzaClient.config.holdA);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), PizzaClient.config.holdD);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), PizzaClient.config.holdS);
      super.onTick();
   }

   public void onToggle(boolean toggled) {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "AutoF11 Macro is now " + Utils.getColouredBoolean(toggled)));
      super.onToggle();
   }

   public boolean applyFailsafes() {
      return true;
   }

   public Locations getLocation() {
      return PizzaClient.config.goBackToIs ? Locations.PRIVATEISLAND : null;
   }
}
