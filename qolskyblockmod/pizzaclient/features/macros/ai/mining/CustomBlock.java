package qolskyblockmod.pizzaclient.features.macros.ai.mining;

import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStone.EnumType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;

public class CustomBlock {
   public static final CustomBlock TITANIUM;
   public final PropertyEnum<?> property;
   public final Enum<?> value;

   public CustomBlock(PropertyEnum<?> property, Enum<?> value) {
      this.property = property;
      this.value = value;
   }

   public boolean equals(IBlockState state) {
      return state.func_177229_b(this.property) == this.value;
   }

   static {
      TITANIUM = new CustomBlock(BlockStone.field_176247_a, EnumType.DIORITE_SMOOTH);
   }
}
