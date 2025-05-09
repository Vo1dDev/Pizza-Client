package qolskyblockmod.pizzaclient.features.macros.builder;

import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.features.macros.ai.Failsafes;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.FarmingMacro;
import qolskyblockmod.pizzaclient.features.macros.builder.macros.Macro;
import qolskyblockmod.pizzaclient.features.macros.farming.AutoF11Macro;
import qolskyblockmod.pizzaclient.features.macros.farming.CocoaBeanMacro;
import qolskyblockmod.pizzaclient.features.macros.farming.CropAura;
import qolskyblockmod.pizzaclient.features.macros.farming.SShapedMacro;
import qolskyblockmod.pizzaclient.features.macros.farming.SugarCaneMacro;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.CommissionMacro;
import qolskyblockmod.pizzaclient.features.macros.mining.dwarven.MithrilMacro;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.Nuker;
import qolskyblockmod.pizzaclient.features.macros.mining.nuker.PowderMacro;
import qolskyblockmod.pizzaclient.features.macros.misc.FishingMacro;
import qolskyblockmod.pizzaclient.features.macros.misc.ForagingMacro;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.Locations;

@SideOnly(Side.CLIENT)
public class MacroBuilder {
   public static final Macro[] macros = new Macro[10];
   public static final FarmingMacro[] farmingMacros = new FarmingMacro[10];
   public static Macro currentMacro;
   public static boolean toggled;
   public static Vec3 lastPos;
   public static MacroState state;

   public MacroBuilder() {
      this.registerFarmingMacro(new AutoF11Macro(), 1);
      this.registerFarmingMacro(new SugarCaneMacro(), 2);
      this.registerFarmingMacro(new SShapedMacro(), 3);
      this.registerFarmingMacro(new CocoaBeanMacro(), 4);
      this.registerMacro(new MithrilMacro(), 2);
      this.registerMacro(new ForagingMacro(), 3);
      this.registerMacro(new PowderMacro(), 4);
      this.registerMacro(new Nuker(), 5);
      this.registerMacro(new CropAura(), 6);
      this.registerMacro(new CommissionMacro(), 7);
      this.registerMacro(new FishingMacro(), 8);
   }

   private void registerMacro(Macro macro, int mode) {
      macros[mode] = macro;
   }

   private void registerFarmingMacro(FarmingMacro macro, int mode) {
      farmingMacros[mode] = macro;
   }

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (toggled) {
         if (PizzaClient.mc.field_71462_r != null) {
            unpressMovement();
            return;
         }

         if (state == null) {
            state = MacroState.ACTIVE;
         }

         switch(state) {
         case ACTIVE:
            if (currentMacro.miscThread.isAlive()) {
               state = MacroState.NEW_THREAD;
               return;
            }

            if (PlayerUtil.warpToSkyblock() || currentMacro.getLocation() != null && PizzaClient.location != currentMacro.getLocation()) {
               currentMacro.enqueueFailsafeThread(MacroBuilder::warpBack);
               return;
            }

            checkFailsafes();
            checkAndUpdatePosition();
            Failsafes.playerInRange();
            if (PizzaClient.config.sneakWhenUsingMacro) {
               KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
            }

            currentMacro.onTick();
            break;
         case NEW_THREAD:
            if (!currentMacro.miscThread.isAlive()) {
               state = MacroState.ACTIVE;
               return;
            }

            if (PlayerUtil.warpToSkyblock() || currentMacro.getLocation() != null && PizzaClient.location != currentMacro.getLocation()) {
               currentMacro.enqueueFailsafeThread(MacroBuilder::warpBack);
               return;
            }

            checkFailsafes();
            checkAndUpdatePosition();
            break;
         case FAILSAFE:
            if (!currentMacro.miscThread.isAlive()) {
               state = MacroState.ACTIVE;
            }
         }
      }

   }

   public static void onKey() {
      state = MacroState.ACTIVE;
      if (currentMacro != null) {
         if (currentMacro.miscThread == null) {
            currentMacro.miscThread = new Thread(() -> {
            });
         } else {
            currentMacro.terminateIfAlive();
         }
      }

      if (PizzaClient.config.macroKey != 1 && (PizzaClient.config.macroKey != 0 || PizzaClient.config.farmingMacroKey == 0)) {
         currentMacro = macros[PizzaClient.config.macroKey];
      } else {
         currentMacro = farmingMacros[PizzaClient.config.farmingMacroKey];
      }

      if (PizzaClient.location == Locations.DUNGEON) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Can't use macros in dungeons."));
         toggled = false;
      } else if (currentMacro == null) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "You need to change the macro key mode in order for the macro to work!"));
         toggled = false;
      } else {
         toggled = !toggled;
         currentMacro.onToggle(toggled);
         if (!toggled) {
            unpressMovement();
            PizzaClient.rotater = null;
            currentMacro.onDisable();
         } else {
            updatePosition();
            currentMacro.onMove();
            currentMacro.onTick();
         }

      }
   }

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      if (toggled) {
         if (state != MacroState.FAILSAFE) {
            currentMacro.terminateIfAlive();
            currentMacro.onDisable();
         }

         unpressMovement();
         if (PizzaClient.config.disableOnWorldLoad) {
            toggled = false;
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Detected world change, disabling the macro."));
            (new Thread(() -> {
               Failsafes.writeToWebhook("Detected World Change");
            })).start();
         }
      }

   }

   @SubscribeEvent(
      receiveCanceled = true
   )
   public void onOpenGui(GuiOpenEvent event) {
      if (event.gui instanceof GuiDisconnected && (toggled || PizzaClient.config.autoReconnect)) {
         this.reconnect();
         event.setCanceled(true);
      }

   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST
   )
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (toggled) {
         currentMacro.onRender();
      }

   }

   private static void unpressMovement() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
   }

   private void reconnect() {
      FMLClientHandler.instance().connectToServer(new GuiMultiplayer(new GuiMainMenu()), new ServerData("Hypixel", "mc.hypixel.net", false));
   }

   private static void warpBack() {
      Locations.warpToSb();
      if (currentMacro.getLocation() != null) {
         Utils.sleep(2000);
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Going back to " + currentMacro.getLocation().toString() + "..."));
         Failsafes.writeToWebhook("Warped Out");
         currentMacro.warpBack();
         Utils.sleep(1500);
      } else {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "No warp back for the current macro, disabling macro."));
         currentMacro.terminateIfAlive();
         toggled = false;
      }

   }

   public static void updatePosition() {
      lastPos = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
   }

   private static void checkFailsafes() {
      if (currentMacro.applyFailsafes()) {
         if (PizzaClient.config.optimalFailsafes) {
            PizzaClient.config.bedrockBoxFailsafe = currentMacro.applyBedrockFailsafe();
            PizzaClient.config.positionChangeFailsafe = currentMacro.applyPositionFailsafe();
            PizzaClient.config.rotationFailsafe = currentMacro.applyRotationFailsafe();
         }

         Failsafes.checkBedrockBox();
      }

   }

   private static void checkAndUpdatePosition() {
      if (lastPos == null) {
         lastPos = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
         currentMacro.onMove();
      } else if (lastPos.field_72450_a != PizzaClient.mc.field_71439_g.field_70165_t || lastPos.field_72449_c != PizzaClient.mc.field_71439_g.field_70161_v || lastPos.field_72448_b != PizzaClient.mc.field_71439_g.field_70163_u) {
         lastPos = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
         currentMacro.onMove();
      }

   }

   public static void disable() {
      currentMacro.terminateIfAlive();
      toggled = false;
   }
}
