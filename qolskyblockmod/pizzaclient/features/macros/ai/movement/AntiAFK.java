package qolskyblockmod.pizzaclient.features.macros.ai.movement;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class AntiAFK {
   private static long lastAfkTime;
   private static int nextDelay;
   public static int mode;

   public static void check() {
      if (nextDelay == 0) {
         nextDelay = 4000 + Utils.random.nextInt(3000);
         mode = 0;
      }

      if (lastAfkTime == 0L) {
         lastAfkTime = System.currentTimeMillis();
      }

      if (System.currentTimeMillis() - lastAfkTime >= (long)nextDelay) {
         lastAfkTime = System.currentTimeMillis();
         switch(mode) {
         case 0:
            nextDelay = 300 + Utils.random.nextInt(100);
            mode = 1;
            break;
         case 1:
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
            mode = 2;
            nextDelay = 300 + Utils.random.nextInt(125);
            break;
         case 2:
            KeyBinding.func_74510_a(PlayerUtil.getRandomMovement(), true);
            mode = 3;
            nextDelay = 200 + Utils.random.nextInt(100);
            break;
         case 3:
            PlayerUtil.moveOpposite();
            mode = 4;
            break;
         case 4:
            Movement.disableMovement();
            mode = 5;
            nextDelay = 450 + Utils.random.nextInt(150);
            break;
         case 5:
            KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
            mode = 6;
            nextDelay = 300 + Utils.random.nextInt(100);
            break;
         case 6:
            MovingObjectPosition rayTrace = VecUtil.rayTraceStopLiquid(100.0F);
            if (rayTrace != null) {
               BlockPos pos = rayTrace.func_178782_a();
               Vec3 hit = VecUtil.getRandomHittableStopLiquid(pos, PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c().func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72331_e(0.251D, 0.251D, 0.251D));
               if (hit != null) {
                  (new Rotater(hit)).setRotationAmount(12).rotate();
               } else {
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to rotate to a random spot. Reason: found no hittable"));
               }
            } else {
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to rotate to a random spot. Reason: found no vec"));
            }

            mode = 0;
            nextDelay = 4000 + Utils.random.nextInt(3000);
         }
      }

   }

   public static void reset() {
      nextDelay = 4000 + Utils.random.nextInt(3000);
      lastAfkTime = 0L;
      mode = 0;
   }

   public static boolean isMoving() {
      return mode >= 2 && mode != 6;
   }
}
