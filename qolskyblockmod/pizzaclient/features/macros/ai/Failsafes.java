package qolskyblockmod.pizzaclient.features.macros.ai;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroBuilder;
import qolskyblockmod.pizzaclient.features.macros.builder.MacroState;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.SBInfo;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.remote.DiscordWebhook;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.FailsafeRotater;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class Failsafes {
   public static final List<String> messages = new ArrayList(Arrays.asList("hi?", "wtf", "what", "???", "lol wtf?", "uhm what?", "huh??"));
   public static final int TIMEOUT = 1800000;

   public static void checkForCaptchas() {
      if (SBInfo.inSkyblock) {
         int i = 0;

         while(true) {
            if (i >= 8) {
               Utils.playOrbSound();
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Captcha!!"));
               break;
            }

            ItemStack item = PizzaClient.mc.field_71439_g.field_71071_by.field_70462_a[i];
            if (item == null || item.func_77973_b() != Items.field_151148_bJ) {
               return;
            }

            ++i;
         }
      }

   }

   public static void playerInRange() {
      if (PizzaClient.config.stopWhenNearPlayer && MacroBuilder.currentMacro.applyPlayerFailsafes()) {
         Iterator var0 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

         Entity entity;
         String name;
         do {
            do {
               do {
                  do {
                     do {
                        do {
                           if (!var0.hasNext()) {
                              return;
                           }

                           entity = (Entity)var0.next();
                        } while(!(entity instanceof EntityOtherPlayerMP));

                        name = entity.func_145748_c_().func_150254_d().trim();
                     } while(!name.startsWith("§r§"));
                  } while(name.startsWith("§r§f"));
               } while(StringUtils.func_76338_a(name).equals("Goblin"));
            } while(entity.func_82150_aj() && !hasSorrow((EntityOtherPlayerMP)entity));
         } while(!(PlayerUtil.getPositionEyes().func_72436_e(new Vec3(entity.field_70165_t, entity.field_70163_u + 1.6200000047683716D, entity.field_70161_v)) < (double)(PizzaClient.config.stopWhenNearPlayerRange * PizzaClient.config.stopWhenNearPlayerRange)));

         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > Found player nearby, warping out. Player name: " + name));
         disable();
         unpressMovement();
         PizzaClient.rotater = null;
         MacroBuilder.currentMacro.enqueueFailsafeThread(() -> {
            PizzaClient.mc.field_71439_g.func_71165_d("/hub");
            Utils.sleep(5000 + PizzaClient.config.stopWhenNearPlayerTimeout * 1000);
            reEnable();
         });
         writeToWebhook("Player Detection (Name: " + StringUtils.func_76338_a(name) + ")");
      }
   }

   public static void unpressMovement() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
   }

   public static void checkBedrockBox() {
      if (PizzaClient.config.bedrockBoxFailsafe && MacroBuilder.currentMacro.applyBedrockFailsafe() && PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u - 1.0D, PizzaClient.mc.field_71439_g.field_70161_v)).func_177230_c() == Blocks.field_150357_h) {
         disable();
         MacroBuilder.currentMacro.enqueueFailsafeThread(() -> {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Found bedrock box, applying failsafe."));
            sleepAndStop();
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
            Utils.sleep(1000 + Utils.random.nextInt(400));
            int currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getAdjacentHotbarSlot();
            sendRandomMessage();
            warpBackAndCooldown();
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
            Utils.sleep(3000);
            reEnable();
         });
         writeToWebhook("Bedrock Box");
      }

   }

   public static void onPacketPosLook(float yawIn) {
      if (MacroBuilder.currentMacro.applyFailsafes() && MacroBuilder.currentMacro.applyRotationFailsafe()) {
         float yaw = PizzaClient.mc.field_71439_g.field_70177_z;
         float pitch = PizzaClient.mc.field_71439_g.field_70125_A;
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Detected rotation packet, applying failsafe. Rotated yaw: " + yawIn));
         disable();
         MacroBuilder.currentMacro.enqueueFailsafeThread(() -> {
            if (PizzaClient.rotater != null) {
               Utils.sleep(250 + Utils.random.nextInt(150));
            } else {
               Utils.sleep(500 + Utils.random.nextInt(250));
            }

            PizzaClient.rotater = null;
            Utils.sleep(100 + Utils.random.nextInt(50));
            unpressMovement();
            Utils.sleep(1000 + Utils.random.nextInt(200));
            float goalYaw = MathHelper.func_76142_g(yaw - PizzaClient.mc.field_71439_g.field_70177_z);
            float goalPitch = MathHelper.func_76142_g(pitch - PizzaClient.mc.field_71439_g.field_70125_A);
            (new Rotater(goalYaw, goalPitch)).rotate();
            Utils.sleep(10000 + Utils.random.nextInt(3000));
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "re-enabling the macro!"));
            MacroBuilder.updatePosition();
         });
         writeToWebhook("Received A Rotation Packet");
      }

   }

   public static void onChangePosition() {
      if (PizzaClient.config.positionChangeFailsafe && MacroBuilder.currentMacro.applyFailsafes() && MacroBuilder.currentMacro.applyPositionFailsafe()) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Detected position change, applying failsafe"));
         disable();
         MacroBuilder.currentMacro.enqueueFailsafeThread(() -> {
            sleepAndStop(3000);
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
            Utils.sleep(1000 + Utils.random.nextInt(400));
            int currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.getAdjacentHotbarSlot();
            Utils.sleep(2000);
            warpBackAndCooldown();
            PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
            Utils.sleep(3000);
            reEnable();
         });
         writeToWebhook("Player Changed Position");
      }

   }

   public static void reEnable() {
      if (!PizzaClient.config.disableOnWorldLoad) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "re-enabling the macro!"));
         MacroBuilder.updatePosition();
      } else {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Not re-enabling the macro since \"Disable Macro On Switch World\" is enabled."));
      }

   }

   private static boolean hasSorrow(EntityOtherPlayerMP entity) {
      ItemStack[] var1 = entity.field_71071_by.field_70460_b;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ItemStack item = var1[var3];
         if (item != null) {
            Item item1 = item.func_77973_b();
            if (item1 == Items.field_151029_X || item1 == Items.field_151022_W || item1 == Items.field_151023_V || item1 == Items.field_151020_U) {
               return true;
            }
         }
      }

      return false;
   }

   private static void sleepAndStop() {
      sleepAndStop(4000);
   }

   private static void sleepAndStop(int rotationAmt) {
      if (PizzaClient.rotater == null) {
         Utils.sleep(500 + Utils.random.nextInt(300));
      } else {
         Utils.sleep(300 + Utils.random.nextInt(150));
         PizzaClient.rotater = null;
         Utils.sleep(50 + Utils.random.nextInt(25));
      }

      unpressMovement();
      Utils.sleep(300 + Utils.random.nextInt(150));
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
      Utils.sleep(400 + Utils.random.nextInt(100));
      (new FailsafeRotater(rotationAmt)).rotate();

      while(Rotater.rotating) {
         Utils.sleep(1);
      }

      Utils.sleep(1500 + Utils.random.nextInt(500));
   }

   private static void sneak() {
      Utils.sleep(1000 + Utils.random.nextInt(400));
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
      Utils.sleep(100 + Utils.random.nextInt(50));
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
      Utils.sleep(110 + Utils.random.nextInt(60));
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
      Utils.sleep(100 + Utils.random.nextInt(50));
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
      Utils.sleep(400 + Utils.random.nextInt(100));
   }

   private static void sendRandomMessage() {
      Utils.sleep(2400 + Utils.random.nextInt(500));
      String msg = "/ac " + (String)messages.get(Utils.random.nextInt(messages.size()));
      PizzaClient.mc.field_71439_g.func_71165_d(msg);
      Utils.sleep(300 + Utils.random.nextInt(150));
   }

   private static void warpBackAndCooldown() {
      Utils.sleep(3300 + Utils.random.nextInt(1000));
      PizzaClient.mc.field_71439_g.func_71165_d("/hub");
      timeout();
   }

   public static void disable() {
      Utils.playOrbSound();
      MacroBuilder.currentMacro.terminateIfAlive();
      MacroBuilder.state = MacroState.FAILSAFE;
   }

   public static void writeToWebhook(String reason) {
      if (PizzaClient.config.failsafeWebhook) {
         if (!PizzaClient.config.failsafeWebhookName.startsWith("https://discord.com")) {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Invalid webhook name."));
            return;
         }

         (new Thread(() -> {
            try {
               DiscordWebhook webhook = new DiscordWebhook(PizzaClient.config.failsafeWebhookName);
               webhook.setUsername("PizzaClient Failsafe");
               if (PizzaClient.config.failsafeWebhookPing) {
                  webhook.setContent("@everyone");
               }

               webhook.addEmbed((new DiscordWebhook.EmbedObject()).setTitle("Failsafe Activated").setDescription(Utils.getFormattedDate()).addField("Username", PizzaClient.username, true).addField("Cause", reason, true).setColor(Color.CYAN));
               webhook.execute();
            } catch (Exception var2) {
               var2.printStackTrace();
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to write to the webhook. See logs for more info."));
            }

         }, "Failsafe Webhook")).start();
      }

   }

   public static void writeToWebhookAndPing(String reason) {
      if (PizzaClient.config.failsafeWebhook) {
         if (!PizzaClient.config.failsafeWebhookName.startsWith("https://discord.com")) {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Invalid webhook name."));
            return;
         }

         (new Thread(() -> {
            try {
               DiscordWebhook webhook = new DiscordWebhook(PizzaClient.config.failsafeWebhookName);
               webhook.setUsername("PizzaClient Failsafe");
               webhook.setContent("@everyone");
               webhook.addEmbed((new DiscordWebhook.EmbedObject()).setTitle("Failsafe Activated (IMPORTANT)").setDescription(Utils.getFormattedDate()).addField("Username", PizzaClient.username, true).addField("Cause", reason, true).setColor(Color.RED));
               webhook.execute();
            } catch (Exception var2) {
               var2.printStackTrace();
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to write to the webhook. See logs for more info."));
            }

         }, "Important Failsafe Webhook")).start();
      }

   }

   public static void timeout() {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Disabling the macros, will re-enable in 30 minutes to make sure that the admin has left."));
      Utils.sleep(1800000);
   }

   public static void timeout(int timeout) {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Disabling the macros, will re-enable after a while to make sure that the admin has left."));
      Utils.sleep(timeout);
   }

   private static String getScreenshotName() {
      return "PizzaClient Failsafe, at " + Utils.DATE_FORMAT.format(new Date());
   }
}
