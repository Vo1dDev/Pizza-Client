package qolskyblockmod.pizzaclient.util.graphics.shaders.chroma;

import qolskyblockmod.pizzaclient.util.graphics.shaders.Shader;

public class TestShader extends Shader {
   public static TestShader instance = new TestShader();

   public TestShader() {
      super((String)null, "testFragment");
   }

   public void registerUniforms() {
   }
}
