package qolskyblockmod.pizzaclient.util.graphics.shaders.util;

import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GLContext;

public class ShaderUtil {
   public static final boolean ARB_SHADERS;

   public static void glUniform1f(int location, float v0) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform1fARB(location, v0);
      } else {
         GL20.glUniform1f(location, v0);
      }

   }

   public static void glUniform1f(int location, double v0) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform1fARB(location, (float)v0);
      } else {
         GL20.glUniform1f(location, (float)v0);
      }

   }

   public static void glUniform3f(int location, float v0, float v1, float v2) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform3fARB(location, v0, v1, v2);
      } else {
         GL20.glUniform3f(location, v0, v1, v2);
      }

   }

   public static void glUniform3d(int location, double v0, double v1, double v2) {
      if (ARB_SHADERS) {
         ARBShaderObjects.glUniform3fARB(location, (float)v0, (float)v1, (float)v2);
      } else {
         GL20.glUniform3f(location, (float)v0, (float)v1, (float)v2);
      }

   }

   static {
      ARB_SHADERS = !GLContext.getCapabilities().OpenGL21;
   }
}
