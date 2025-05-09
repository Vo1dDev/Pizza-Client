package qolskyblockmod.pizzaclient.util.graphics.shaders.uniform;

import java.util.function.Supplier;
import net.minecraft.client.renderer.OpenGlHelper;
import qolskyblockmod.pizzaclient.util.graphics.shaders.util.ShaderUtil;

public class FloatUniform implements IUniform {
   public final Supplier<Float> supplier;
   public final int id;
   public float lastValue;

   public FloatUniform(int program, String name, Supplier<Float> supplier) {
      this.id = OpenGlHelper.func_153194_a(program, name);
      this.supplier = supplier;
      this.lastValue = (Float)supplier.get();
   }

   public void update() {
      float current = (Float)this.supplier.get();
      if (current != this.lastValue) {
         ShaderUtil.glUniform1f(this.id, current);
         this.lastValue = current;
      }

   }
}
