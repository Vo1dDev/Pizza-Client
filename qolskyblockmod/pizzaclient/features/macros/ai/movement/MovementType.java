package qolskyblockmod.pizzaclient.features.macros.ai.movement;

import qolskyblockmod.pizzaclient.PizzaClient;

public enum MovementType {
   FORWARDS,
   BACKWARDS,
   LEFT,
   RIGHT,
   NULL;

   public MovementType left() {
      switch(this) {
      case FORWARDS:
         return LEFT;
      case LEFT:
         return BACKWARDS;
      case BACKWARDS:
         return RIGHT;
      case RIGHT:
         return FORWARDS;
      default:
         return NULL;
      }
   }

   public MovementType right() {
      switch(this) {
      case FORWARDS:
         return RIGHT;
      case LEFT:
         return FORWARDS;
      case BACKWARDS:
         return LEFT;
      case RIGHT:
         return BACKWARDS;
      default:
         return NULL;
      }
   }

   public MovementType opposite() {
      switch(this) {
      case FORWARDS:
         return BACKWARDS;
      case LEFT:
         return RIGHT;
      case BACKWARDS:
         return FORWARDS;
      case RIGHT:
         return LEFT;
      default:
         return NULL;
      }
   }

   public static MovementType getCurrent() {
      if (PizzaClient.mc.field_71474_y.field_74351_w.func_151470_d()) {
         return FORWARDS;
      } else if (PizzaClient.mc.field_71474_y.field_74370_x.func_151470_d()) {
         return LEFT;
      } else if (PizzaClient.mc.field_71474_y.field_74366_z.func_151470_d()) {
         return RIGHT;
      } else {
         return PizzaClient.mc.field_71474_y.field_74368_y.func_151470_d() ? BACKWARDS : NULL;
      }
   }
}
