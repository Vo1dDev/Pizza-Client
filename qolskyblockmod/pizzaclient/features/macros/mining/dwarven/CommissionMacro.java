package qolskyblockmod.pizzaclient.features.macros.mining.dwarven;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StringUtils;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.Refuel;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.AOTVMovement;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.EntityPath;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.PathBase;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathFinder;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.Macro;
import qolskyblockmod.pizzaclient.util.ItemUtil;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class CommissionMacro extends Macro {
   public static final BlockPos CRUCIBLE = new BlockPos(0, 166, -11);
   private static final List<CommissionMacro.CommissionLocation> mithrils = new ArrayList();
   private CommissionMacro.CommissionLocation commission;
   private static EntityArmorStand king;

   public void onTick() {
      if (PizzaClient.location == Locations.DWARVENMINES) {
         if (king == null) {
            findKing();
         }

         if (this.commission == null) {
            this.findCommission(true);
         } else if (this.commission.mithril) {
            if (!PizzaClient.config.sneakWhenUsingMacro) {
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
            }

            MacroBuilder.macros[2].onTick();
         } else if (!this.miscThread.isAlive()) {
            this.huntMobs();
         }

      }
   }

   private void findCommission(boolean mithril) {
      this.terminateIfAlive();

      for(int i = 0; i < 8; ++i) {
         ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
         if (item != null && StringUtils.func_76338_a(item.func_82833_r()).equals("Royal Pigeon")) {
            this.enqueueThread(() -> {
               if (!mithril) {
                  Utils.sleep(1000);
                  PizzaClient.mc.field_71439_g.func_71165_d("/l");
                  this.commission = null;
               } else {
                  PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = i;
                  Utils.sleep(500);

                  while(PizzaClient.mc.field_71462_r == null) {
                     PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g());
                     Utils.sleep(2000);
                  }

                  this.findComm();
               }
            });
            return;
         }
      }

      if (king == null) {
         findKing();
         if (king == null) {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Found no pigeon nor king. If you want to use the king method, you need to get near a king or an Emissary for every lobby."));
            return;
         }
      }

      this.enqueueThread(() -> {
         if (!mithril) {
            Utils.sleep(1000);
            PizzaClient.mc.field_71439_g.func_71165_d("/l");
            this.commission = null;
            Utils.sleep(3000);
         } else {
            while(PizzaClient.mc.field_71462_r == null) {
               PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, king);
               Utils.sleep(2500);
            }

            this.findComm();
         }
      });
   }

   private static void findKing() {
      Iterator var0 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      while(var0.hasNext()) {
         Entity entity = (Entity)var0.next();
         if (entity instanceof EntityArmorStand) {
            String name = entity.func_145748_c_().func_150260_c();
            if (name.startsWith("§6§lKing ") || name.startsWith("§6Emissary ") && !name.contains("Braum")) {
               king = (EntityArmorStand)entity;
               break;
            }
         }
      }

   }

   private void findComm() {
      Iterator var1 = PizzaClient.mc.field_71439_g.field_71070_bA.field_75151_b.iterator();

      while(true) {
         while(true) {
            Slot slot;
            ItemStack stack;
            do {
               do {
                  if (!var1.hasNext()) {
                     CommissionMacro.CommissionLocation worseComm = null;
                     CommissionMacro.CommissionLocation bestComm = null;
                     Iterator var13 = PizzaClient.mc.field_71439_g.field_71070_bA.field_75151_b.iterator();

                     label63:
                     while(var13.hasNext()) {
                        Slot slot = (Slot)var13.next();
                        ItemStack stack = slot.func_75211_c();
                        if (stack != null && stack.func_77973_b() == Items.field_151099_bA) {
                           Iterator var16 = ItemUtil.getItemLore(stack).iterator();

                           while(var16.hasNext()) {
                              String s = (String)var16.next();
                              String stripped = StringUtils.func_76338_a(s);
                              CommissionMacro.CommissionLocation comm = CommissionMacro.CommissionLocation.getBestCommissionFromString(stripped);
                              if (worseComm != CommissionMacro.CommissionLocation.ICE_WALKERS) {
                                 CommissionMacro.CommissionLocation loc = CommissionMacro.CommissionLocation.getWorseCommissionFromString(stripped);
                                 if (loc != null) {
                                    worseComm = loc;
                                 }
                              }

                              if (comm != null) {
                                 bestComm = comm;
                                 break label63;
                              }
                           }
                        }
                     }

                     if (bestComm == null) {
                        if (worseComm == null) {
                           PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to find a commission somehow."));
                           this.commission = null;
                           return;
                        }

                        bestComm = worseComm;
                     }

                     PizzaClient.mc.field_71439_g.func_71053_j();
                     Utils.sleep(2000);
                     if (bestComm != this.commission) {
                        PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
                        this.commission = bestComm;
                        Utils.sleep(3000);
                        this.commission.pathfindToPos();
                     }

                     Utils.sleep(400);
                     MacroBuilder.updatePosition();
                     MithrilMacro.miningAi.onMove();
                     PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = this.commission.getTool();
                     Utils.sleep(700);
                     if (this.commission.mithril && PizzaClient.mc.field_71476_x != null && PizzaClient.mc.field_71476_x.field_72308_g == null) {
                        KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
                     }

                     Utils.sleep(400);
                     MithrilMacro.miningAi.update();
                     return;
                  }

                  slot = (Slot)var1.next();
                  stack = slot.func_75211_c();
               } while(stack == null);
            } while(stack.func_77973_b() != Items.field_151099_bA);

            Iterator var4 = ItemUtil.getItemLore(stack).iterator();

            while(var4.hasNext()) {
               String s = (String)var4.next();
               String stripped = StringUtils.func_76338_a(s);
               if (stripped.contains("COMPLETED")) {
                  PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, slot.field_75222_d, 0, 0, PizzaClient.mc.field_71439_g);
                  Utils.sleep(1000);
                  break;
               }
            }
         }
      }
   }

   private void huntMobs() {
      List<EntityPath> paths = EntityPath.getAllSorted(EntityOtherPlayerMP.class, this.commission.entityName, this.commission.minY, this.commission.maxY);
      if (paths.size() != 0) {
         this.enqueueThread(() -> {
            for(int i = 0; paths.size() > i && !(new PathFinder((PathBase)paths.get(i))).run(false); ++i) {
            }

         });
      }

   }

   public void onChat(String unformatted) {
      if (unformatted.equals("Mining Speed Boost is now available!") && this.commission != null && this.commission.mithril) {
         PlayerUtil.useAbility();
      } else if (unformatted.startsWith("Your ") && unformatted.endsWith("Refuel it by talking to a Drill Mechanic!")) {
         this.enqueueThread(() -> {
            Refuel.legitRefuel();
            PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
            Utils.sleep(4000);
            this.commission = null;
         });
      } else if (unformatted.contains("Commission Complete! Visit the King") && !unformatted.contains(":") && this.commission != null) {
         this.findCommission(this.commission.mithril);
      }

   }

   public void onToggle(boolean toggled) {
      MithrilMacro.miningAi.onToggle();
      this.addToggleMessage("Commission Macro");
      this.commission = null;
   }

   public Locations getLocation() {
      return Locations.DWARVENMINES;
   }

   public void warpBack() {
      this.commission = null;
      Locations.DWARVENMINES.warpTo();
   }

   public boolean applyFailsafes() {
      return this.commission != null;
   }

   public void onDisable() {
      king = null;
      this.commission = null;
      MithrilMacro.miningAi.disable();
   }

   public void onMove() {
      MithrilMacro.miningAi.onMove();
   }

   public boolean applyPositionFailsafe() {
      return this.commission != null && this.commission.mithril;
   }

   public boolean applyBedrockFailsafe() {
      return this.commission != null && !this.commission.mithril;
   }

   public boolean applyPlayerFailsafes() {
      return this.commission != null && this.commission.mithril;
   }

   public static enum CommissionLocation {
      ROYAL_MINES(new ArrayList(Arrays.asList(CommissionMacro.CRUCIBLE, new BlockPos(9, 181, 19), new BlockPos(30, 173, 15), new BlockPos(56, 161, 59), new BlockPos(84, 144, 45), new BlockPos(107, 156, 37), new BlockPos(130, 156, 27))), new ArrayList(Arrays.asList(new BlockPos(139, 153, 25), new BlockPos(104, 164, 33), new BlockPos(107, 153, 53))), true),
      CLIFFSIDE_VEINS(new ArrayList(Arrays.asList(CommissionMacro.CRUCIBLE, new BlockPos(9, 181, 19), new BlockPos(30, 173, 15), new BlockPos(56, 161, 59), new BlockPos(30, 127, 42))), new ArrayList(Arrays.asList(new BlockPos(28, 130, 26), new BlockPos(50, 146, 3), new BlockPos(21, 138, 30))), true),
      LAVA_SPRINGS(new ArrayList(Arrays.asList(CommissionMacro.CRUCIBLE, new BlockPos(8, 181, 19), new BlockPos(38, 206, 2))), new ArrayList(Arrays.asList(new BlockPos(47, 216, -29), new BlockPos(74, 212, -28), new BlockPos(71, 218, 6), new BlockPos(53, 205, 16), new BlockPos(80, 221, -13))), true),
      RAMPART_QUARRY(new ArrayList(Arrays.asList(CommissionMacro.CRUCIBLE, new BlockPos(-24, 179, -27), new BlockPos(-62, 205, -10))), new ArrayList(Arrays.asList(new BlockPos(-100, 233, -20), new BlockPos(-44, 211, 24), new BlockPos(-48, 222, 21), new BlockPos(-47, 190, 32))), true),
      UPPER_MINES(new ArrayList(Arrays.asList(CommissionMacro.CRUCIBLE, new BlockPos(-24, 179, -27), new BlockPos(-62, 205, -25), new BlockPos(-74, 222, -50), new BlockPos(-89, 198, -51), new BlockPos(-132, 175, -72), new BlockPos(-130, 171, -12))), new ArrayList(Arrays.asList(new BlockPos(-146, 170, -31), new BlockPos(-148, 177, -27), new BlockPos(-132, 175, -72))), true),
      ICE_WALKERS(new ArrayList(Arrays.asList(CommissionMacro.CRUCIBLE, new BlockPos(9, 181, 19), new BlockPos(9, 181, 30), new BlockPos(8, 161, 66), new BlockPos(8, 161, 106), new BlockPos(-5, 127, 137), new BlockPos(-9, 129, 177))), new ArrayList(), "Ice Walker", 0.0F, 135.0F),
      GOBLIN(new ArrayList(Arrays.asList(CommissionMacro.CRUCIBLE, new BlockPos(9, 181, 19), new BlockPos(9, 181, 30), new BlockPos(8, 161, 66), new BlockPos(8, 161, 106), new BlockPos(-5, 127, 137), new BlockPos(-9, 129, 177), new BlockPos(-39, 130, 156), new BlockPos(-55, 134, 151))), new ArrayList(), "Goblin", 125.0F, 255.0F);

      public final List<BlockPos> requiredPath;
      public final List<BlockPos> randomPath;
      public final boolean mithril;
      public final String entityName;
      public final float minY;
      public final float maxY;

      private CommissionLocation(List<BlockPos> requiredPath, List<BlockPos> randomPath, boolean mithril) {
         this.requiredPath = requiredPath;
         this.randomPath = randomPath;
         this.mithril = mithril;
         if (mithril) {
            CommissionMacro.mithrils.add(this);
         }

         this.entityName = null;
         this.minY = 0.0F;
         this.maxY = 255.0F;
      }

      private CommissionLocation(List<BlockPos> requiredPath, List<BlockPos> randomPath, String entityName, float minY, float maxY) {
         this.requiredPath = requiredPath;
         this.randomPath = randomPath;
         this.mithril = false;
         this.entityName = entityName;
         this.minY = minY;
         this.maxY = maxY;
      }

      public static CommissionMacro.CommissionLocation getBestCommissionFromString(String s) {
         if (s.contains("Upper Mines")) {
            return UPPER_MINES;
         } else if (s.contains("Mine 500 Mithril Ore.")) {
            return getRandom();
         } else if (s.contains("Lava Springs")) {
            return LAVA_SPRINGS;
         } else if (s.contains("Cliffside Veins")) {
            return CLIFFSIDE_VEINS;
         } else if (s.contains("Rampart's Quarry")) {
            return RAMPART_QUARRY;
         } else if (s.contains("Royal Mines")) {
            return ROYAL_MINES;
         } else {
            return s.contains("Mine 15 Titanium Ore.") ? getRandom() : null;
         }
      }

      public static CommissionMacro.CommissionLocation getWorseCommissionFromString(String s) {
         if (s.contains("Ice Walker")) {
            return ICE_WALKERS;
         } else {
            return s.contains("Goblin") ? GOBLIN : null;
         }
      }

      public static CommissionMacro.CommissionLocation getRandom() {
         return (CommissionMacro.CommissionLocation)CommissionMacro.mithrils.get(Utils.random.nextInt(CommissionMacro.mithrils.size()));
      }

      public void pathfindToPos() {
         AOTVMovement.tpToRandom(this.requiredPath, this.randomPath, () -> {
            if (Refuel.drillNPC == null) {
               Refuel.findDrillNPC();
            }

            if (CommissionMacro.king == null) {
               CommissionMacro.findKing();
            }

         });
      }

      public int getTool() {
         switch(this) {
         case GOBLIN:
            return PlayerUtil.checkHotBarForEtherwarp();
         case ICE_WALKERS:
            int pickaxe = PlayerUtil.getPickaxe();
            return pickaxe == -1 ? PlayerUtil.checkHotBarForEtherwarp() : pickaxe;
         default:
            return PlayerUtil.getMiningTool();
         }
      }
   }
}
