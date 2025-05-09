package qolskyblockmod.pizzaclient.util.graphics;

import org.lwjgl.opengl.GL11;

public class ColorState {
   public static float red;
   public static float green;
   public static float blue;

   public static void setColor(int redIn, int greenIn, int blueIn) {
      red = (float)redIn;
      green = (float)greenIn;
      blue = (float)blueIn;
   }

   public static void setColorAndRender(int redIn, int greenIn, int blueIn) {
      red = (float)redIn;
      green = (float)greenIn;
      blue = (float)blueIn;
      GL11.glColor3f((float)redIn, (float)greenIn, (float)blueIn);
   }

   public static void setColor(float redIn, float greenIn, float blueIn) {
      red = redIn;
      green = greenIn;
      blue = blueIn;
   }

   public static void setColorAndRender(float redIn, float greenIn, float blueIn) {
      red = redIn;
      green = greenIn;
      blue = blueIn;
      GL11.glColor3f(redIn, greenIn, blueIn);
   }

   public static void setColor() {
      GL11.glColor3f(red, blue, green);
   }
}
