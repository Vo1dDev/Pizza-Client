package qolskyblockmod.pizzaclient.util;

import java.awt.Color;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Slot;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;

public class RenderUtil {
   public static final RenderManager renderManager;

   public static void drawFilledEsp(AxisAlignedBB aabb, Color c, float alphaMultiplier) {
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
      drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
   }

   public static void drawFilledEsp(AxisAlignedBB aabb, Color c) {
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
   }

   public static void drawFilledBoxNoESP(AxisAlignedBB aabb, Color c, float alphaMultiplier) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
      drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledBoxNoESP(AxisAlignedBB aabb, Color c) {
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawFullAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledEsp(BlockPos pos, Color c, float alphaMultiplier) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
      drawFullAABB(block.func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledEsp(BlockPos pos, Color c, float alphaMultiplier, float expand) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F * alphaMultiplier);
      drawFullAABB(block.func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72314_b((double)expand, (double)expand, (double)expand).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawFilledEsp(BlockPos pos, Color c) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawFullAABB(block.func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179126_j();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
   }

   public static void drawOutlinedEsp(BlockPos pos, Color c, float width) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawOutlinedAABB(block.func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
   }

   public static void drawOutlinedEsp(BlockPos pos, Color c) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179097_i();
      GlStateManager.func_179140_f();
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawOutlinedAABB(block.func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
   }

   public static void drawOutlinedBoxNoESP(BlockPos pos, Color c, float width) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawOutlinedAABB(block.func_180646_a(PizzaClient.mc.field_71441_e, pos).func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179098_w();
      GlStateManager.func_179126_j();
   }

   public static void drawOutlinedEsp(AxisAlignedBB aabb, Color c, float width) {
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      GlStateManager.func_179131_c((float)c.getRed() / 255.0F, (float)c.getGreen() / 255.0F, (float)c.getBlue() / 255.0F, (float)c.getAlpha() / 255.0F);
      drawOutlinedAABB(aabb.func_72317_d(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n));
      GlStateManager.func_179098_w();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
   }

   private static void drawOutlinedAABB(AxisAlignedBB boundingBox) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72340_a, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72337_e, boundingBox.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(boundingBox.field_72336_d, boundingBox.field_72338_b, boundingBox.field_72339_c).func_181675_d();
      tessellator.func_78381_a();
   }

   private static void drawFullAABB(AxisAlignedBB aabb) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldrenderer = tessellator.func_178180_c();
      worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181705_e);
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72340_a, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72339_c).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72337_e, aabb.field_72334_f).func_181675_d();
      worldrenderer.func_181662_b(aabb.field_72336_d, aabb.field_72338_b, aabb.field_72334_f).func_181675_d();
      tessellator.func_78381_a();
   }

   public static void drawOnSlot(Slot slot, int color) {
      Gui.func_73734_a(slot.field_75223_e, slot.field_75221_f, slot.field_75223_e + 16, slot.field_75221_f + 16, color);
   }

   public static void drawOnSlot(Slot slot, Color color) {
      Gui.func_73734_a(slot.field_75223_e, slot.field_75221_f, slot.field_75223_e + 16, slot.field_75221_f + 16, color.getRGB());
   }

   public static void drawRainbowPath(List<BetterBlockPos> positions, float width) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int i = positions.size() - 1; i >= 0; --i) {
         BetterBlockPos pos = (BetterBlockPos)positions.get(i);
         int color = Color.HSBtoRGB((float)((time - (long)(i * 60)) % 5000L) / 5000.0F, 0.75F, 1.0F);
         worldRenderer.func_181662_b((double)pos.field_177962_a + 0.5D, (double)pos.field_177960_b + 0.1D, (double)pos.field_177961_c + 0.5D).func_181666_a((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 1.0F).func_181675_d();
      }

      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
      GL11.glShadeModel(7424);
   }

   public static void drawRainbowPath(List<BetterBlockPos> positions) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int i = positions.size() - 1; i >= 0; --i) {
         BetterBlockPos pos = (BetterBlockPos)positions.get(i);
         int color = Color.HSBtoRGB((float)((time - (long)(i * 60)) % 5000L) / 5000.0F, 0.75F, 1.0F);
         worldRenderer.func_181662_b((double)pos.field_177962_a + 0.5D, (double)pos.field_177960_b + 0.1D, (double)pos.field_177961_c + 0.5D).func_181666_a((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 1.0F).func_181675_d();
      }

      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
      GL11.glShadeModel(7424);
   }

   public static void drawRainbowLines(List<Vec3> positions) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int i = positions.size() - 1; i >= 0; --i) {
         Vec3 pos = (Vec3)positions.get(i);
         int color = Color.HSBtoRGB((float)((time - (long)(i * 60)) % 5000L) / 5000.0F, 0.75F, 1.0F);
         worldRenderer.func_181662_b(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c).func_181666_a((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 1.0F).func_181675_d();
      }

      tessellator.func_78381_a();
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
      GL11.glShadeModel(7424);
   }

   public static void drawRainbowLines(List<Vec3> positions, float width) {
      Tessellator tessellator = Tessellator.func_178181_a();
      WorldRenderer worldRenderer = tessellator.func_178180_c();
      long time = System.currentTimeMillis();
      GL11.glShadeModel(7425);
      GL11.glTranslated(-renderManager.field_78730_l, -renderManager.field_78731_m, -renderManager.field_78728_n);
      GlStateManager.func_179097_i();
      GlStateManager.func_179147_l();
      GlStateManager.func_179090_x();
      GlStateManager.func_179140_f();
      GL11.glLineWidth(width);
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181706_f);

      for(int i = positions.size() - 1; i >= 0; --i) {
         Vec3 pos = (Vec3)positions.get(i);
         int color = Color.HSBtoRGB((float)((time - (long)(i * 60)) % 5000L) / 5000.0F, 0.75F, 1.0F);
         worldRenderer.func_181662_b(pos.field_72450_a, pos.field_72448_b, pos.field_72449_c).func_181666_a((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, 1.0F).func_181675_d();
      }

      tessellator.func_78381_a();
      GL11.glLineWidth(1.0F);
      GlStateManager.func_179098_w();
      GlStateManager.func_179084_k();
      GlStateManager.func_179126_j();
      GL11.glTranslated(renderManager.field_78730_l, renderManager.field_78731_m, renderManager.field_78728_n);
      GL11.glShadeModel(7424);
   }

   static {
      renderManager = PizzaClient.mc.func_175598_ae();
   }
}
