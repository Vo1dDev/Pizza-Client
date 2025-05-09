package qolskyblockmod.pizzaclient.mixins.mixin.session;

import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import qolskyblockmod.pizzaclient.features.misc.SessionProtection;

@Mixin(
   value = {YggdrasilMinecraftSessionService.class},
   priority = Integer.MAX_VALUE,
   remap = false
)
public class MixinYggdrasil {
   @ModifyVariable(
      method = {"joinServer"},
      at = @At("HEAD"),
      ordinal = 0,
      argsOnly = true
   )
   private String onJoinServer(String value) {
      return SessionProtection.changedToken ? SessionProtection.changed : value;
   }
}
