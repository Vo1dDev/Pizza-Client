package qolskyblockmod.pizzaclient.util.graphics.shaders.uniform;

import java.util.function.Supplier;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.Vec3;
import qolskyblockmod.pizzaclient.util.graphics.shaders.util.ShaderUtil;

public class VectorUniform implements IUniform {
   public final Supplier<Vec3> supplier;
   public final int id;
   public Vec3 lastValue;

   public VectorUniform(int program, String name, Supplier<Vec3> supplier) {
      this.id = OpenGlHelper.func_153194_a(program, name);
      this.supplier = supplier;
      this.lastValue = (Vec3)supplier.get();
   }

   public void update() {
      Vec3 current = (Vec3)this.supplier.get();
      if (!current.equals(this.lastValue)) {
         ShaderUtil.glUniform3f(this.id, (float)current.field_72450_a, (float)current.field_72448_b, (float)current.field_72449_c);
         this.lastValue = current;
      }

   }
}
