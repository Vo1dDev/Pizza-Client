package qolskyblockmod.pizzaclient.util.graphics.shaders.util;

import java.io.BufferedInputStream;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;
import qolskyblockmod.pizzaclient.util.graphics.shaders.Shader;

public class ShaderLoader {
   public static void load(Shader shader, String vertex, String fragment) {
      try {
         ResourceLocation resourceLocation;
         BufferedInputStream bufferedInputStream;
         byte[] bytes;
         ByteBuffer buffer;
         int shaderID;
         if (vertex != null) {
            resourceLocation = new ResourceLocation("pizzaclient", "shaders/" + vertex + ".vsh");
            bufferedInputStream = new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b());
            bytes = Utils.toByteArray(bufferedInputStream);
            buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.position(0);
            shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153209_q);
            OpenGlHelper.func_153169_a(shaderID, buffer);
            OpenGlHelper.func_153170_c(shaderID);
            OpenGlHelper.func_153178_b(shader.program, shaderID);
            if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
               throw new RuntimeException();
            }
         }

         if (fragment != null) {
            resourceLocation = new ResourceLocation("pizzaclient", "shaders/" + fragment + ".fsh");
            bufferedInputStream = new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b());
            bytes = Utils.toByteArray(bufferedInputStream);
            buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.position(0);
            shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153210_r);
            OpenGlHelper.func_153169_a(shaderID, buffer);
            OpenGlHelper.func_153170_c(shaderID);
            OpenGlHelper.func_153178_b(shader.program, shaderID);
            if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
               throw new RuntimeException();
            }
         }

      } catch (Exception var8) {
         var8.printStackTrace();
         throw new RuntimeException(var8);
      }
   }

   public static void load(Shader shader, String location) {
      try {
         if (location != null) {
            ResourceLocation resourceLocation = new ResourceLocation("pizzaclient", "shaders/" + location + ".vsh");
            BufferedInputStream bufferedInputStream = new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b());
            byte[] bytes = Utils.toByteArray(bufferedInputStream);
            ByteBuffer buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.position(0);
            int shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153209_q);
            OpenGlHelper.func_153169_a(shaderID, buffer);
            OpenGlHelper.func_153170_c(shaderID);
            OpenGlHelper.func_153178_b(shader.program, shaderID);
            if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
               throw new RuntimeException();
            }

            resourceLocation = new ResourceLocation("pizzaclient", "shaders/" + location + ".fsh");
            bufferedInputStream = new BufferedInputStream(PizzaClient.mc.func_110442_L().func_110536_a(resourceLocation).func_110527_b());
            bytes = Utils.toByteArray(bufferedInputStream);
            buffer = BufferUtils.createByteBuffer(bytes.length);
            buffer.put(bytes);
            buffer.position(0);
            shaderID = OpenGlHelper.func_153195_b(OpenGlHelper.field_153210_r);
            OpenGlHelper.func_153169_a(shaderID, buffer);
            OpenGlHelper.func_153170_c(shaderID);
            OpenGlHelper.func_153178_b(shader.program, shaderID);
            if (OpenGlHelper.func_153157_c(shaderID, OpenGlHelper.field_153208_p) == 0) {
               throw new RuntimeException();
            }
         }

      } catch (Exception var7) {
         var7.printStackTrace();
         throw new RuntimeException(var7);
      }
   }
}
