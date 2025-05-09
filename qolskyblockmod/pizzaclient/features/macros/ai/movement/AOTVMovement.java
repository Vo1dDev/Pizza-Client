package qolskyblockmod.pizzaclient.features.macros.ai.movement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;
import qolskyblockmod.pizzaclient.util.rotation.rotaters.Rotater;

public class AOTVMovement {
   public static void run(List<BlockPos> moves, Runnable tick) {
      if (moves.size() == 0) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "The AOTV Movement moves are empty."));
      } else {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
         int currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
         PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.checkHotBarForEtherwarp();
         Iterator var3 = moves.iterator();

         while(var3.hasNext()) {
            BlockPos pos = (BlockPos)var3.next();
            Vec3 hittable = VecUtil.getVeryAccurateHittableHitVec(pos);
            if (tick != null) {
               tick.run();
            }

            if (hittable == null) {
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Something went wrong with the AOTV Movement, trying again"));
               PizzaClient.mc.field_71439_g.func_71165_d("/hub");
               Utils.sleep(2500);

               while(PizzaClient.location != Locations.DWARVENMINES) {
                  PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
                  Utils.sleep(1000);
               }

               BlockPos forge = new BlockPos(0, 149, -69);

               while(!(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)).equals(forge)) {
                  Utils.sleep(1000);
                  PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
               }

               Utils.sleep(500);
               run(moves, tick);
               return;
            }

            (new Rotater(hittable)).rotate();

            while(PizzaClient.rotater != null) {
               Utils.sleep(1);
            }

            Utils.sleep(200);
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
            Utils.sleep(PizzaClient.config.commissionMacroTpWait);
         }

         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
         PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
         Utils.sleep(500);
      }
   }

   public static void run(Runnable tick, BlockPos... moves) {
      if (moves.length == 0) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "The AOTV Movement moves are empty."));
      } else {
         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), true);
         int currentItem = PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c;
         PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = PlayerUtil.checkHotBarForEtherwarp();
         BlockPos[] var3 = moves;
         int var4 = moves.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            BlockPos pos = var3[var5];
            Vec3 hittable = VecUtil.getVeryAccurateHittableHitVec(pos);
            if (tick != null) {
               tick.run();
            }

            if (hittable == null) {
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Something went wrong with the AOTV Movement, trying again"));
               PizzaClient.mc.field_71439_g.func_71165_d("/hub");
               Utils.sleep(2500);

               while(PizzaClient.location != Locations.DWARVENMINES) {
                  Locations.warpToSb();
                  PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
                  Utils.sleep(1000);
               }

               BlockPos forge = new BlockPos(0, 149, -69);

               while(!(new BlockPos(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v)).equals(forge)) {
                  Utils.sleep(1000);
                  PizzaClient.mc.field_71439_g.func_71165_d("/warp forge");
               }

               Utils.sleep(500);
               run(tick, moves);
               return;
            }

            (new Rotater(hittable)).setRotationAmount(40).rotate();

            while(PizzaClient.rotater != null) {
               Utils.sleep(1);
            }

            Utils.sleep(200);
            KeyBinding.func_74507_a(PizzaClient.mc.field_71474_y.field_74313_G.func_151463_i());
            Utils.sleep(PizzaClient.config.commissionMacroTpWait);
         }

         KeyBinding.func_74510_a(PizzaClient.mc.field_71474_y.field_74311_E.func_151463_i(), false);
         PizzaClient.mc.field_71439_g.field_71071_by.field_70461_c = currentItem;
         Utils.sleep(500);
      }
   }

   public static void run(List<BlockPos> moves) {
      run((List)moves, (Runnable)null);
   }

   public static void tpToRandom(List<BlockPos> moves, List<BlockPos> randMoves) {
      if (moves.size() != 0) {
         List<BlockPos> addedMoves = new ArrayList(moves);
         if (randMoves.size() != 0) {
            addedMoves.add(randMoves.get(Utils.random.nextInt(randMoves.size())));
         }

         run((List)addedMoves, (Runnable)null);
      }
   }

   public static void tpToRandom(List<BlockPos> moves, List<BlockPos> randMoves, Runnable tick) {
      if (moves.size() != 0) {
         List<BlockPos> addedMoves = new ArrayList(moves);
         if (randMoves.size() != 0) {
            addedMoves.add(randMoves.get(Utils.random.nextInt(randMoves.size())));
         }

         run((List)addedMoves, (Runnable)tick);
      }
   }
}
