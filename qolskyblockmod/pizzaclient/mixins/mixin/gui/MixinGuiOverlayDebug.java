package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import net.minecraft.client.gui.GuiOverlayDebug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import qolskyblockmod.pizzaclient.util.Utils;

@Mixin({GuiOverlayDebug.class})
public class MixinGuiOverlayDebug {
   @Redirect(
      method = {"call"},
      at = @At(
   value = "INVOKE",
   target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;",
   ordinal = 5
)
   )
   private String redirectWorldDate(String s, Object[] objects) {
      return "Local Difficulty: " + Utils.DECIMAL_FORMAT.format((double)(Float)objects[0]) + " (Day " + Utils.DECIMAL_FORMAT.format((double)Utils.getExactDay()) + ")";
   }
}
