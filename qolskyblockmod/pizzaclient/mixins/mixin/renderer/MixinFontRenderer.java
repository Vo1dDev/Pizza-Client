package qolskyblockmod.pizzaclient.mixins.mixin.renderer;

import java.util.Locale;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.graphics.custom.CustomString;
import qolskyblockmod.pizzaclient.util.graphics.custom.ModMessageString;
import qolskyblockmod.pizzaclient.util.graphics.custom.names.RainbowString;
import qolskyblockmod.pizzaclient.util.graphics.custom.names.RightSideRainbowString;

@Mixin({FontRenderer.class})
public class MixinFontRenderer {
   @Inject(
      method = {"drawString(Ljava/lang/String;FFIZ)I"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onDrawString(String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
      if (text == null) {
         cir.setReturnValue(0);
      } else {
         if (PizzaClient.mc.field_71441_e != null) {
            if (RainbowString.rgbList.containsKey(text)) {
               CustomString custom = (CustomString)RainbowString.rgbList.get(text);
               if (custom != null) {
                  cir.setReturnValue(custom.render(text, (int)x, (int)y, color, shadow));
               }

               return;
            }

            if (text.startsWith("§rPizzaClient > ")) {
               ModMessageString.addToList(text);
               return;
            }

            String[] var7 = StringUtils.func_76338_a(text).replace("'", " ").replace(":", "").split(" ");
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               String s = var7[var9];
               if (s.length() > 2) {
                  String lowerCase = s.toLowerCase(Locale.ROOT);
                  if (RainbowString.rgbNames.containsKey(lowerCase)) {
                     if (text.endsWith(s)) {
                        new RightSideRainbowString(text, s, lowerCase);
                        cir.setReturnValue(((CustomString)RainbowString.rgbList.get(text)).render(text, (int)x, (int)y, color, shadow));
                        return;
                     }

                     new RainbowString(text, s, lowerCase);
                     cir.setReturnValue(((CustomString)RainbowString.rgbList.get(text)).render(text, (int)x, (int)y, color, shadow));
                     return;
                  }
               }
            }

            RainbowString.rgbList.put(text, (Object)null);
         }

      }
   }
}
