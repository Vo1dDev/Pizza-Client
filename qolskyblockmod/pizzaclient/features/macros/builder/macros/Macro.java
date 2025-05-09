package qolskyblockmod.pizzaclient.features.macros.builder.macros;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.Failsafes;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroState;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public abstract class Macro {
   public Thread miscThread = new Thread(() -> {
   });

   public void addToggleMessage(String name) {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + name + " is now " + (MacroBuilder.toggled ? EnumChatFormatting.GREEN + "On" : EnumChatFormatting.RED + "Off")));
   }

   public abstract void onTick();

   public abstract void onToggle(boolean var1);

   public void onRender() {
   }

   public void onDisable() {
   }

   public void onMove() {
   }

   public void onChat(String msg) {
   }

   public void warpBack() {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "No warp back failsafe for this macro."));
   }

   public void writeToWebhook(String content) {
      Failsafes.writeToWebhook(content);
   }

   public abstract boolean applyFailsafes();

   public Locations getLocation() {
      return null;
   }

   public abstract boolean applyPositionFailsafe();

   public abstract boolean applyBedrockFailsafe();

   public abstract boolean applyPlayerFailsafes();

   public boolean applyRotationFailsafe() {
      return PizzaClient.config.rotationFailsafe;
   }

   public void enqueueThread(Runnable runnable) {
      this.terminateIfAlive();
      MacroBuilder.state = MacroState.NEW_THREAD;
      this.miscThread = new Thread(runnable);
      this.miscThread.start();
   }

   public void enqueueFailsafeThread(Runnable runnable) {
      this.terminateIfAlive();
      MacroBuilder.state = MacroState.FAILSAFE;
      this.miscThread = new Thread(runnable);
      this.miscThread.start();
   }

   public void terminateIfAlive() {
      if (this.miscThread.isAlive()) {
         this.terminate();
      }

   }

   public void terminate() {
      this.miscThread.stop();
   }

   public void onDeath() {
      this.onDisable();
      this.terminateIfAlive();
   }
}
