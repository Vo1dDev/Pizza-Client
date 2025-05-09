package qolskyblockmod.pizzaclient.features.macros.ai.mining.finder;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import qolskyblockmod.pizzaclient.PizzaClient;

public class SingleBlockFinder implements BlockFinder {
   public final Block block;

   public SingleBlockFinder(Block block) {
      this.block = block;
   }

   public boolean isValid(BlockPos pos) {
      return PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() == this.block;
   }
}
