package qolskyblockmod.pizzaclient.util.graphics.shaders.blur;

import net.minecraft.client.renderer.OpenGlHelper;
import qolskyblockmod.pizzaclient.util.graphics.shaders.Shader;
import qolskyblockmod.pizzaclient.util.graphics.shaders.uniform.FloatUniform;

public class BlurShader extends Shader {
   public static BlurShader instance = new BlurShader();

   public BlurShader() {
      super("blur/verticalBlurVertex", "blur/blurFragment");
   }

   public void registerUniforms() {
      this.registerUniform(new FloatUniform(this.program, "targetHeight", () -> {
         return 10.0F;
      }));
   }

   public void endShader() {
      OpenGlHelper.func_153161_d(0);
   }
}
