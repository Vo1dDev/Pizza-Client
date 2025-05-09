package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import net.minecraft.client.gui.GuiKeyBindingList.CategoryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.graphics.FontUtil;

@Mixin({CategoryEntry.class})
public class MixinCategoryEntry {
   @Shadow
   @Final
   private String field_148285_b;
   @Shadow
   @Final
   private int field_148286_c;

   @Inject(
      method = {"drawEntry"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void drawRainbow(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected, CallbackInfo ci) {
      if (this.field_148285_b.equals("Pizza Client")) {
         FontUtil.drawRainbowText(this.field_148285_b, PizzaClient.mc.field_71462_r.field_146294_l / 2 - this.field_148286_c / 2, y + slotHeight - PizzaClient.mc.field_71466_p.field_78288_b - 1);
         ci.cancel();
      }

   }
}
