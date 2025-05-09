package qolskyblockmod.pizzaclient.features.keybinds;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.commands.AutoPetCommand;
import qolskyblockmod.pizzaclient.core.events.ChestBackgroundDrawnEvent;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiElement;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiLocation;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiManager;
import qolskyblockmod.pizzaclient.features.dungeons.AutoSpiritLeap;
import qolskyblockmod.pizzaclient.features.macros.ai.Failsafes;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.MithrilMarkers;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.graphics.FontUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class KeybindFeatures {
   public static int autoWardrobePhase = 3;
   private int ticks;
   private long lastTermClick;
   public static final Set<Block> notGhostBlockable;
   private static boolean rightClickAotv;

   public KeybindFeatures() {
      new KeybindFeatures.ToggleSprintElement();
   }

   @SubscribeEvent
   public void onRenderGui(ChestBackgroundDrawnEvent event) {
      if (event.displayName.endsWith("Pets")) {
         if (autoWardrobePhase == 0) {
            PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, 48, 0, 0, PizzaClient.mc.field_71439_g);
            PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c + 1, 32, 0, 0, PizzaClient.mc.field_71439_g);
            if (PizzaClient.config.autoWardrobeSlot != 0) {
               if (PizzaClient.config.autoWardrobeSlot > 9) {
                  PlayerUtil.windowClick(2, 53, 0, 0);
                  PlayerUtil.windowClick(3, PizzaClient.config.autoWardrobeSlot + 26, 0, 0);
               } else {
                  PlayerUtil.windowClick(2, PizzaClient.config.autoWardrobeSlot + 35, 0, 0);
               }

               PizzaClient.mc.field_71439_g.func_71053_j();
            }

            autoWardrobePhase = 1;
         }

         if (AutoPetCommand.clickSlot != null && ((Slot)event.slots.get(53)).func_75211_c() != null) {
            int clickSlot = 0;

            try {
               clickSlot = Integer.parseInt(AutoPetCommand.clickSlot);
            } catch (Exception var7) {
               Iterator var4 = event.slots.iterator();

               while(var4.hasNext()) {
                  Slot slot = (Slot)var4.next();
                  ItemStack stack = slot.func_75211_c();
                  if (stack != null && StringUtils.func_76338_a(stack.func_82833_r()).toLowerCase().contains(AutoPetCommand.clickSlot)) {
                     clickSlot = slot.getSlotIndex();
                     break;
                  }
               }
            }

            if (clickSlot == 0) {
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Found no pet with the name \"" + AutoPetCommand.clickSlot + "\"."));
            } else {
               PizzaClient.mc.field_71442_b.func_78753_a(PizzaClient.mc.field_71439_g.field_71070_bA.field_75152_c, clickSlot, 0, 0, PizzaClient.mc.field_71439_g);
            }

            AutoPetCommand.clickSlot = null;
         }
      }

   }

   @SubscribeEvent
   public void onInputKey(KeyInputEvent event) {
      if (PizzaClient.keyBindings[0].func_151468_f()) {
         PlayerUtil.findPickonimbus();
         if (PizzaClient.config.rightClickSpammerMode == 3) {
            PlayerUtil.rightClick(PizzaClient.config.clickAmount);
         }
      } else if (PizzaClient.keyBindings[2].func_151468_f()) {
         PizzaClient.mc.field_71439_g.func_71165_d("/pets");
         autoWardrobePhase = 0;
      } else if (PizzaClient.keyBindings[3].func_151468_f()) {
         PizzaClient.config.toggleSprint = !PizzaClient.config.toggleSprint;
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_151444_V.func_151463_i(), PizzaClient.config.toggleSprint);
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "ToggleSprint is now " + Utils.getColouredBoolean(PizzaClient.config.toggleSprint)));
      } else if (PizzaClient.keyBindings[4].func_151468_f()) {
         PizzaClient.mc.func_147108_a(PizzaClient.config.gui());
      } else if (PizzaClient.keyBindings[5].func_151468_f()) {
         MacroKeybind.updateToggle();
      } else if (PizzaClient.keyBindings[6].func_151468_f()) {
         MacroBuilder.onKey();
      } else if (PizzaClient.keyBindings[7].func_151468_f()) {
         MithrilMarkers.onKey();
      } else if (PizzaClient.location == Locations.DUNGEON) {
         if (PizzaClient.config.sneakAotv && PlayerUtil.isHoldingAotv() && PizzaClient.mc.field_71474_y.field_74311_E.func_151468_f()) {
            rightClickAotv = true;
            return;
         }

         if (PizzaClient.config.autoLeapDoor && AutoSpiritLeap.leapName != null) {
            ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
            if (held != null && held.func_82833_r().contains("Spirit Leap") && PizzaClient.mc.field_71474_y.field_74370_x.func_151468_f() && PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, held)) {
               PizzaClient.mc.field_71460_t.field_78516_c.func_78445_c();
               AutoSpiritLeap.leapToDoor = true;
            }
         }
      }

   }

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (rightClickAotv) {
         KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
         rightClickAotv = false;
      }

      if (PizzaClient.keyBindings[0].func_151470_d()) {
         ItemStack itemStack;
         switch(PizzaClient.config.rightClickSpammerMode) {
         case 1:
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
            break;
         case 2:
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
         case 3:
         default:
            break;
         case 4:
            itemStack = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
            if (itemStack != null) {
               PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, itemStack);
            }
            break;
         case 5:
            itemStack = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
            if (itemStack != null) {
               PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, itemStack);
            }
         }
      } else if (PizzaClient.keyBindings[8].func_151470_d()) {
         AnyItemWithAnything.use();
      }

      if (PizzaClient.mc.field_71462_r == null) {
         if (this.ticks % 4 == 0) {
            if (PizzaClient.config.captchaWarning) {
               Failsafes.checkForCaptchas();
            }

            if (PizzaClient.config.toggleSprint) {
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_151444_V.func_151463_i(), true);
            }

            this.ticks = 0;
         }

         ++this.ticks;
      }

   }

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (PizzaClient.mc.field_71476_x != null) {
         if (PizzaClient.keyBindings[0].func_151470_d() && PizzaClient.config.rightClickSpammerMode == 2) {
            PlayerUtil.rightClick();
         }

         if (PizzaClient.keyBindings[1].func_151470_d()) {
            switch(PizzaClient.config.fasterGhostblocks) {
            case 0:
               if (PizzaClient.mc.field_71476_x.func_178782_a() != null && !notGhostBlockable.contains(PizzaClient.mc.field_71441_e.func_180495_p(PizzaClient.mc.field_71476_x.func_178782_a()).func_177230_c())) {
                  PizzaClient.mc.field_71441_e.func_175698_g(PizzaClient.mc.field_71476_x.func_178782_a());
               }
               break;
            case 1:
               PlayerUtil.ghostBlock(6.0F);
               break;
            case 2:
               PlayerUtil.ghostBlockIgnoreInteractables(150.0F);
            }
         }
      }

      if (PizzaClient.config.terminatorClicker && PizzaClient.mc.field_71474_y.field_74313_G.func_151470_d()) {
         ItemStack held = PizzaClient.mc.field_71439_g.field_71071_by.func_70448_g();
         if (held != null) {
            String displayName = held.func_82833_r();
            if (displayName.contains("Terminator") || displayName.contains("Juju")) {
               if (this.lastTermClick == 0L) {
                  if (PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, held)) {
                     PizzaClient.mc.field_71460_t.field_78516_c.func_78445_c();
                  }

                  this.lastTermClick = System.currentTimeMillis();
                  return;
               }

               if (System.currentTimeMillis() - this.lastTermClick >= (long)PizzaClient.config.terminatorClickerDelay) {
                  if (PizzaClient.mc.field_71442_b.func_78769_a(PizzaClient.mc.field_71439_g, PizzaClient.mc.field_71441_e, held)) {
                     PizzaClient.mc.field_71460_t.field_78516_c.func_78445_c();
                  }

                  this.lastTermClick = System.currentTimeMillis() - this.lastTermClick % (long)PizzaClient.config.terminatorClickerDelay;
               }
            }
         }
      }

   }

   static {
      notGhostBlockable = new HashSet(Arrays.asList(Blocks.field_180410_as, Blocks.field_150467_bQ, Blocks.field_150461_bJ, Blocks.field_150324_C, Blocks.field_180412_aq, Blocks.field_150382_bo, Blocks.field_150483_bI, Blocks.field_150462_ai, Blocks.field_150486_ae, Blocks.field_180409_at, Blocks.field_150453_bW, Blocks.field_180402_cm, Blocks.field_150367_z, Blocks.field_150409_cd, Blocks.field_150381_bn, Blocks.field_150477_bB, Blocks.field_150460_al, Blocks.field_180411_ar, Blocks.field_150442_at, Blocks.field_150323_B, Blocks.field_150455_bV, Blocks.field_150441_bU, Blocks.field_150416_aS, Blocks.field_150413_aR, Blocks.field_150472_an, Blocks.field_150444_as, Blocks.field_150415_aT, Blocks.field_150447_bR, Blocks.field_150471_bO, Blocks.field_150430_aB, Blocks.field_180413_ao, Blocks.field_150355_j, Blocks.field_150353_l, Blocks.field_150358_i, Blocks.field_150356_k));
   }

   public static class ToggleSprintElement extends GuiElement {
      public ToggleSprintElement() {
         super("ToggleSprint", new GuiLocation(100, 10));
         GuiManager.registerElement(this);
      }

      public void render() {
         if (this.getToggled()) {
            float x = this.getActualX();
            float y = this.getActualY();
            GlStateManager.func_179139_a((double)this.scale, (double)this.scale, 1.0D);
            PizzaClient.mc.field_71466_p.func_175065_a(PizzaClient.config.customToggleSprintName, x, y, PizzaClient.config.customToggleSprintColor.getRGB(), true);
            GlStateManager.func_179152_a(1.0F / this.scale, 1.0F / this.scale, 1.0F);
         }

      }

      public void demoRender() {
         FontUtil.drawString(PizzaClient.config.customToggleSprintName, this.getActualX(), this.getActualY(), PizzaClient.config.customToggleSprintColor.getRGB());
      }

      public boolean getToggled() {
         return PizzaClient.config.toggleSprint;
      }

      public int getHeight() {
         return PizzaClient.mc.field_71466_p.field_78288_b;
      }

      public int getWidth() {
         return PizzaClient.mc.field_71466_p.func_78256_a(PizzaClient.config.customToggleSprintName);
      }
   }
}
