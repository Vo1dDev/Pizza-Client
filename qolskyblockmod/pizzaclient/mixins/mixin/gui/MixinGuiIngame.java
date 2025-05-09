package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({GuiIngame.class})
public class MixinGuiIngame {
   @Redirect(
      method = {"renderScoreboard"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;III)I",
   ordinal = 1
)
   )
   private int cancelRenderingNumbers(FontRenderer instance, String text, int x, int y, int color) {
      return 0;
   }
}
