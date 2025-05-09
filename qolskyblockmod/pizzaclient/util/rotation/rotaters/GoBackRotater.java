package qolskyblockmod.pizzaclient.util.rotation.rotaters;

import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;

public class GoBackRotater extends Rotater {
   public GoBackRotater(float yawIn, float pitchIn) {
      super(yawIn, pitchIn);
   }

   public void add() {
      float elapsed = (float)(System.currentTimeMillis() - this.timeElapsed);
      if (elapsed >= this.changedDivider + 200.0F) {
         (new Rotater(this.startYaw - PizzaClient.mc.field_71439_g.field_70177_z + MathUtil.randomFloat(), MathUtil.randomFloat())).setRotationAmount((int)(this.divider / 10.0F)).rotate();
      } else if (this.goalPitch != 999.0F && elapsed >= this.changedDivider) {
         PizzaClient.mc.field_71439_g.field_70177_z = this.goalYaw;
         PizzaClient.mc.field_71439_g.field_70125_A = MathUtil.clampPitch(this.goalPitch);
         this.goalPitch = 999.0F;
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
