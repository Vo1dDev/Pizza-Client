package qolskyblockmod.pizzaclient.mixins.mixin.misc;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.listeners.SoundListener;

@Mixin({SoundManager.class})
public class MixinSoundManager {
   @Inject(
      method = {"playSound"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onPlaySound(ISound p_sound, CallbackInfo ci) {
      if (PizzaClient.mc.field_71441_e != null) {
         if (SoundListener.onPlaySound(p_sound, p_sound.func_147650_b().func_110623_a())) {
            ci.cancel();
         }

      }
   }
}
