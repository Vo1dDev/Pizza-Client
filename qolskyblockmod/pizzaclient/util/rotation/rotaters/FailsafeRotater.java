package qolskyblockmod.pizzaclient.util.rotation.rotaters;

import net.minecraft.util.MathHelper;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class FailsafeRotater implements IRotater {
   private float divider;
   private long timeElapsed;
   private float yaw;
   private float pitch;
   private float startYaw;
   private float startPitch;
   private float goalYaw;
   private float goalPitch;
   private long lastRotation;
   private int nextRotation;
   private final int duration;
   private long totalTime;

   public FailsafeRotater() {
      this.duration = 4000;
   }

   public FailsafeRotater(int duration) {
      this.duration = duration;
   }

   public void rotate() {
      this.update();
      this.totalTime = System.currentTimeMillis();
      PizzaClient.rotater = this;
      Rotater.rotating = true;
   }

   public void add() {
      long millis = System.currentTimeMillis();
      double elapsed = (double)(millis - this.timeElapsed);
      if (elapsed >= (double)this.divider) {
         if (millis - this.totalTime >= (long)this.duration) {
            this.shutdown();
         } else {
            PizzaClient.mc.field_71439_g.field_70177_z = this.goalYaw;
            PizzaClient.mc.field_71439_g.field_70125_A = MathHelper.func_76131_a(this.goalPitch, -90.0F, 90.0F);
            this.lastRotation = millis;
            this.timeElapsed = millis + millis;
            this.nextRotation = 800 + Utils.random.nextInt(300);
         }
      } else if (this.lastRotation != 0L) {
         if (millis - this.lastRotation >= (long)this.nextRotation) {
            this.update();
            this.lastRotation = 0L;
         }

      } else {
         PizzaClient.mc.field_71439_g.field_70177_z = (float)((double)this.startYaw + (double)this.yaw * elapsed);
         PizzaClient.mc.field_71439_g.field_70125_A = MathHelper.func_76131_a((float)((double)this.startPitch + (double)this.pitch * elapsed), -90.0F, 90.0F);
      }
   }

   private void update() {
      this.yaw = 90.0F + MathUtil.positiveFloat(40.0F);
      this.goalPitch = MathUtil.randomFloat(20.0F) - 5.0F;
      this.pitch = this.goalPitch - PizzaClient.mc.field_71439_g.field_70125_A;
      this.startPitch = PizzaClient.mc.field_71439_g.field_70125_A;
      this.startYaw = PizzaClient.mc.field_71439_g.field_70177_z;
      this.divider = (float)(270 + Utils.random.nextInt(80));
      this.goalYaw = this.yaw + PizzaClient.mc.field_71439_g.field_70177_z;
      this.yaw /= this.divider;
      this.pitch /= this.divider;
      this.timeElapsed = System.currentTimeMillis();
   }
}
