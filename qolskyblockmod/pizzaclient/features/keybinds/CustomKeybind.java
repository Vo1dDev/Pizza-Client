package qolskyblockmod.pizzaclient.features.keybinds;

import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.PlayerUtil;

public class CustomKeybind {
   public int delay;
   public KeybindAction actionType;
   public long lastSwitch;

   public CustomKeybind(int delay, KeybindAction actionType) {
      this.delay = delay;
      this.actionType = actionType;
      this.lastSwitch = System.currentTimeMillis();
   }

   public void useItemAndUpdate(int slot) {
      int currentSlot = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
      PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = slot;
      if (this.actionType == KeybindAction.LEFT) {
         PlayerUtil.forceUpdateController();
         PlayerUtil.leftClick();
      } else {
         PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[slot]);
      }

      PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentSlot;
      if (PizzaClient.config.autoMacroKeyToggleSync) {
         PlayerUtil.forceUpdateController();
      }

      this.lastSwitch = System.currentTimeMillis();
   }

   public String toString() {
      return "{delay: " + this.delay + ", actionType: " + KeybindAction.getString(this.actionType) + "}";
   }
}
