package qolskyblockmod.pizzaclient.features.dungeons;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogDensity;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;

public class DungeonFeatures {
   public static boolean shouldLividsSpawn = false;
   private static Color c;
   public static String lividName;
   public static long blindnessDuration;
   private final Set<String> bloodMobs = new HashSet(Arrays.asList("Revoker", "Psycho", "Reaper", "Cannibal", "Mute", "Ooze", "Putrid", "Freak", "Leech", "Tear", "Parasite", "Flamer", "Skull", "Mr. Dead", "Vader", "Frost", "Walker", "WanderingSoul"));
   private final Set<Entity> killedMobs = new HashSet();

   @SubscribeEvent(
      priority = EventPriority.HIGHEST,
      receiveCanceled = true
   )
   public void onRenderLiving(Pre<EntityLivingBase> event) {
      if (PizzaClient.location == Locations.DUNGEON) {
         String name;
         if (!(event.entity instanceof EntityArmorStand)) {
            if (event.entity instanceof EntityOtherPlayerMP) {
               name = event.entity.func_70005_c_();
               if (PizzaClient.config.bloodTriggerBot) {
                  Iterator var5 = this.bloodMobs.iterator();

                  while(var5.hasNext()) {
                     String s = (String)var5.next();
                     if (name.contains(s) && !this.killedMobs.contains(event.entity) && VecUtil.isFacingAABB(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u + 2.0D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u - 1.0D, event.entity.field_70161_v + 0.5D), 25.0F)) {
                        KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74312_F.func_151463_i());
                        this.killedMobs.add(event.entity);
                        return;
                     }
                  }
               }

               if (PizzaClient.config.alwaysAimAtSpiritBear && name.startsWith("Spirit Bear")) {
                  FakeRotation.rotationVec = event.entity.field_70163_u % 1.0D == 0.0D ? event.entity.func_174824_e(1.0F) : new Vec3(event.entity.field_70165_t, 69.5D, event.entity.field_70161_v);
                  return;
               }

               if (name.startsWith("Shadow Assassin")) {
                  if (PizzaClient.config.showHiddenMobs && event.entity.func_82150_aj()) {
                     event.entity.func_82142_c(false);
                  }

                  if (PizzaClient.config.starredMobsEsp) {
                     RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u + 2.0D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u, event.entity.field_70161_v + 0.5D), new Color(120, 0, 15), 5.0F);
                  }
               }
            } else if (event.entity instanceof EntityEnderman) {
               if (PizzaClient.config.showHiddenMobs && event.entity.func_82150_aj()) {
                  event.entity.func_82142_c(false);
               }
            } else if (event.entity instanceof EntityBat && PizzaClient.config.batEsp && event.entity.func_110138_aP() == 100.0F) {
               RenderUtil.drawFilledEsp(event.entity.func_174813_aQ(), PizzaClient.config.batEspColor);
            }
         } else {
            name = StringUtils.func_76338_a(event.entity.func_95999_t());
            if (PizzaClient.config.starredMobsEsp && name.startsWith("✯ ") && name.contains("❤")) {
               Vec3 entityPos = event.entity.func_174824_e(1.0F);
               if (Utils.getXandZDistanceSquared(entityPos.field_72450_a, entityPos.field_72449_c) < (double)(PizzaClient.config.starredMobsEspRange * PizzaClient.config.starredMobsEspRange)) {
                  if (!name.contains("Lurker") && !name.contains("Dreadlord") && !name.contains("Souleater") && !name.contains("Zombie") && !name.contains("Skeleton") && !name.contains("Skeletor") && !name.contains("Sniper") && !name.contains("Super Archer") && !name.contains("Spider") && !name.contains("Fels") && !name.contains("Withermancer")) {
                     if (!name.contains("Shadow Assassin")) {
                        RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u - 2.0D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u, event.entity.field_70161_v + 0.5D), new Color(120, 0, 15), 4.5F);
                     }
                  } else {
                     RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u - 2.0D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u, event.entity.field_70161_v + 0.5D), PizzaClient.config.starredMobsEspColor, 3.0F);
                  }
               }

               return;
            }

            if (PizzaClient.config.dungeonKeyEsp) {
               if (name.equals("Wither Key")) {
                  RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u + 2.4D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u + 0.9D, event.entity.field_70161_v + 0.5D), new Color(20, 20, 20), 3.0F);
                  return;
               }

               if (name.equals("Blood Key")) {
                  RenderUtil.drawOutlinedEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u + 2.4D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u + 0.9D, event.entity.field_70161_v + 0.5D), new Color(180, 0, 0), 3.0F);
                  return;
               }
            }

            if (PizzaClient.config.hideSuperboom && (name.equals("Revive Stone") || name.startsWith("Superboom TNT"))) {
               PizzaClient.mc.field_71441_e.func_72900_e(event.entity);
               return;
            }

            if (lividName != null && event.entity.func_70005_c_().startsWith(lividName)) {
               if (PizzaClient.config.lividAura) {
                  FakeRotation.rotationVec = event.entity.func_174824_e(1.0F);
               }

               if (PizzaClient.config.lividEsp) {
                  RenderUtil.drawFilledEsp(new AxisAlignedBB(event.entity.field_70165_t - 0.5D, event.entity.field_70163_u - 2.0D, event.entity.field_70161_v - 0.5D, event.entity.field_70165_t + 0.5D, event.entity.field_70163_u, event.entity.field_70161_v + 0.5D), c);
               }
            }
         }

      }
   }

   @SubscribeEvent(
      receiveCanceled = true
   )
   public void onEntityJoin(EntityJoinWorldEvent event) {
      if (event.entity instanceof EntityOtherPlayerMP && PizzaClient.location == Locations.DUNGEON && PizzaClient.config.spiritBearAura && event.entity.func_70005_c_().startsWith("Spirit Bear")) {
         FakeRotation.leftClick = true;
         FakeRotation.rotationVec = new Vec3(event.entity.field_70165_t, 69.5D, event.entity.field_70161_v);
      }

   }

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      lividName = null;
      blindnessDuration = 0L;
      this.killedMobs.clear();
   }

   @SubscribeEvent(
      priority = EventPriority.HIGHEST,
      receiveCanceled = true
   )
   public void onFogDensity(FogDensity event) {
      if (PizzaClient.mc.field_71441_e != null) {
         if (PizzaClient.mc.field_71439_g.func_70644_a(Potion.field_76440_q) && (PizzaClient.config.lividEsp || PizzaClient.config.lividAura) && shouldLividsSpawn) {
            if (blindnessDuration == 0L) {
               blindnessDuration = System.currentTimeMillis();
            }

            if (System.currentTimeMillis() - blindnessDuration >= 600L) {
               getLivid();
            }
         }

         if (PizzaClient.config.antiBlindness) {
            event.density = 0.0F;
            event.setCanceled(true);
         }

      }
   }

   private static void getLivid() {
      EnumChatFormatting chatColor;
      switch((EnumDyeColor)PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(6, 109, 43)).func_177229_b(BlockStainedGlass.field_176547_a)) {
      case WHITE:
         chatColor = EnumChatFormatting.WHITE;
         c = new Color(255, 250, 250);
         break;
      case PINK:
         chatColor = EnumChatFormatting.LIGHT_PURPLE;
         c = Color.MAGENTA;
         break;
      case RED:
         chatColor = EnumChatFormatting.RED;
         c = new Color(200, 0, 0);
         break;
      case SILVER:
         chatColor = EnumChatFormatting.GRAY;
         c = new Color(180, 180, 180);
         break;
      case GRAY:
         chatColor = EnumChatFormatting.GRAY;
         c = new Color(100, 100, 100);
         break;
      case GREEN:
         chatColor = EnumChatFormatting.DARK_GREEN;
         c = new Color(34, 100, 34);
         break;
      case LIME:
         chatColor = EnumChatFormatting.GREEN;
         c = new Color(0, 245, 0);
         break;
      case BLUE:
         chatColor = EnumChatFormatting.BLUE;
         c = new Color(0, 0, 225);
         break;
      case PURPLE:
         chatColor = EnumChatFormatting.DARK_PURPLE;
         c = new Color(128, 0, 160);
         break;
      case YELLOW:
         chatColor = EnumChatFormatting.YELLOW;
         c = new Color(245, 245, 0);
         break;
      case MAGENTA:
         chatColor = EnumChatFormatting.LIGHT_PURPLE;
         c = new Color(240, 0, 240);
         break;
      default:
         c = null;
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.DARK_RED + "ERROR: " + EnumChatFormatting.RED + "Could not find the glass color! Please report this. The unknown Glass Color is: " + EnumChatFormatting.GOLD + ((EnumDyeColor)PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(205, 109, 242)).func_177229_b(BlockStainedGlass.field_176547_a)).name().toLowerCase()));
         return;
      }

      Iterator var1 = PizzaClient.mc.field_71441_e.func_72910_y().iterator();

      while(var1.hasNext()) {
         Entity entity = (Entity)var1.next();
         if (entity.func_70005_c_().startsWith(chatColor + "﴾ " + chatColor + "§lLivid")) {
            lividName = entity.func_70005_c_();
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > Real Livid: " + lividName));
            shouldLividsSpawn = false;
         }
      }

   }

   public static void clipGhostBlocks() {
      List<BlockPos> ghostBlockCoords = new ArrayList(Arrays.asList(new BlockPos(299, 168, 243), new BlockPos(299, 168, 244), new BlockPos(299, 168, 246), new BlockPos(299, 168, 247), new BlockPos(300, 168, 247), new BlockPos(300, 168, 246), new BlockPos(300, 168, 244), new BlockPos(300, 168, 243), new BlockPos(298, 168, 247), new BlockPos(298, 168, 246), new BlockPos(298, 168, 244), new BlockPos(298, 168, 243), new BlockPos(287, 167, 240), new BlockPos(288, 167, 240), new BlockPos(289, 167, 240), new BlockPos(290, 167, 240), new BlockPos(291, 167, 240), new BlockPos(292, 167, 240), new BlockPos(293, 167, 240), new BlockPos(294, 167, 240), new BlockPos(295, 167, 240), new BlockPos(290, 167, 239), new BlockPos(291, 167, 239), new BlockPos(292, 167, 239), new BlockPos(293, 167, 239), new BlockPos(294, 167, 239), new BlockPos(295, 167, 239), new BlockPos(290, 166, 239), new BlockPos(291, 166, 239), new BlockPos(292, 166, 239), new BlockPos(293, 166, 239), new BlockPos(294, 166, 239), new BlockPos(295, 166, 239), new BlockPos(290, 166, 240), new BlockPos(291, 166, 240), new BlockPos(292, 166, 240), new BlockPos(293, 166, 240), new BlockPos(294, 166, 240), new BlockPos(295, 166, 240)));
      Iterator var1 = ghostBlockCoords.iterator();

      while(var1.hasNext()) {
         BlockPos pos = (BlockPos)var1.next();
         PizzaClient.mc.field_71441_e.func_175698_g(new BlockPos(pos.func_177958_n() - 199, pos.func_177956_o(), pos.func_177952_p() - 199));
      }

   }
}
