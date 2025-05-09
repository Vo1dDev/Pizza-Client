package qolskyblockmod.pizzaclient.features.macros.ai.movement;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;
import qolskyblockmod.pizzaclient.util.Utils;

public class Movement {
   public static void setMovement(MovementType movement) {
      switch(movement) {
      case FORWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
         break;
      case BACKWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
         break;
      case LEFT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
         break;
      case RIGHT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
      }

   }

   public static void setMovementToForward() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
   }

   public static void setMovement(BetterBlockPos current, BetterBlockPos next) {
      setMovement(calculateRequiredMovement(current, next));
   }

   public static void setMovement(Vec3 current, BetterBlockPos next) {
      setMovement(calculateRequiredMovement(new BetterBlockPos(current), next));
   }

   public static void move(MovementType type) {
      switch(type) {
      case FORWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
         break;
      case BACKWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
         break;
      case LEFT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
         break;
      case RIGHT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
      }

   }

   public static void addMovement(MovementType type) {
      switch(type) {
      case FORWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), true);
         break;
      case BACKWARDS:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), true);
         break;
      case LEFT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), true);
         break;
      case RIGHT:
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), true);
      }

   }

   public static void endMovement() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
   }

   public static void addMovement(BetterBlockPos current, BetterBlockPos next) {
      addMovement(calculateRequiredMovement(current, next));
   }

   public static void disableMovement() {
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74351_w.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74368_y.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74370_x.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74366_z.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74314_A.func_151463_i(), false);
      KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
   }

   public static MovementType calculateRequiredMovement(BetterBlockPos current, BetterBlockPos next) {
      EnumFacing facing = PizzaClient.mc.field_71439_g.func_174811_aO();
      BetterBlockPos pos = current.offset(facing);
      if (pos.isSameXandZ(next)) {
         return MovementType.FORWARDS;
      } else if (current.offset(Utils.getLeftEnumfacing(facing)).isSameXandZ(next)) {
         return MovementType.LEFT;
      } else {
         return current.offset(Utils.getRightEnumfacing(facing)).isSameXandZ(next) ? MovementType.RIGHT : MovementType.NULL;
      }
   }

   public static boolean isMoving() {
      return PizzaClient.mc.field_71474_y.field_74351_w.func_151470_d() || PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d() || PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d() || PizzaClient.mc.field_71474_y.field_74368_y.func_151470_d();
   }
}
