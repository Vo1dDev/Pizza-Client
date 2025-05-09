package qolskyblockmod.pizzaclient.util.graphics.shaders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.OpenGlHelper;
import qolskyblockmod.pizzaclient.util.graphics.shaders.uniform.IUniform;
import qolskyblockmod.pizzaclient.util.graphics.shaders.util.ShaderLoader;

public abstract class Shader {
   private final List<IUniform> uniforms = new ArrayList();
   public final int program = OpenGlHelper.func_153183_d();

   public Shader(String vertex, String fragment) {
      ShaderLoader.load(this, vertex, fragment);
      OpenGlHelper.func_153179_f(this.program);
      this.registerUniforms();
   }

   public Shader(String location) {
      ShaderLoader.load(this, location);
      OpenGlHelper.func_153179_f(this.program);
      this.registerUniforms();
   }

   public void endShader() {
      OpenGlHelper.func_153161_d(0);
   }

   public final void registerUniform(IUniform uniform) {
      this.uniforms.add(uniform);
   }

   public void applyShader() {
      Iterator var1 = this.uniforms.iterator();

      while(var1.hasNext()) {
         IUniform uniform = (IUniform)var1.next();
         uniform.update();
      }

      OpenGlHelper.func_153161_d(this.program);
   }

   public abstract void registerUniforms();
}
