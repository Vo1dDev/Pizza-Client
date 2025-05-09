package qolskyblockmod.pizzaclient.mixins.mixin.renderer;

import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;

@Mixin({LayerCustomHead.class})
public class MixinLayerCustomHead {
   @Inject(
      method = {"doRenderLayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRender(EntityLivingBase entity, float f2, float s, float nbttagcompound, float f3, float gameprofile, float item, float minecraft, CallbackInfo ci) {
      if (PizzaClient.config.auschwitzSimulator && entity instanceof EntityPlayer) {
         ci.cancel();
      }

   }
}
