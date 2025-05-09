package qolskyblockmod.pizzaclient.features.dungeons.f7;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.features.player.TPAuraHelper;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.misc.distance.EntityDistanceHelper;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.SnapRotater;

public class F7TPAura {
   public static void onFlyChecksDisabled() {
      TPAuraHelper.update();
      EntityDistanceHelper helper = new EntityDistanceHelper();
      Iterator var1;
      Entity entity;
      if (PizzaClient.mc.field_71439_g.field_70163_u > 210.0D) {
         var1 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

         while(var1.hasNext()) {
            entity = (Entity)var1.next();
            if (entity instanceof EntityArmorStand && entity.func_145818_k_() && entity.func_95999_t().contains("CLICK HERE")) {
               helper.compare(entity);
            }
         }

         if (helper.isNotNull()) {
            Entity entity = helper.closest;
            TPAuraHelper.runPathfinder(new BetterBlockPos(entity.field_70165_t, entity.field_70163_u, entity.field_70161_v), () -> {
               SnapRotater.snapTo(entity);
               PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, entity);
               TPAuraHelper.runPathfinder(getClosestPlate());
            });
         }
      } else {
         var1 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

         while(var1.hasNext()) {
            entity = (Entity)var1.next();
            if (entity instanceof EntityArmorStand && entity.func_145818_k_() && entity.func_95999_t().contains("CLICK HERE")) {
               double x = entity.field_70165_t;
               double y = entity.field_70163_u;
               double z = entity.field_70161_v;
               if (PizzaClient.mc.field_71441_e.func_72872_a(EntityPlayer.class, new AxisAlignedBB(x - 3.0D, y - 3.0D, z - 3.0D, x + 3.0D, y + 3.0D, z + 3.0D)).size() == 0) {
                  helper.compare(entity);
               }
            }
         }

         if (helper.isNotNull()) {
            SnapRotater.snapTo(helper.closest);
            PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, helper.closest);
         }
      }

   }

   private static BetterBlockPos getClosestPlate() {
      BlockPos left = new BlockPos(94, 224, 41);
      BlockPos right = new BlockPos(52, 224, 41);
      Vec3 player = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
      return Utils.squareDistanceToBlockPos(player, left) > Utils.squareDistanceToBlockPos(player, right) ? new BetterBlockPos(right) : new BetterBlockPos(left);
   }
}
