package qolskyblockmod.pizzaclient.util.graphics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.mixins.mixin.accessor.AccessorFontRenderer;
import qolskyblockmod.pizzaclient.util.Utils;

public class FontUtil {
   public static final char[] modMessageChars = "PizzaClient".toCharArray();
   public static int posX;
   public static int posY;
   public static final int[] CHAR_WIDTH = new int[256];
   public static final float RGB_SPEED = 3000.0F;
   public static final AccessorFontRenderer fontRenderer;
   public static int[] colorCode;

   public static void init() {
      colorCode = fontRenderer.getColorCodes();

      BufferedImage bufferedimage;
      try {
         bufferedimage = Utils.readBufferedImage(new ResourceLocation("textures/font/ascii.png"));
      } catch (IOException var16) {
         throw new RuntimeException(var16);
      }

      int i = bufferedimage.getWidth();
      int j = bufferedimage.getHeight();
      int[] aint = new int[i * j];
      bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
      int k = j / 16;
      int l = i / 16;
      int i1 = 1;
      float f = 8.0F / (float)l;

      for(int j1 = 0; j1 < 256; ++j1) {
         int k1 = j1 % 16;
         int l1 = j1 / 16;
         if (j1 == 32) {
            CHAR_WIDTH[j1] = 3 + i1;
         }

         int i2;
         for(i2 = l - 1; i2 >= 0; --i2) {
            int j2 = k1 * l + i2;
            boolean flag = true;

            for(int k2 = 0; k2 < k; ++k2) {
               int l2 = (l1 * l + k2) * i;
               if ((aint[j2 + l2] >> 24 & 255) != 0) {
                  flag = false;
                  break;
               }
            }

            if (!flag) {
               break;
            }
         }

         ++i2;
         CHAR_WIDTH[j1] = (int)(0.5D + (double)((float)i2 * f)) + i1;
      }

   }

   public static int[] loadCharacters() {
      int[] characters = new int[256];
      Arrays.fill(characters, -1);
      char[] chars = "ÀÁÂÈÊËÍÓÔÕÚßãõğİıŒœŞşŴŵžȇ\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000ÇüéâäàåçêëèïîìÄÅÉæÆôöòûùÿÖÜø£Ø×ƒáíóúñÑªº¿®¬½¼¡«»░▒▓│┤╡╢╖╕╣║╗╝╜╛┐└┴┬├─┼╞╟╚╔╩╦╠═╬╧╨╤╥╙╘╒╓╫╪┘┌█▄▌▐▀αβΓπΣσμτΦΘΩδ∞∅∈∩≡±≥≤⌠⌡÷≈°∙·√ⁿ²■\u0000".toCharArray();

      for(int i = 0; i < chars.length; characters[chars[i]] = i++) {
      }

      return characters;
   }

   public static void drawCenteredString(String text, float x, float y, int color) {
      PizzaClient.mc.field_71466_p.func_175065_a(text, x - (float)PizzaClient.mc.field_71466_p.func_78256_a(text) / 2.0F, y, color, false);
   }

   public static void drawCenteredRainbowModMessage(float x, float y) {
      drawRainbowModMessage((int)(x - (float)PizzaClient.mc.field_71466_p.func_78256_a("PizzaClient > ") / 2.0F), (int)y);
   }

   public static void drawCenteredRainbowString(String text, float x, float y) {
      drawRainbowText(text, (int)(x - (float)PizzaClient.mc.field_71466_p.func_78256_a(text) / 2.0F), (int)y);
   }

   public static void drawString(String text, float x, float y, int color) {
      PizzaClient.mc.field_71466_p.func_175065_a(text, x, y, color, false);
   }

   public static void drawBackground(int width, int height) {
      Gui.func_73734_a(0, 0, width, height, (new Color(0, 0, 0, 90)).getRGB());
   }

   public static void drawBackground(int width, int height, int alpha) {
      Gui.func_73734_a(0, 0, width, height, (new Color(0, 0, 0, alpha)).getRGB());
   }

   public static void drawBackground(float width, float height) {
      Gui.func_73734_a(0, 0, (int)width, (int)height, (new Color(0, 0, 0, 90)).getRGB());
   }

   public static void drawRect(double left, double top, double right, double bottom, int color) {
      Gui.func_73734_a((int)left, (int)top, (int)right, (int)bottom, color);
   }

   public static void drawRect(double left, double top, double right, double bottom, Color color) {
      Gui.func_73734_a((int)left, (int)top, (int)right, (int)bottom, color.getRGB());
   }

   public static void drawRainbowText(String input, float x, float y) {
      drawRainbowText(input.toCharArray(), (int)x, (int)y);
   }

   public static void drawRainbowText(String input, int x, int y) {
      drawRainbowText(input.toCharArray(), x, y);
   }

   public static void drawRainbowText(char[] input, int x, int y) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getLocationFontTexture());
      posX = x;
      posY = y;
      long time = System.currentTimeMillis();
      int i;
      if (Character.isDigit(input[0])) {
         int color = Character.getNumericValue(input[0]);

         for(i = 1; i < input.length; ++i) {
            posX = (int)((float)posX + renderRainbowChar(input[i], time, color, true));
         }
      } else {
         char[] var9 = input;
         i = input.length;

         for(int var7 = 0; var7 < i; ++var7) {
            char ch = var9[var7];
            posX = (int)((float)posX + renderRainbowChar(ch, time, 0, true));
         }
      }

      GlStateManager.func_179117_G();
   }

   public static void drawRainbowText(char[] input, int x, int y, boolean shadow) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getLocationFontTexture());
      posX = x;
      posY = y;
      long time = System.currentTimeMillis();
      int i;
      if (Character.isDigit(input[0])) {
         int color = Character.getNumericValue(input[0]);

         for(i = 1; i < input.length; ++i) {
            posX = (int)((float)posX + renderRainbowChar(input[i], time, color, shadow));
         }
      } else {
         char[] var10 = input;
         i = input.length;

         for(int var8 = 0; var8 < i; ++var8) {
            char ch = var10[var8];
            posX = (int)((float)posX + renderRainbowChar(ch, time, 0, shadow));
         }
      }

      GlStateManager.func_179117_G();
   }

   public static float drawRainbowName(char[] input, int x, int y) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getLocationFontTexture());
      posX = x;
      posY = y;
      if (input[0] == '4') {
         drawColoredName(input);
      } else {
         long time = System.currentTimeMillis();
         int color = Character.getNumericValue(input[0]);

         for(int i = 1; i < input.length; ++i) {
            posX = (int)((float)posX + renderRainbowChar(input[i], time, color, true));
         }
      }

      GlStateManager.func_179117_G();
      return (float)posX;
   }

   public static float drawRainbowName(char[] input, int x, int y, boolean shadow) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getLocationFontTexture());
      posX = x;
      posY = y;
      if (input[0] == '4') {
         drawColoredName(input);
      } else {
         long time = System.currentTimeMillis();
         int color = Character.getNumericValue(input[0]);

         for(int i = 1; i < input.length; ++i) {
            posX = (int)((float)posX + renderRainbowChar(input[i], time, color, shadow));
         }
      }

      GlStateManager.func_179117_G();
      return (float)posX;
   }

   public static float drawRainbowModMessage(int y) {
      return drawRainbowModMessage(0, y);
   }

   public static float drawRainbowModMessage(int x, int y) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getLocationFontTexture());
      posX = x;
      posY = y;
      long time = System.currentTimeMillis();
      char[] var4 = modMessageChars;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         char c = var4[var6];
         posX = (int)((float)posX + renderRainbowChar(c, time, true));
      }

      posX = (int)((float)posX + 4.0F);
      char c = 62;
      posX = (int)((float)posX + renderDefaultModMessageChar(c));
      GlStateManager.func_179117_G();
      return (float)posX + 4.0F;
   }

   public static float drawRainbowModMessage(int x, int y, boolean shadow) {
      GlStateManager.func_179141_d();
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getLocationFontTexture());
      posX = x;
      posY = y;
      long time = System.currentTimeMillis();
      char[] var5 = modMessageChars;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         char c = var5[var7];
         posX = (int)((float)posX + renderRainbowChar(c, time, shadow));
      }

      posX = (int)((float)posX + 4.0F);
      char c = 62;
      posX = (int)((float)posX + renderDefaultModMessageChar(c));
      GlStateManager.func_179117_G();
      return (float)posX + 4.0F;
   }

   public static void renderChar(char ch) {
      if (ch != 160) {
         if (ch != ' ') {
            renderDefaultChar(ch);
         }
      }
   }

   public static void renderCharNoReturn(char ch) {
      if (ch != 160) {
         if (ch != ' ') {
            renderDefaultCharNoReturn(ch);
         }
      }
   }

   public static float renderDefaultChar(int ch) {
      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      int l = CHAR_WIDTH[ch];
      float f = (float)l - 0.01F;
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX + f - 1.0F, (float)posY, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX + f - 1.0F, (float)posY + 7.99F, 0.0F);
      GL11.glEnd();
      return (float)l;
   }

   public static float renderDefaultModMessageChar(int ch) {
      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      int l = CHAR_WIDTH[ch];
      float f = (float)l - 0.01F;
      ++posX;
      ++posY;
      GL11.glColor4f(0.0F, 0.25F, 0.25F, 1.0F);
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX + f - 1.0F, (float)posY, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX + f - 1.0F, (float)posY + 7.99F, 0.0F);
      GL11.glEnd();
      --posX;
      --posY;
      GL11.glColor3f(0.0F, 1.0F, 1.0F);
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX + f - 1.0F, (float)posY, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX + f - 1.0F, (float)posY + 7.99F, 0.0F);
      GL11.glEnd();
      return (float)l;
   }

   public static void renderDefaultCharNoReturn(int ch) {
      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      float f = (float)CHAR_WIDTH[ch] - 0.01F;
      GL11.glShadeModel(7425);
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX + f - 1.0F, (float)posY, 0.0F);
      GL11.glTexCoord2f(((float)i + f - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX + f - 1.0F, (float)posY + 7.99F, 0.0F);
      GL11.glEnd();
   }

   public static float renderRainbowChar(int ch, long time, boolean shadow) {
      int l = CHAR_WIDTH[ch];
      float width = (float)l - 0.01F;
      long y = (long)((float)posY * 11.0F);
      long position = time - ((long)((float)posX * 11.0F) - y);
      int color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
      float red;
      float blue;
      float green;
      float red2;
      float blue2;
      float green2;
      switch(PizzaClient.config.modMessageColor) {
      case 0:
         red = (float)(color >> 16 & 255) / 255.0F;
         blue = (float)(color >> 8 & 255) / 255.0F;
         green = (float)(color & 255) / 255.0F;
         position = time - ((long)((float)(posX + l) * 11.0F) - y);
         color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
         red2 = (float)(color >> 16 & 255) / 255.0F;
         blue2 = (float)(color >> 8 & 255) / 255.0F;
         green2 = (float)(color & 255) / 255.0F;
         break;
      case 1:
         red = 1.0F;
         blue = (float)(color >> 8 & 255) / 255.0F;
         green = (float)(color & 255) / 255.0F;
         position = time - ((long)((float)(posX + l) * 11.0F) - y);
         color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
         red2 = 1.0F;
         blue2 = (float)(color >> 8 & 255) / 255.0F;
         green2 = (float)(color & 255) / 255.0F;
         break;
      case 2:
         red = (float)(color >> 16 & 255) / 255.0F;
         blue = 1.0F;
         green = (float)(color & 255) / 255.0F;
         position = time - ((long)((float)(posX + l) * 11.0F) - y);
         color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
         red2 = (float)(color >> 16 & 255) / 255.0F;
         blue2 = 1.0F;
         green2 = (float)(color & 255) / 255.0F;
         break;
      default:
         red = (float)(color >> 16 & 255) / 255.0F;
         blue = (float)(color >> 8 & 255) / 255.0F;
         green = 1.0F;
         position = time - ((long)((float)(posX + l) * 11.0F) - y);
         color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
         red2 = (float)(color >> 16 & 255) / 255.0F;
         blue2 = (float)(color >> 8 & 255) / 255.0F;
         green2 = 1.0F;
      }

      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      if (shadow) {
         ++posX;
         ++posY;
         GL11.glColor4f(red / 4.0F, green / 4.0F, blue / 4.0F, 1.0F);
         GL11.glBegin(5);
         GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
         GL11.glVertex3f((float)posX, (float)posY, 0.0F);
         GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
         GL11.glVertex3f((float)posX, (float)posY + 7.99F, 0.0F);
         GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
         GL11.glVertex3f((float)posX + width - 1.0F, (float)posY, 0.0F);
         GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
         GL11.glVertex3f((float)posX + width - 1.0F, (float)posY + 7.99F, 0.0F);
         GL11.glEnd();
         --posX;
         --posY;
      }

      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      GL11.glColor3f(red, green, blue);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY + 7.99F, 0.0F);
      GL11.glColor3f(red2, green2, blue2);
      GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX + width - 1.0F, (float)posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX + width - 1.0F, (float)posY, 0.0F);
      GL11.glEnd();
      GL11.glShadeModel(7424);
      return (float)l;
   }

   public static float renderRainbowChar(int ch, long time, int rgb, boolean shadow) {
      if (ch != 32 && ch != 160) {
         if (ch > 255) {
            return 0.0F;
         } else {
            int l = CHAR_WIDTH[ch];
            float width = (float)l - 0.01F;
            long y = (long)((float)posY * 11.0F);
            long position = time - ((long)((float)posX * 11.0F) - y);
            int color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
            float red;
            float blue;
            float green;
            float red2;
            float blue2;
            float green2;
            switch(rgb) {
            case 0:
               red = (float)(color >> 16 & 255) / 255.0F;
               blue = (float)(color >> 8 & 255) / 255.0F;
               green = (float)(color & 255) / 255.0F;
               position = time - ((long)((float)(posX + l) * 11.0F) - y);
               color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
               red2 = (float)(color >> 16 & 255) / 255.0F;
               blue2 = (float)(color >> 8 & 255) / 255.0F;
               green2 = (float)(color & 255) / 255.0F;
               break;
            case 1:
               red = 1.0F;
               blue = (float)(color >> 8 & 255) / 255.0F;
               green = (float)(color & 255) / 255.0F;
               position = time - ((long)((float)(posX + l) * 11.0F) - y);
               color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
               red2 = 1.0F;
               blue2 = (float)(color >> 8 & 255) / 255.0F;
               green2 = (float)(color & 255) / 255.0F;
               break;
            case 2:
               red = (float)(color >> 16 & 255) / 255.0F;
               blue = 1.0F;
               green = (float)(color & 255) / 255.0F;
               position = time - ((long)((float)(posX + l) * 11.0F) - y);
               color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
               red2 = (float)(color >> 16 & 255) / 255.0F;
               blue2 = 1.0F;
               green2 = (float)(color & 255) / 255.0F;
               break;
            default:
               red = (float)(color >> 16 & 255) / 255.0F;
               blue = (float)(color >> 8 & 255) / 255.0F;
               green = 1.0F;
               position = time - ((long)((float)(posX + l) * 11.0F) - y);
               color = Color.HSBtoRGB((float)(position % 3000L) / 3000.0F, PizzaClient.config.rgbBrightness, 1.0F);
               red2 = (float)(color >> 16 & 255) / 255.0F;
               blue2 = (float)(color >> 8 & 255) / 255.0F;
               green2 = 1.0F;
            }

            int i = ch % 16 * 8;
            int j = ch / 16 * 8;
            if (shadow) {
               ++posX;
               ++posY;
               GL11.glColor4f(red / 4.0F, green / 4.0F, blue / 4.0F, 1.0F);
               GL11.glBegin(5);
               GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
               GL11.glVertex3f((float)posX, (float)posY, 0.0F);
               GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
               GL11.glVertex3f((float)posX, (float)posY + 7.99F, 0.0F);
               GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
               GL11.glVertex3f((float)posX + width - 1.0F, (float)posY, 0.0F);
               GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
               GL11.glVertex3f((float)posX + width - 1.0F, (float)posY + 7.99F, 0.0F);
               GL11.glEnd();
               --posX;
               --posY;
            }

            GL11.glShadeModel(7425);
            GL11.glBegin(7);
            GL11.glColor3f(red, green, blue);
            GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
            GL11.glVertex3f((float)posX, (float)posY, 0.0F);
            GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
            GL11.glVertex3f((float)posX, (float)posY + 7.99F, 0.0F);
            GL11.glColor3f(red2, green2, blue2);
            GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
            GL11.glVertex3f((float)posX + width - 1.0F, (float)posY + 7.99F, 0.0F);
            GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
            GL11.glVertex3f((float)posX + width - 1.0F, (float)posY, 0.0F);
            GL11.glEnd();
            GL11.glShadeModel(7424);
            return (float)l;
         }
      } else {
         return 4.0F;
      }
   }

   public static int getStringWidth(char[] chars) {
      int i = 0;
      boolean flag = false;

      for(int j = 0; j < chars.length; ++j) {
         char c0 = chars[j];
         int k = getCharWidth(c0);
         if (k < 0 && j < chars.length - 1) {
            ++j;
            c0 = chars[j];
            if (c0 != 'l' && c0 != 'L') {
               if (c0 == 'r' || c0 == 'R') {
                  flag = false;
               }
            } else {
               flag = true;
            }

            k = 0;
         }

         i += k;
         if (flag && k > 0) {
            ++i;
         }
      }

      return i;
   }

   public static int getRainbowStringWidth(char[] chars) {
      int i = 0;
      boolean flag = false;

      for(int j = 1; j < chars.length; ++j) {
         char c0 = chars[j];
         int k = getCharWidth(c0);
         if (k < 0 && j < chars.length - 1) {
            ++j;
            c0 = chars[j];
            if (c0 != 'l' && c0 != 'L') {
               if (c0 == 'r' || c0 == 'R') {
                  flag = false;
               }
            } else {
               flag = true;
            }

            k = 0;
         }

         i += k;
         if (flag && k > 0) {
            ++i;
         }
      }

      return i;
   }

   public static int getCharWidth(char ch) {
      if (ch == ' ') {
         return 4;
      } else {
         return ch > 255 ? fontRenderer.getGlyphWidths()[ch] : CHAR_WIDTH[ch];
      }
   }

   public static void bindUnicodeTexture(int page) {
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getUnicodePageLocation(page));
   }

   public static float drawColoredName(char[] chars, int x) {
      PizzaClient.mc.field_71446_o.func_110577_a(fontRenderer.getLocationFontTexture());
      posX = x;
      return drawColoredName(chars);
   }

   public static float drawColoredName(char[] chars) {
      drawShadowedText(chars);

      for(int i = 1; i < chars.length; ++i) {
         char ch = chars[i];
         if (ch == ' ') {
            posX = (int)((float)posX + 4.0F);
         } else if (ch == 167) {
            int color = colorCode["0123456789abcdefklmnor".indexOf(chars[i + 1])];
            GL11.glColor3f((float)(color >> 16) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F);
            ++i;
         } else {
            posX = (int)((float)posX + renderDefaultChar(ch));
         }
      }

      GlStateManager.func_179124_c(1.0F, 1.0F, 1.0F);
      return (float)posX;
   }

   public static void drawShadowedText(char[] chars) {
      int originX = posX;

      for(int i = 1; i < chars.length; ++i) {
         char ch = chars[i];
         if (ch == ' ') {
            posX = (int)((float)posX + 4.0F);
         } else if (ch == 167) {
            int color = colorCode["0123456789abcdefklmnor".indexOf(chars[i + 1]) + 16];
            GL11.glColor3f((float)(color >> 16) / 1020.0F, (float)(color >> 8 & 255) / 1020.0F, (float)(color & 255) / 1020.0F);
            ++i;
         } else {
            posX = (int)((float)posX + drawShadowedChar(ch));
         }
      }

      posX = originX;
   }

   public static float drawShadowedChar(char ch) {
      int l = CHAR_WIDTH[ch];
      float width = (float)l - 0.01F;
      int i = ch % 16 * 8;
      int j = ch / 16 * 8;
      ++posX;
      ++posY;
      GL11.glBegin(5);
      GL11.glTexCoord2f((float)i / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY, 0.0F);
      GL11.glTexCoord2f((float)i / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX, (float)posY + 7.99F, 0.0F);
      GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, (float)j / 128.0F);
      GL11.glVertex3f((float)posX + width - 1.0F, (float)posY, 0.0F);
      GL11.glTexCoord2f(((float)i + width - 1.0F) / 128.0F, ((float)j + 7.99F) / 128.0F);
      GL11.glVertex3f((float)posX + width - 1.0F, (float)posY + 7.99F, 0.0F);
      GL11.glEnd();
      --posX;
      --posY;
      return (float)l;
   }

   public static float renderShadowedString(String text, int x, int y, int color) {
      GlStateManager.func_179141_d();
      fontRenderer.resetStyles();
      return (float)Math.max(fontRenderer.renderString(text, (float)x + 1.0F, (float)y + 1.0F, color, true), fontRenderer.renderString(text, (float)x, (float)y, color, false));
   }

   public static float renderString(String text, int x, int y, int color, boolean shadow) {
      GlStateManager.func_179141_d();
      fontRenderer.resetStyles();
      return shadow ? (float)Math.max(fontRenderer.renderString(text, (float)x + 1.0F, (float)y + 1.0F, color, true), fontRenderer.renderString(text, (float)x, (float)y, color, false)) : (float)fontRenderer.renderString(text, (float)x, (float)y, color, false);
   }

   static {
      fontRenderer = (AccessorFontRenderer)PizzaClient.mc.field_71466_p;
   }
}
