package qolskyblockmod.pizzaclient.util.rotation.rotaters;

import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;

public class RunnableRotater extends Rotater {
   private final Runnable runnable;

   public RunnableRotater(Vec3 target, Runnable runnable) {
      super(target);
      this.runnable = runnable;
   }

   public RunnableRotater(float yaw, float pitch, Runnable runnable) {
      super(yaw, pitch);
      this.runnable = runnable;
   }

   public void add() {
      float elapsed = (float)(System.currentTimeMillis() - this.timeElapsed);
      if (elapsed >= this.changedDivider) {
         PizzaClient.mc.field_71439_g.field_70177_z = this.goalYaw;
         PizzaClient.mc.field_71439_g.field_70125_A = MathUtil.clampPitch(this.goalPitch);
         PizzaClient.rotater = null;
         this.runnable.run();
      } else {
         if (elapsed > 80.0F) {
            float diff = (float)Math.sqrt((double)((elapsed - 80.0F) / this.changedDivider + 1.0F)) * 1.25F;
            this.changedYaw = this.yaw / diff;
            this.changedPitch = this.pitch / diff;
            this.changedDivider = this.divider * diff;
         }

         PizzaClient.mc.field_71439_g.field_70177_z = this.startYaw + this.changedYaw * elapsed;
         PizzaClient.mc.field_71439_g.field_70125_A = MathUtil.clampPitch(this.startPitch + this.changedPitch * elapsed);
      }
   }
}
