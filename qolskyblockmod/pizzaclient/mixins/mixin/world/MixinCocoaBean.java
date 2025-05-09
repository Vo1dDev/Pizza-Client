package qolskyblockmod.pizzaclient.mixins.mixin.world;

import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;

@Mixin({BlockCocoa.class})
public abstract class MixinCocoaBean extends BlockDirectional {
   protected MixinCocoaBean(Material materialIn) {
      super(materialIn);
   }

   @Inject(
      method = {"setBlockBoundsBasedOnState"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void changeBlockBounds(IBlockAccess worldIn, BlockPos pos, CallbackInfo ci) {
      if (PizzaClient.config.cocoaBeanSize) {
         if ((Integer)worldIn.func_180495_p(pos).func_177229_b(BlockCocoa.field_176501_a) < 2) {
            this.func_149676_a(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
         } else {
            this.func_149676_a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
         }

         ci.cancel();
      }

   }
}
