package qolskyblockmod.pizzaclient.features.macros.ai.mining.finder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;
import qolskyblockmod.pizzaclient.PizzaClient;

public class MultipleBlockFinder implements BlockFinder {
   public final Set<Block> blocks;

   public MultipleBlockFinder(Set<Block> blocks) {
      this.blocks = blocks;
   }

   public MultipleBlockFinder(Block... blocks) {
      this.blocks = new HashSet(Arrays.asList(blocks));
   }

   public boolean isValid(BlockPos pos) {
      return this.blocks.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
   }
}
