package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.PathBase;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder.PathNode;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.PathComparator;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.distance.EntityDistanceHelper;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.SnapRotater;

public final class EntityPath extends PathBase {
   private Entity entity;
   private final Class<? extends Entity> clazz;
   private final String displayName;
   private final float maxY;
   private final float minY;
   private boolean recalculate;

   public static EntityPath findClosest(Class<? extends Entity> entity) {
      EntityDistanceHelper helper = new EntityDistanceHelper();
      Iterator var2 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      while(var2.hasNext()) {
         Entity en = (Entity)var2.next();
         if (en.getClass() == entity) {
            helper.compare(en);
         }
      }

      if (helper.isNotNull()) {
         return new EntityPath(entity, (String)null, helper.closest);
      } else {
         return null;
      }
   }

   public static EntityPath findClosest(Class<? extends Entity> entity, String displayName, float maxY) {
      EntityDistanceHelper helper = new EntityDistanceHelper();
      Iterator var4 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      while(var4.hasNext()) {
         Entity en = (Entity)var4.next();
         if (en.getClass() == entity && en.func_70005_c_().contains(displayName) && en.func_174813_aQ().field_72338_b < (double)maxY) {
            helper.compare(en);
         }
      }

      if (helper.isNotNull()) {
         return new EntityPath(entity, displayName, helper.closest, maxY);
      } else {
         return null;
      }
   }

   public static EntityPath findClosest(Class<? extends Entity> entity, String displayName, float minY, float maxY) {
      EntityDistanceHelper helper = new EntityDistanceHelper();
      Iterator var5 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      while(var5.hasNext()) {
         Entity en = (Entity)var5.next();
         if (en.getClass() == entity && en.func_70005_c_().contains(displayName) && en.func_174813_aQ().field_72338_b < (double)maxY && en.func_174813_aQ().field_72338_b > (double)minY) {
            helper.compare(en);
         }
      }

      if (helper.isNotNull()) {
         return new EntityPath(entity, displayName, helper.closest, maxY);
      } else {
         return null;
      }
   }

   public static List<EntityPath> getAllSorted(Class<? extends Entity> entity, String displayName, float minY, float maxY) {
      List<EntityPath> paths = new ArrayList();
      Iterator var5 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      while(var5.hasNext()) {
         Entity en = (Entity)var5.next();
         if (en.getClass() == entity && en.func_70005_c_().contains(displayName) && en.func_174813_aQ().field_72338_b < (double)maxY && en.func_174813_aQ().field_72338_b > (double)minY) {
            paths.add(new EntityPath(entity, displayName, en, minY, maxY));
         }
      }

      paths.sort(new PathComparator());
      return paths;
   }

   public static EntityPath findClosest(Class<? extends Entity> entity, String displayName) {
      EntityDistanceHelper helper = new EntityDistanceHelper();
      Iterator var3 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

      while(var3.hasNext()) {
         Entity en = (Entity)var3.next();
         if (en.getClass() == entity && en.func_70005_c_().contains(displayName)) {
            helper.compare(en);
         }
      }

      if (helper.isNotNull()) {
         return new EntityPath(entity, displayName, helper.closest);
      } else {
         return null;
      }
   }

   public EntityPath(Class<? extends Entity> clazz, String displayName, Entity to) {
      super(new Vec3(to.field_70165_t, to.field_70163_u, to.field_70161_v));
      this.entity = to;
      this.clazz = clazz;
      this.displayName = displayName;
      this.maxY = 255.0F;
      this.minY = 0.0F;
   }

   public EntityPath(Class<? extends Entity> clazz, String displayName, Entity to, float maxY) {
      super(new Vec3(to.field_70165_t, to.field_70163_u, to.field_70161_v));
      this.entity = to;
      this.clazz = clazz;
      this.displayName = displayName;
      this.maxY = maxY;
      this.minY = 0.0F;
   }

   public EntityPath(Class<? extends Entity> clazz, String displayName, Entity to, float maxY, float minY) {
      super(new Vec3(to.field_70165_t, to.field_70163_u, to.field_70161_v));
      this.entity = to;
      this.clazz = clazz;
      this.displayName = displayName;
      this.maxY = maxY;
      this.minY = minY;
   }

   public void move() {
      this.recheckForEntity();
      if (this.entity != null && !this.entity.field_70128_L && ((EntityLivingBase)this.entity).field_70725_aQ <= 0 && PizzaClient.mc.field_71441_e.field_72996_f.contains(this.entity)) {
         this.attackEntity();
         if (this.moves.size() != 0) {
            BetterBlockPos pos = new BetterBlockPos(this.entity.field_70165_t, this.entity.field_70163_u, this.entity.field_70161_v);
            BetterBlockPos last = (BetterBlockPos)this.moves.get(this.moves.size() - 1);
            if (!pos.equals(last)) {
               if (this.moves.contains(pos)) {
                  this.moves.remove(last);
               }

               this.moves.add(pos);
            }

            if (this.isVerticalPassable(this.entity.func_174813_aQ())) {
               this.recalculate = true;
               if (PizzaClient.rotater == null) {
                  if (!VecUtil.isFacingAABB(this.entity.func_174813_aQ().func_72314_b(0.25D, 0.5D, 0.25D), 200.0F)) {
                     (new Rotater(new Vec3(this.entity.field_70165_t, this.entity.field_70163_u + 1.3D, this.entity.field_70161_v))).rotate();
                  } else {
                     SnapRotater.snapTo(new Vec3(this.entity.field_70165_t, this.entity.field_70163_u + 1.3D, this.entity.field_70161_v));
                  }

                  this.moveToEntity();
               } else {
                  Movement.disableMovement();
               }

            } else if (this.recalculate) {
               Movement.disableMovement();
               this.update();
               this.recalculate = false;
            } else {
               if (this.moves.size() != 0) {
                  this.useDefaultMovement();
                  this.rotateIfNeeded();
               }

            }
         }
      } else {
         this.shutdown();
      }
   }

   public void moveToEntity() {
      Movement.setMovementToForward();
   }

   private void recheckForEntity() {
      EntityDistanceHelper helper = new EntityDistanceHelper();
      Iterator var2;
      Entity entity;
      if (this.displayName == null) {
         var2 = (new ArrayList(PizzaClient.mc.field_71441_e.field_72996_f)).iterator();

         while(var2.hasNext()) {
            entity = (Entity)var2.next();
            if (entity != null) {
               AxisAlignedBB aabb = entity.func_174813_aQ();
               if (aabb != null && entity.getClass() == this.clazz && aabb.field_72338_b < (double)this.maxY && aabb.field_72338_b > (double)this.minY) {
                  helper.compare(entity);
               }
            }
         }
      } else {
         var2 = (new ArrayList(PizzaClient.mc.field_71441_e.field_72996_f)).iterator();

         while(var2.hasNext()) {
            entity = (Entity)var2.next();
            if (entity != null) {
               String name = entity.func_70005_c_();
               if (name != null) {
                  AxisAlignedBB aabb = entity.func_174813_aQ();
                  if (aabb != null && entity.getClass() == this.clazz && name.contains(this.displayName) && aabb.field_72338_b < (double)this.maxY && aabb.field_72338_b > (double)this.minY) {
                     helper.compare(entity);
                  }
               }
            }
         }
      }

      if (helper.isNotNull() && !this.entity.equals(helper.closest)) {
         this.update(new BetterBlockPos(helper.closest.field_70165_t, helper.closest.field_70163_u, helper.closest.field_70161_v));
         this.entity = helper.closest;
      }

   }

   public void initRotater() {
      if (!this.isVerticalPassable(this.entity.func_174813_aQ())) {
         super.initRotater();
      }

   }

   public boolean addBlock(PathNode node) {
      return this.getDefaultAddBlock(node);
   }

   public void attackEntity() {
      if (PizzaClient.mc.field_71476_x != null && PizzaClient.mc.field_71476_x.field_72308_g != null) {
         if (PizzaClient.mc.field_71476_x.field_72308_g.equals(this.entity)) {
            PlayerUtil.leftClick();
            PizzaClient.mc.field_71441_e.func_72900_e(this.entity);
            this.shutdown();
            Utils.sleep(50);
         } else if (PizzaClient.mc.field_71476_x.field_72308_g.getClass() == this.clazz && (this.displayName == null || PizzaClient.mc.field_71476_x.field_72308_g.func_70005_c_().contains(this.displayName))) {
            PlayerUtil.leftClick();
            if (PizzaClient.mc.field_71476_x.field_72308_g != null) {
               PizzaClient.mc.field_71441_e.func_72900_e(PizzaClient.mc.field_71476_x.field_72308_g);
            }

            Utils.sleep(100);
         }
      }

   }
}
