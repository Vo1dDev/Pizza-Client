package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.core.events.SendChatMessageEvent;

@Mixin({GuiScreen.class})
public abstract class MixinGuiScreen extends Gui {
   @Inject(
      method = {"sendChatMessage(Ljava/lang/String;Z)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onSendChatMessage(String message, boolean addToChat, CallbackInfo ci) {
      if (MinecraftForge.EVENT_BUS.post(new SendChatMessageEvent(message))) {
         ci.cancel();
      }

   }
}
