package qolskyblockmod.pizzaclient.mixins.mixin.renderer;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;

@Mixin({ModelBiped.class})
public abstract class MixinModelBiped extends ModelBase {
   @Shadow
   public ModelRenderer field_78116_c;

   @Inject(
      method = {"setRotationAngles"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/util/MathHelper;cos(F)F",
   ordinal = 0
)}
   )
   private void changeRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn, CallbackInfo ci) {
      if (FakeRotation.lastPitch != 69420.0F && entityIn instanceof EntityPlayerSP) {
         this.field_78116_c.field_78795_f = FakeRotation.lastPitch / 57.29578F;
      }

   }
}
