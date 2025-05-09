package qolskyblockmod.pizzaclient.features.dungeons.f7;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.BlockChangeEvent;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.RenderUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;

public class FunnyDevice {
   private final Set<BlockPos> clickedItemFrames = new HashSet();
   private static final Map<BlockPos, Integer> requiredClicksForEntity = new HashMap();
   private int ticks = 1;
   private boolean foundPattern;
   private final List<BlockPos> simonSaysQueue = new ArrayList();
   public static final BlockPos simonSaysStart = new BlockPos(110, 121, 91);
   public static boolean clickedSimonSays;
   private long lastInteractTime;

   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
      if (PizzaClient.config.autoAlignment && this.foundPattern) {
         Iterator var2 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

         while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            if (entity instanceof EntityItemFrame) {
               EntityItemFrame itemFrame = (EntityItemFrame)entity;
               if (itemFrame.func_82335_i() != null && itemFrame.func_82335_i().func_77973_b() == Items.field_151032_g) {
                  BlockPos pos = new BlockPos(itemFrame.field_70165_t, itemFrame.field_70163_u, itemFrame.field_70161_v);
                  if (requiredClicksForEntity.containsKey(pos)) {
                     double x = itemFrame.field_70165_t;
                     double y = itemFrame.field_70163_u;
                     double z = itemFrame.field_70161_v;
                     RenderUtil.drawFilledBoxNoESP(new AxisAlignedBB(x - 0.0025D, y + 0.33D, z - 0.33D, x + 0.025D, y - 0.33D, z + 0.33D), this.clickedItemFrames.contains(pos) ? new Color(0, 200, 0) : new Color(200, 0, 0));
                  }
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (PizzaClient.location == Locations.DUNGEON && PizzaClient.mc.field_71462_r == null && PizzaClient.mc.field_71476_x != null) {
         if (PizzaClient.config.autoAlignment) {
            if (this.foundPattern && PizzaClient.mc.field_71476_x.field_72308_g instanceof EntityItemFrame) {
               EntityItemFrame itemFrame = (EntityItemFrame)PizzaClient.mc.field_71476_x.field_72308_g;
               if (itemFrame.func_82335_i() != null && itemFrame.func_82335_i().func_77973_b() == Items.field_151032_g) {
                  BlockPos itemFrameFixedPos = new BlockPos(itemFrame.field_70165_t, itemFrame.field_70163_u, itemFrame.field_70161_v);
                  if (!this.clickedItemFrames.contains(itemFrameFixedPos) && requiredClicksForEntity.containsKey(itemFrameFixedPos)) {
                     int requiredRotation = (Integer)requiredClicksForEntity.get(itemFrameFixedPos);
                     int currentRotation = itemFrame.func_82333_j();
                     if (currentRotation != requiredRotation) {
                        int clickAmount = currentRotation < requiredRotation ? requiredRotation - currentRotation : requiredRotation - currentRotation + 8;
                        PlayerUtil.rightClick(clickAmount);
                     }

                     this.clickedItemFrames.add(itemFrameFixedPos);
                  }
               }
            }

            if (this.ticks % 70 == 0) {
               this.calculatePattern();
               this.ticks = 0;
            }

            ++this.ticks;
         }

      }
   }

   @SubscribeEvent
   public void onBlockChange(BlockChangeEvent event) {
      if (PizzaClient.config.autoAimingDevice && event.currentState.func_177230_c() == Blocks.field_150475_bE) {
         if (event.oldState.func_177230_c() == Blocks.field_150406_ce) {
            FakeRotation.leftClick = true;
            FakeRotation.rotationVec = new Vec3((double)event.pos.func_177958_n() + 0.5D, (double)event.pos.func_177956_o() + 0.99D, (double)event.pos.func_177952_p() + 0.5D);
         }

      }
   }

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      this.foundPattern = false;
      this.clickedItemFrames.clear();
      requiredClicksForEntity.clear();
      this.simonSaysQueue.clear();
      clickedSimonSays = false;
   }

   private void calculatePattern() {
      requiredClicksForEntity.clear();
      Map<BlockPos, Entity> itemFrames = new HashMap();
      List<BlockPos> currentItemFrames = new ArrayList();
      List<BlockPos> startItemFrames = new ArrayList();
      Set<BlockPos> endItemFrames = new HashSet();
      Iterator var5 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      while(var5.hasNext()) {
         Entity entity = (Entity)var5.next();
         if (entity instanceof EntityItemFrame) {
            ItemStack displayed = ((EntityItemFrame)entity).func_82335_i();
            if (displayed != null) {
               Item item = displayed.func_77973_b();
               if (item == Items.field_151032_g) {
                  itemFrames.put(new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v), entity);
               } else if (item == Item.func_150898_a(Blocks.field_150325_L)) {
                  if (EnumDyeColor.func_176764_b(displayed.func_77952_i()) == EnumDyeColor.LIME) {
                     startItemFrames.add(new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v));
                  } else {
                     endItemFrames.add(new BlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v));
                  }
               }
            }
         }
      }

      if (itemFrames.size() >= 9 && startItemFrames.size() != 0) {
         var5 = startItemFrames.iterator();

         while(var5.hasNext()) {
            BlockPos pos = (BlockPos)var5.next();
            BlockPos adjacent = pos.func_177984_a();
            if (itemFrames.containsKey(adjacent)) {
               currentItemFrames.add(adjacent);
            }

            adjacent = pos.func_177977_b();
            if (itemFrames.containsKey(adjacent)) {
               currentItemFrames.add(adjacent);
            }

            adjacent = pos.func_177968_d();
            if (itemFrames.containsKey(adjacent)) {
               currentItemFrames.add(adjacent);
            }

            adjacent = pos.func_177978_c();
            if (itemFrames.containsKey(adjacent)) {
               currentItemFrames.add(adjacent);
            }
         }

         label111:
         for(int i = 0; i < 200; ++i) {
            if (currentItemFrames.size() == 0) {
               if (!this.foundPattern) {
                  this.foundPattern = true;
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Pattern!"));
               }

               return;
            }

            List<BlockPos> list = new ArrayList(currentItemFrames);
            currentItemFrames.clear();
            Iterator var15 = list.iterator();

            while(true) {
               while(true) {
                  if (!var15.hasNext()) {
                     continue label111;
                  }

                  BlockPos pos = (BlockPos)var15.next();
                  BlockPos adjacent = pos.func_177984_a();
                  if (endItemFrames.contains(adjacent)) {
                     requiredClicksForEntity.put(pos, 7);
                  } else {
                     adjacent = pos.func_177977_b();
                     if (endItemFrames.contains(adjacent)) {
                        requiredClicksForEntity.put(pos, 3);
                     } else {
                        adjacent = pos.func_177968_d();
                        if (endItemFrames.contains(adjacent)) {
                           requiredClicksForEntity.put(pos, 5);
                        } else {
                           adjacent = pos.func_177978_c();
                           if (endItemFrames.contains(adjacent)) {
                              requiredClicksForEntity.put(pos, 1);
                           } else if (!requiredClicksForEntity.containsKey(pos)) {
                              adjacent = pos.func_177984_a();
                              if (itemFrames.containsKey(adjacent) && !requiredClicksForEntity.containsKey(adjacent)) {
                                 currentItemFrames.add(adjacent);
                                 requiredClicksForEntity.put(pos, 7);
                              } else {
                                 adjacent = pos.func_177977_b();
                                 if (itemFrames.containsKey(adjacent) && !requiredClicksForEntity.containsKey(adjacent)) {
                                    currentItemFrames.add(adjacent);
                                    requiredClicksForEntity.put(pos, 3);
                                 } else {
                                    adjacent = pos.func_177968_d();
                                    if (itemFrames.containsKey(adjacent) && !requiredClicksForEntity.containsKey(adjacent)) {
                                       currentItemFrames.add(adjacent);
                                       requiredClicksForEntity.put(pos, 5);
                                    } else {
                                       adjacent = pos.func_177978_c();
                                       if (itemFrames.containsKey(adjacent) && !requiredClicksForEntity.containsKey(adjacent)) {
                                          currentItemFrames.add(adjacent);
                                          requiredClicksForEntity.put(pos, 1);
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         this.foundPattern = false;
      }

   }
}
