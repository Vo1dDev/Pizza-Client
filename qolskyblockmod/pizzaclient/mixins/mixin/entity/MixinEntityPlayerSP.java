package qolskyblockmod.pizzaclient.mixins.mixin.entity;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.player.TPAuraHelper;
import qolskyblockmod.pizzaclient.util.rotation.fake.FakeRotation;

@Mixin(
   value = {EntityPlayerSP.class},
   priority = 999
)
public class MixinEntityPlayerSP {
   @Shadow
   private boolean field_175171_bO;
   @Shadow
   private boolean field_175170_bN;
   @Shadow
   private float field_175164_bL;
   @Shadow
   private double field_175166_bJ;
   @Shadow
   private double field_175172_bI;
   @Shadow
   private double field_175167_bK;
   @Shadow
   private int field_175168_bP;
   @Shadow
   private float field_175165_bM;

   @Inject(
      method = {"onUpdateWalkingPlayer"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onPlayerWalkPre(CallbackInfo ci) {
      if (TPAuraHelper.path != null && PizzaClient.mc.field_71462_r == null) {
         if (TPAuraHelper.isFlyActive()) {
            TPAuraHelper.teleport();
         } else {
            TPAuraHelper.disable();
         }
      }

      if (FakeRotation.rotationVec != null) {
         FakeRotation.onPlayerMovePre();
         boolean flag = PizzaClient.mc.field_71439_g.func_70051_ag();
         if (flag != this.field_175171_bO) {
            if (flag) {
               PizzaClient.mc.field_71439_g.field_71174_a.func_147297_a(new C0BPacketEntityAction(PizzaClient.mc.field_71439_g, Action.START_SPRINTING));
            } else {
               PizzaClient.mc.field_71439_g.field_71174_a.func_147297_a(new C0BPacketEntityAction(PizzaClient.mc.field_71439_g, Action.STOP_SPRINTING));
            }

            this.field_175171_bO = flag;
         }

         boolean flag1 = PizzaClient.mc.field_71439_g.func_70093_af();
         if (flag1 != this.field_175170_bN) {
            if (flag1) {
               PizzaClient.mc.field_71439_g.field_71174_a.func_147297_a(new C0BPacketEntityAction(PizzaClient.mc.field_71439_g, Action.START_SNEAKING));
            } else {
               PizzaClient.mc.field_71439_g.field_71174_a.func_147297_a(new C0BPacketEntityAction(PizzaClient.mc.field_71439_g, Action.STOP_SNEAKING));
            }

            this.field_175170_bN = flag1;
         }

         if (PizzaClient.mc.func_175606_aa() == PizzaClient.mc.field_71439_g) {
            double d0 = PizzaClient.mc.field_71439_g.field_70165_t - this.field_175172_bI;
            double d1 = PizzaClient.mc.field_71439_g.func_174813_aQ().field_72338_b - this.field_175166_bJ;
            double d2 = PizzaClient.mc.field_71439_g.field_70161_v - this.field_175167_bK;
            double d3 = (double)(PizzaClient.mc.field_71439_g.field_70177_z - this.field_175164_bL);
            double d4 = (double)(PizzaClient.mc.field_71439_g.field_70125_A - this.field_175165_bM);
            boolean flag2 = d0 * d0 + d1 * d1 + d2 * d2 > 9.0E-4D || this.field_175168_bP >= 20;
            boolean flag3 = d3 != 0.0D || d4 != 0.0D;
            if (PizzaClient.mc.field_71439_g.field_70154_o == null) {
               if (flag2 && flag3) {
                  PizzaClient.mc.field_71439_g.field_71174_a.func_147297_a(new C06PacketPlayerPosLook(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.func_174813_aQ().field_72338_b, PizzaClient.mc.field_71439_g.field_70161_v, PizzaClient.mc.field_71439_g.field_70177_z, PizzaClient.mc.field_71439_g.field_70125_A, PizzaClient.mc.field_71439_g.field_70122_E));
               } else if (flag2) {
                  PizzaClient.mc.field_71439_g.field_71174_a.func_147297_a(new C04PacketPlayerPosition(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.func_174813_aQ().field_72338_b, PizzaClient.mc.field_71439_g.field_70161_v, PizzaClient.mc.field_71439_g.field_70122_E));
               } else if (flag3) {
                  PizzaClient.mc.field_71439_g.field_71174_a.func_147297_a(new C05PacketPlayerLook(PizzaClient.mc.field_71439_g.field_70177_z, PizzaClient.mc.field_71439_g.field_70125_A, PizzaClient.mc.field_71439_g.field_70122_E));
               } else {
                  PizzaClient.mc.field_71439_g.field_71174_a.func_147297_a(new C03PacketPlayer(PizzaClient.mc.field_71439_g.field_70122_E));
               }
            } else {
               PizzaClient.mc.field_71439_g.field_71174_a.func_147297_a(new C06PacketPlayerPosLook(PizzaClient.mc.field_71439_g.field_70159_w, -999.0D, PizzaClient.mc.field_71439_g.field_70179_y, PizzaClient.mc.field_71439_g.field_70177_z, PizzaClient.mc.field_71439_g.field_70125_A, PizzaClient.mc.field_71439_g.field_70122_E));
               flag2 = false;
            }

            ++this.field_175168_bP;
            if (flag2) {
               this.field_175172_bI = PizzaClient.mc.field_71439_g.field_70165_t;
               this.field_175166_bJ = PizzaClient.mc.field_71439_g.func_174813_aQ().field_72338_b;
               this.field_175167_bK = PizzaClient.mc.field_71439_g.field_70161_v;
               this.field_175168_bP = 0;
            }

            if (flag3) {
               this.field_175164_bL = PizzaClient.mc.field_71439_g.field_70177_z;
               this.field_175165_bM = PizzaClient.mc.field_71439_g.field_70125_A;
            }
         }

         FakeRotation.onPlayerMovePost();
         ci.cancel();
      } else {
         FakeRotation.lastPitch = 69420.0F;
         FakeRotation.preventSnap = false;
      }

   }

   @Redirect(
      method = {"onLivingUpdate"},
      at = @At(
   value = "INVOKE",
   target = "Lnet/minecraft/client/gui/GuiScreen;doesGuiPauseGame()Z"
)
   )
   private boolean useChatInPortal(GuiScreen gui) {
      return gui.func_73868_f() || !(gui instanceof GuiContainer);
   }
}
