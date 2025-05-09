package qolskyblockmod.pizzaclient.mixins.mixin.misc;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import qolskyblockmod.pizzaclient.PizzaClient;

@Mixin(
   value = {SplashProgress.class},
   priority = 2000
)
public class MixinSplashProgress {
   @Unique
   private static final ResourceLocation troll = new ResourceLocation("pizzaclient", "splash/troll.png");
   @Unique
   private static final ResourceLocation arabic = new ResourceLocation("pizzaclient", "splash/arabic.png");

   @ModifyVariable(
      method = {"start"},
      at = @At("STORE"),
      ordinal = 1,
      remap = false
   )
   private static ResourceLocation setForgeTitle(ResourceLocation original) {
      try {
         return PizzaClient.config.funnyLoadingScreen && PizzaClient.config.funnyLoadingScreenTrollFace ? troll : original;
      } catch (Exception var2) {
         var2.printStackTrace();
         System.out.println("Failed to apply custom splash screen.");
         return original;
      }
   }

   @ModifyVariable(
      method = {"start"},
      at = @At("STORE"),
      ordinal = 0,
      remap = false
   )
   private static ResourceLocation setForgeUnicode(ResourceLocation original) {
      try {
         return PizzaClient.config.funnyLoadingScreen && PizzaClient.config.funnyLoadingScreenArab ? arabic : original;
      } catch (Exception var2) {
         var2.printStackTrace();
         System.out.println("Failed to apply custom splash screen.");
         return original;
      }
   }
}
