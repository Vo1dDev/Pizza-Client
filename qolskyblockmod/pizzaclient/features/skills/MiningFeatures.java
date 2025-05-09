package qolskyblockmod.pizzaclient.features.skills;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.RenderLivingEvent.Pre;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.BlockChangeEvent;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiElement;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiLocation;
import qolskyblockmod.pizzaclient.core.guioverlay.GuiManager;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.graphics.FontUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class MiningFeatures {
   private static boolean sayCoordsBal = true;
   protected static boolean sayCoordsFairyGrotto = true;
   private static boolean sayCoordsCorleone = true;
   public static boolean foundCorleone = false;
   protected static final ArrayList<String> miningCoords = new ArrayList();

   public MiningFeatures() {
      new MiningFeatures.MiningElement();
   }

   @SubscribeEvent(
      receiveCanceled = true
   )
   public void onRenderEntity(Pre<EntityLivingBase> event) {
      if (PizzaClient.location == Locations.CHOLLOWS || PizzaClient.location == Locations.DWARVENMINES) {
         double x = event.entity.field_70165_t;
         double y = event.entity.field_70163_u;
         double z = event.entity.field_70161_v;
         if (event.entity instanceof EntityArmorStand) {
            EntityArmorStand entity = (EntityArmorStand)event.entity;
            if (!entity.func_145818_k_()) {
               return;
            }

            String entityName = StringUtils.func_76338_a(entity.func_95999_t());
            if (PizzaClient.config.balEsp && entityName.equals("[Lv100] Bal ???❤")) {
               RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 4.0D, y - 10.0D, z - 4.0D, x + 4.0D, y, z + 4.0D), Color.RED, 5.0F);
               if (sayCoordsBal) {
                  Utils.playSound("random.orb", 0.5D);
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Bal in: " + EnumChatFormatting.RED + "X = " + (int)x + ", Y = " + (int)y + ", Z = " + (int)z));
                  miningCoords.add("§cBal: §ax " + (int)x + ", y " + (int)y + ", z " + (int)z);
                  sayCoordsBal = false;
               }

               return;
            }

            if (PizzaClient.config.butterflyEsp && entityName.startsWith("[Lv100] Butterfly ")) {
               RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.35D, y + 0.75D, z - 0.35D, x + 0.35D, y, z + 0.35D), new Color(255, 100, 255), 2.0F);
               if (sayCoordsFairyGrotto) {
                  Utils.playSound("random.orb", 0.5D);
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Fairy Grotto in: " + EnumChatFormatting.RED + "X = " + (int)x + ", Y = " + (int)y + ", Z = " + (int)z));
                  miningCoords.add("§dFairy Grotto: §ax " + (int)x + ", y " + (int)y + ", z " + (int)z);
                  sayCoordsFairyGrotto = false;
               }

               return;
            }

            if (PizzaClient.config.corleonePing && entityName.startsWith("[Lv200] Boss Corleone ") && sayCoordsCorleone) {
               Utils.playSound("random.orb", 0.5D);
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Boss Corleone in: " + EnumChatFormatting.RED + "X = " + (int)x + ", Y = " + (int)y + ", Z = " + (int)z));
               miningCoords.add("§bBoss Corleone: §ax " + (int)x + ", y " + (int)y + ", z " + (int)z);
               sayCoordsCorleone = false;
            }
         } else if (event.entity instanceof EntityOtherPlayerMP) {
            String entityName = event.entity.func_70005_c_();
            if (entityName.equals("Team Treasurite") && PizzaClient.config.teamTresuriteEsp) {
               RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.5D, y + 2.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), new Color(255, 255, 51), 2.0F);
            }

            if (PizzaClient.config.goblinEsp) {
               if (entityName.startsWith("Weakling")) {
                  RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.5D, y + 2.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), new Color(130, 67, 0), 2.0F);
                  return;
               }

               if (entityName.startsWith("Goblin")) {
                  RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.5D, y + 2.0D, z - 0.5D, x + 0.5D, y, z + 0.5D), new Color(130, 67, 0), 2.0F);
               }
            }

            if (PizzaClient.config.hideGoldenGoblin && entityName.startsWith("Goblin") && Utils.isGoldenGoblin((EntityOtherPlayerMP)event.entity)) {
               PizzaClient.mc.field_71441_e.func_72900_e(event.entity);
            }
         } else if (event.entity instanceof EntitySlime) {
            if (event.entity instanceof EntityMagmaCube) {
               if (PizzaClient.config.yogEsp) {
                  RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.8D, y + 1.66D, z - 0.8D, x + 0.8D, y, z + 0.8D), new Color(175, 34, 34), 2.0F);
               }
            } else if (PizzaClient.config.sludgeEsp) {
               RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.8D, y + 1.66D, z - 0.8D, x + 0.8D, y, z + 0.8D), new Color(50, 200, 50), 2.0F);
            }
         } else if (event.entity instanceof EntityIronGolem && PizzaClient.config.ironGolemEsp) {
            RenderUtil.drawOutlinedEsp(new AxisAlignedBB(x - 0.6D, y + 2.5D, z - 0.6D, x + 0.6D, y, z + 0.6D), new Color(0, 0, 111), 2.0F);
         }
      }

   }

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (PizzaClient.location == Locations.CHOLLOWS && PizzaClient.config.gemstoneScanner) {
         Iterator var2 = WorldScanner.stainedGlass.entrySet().iterator();

         Entry map;
         while(var2.hasNext()) {
            map = (Entry)var2.next();
            RenderUtil.drawOutlinedEsp((BlockPos)map.getKey(), (Color)map.getValue(), (float)PizzaClient.config.gemstoneEspSize / 16.0F);
         }

         if (PizzaClient.config.glassPanesEsp) {
            var2 = WorldScanner.glassPanes.entrySet().iterator();

            while(var2.hasNext()) {
               map = (Entry)var2.next();
               RenderUtil.drawOutlinedEsp((BlockPos)map.getKey(), (Color)map.getValue(), (float)PizzaClient.config.pinkGlassPanesEspSize / 16.0F);
            }
         }
      }

   }

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      sayCoordsBal = true;
      sayCoordsFairyGrotto = true;
      sayCoordsCorleone = true;
      foundCorleone = false;
      miningCoords.clear();
      WorldScanner.stainedGlass.clear();
      WorldScanner.glassPanes.clear();
   }

   @SubscribeEvent
   public void onBlockChange(BlockChangeEvent event) {
      if (PizzaClient.config.gemstoneScanner && PizzaClient.location == Locations.CHOLLOWS) {
         Block block = event.oldState.func_177230_c();
         if (block == Blocks.field_150399_cn) {
            WorldScanner.stainedGlass.remove(event.pos);
         } else if (block == Blocks.field_150397_co) {
            WorldScanner.glassPanes.remove(event.pos);
         }
      }

   }

   public static class MiningElement extends GuiElement {
      private static final String[] locations = new String[]{"§dFairy Grotto: §ax 1000, y 256, z 1000", "§bBoss Corleone: §ax 1000, y 256, z 1000", "§cBal: §ax 1000, y 256, z 1000"};

      public MiningElement() {
         super("Mining List", new GuiLocation(100, 30));
         GuiManager.registerElement(this);
      }

      public void render() {
         if (this.getToggled()) {
            for(int i = 0; i < MiningFeatures.miningCoords.size(); ++i) {
               FontUtil.drawString((String)MiningFeatures.miningCoords.get(i), this.getActualX(), this.getActualY() + (float)(i * PizzaClient.mc.field_71466_p.field_78288_b), 16777215);
            }
         }

      }

      public void demoRender() {
         for(int i = 0; i < locations.length; ++i) {
            FontUtil.drawString(locations[i], this.getActualX(), this.getActualY() + (float)(i * PizzaClient.mc.field_71466_p.field_78288_b), 16777215);
         }

      }

      public boolean getToggled() {
         return PizzaClient.config.coordsList && PizzaClient.location == Locations.CHOLLOWS;
      }

      public int getHeight() {
         return PizzaClient.mc.field_71466_p.field_78288_b * 3;
      }

      public int getWidth() {
         return PizzaClient.mc.field_71466_p.func_78256_a("Boss Corleone: x -1000, y 200, z -1000 ");
      }
   }
}
