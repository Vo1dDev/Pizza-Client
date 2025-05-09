package qolskyblockmod.pizzaclient.features.macros.mining.dwarven;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.config.ConfigFile;
import qolskyblockmod.pizzaclient.features.macros.ai.mining.Refuel;
import qolskyblockmod.pizzaclient.features.macros.ai.movement.AOTVMovement;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.VecUtil;
import qolskyblockmod.pizzaclient.util.misc.Locations;

public class MithrilMarkers extends ConfigFile {
   public static final File configFile;
   public static final List<BlockPos> markers;

   public MithrilMarkers() {
      super(configFile);
   }

   public void read() {
      try {
         FileReader in = new FileReader(configFile);
         Throwable var2 = null;

         try {
            JsonArray arr = (JsonArray)PizzaClient.gson.fromJson(in, JsonArray.class);
            int arraySize = arr.size();

            for(int i = 0; i < arraySize; ++i) {
               JsonObject o = arr.get(i).getAsJsonObject();
               markers.add(new BlockPos(o.get("x").getAsInt(), o.get("y").getAsInt(), o.get("z").getAsInt()));
            }
         } catch (Throwable var15) {
            var2 = var15;
            throw var15;
         } finally {
            if (in != null) {
               if (var2 != null) {
                  try {
                     in.close();
                  } catch (Throwable var14) {
                     var2.addSuppressed(var14);
                  }
               } else {
                  in.close();
               }
            }

         }
      } catch (Exception var17) {
         var17.printStackTrace();
      }

   }

   public static void write() {
      JsonArray arr = new JsonArray();
      Iterator var1 = markers.iterator();

      while(var1.hasNext()) {
         BlockPos pos = (BlockPos)var1.next();
         JsonObject o = new JsonObject();
         o.addProperty("x", pos.func_177958_n());
         o.addProperty("y", pos.func_177956_o());
         o.addProperty("z", pos.func_177952_p());
         arr.add(o);
      }

      ConfigFile.write(arr, configFile);
   }

   public static void run() {
      Locations.DWARVENMINES.warpTo();
      if (markers.size() == 0) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "The Markers are empty. Try adding some markers."));
      } else {
         AOTVMovement.run(markers, () -> {
            if (Refuel.drillNPC == null) {
               Refuel.drillNPC = Refuel.findDrillNPC();
            }

         });
      }
   }

   public static void onKey() {
      MovingObjectPosition position = VecUtil.rayTrace(61.0F);
      if (position == null) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Invalid marker. Was the block out of range?"));
      } else {
         if (position.func_178782_a() == null) {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "You must face a block in order to add it to the marker."));
         } else {
            BlockPos pos = position.func_178782_a();
            if (PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c() == Blocks.field_150350_a) {
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Invalid marker. Was the block out of range?"));
               return;
            }

            if (markers.contains(pos)) {
               markers.remove(pos);
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Removed " + pos.toString() + " from the marker list."));
            } else {
               markers.add(pos);
               write();
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Added " + pos.toString() + " to the marker list."));
            }
         }

      }
   }

   static {
      configFile = new File(PizzaClient.modDir, "mithrilmarkers.json");
      markers = new ArrayList();
   }
}
