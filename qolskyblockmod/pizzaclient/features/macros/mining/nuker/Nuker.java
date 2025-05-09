package qolskyblockmod.pizzaclient.features.macros.mining.nuker;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockStem;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.NukerMacro;
import qolskyblockmod.pizzaclient.features.skills.WorldScanner;

@SideOnly(Side.CLIENT)
public class Nuker extends NukerMacro {
   private Set<Block> targets;

   public boolean isBlockValid(BlockPos pos) {
      IBlockState state;
      switch(PizzaClient.config.nukerType) {
      case 0:
         return !avoid.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
      case 1:
         state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
         return this.targets.contains(state.func_177230_c()) && (vec != null && pos.equals(new BlockPos(vec)) || PizzaClient.config.gemstoneNukerType == 0 || PizzaClient.config.gemstoneNukerType == WorldScanner.getGemstoneType(state));
      case 2:
         state = PizzaClient.mc.field_71441_e.func_180495_p(pos);
         Block block = state.func_177230_c();
         return block instanceof IGrowable && block != Blocks.field_150349_c && !(block instanceof BlockStem) && !(block instanceof BlockTallGrass) && !(block instanceof BlockDoublePlant) && !((IGrowable)block).func_176473_a(PizzaClient.mc.field_71441_e, pos, state, PizzaClient.mc.field_71441_e.field_72995_K) || block instanceof IPlantable && block.func_176201_c(state) > 0;
      default:
         return this.targets.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c());
      }
   }

   private Set<Block> targetBlock() {
      switch(PizzaClient.config.nukerType) {
      case 1:
         return new HashSet(PizzaClient.config.gemstoneNukerGlassPanes ? Arrays.asList(Blocks.field_150399_cn, Blocks.field_150397_co) : Collections.singletonList(Blocks.field_150399_cn));
      case 2:
         return new HashSet();
      case 3:
         return new HashSet(Collections.singletonList(Blocks.field_150340_R));
      case 4:
         return new HashSet(Collections.singletonList(Blocks.field_150324_C));
      case 5:
         return new HashSet(Arrays.asList(Blocks.field_150364_r, Blocks.field_150363_s));
      case 6:
         return new HashSet(Collections.singletonList(Blocks.field_150354_m));
      case 7:
         return new HashSet(Arrays.asList(Blocks.field_150365_q, Blocks.field_150366_p, Blocks.field_150352_o, Blocks.field_150369_x, Blocks.field_150450_ax, Blocks.field_150439_ay, Blocks.field_150482_ag, Blocks.field_150412_bA, Blocks.field_150449_bY));
      default:
         return new HashSet();
      }
   }

   public void onTick() {
      this.targets = this.targetBlock();
      NukerBase.nuker = this;
   }

   public void onToggle(boolean toggled) {
      if (toggled && !PizzaClient.config.nuker) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Enable the nuker in order to activate this macro!"));
         MacroBuilder.toggled = false;
      } else {
         this.addToggleMessage("Nuker");
      }
   }

   public void onDisable() {
      vec = null;
   }

   public boolean findVec() {
      if (PizzaClient.config.nukerType == 1) {
         int type;
         boolean found;
         if (PizzaClient.config.gemstoneNukerGlassPanes) {
            this.targets = new HashSet(Collections.singletonList(Blocks.field_150399_cn));
            if (!super.findVec()) {
               this.targets = new HashSet(Collections.singletonList(Blocks.field_150397_co));
               if (!super.findVec()) {
                  if (PizzaClient.config.gemstoneNukerType == 0) {
                     return false;
                  }

                  type = PizzaClient.config.gemstoneNukerType;
                  PizzaClient.config.gemstoneNukerType = 0;
                  this.targets = new HashSet(Collections.singletonList(Blocks.field_150399_cn));
                  if (!super.findVec()) {
                     this.targets = new HashSet(Collections.singletonList(Blocks.field_150397_co));
                     found = super.findVec();
                     PizzaClient.config.gemstoneNukerType = type;
                     return found;
                  }

                  PizzaClient.config.gemstoneNukerType = type;
               }
            }
         } else {
            this.targets = new HashSet(Collections.singletonList(Blocks.field_150399_cn));
            if (!super.findVec()) {
               if (PizzaClient.config.gemstoneNukerType == 0) {
                  return false;
               }

               type = PizzaClient.config.gemstoneNukerType;
               PizzaClient.config.gemstoneNukerType = 0;
               found = super.findVec();
               PizzaClient.config.gemstoneNukerType = type;
               return found;
            }
         }

         return true;
      } else {
         return super.findVec();
      }
   }
}
