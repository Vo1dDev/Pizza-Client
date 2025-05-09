package qolskyblockmod.pizzaclient.mixins.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.NukerBase;
import qolskyblockmod.pizzaclient.util.PlayerUtil;

@Mixin(
   value = {Minecraft.class},
   priority = 999
)
public class MixinMinecraftt {
   @Inject(
      method = {"clickMouse"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onClickMouse(CallbackInfo ci) {
      if (PizzaClient.mc.field_71439_g != null && PizzaClient.config.terminatorValk) {
         ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
         if (held != null && held.func_82833_r().toLowerCase().contains("valkyrie")) {
            for(int i = 0; i < 8; ++i) {
               ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
               if (item != null && item.func_82833_r().toLowerCase().contains("terminator")) {
                  PlayerUtil.sendSlotRightClickPacket(i);
                  ci.cancel();
                  break;
               }
            }
         }
      }

   }

   @Inject(
      method = {"sendClickBlockToController"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void sendClickBlockToController(boolean leftClick, CallbackInfo ci) {
      if (PizzaClient.mc.field_71462_r == null && NukerBase.nukedThisTick()) {
         ci.cancel();
      }

   }

   @Inject(
      method = {"startGame"},
      at = {@At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/shader/Framebuffer;setFramebufferColor(FFFF)V",
   shift = Shift.AFTER
)}
   )
   private void onInit(CallbackInfo ci) {
      PizzaClient.onStartGame();
   }

   @Inject(
      method = {"runTick"},
      at = {@At(
   value = "FIELD",
   target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;",
   ordinal = 0
)}
   )
   private void onTick(CallbackInfo ci) {
      PizzaClient.onTick();
   }

   @Redirect(
      method = {"setIngameFocus"},
      at = @At(
   value = "INVOKE",
   target = "Lorg/lwjgl/opengl/Display;isActive()Z"
)
   )
   private boolean redirectIsActive() {
      return true;
   }
}
