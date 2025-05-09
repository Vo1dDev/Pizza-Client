package qolskyblockmod.pizzaclient.mixins.mixin.renderer;

import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;

@Mixin({LayerArmorBase.class})
public class MixinLayerArmorRenderer {
   @Inject(
      method = {"doRenderLayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRender(EntityLivingBase entity, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale, CallbackInfo ci) {
      if (PizzaClient.config.auschwitzSimulator && entity instanceof EntityPlayer) {
         ci.cancel();
      }

   }
}
