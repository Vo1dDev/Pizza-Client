package qolskyblockmod.pizzaclient.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import qolskyblockmod.pizzaclient.PizzaClient;

public class RetardedUtil {
   public static final JsonObject cryptOverlay = getCrypts();

   public static void isCrypt(BlockPos pos) {
   }

   public static JsonObject getCrypts() {
      try {
         ResourceLocation resource = new ResourceLocation("pizzaclient", "json.crypts_data.json");
         return (new JsonParser()).parse(IOUtils.toString(PizzaClient.mc.func_110442_L().func_110536_a(resource).func_110527_b(), StandardCharsets.UTF_8)).getAsJsonObject();
      } catch (Exception var1) {
         var1.printStackTrace();
         throw new RuntimeException(var1);
      }
   }
}
