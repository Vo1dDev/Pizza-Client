package qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.pathfinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.Movement;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.Pathfinding;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.path.base.PathBase;
import qolskyblockmod.pizzaclient.util.PlayerUtil;
import qolskyblockmod.pizzaclient.util.Utils;

public class PathFinder extends BasePathfinder {
   public PathFinder(PathBase path) {
      super(path);
   }

   public boolean run(boolean messages) {
      if (path == null) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Path returned null. Please report this."));
         return false;
      } else {
         try {
            if (path.currentPos.equals(path.goalPos)) {
               this.shutdown();
               return true;
            } else {
               nodes = new ArrayList(Collections.singletonList(new PathNode()));

               while(!path.finished) {
                  Iterator var2 = (new ArrayList(nodes)).iterator();

                  while(var2.hasNext()) {
                     PathNode node = (PathNode)var2.next();
                     nodes.remove(node);
                     if (path.addBlock(node)) {
                        MinecraftForge.EVENT_BUS.register(Pathfinding.instance);
                        break;
                     }
                  }

                  if (nodes.size() == 0) {
                     if (messages) {
                        PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Failed to find a path."));
                     }

                     this.shutdown();
                     return false;
                  }

                  path.onTick();
               }

               if (messages) {
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Found Path!"));
               }

               if (path.moves.size() != 0) {
                  path.onBeginMove();

                  while(path.moves != null && path.moves.size() != 0) {
                     if (PizzaClient.mc.field_71462_r != null) {
                        Movement.disableMovement();
                        Utils.sleep(200);
                        PlayerUtil.closeScreen();
                     } else {
                        path.move();
                     }
                  }

                  path.onEndMove();
               }

               Movement.disableMovement();
               this.shutdown();
               return true;
            }
         } catch (Exception var4) {
            var4.printStackTrace();
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "PizzaClient caught an logged an exception when calculating the path. Please report this."));
            this.shutdown();
            return false;
         }
      }
   }
}
