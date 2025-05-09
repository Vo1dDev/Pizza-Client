package qolskyblockmod.pizzaclient.mixins.mixin.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;

@Mixin({AbstractClientPlayer.class})
public abstract class MixinAbstractPlayerClient extends EntityPlayer {
   @Unique
   private final ResourceLocation skin = new ResourceLocation("pizzaclient", "skin/skin.png");
   @Unique
   private final ResourceLocation nice_guy = new ResourceLocation("pizzaclient", "skin/auschwitz/nice_guy.png");

   public MixinAbstractPlayerClient(World worldIn, GameProfile gameProfileIn) {
      super(worldIn, gameProfileIn);
   }

   @Inject(
      method = {"getLocationSkin()Lnet/minecraft/util/ResourceLocation;"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void replaceSkin(CallbackInfoReturnable<ResourceLocation> cir) {
      if (PizzaClient.config.auschwitzSimulator) {
         if (this.func_70005_c_().equals(PizzaClient.username)) {
            cir.setReturnValue(this.nice_guy);
         } else {
            cir.setReturnValue(new ResourceLocation("pizzaclient", "skin/auschwitz/jew" + MathUtil.abs(this.func_70005_c_().hashCode() % 2) + ".png"));
         }
      } else if (PizzaClient.config.replaceSkin) {
         cir.setReturnValue(this.skin);
      }

   }
}
