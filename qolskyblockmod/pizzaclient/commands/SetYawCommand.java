package qolskyblockmod.pizzaclient.commands;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.MathUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.SnapRotater;

public class SetYawCommand extends CommandBase {
   public String func_71517_b() {
      return "setyaw";
   }

   public List<String> func_71514_a() {
      return Collections.singletonList("setrotation");
   }

   public String func_71518_a(ICommandSender sender) {
      return null;
   }

   public int func_82362_a() {
      return 0;
   }

   public void func_71515_b(ICommandSender sender, String[] args) {
      float x;
      EntityPlayerSP var10000;
      switch(args.length) {
      case 1:
         String s = args[0];

         try {
            float goalYaw = Float.parseFloat(s);
            var10000 = PizzaClient.mc.field_71439_g;
            var10000.field_70177_z += goalYaw - MathHelper.func_76142_g(PizzaClient.mc.field_71439_g.field_70177_z);
         } catch (Exception var15) {
            sender.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "\"" + s + "\" is not a valid number!"));
            var15.printStackTrace();
         }
         break;
      case 2:
         String yaw = args[0];

         try {
            float goalYaw = Float.parseFloat(yaw);
            var10000 = PizzaClient.mc.field_71439_g;
            var10000.field_70177_z += goalYaw - MathHelper.func_76142_g(PizzaClient.mc.field_71439_g.field_70177_z);
         } catch (Exception var14) {
            sender.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "\"" + yaw + "\" is not a valid number!"));
            var14.printStackTrace();
         }

         String pitch = args[1];

         try {
            x = Float.parseFloat(pitch);
            PizzaClient.mc.field_71439_g.field_70125_A = MathUtil.clampPitch(x);
         } catch (Exception var13) {
            sender.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "\"" + pitch + "\" is not a valid number!"));
            var13.printStackTrace();
         }
         break;
      case 3:
         try {
            x = Float.parseFloat(args[0]);
         } catch (Exception var12) {
            sender.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "\"" + args[0] + "\" is not a valid number!"));
            var12.printStackTrace();
            return;
         }

         float y;
         try {
            y = Float.parseFloat(args[1]);
         } catch (Exception var11) {
            sender.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "\"" + args[1] + "\" is not a valid number!"));
            var11.printStackTrace();
            return;
         }

         float z;
         try {
            z = Float.parseFloat(args[2]);
         } catch (Exception var10) {
            sender.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "\"" + args[2] + "\" is not a valid number!"));
            var10.printStackTrace();
            return;
         }

         SnapRotater.snapTo(new Vec3((double)x, (double)y, (double)z));
         break;
      default:
         sender.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Usage: /setyaw [yaw] or /setyaw [yaw] [pitch]"));
      }

   }
}
