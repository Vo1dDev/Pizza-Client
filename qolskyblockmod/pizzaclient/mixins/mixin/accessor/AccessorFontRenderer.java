package qolskyblockmod.pizzaclient.mixins.mixin.accessor;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({FontRenderer.class})
public interface AccessorFontRenderer {
   @Accessor("glyphWidth")
   byte[] getGlyphWidths();

   @Accessor("locationFontTexture")
   ResourceLocation getLocationFontTexture();

   @Invoker("getUnicodePageLocation")
   ResourceLocation getUnicodePageLocation(int var1);

   @Accessor("colorCode")
   int[] getColorCodes();

   @Invoker("resetStyles")
   void resetStyles();

   @Invoker("renderString")
   int renderString(String var1, float var2, float var3, int var4, boolean var5);
}
