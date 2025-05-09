package qolskyblockmod.pizzaclient.mixins.mixin.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.BlockChangeEvent;

@Mixin({Chunk.class})
public class MixinChunk {
   @Inject(
      method = {"setBlockState"},
      at = {@At("HEAD")}
   )
   private void onBlockChange(BlockPos pos, IBlockState state, CallbackInfoReturnable<IBlockState> cir) {
      if (PizzaClient.mc.field_71441_e != null) {
         IBlockState old = PizzaClient.mc.field_71441_e.func_180495_p(pos);
         if (!old.equals(state)) {
            MinecraftForge.EVENT_BUS.post(new BlockChangeEvent(old, state, pos));
         }

      }
   }
}
