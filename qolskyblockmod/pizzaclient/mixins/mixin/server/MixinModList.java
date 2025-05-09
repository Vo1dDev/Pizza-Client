package qolskyblockmod.pizzaclient.mixins.mixin.server;

import java.util.Map;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;

@Mixin(
   value = {ModList.class},
   remap = false
)
public class MixinModList {
   @Shadow
   private Map<String, String> modTags;

   @Inject(
      method = {"<init>(Ljava/util/List;)V"},
      at = {@At("RETURN")}
   )
   private void removeMod(CallbackInfo ci) {
      if (!PizzaClient.mc.func_71387_A()) {
         this.modTags.remove("pizzaclient");
      }

   }
}
