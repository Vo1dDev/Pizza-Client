package qolskyblockmod.pizzaclient.mixins.mixin.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.core.events.ChestBackgroundDrawnEvent;

@Mixin(
   value = {GuiContainer.class},
   priority = 999
)
public abstract class MixinContainerGui extends GuiScreen {
   @Shadow
   public Container field_147002_h;

   @Inject(
      method = {"drawScreen"},
      at = {@At("HEAD")}
   )
   private void backgroundDrawn(CallbackInfo ci) {
      if (this.field_147002_h instanceof ContainerChest) {
         IInventory chest = ((ContainerChest)this.field_147002_h).func_85151_d();
         MinecraftForge.EVENT_BUS.post(new ChestBackgroundDrawnEvent(this.field_147002_h, StringUtils.func_76338_a(chest.func_145748_c_().func_150260_c().trim()), this.field_147002_h.field_75151_b.size(), this.field_147002_h.field_75151_b, chest));
      }

   }
}
