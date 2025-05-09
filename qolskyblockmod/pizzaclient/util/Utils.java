package qolskyblockmod.pizzaclient.util;

import com.google.common.collect.Iterables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.authlib.properties.Property;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.Vec3;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.features.macros.ai.pathfinding.util.BetterBlockPos;

public class Utils {
   public static final Random random = new Random();
   public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
   public static final Set<Block> uncollidables;
   public static final String ERROR_MESSAGE;
   public static final String SUCCESS_MESSAGE;
   public static final DateFormat DATE_FORMAT;

   public static boolean isInTablist(EntityPlayer player) {
      Iterator var1 = PizzaClient.mc.func_147114_u().func_175106_d().iterator();

      NetworkPlayerInfo pi;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         pi = (NetworkPlayerInfo)var1.next();
      } while(!pi.func_178845_a().getName().equalsIgnoreCase(player.func_70005_c_()));

      return true;
   }

   public static String getColouredBoolean(boolean bool) {
      return bool ? EnumChatFormatting.GREEN + "On" : EnumChatFormatting.RED + "Off";
   }

   public static void sleep(int sleeptime) {
      try {
         Thread.sleep((long)sleeptime);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }

   }

   public static void playSound(String sound, double pitch) {
      PizzaClient.mc.field_71439_g.func_85030_a(sound, 1.0F, (float)pitch);
   }

   public static void playOrbSound(double pitch) {
      PizzaClient.mc.field_71439_g.func_85030_a("random.orb", 1.0F, (float)pitch);
   }

   public static void playOrbSound() {
      PizzaClient.mc.field_71439_g.func_85030_a("random.orb", 1.0F, 0.5F);
   }

   public static void writeToClipboard(String text, String successMessage) {
      try {
         Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         StringSelection output = new StringSelection(text);
         clipboard.setContents(output, output);
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + successMessage));
      } catch (IllegalStateException var4) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "Clipboard not available!"));
         var4.printStackTrace();
      }

   }

   public static void writeToClipboard(String text) {
      try {
         Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         StringSelection output = new StringSelection(text);
         clipboard.setContents(output, output);
      } catch (Exception var3) {
         FMLLog.getLogger().log(Level.ERROR, "Failed to copy to clipboard.");
         var3.printStackTrace();
      }

   }

   public static int betterRandom(int randomness) {
      int randomNumb;
      for(randomNumb = 0; randomness > 0; --randomness) {
         randomNumb = (int)((double)randomNumb + random.nextGaussian() * (double)randomness);
      }

      return randomNumb;
   }

   public static <T> T firstOrNull(Iterable<T> iterable) {
      return Iterables.getFirst(iterable, (Object)null);
   }

   public static boolean isWitherEssence(TileEntitySkull skull) {
      if (skull.func_145904_a() != 3) {
         return false;
      } else {
         Property property = (Property)firstOrNull(skull.func_152108_a().getProperties().get("textures"));
         return property != null && property.getValue().equals("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzRkYjRhZGZhOWJmNDhmZjVkNDE3MDdhZTM0ZWE3OGJkMjM3MTY1OWZjZDhjZDg5MzQ3NDlhZjRjY2U5YiJ9fX0=");
      }
   }

   public static boolean isSkullTexture(TileEntitySkull skull, String texture) {
      if (skull.func_145904_a() != 3) {
         return false;
      } else {
         Property property = (Property)firstOrNull(skull.func_152108_a().getProperties().get("textures"));
         return property != null && property.getValue().equals(texture);
      }
   }

   public static String readURL(URL url) throws IOException {
      StringBuilder sb = new StringBuilder();
      InputStream in = url.openStream();
      Throwable var4 = null;

      try {
         BufferedReader reader = new BufferedReader(new InputStreamReader(in));

         String line;
         while((line = reader.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
         }
      } catch (Throwable var13) {
         var4 = var13;
         throw var13;
      } finally {
         if (in != null) {
            if (var4 != null) {
               try {
                  in.close();
               } catch (Throwable var12) {
                  var4.addSuppressed(var12);
               }
            } else {
               in.close();
            }
         }

      }

      return sb.toString();
   }

   public static String readFile(String file) throws IOException {
      StringBuilder sb = new StringBuilder();
      BufferedReader reader = new BufferedReader(new FileReader(file));

      String line;
      while((line = reader.readLine()) != null) {
         sb.append(line).append(System.lineSeparator());
      }

      return sb.toString();
   }

   public static String readFile(File file) {
      try {
         StringBuilder sb = new StringBuilder();
         BufferedReader reader = new BufferedReader(new FileReader(file));

         String line;
         while((line = reader.readLine()) != null) {
            sb.append(line).append(System.lineSeparator());
         }

         return sb.toString();
      } catch (IOException var4) {
         var4.printStackTrace();
         return "";
      }
   }

   public static String readURL(String url) {
      try {
         StringBuilder sb = new StringBuilder();
         URLConnection connection = (new URL(url)).openConnection();
         connection.setRequestProperty("User-Agent", "Mozilla/5.0");
         InputStream in = connection.getInputStream();
         Throwable var5 = null;

         try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while((line = reader.readLine()) != null) {
               sb.append(line).append(System.lineSeparator());
            }
         } catch (Throwable var15) {
            var5 = var15;
            throw var15;
         } finally {
            if (in != null) {
               if (var5 != null) {
                  try {
                     in.close();
                  } catch (Throwable var14) {
                     var5.addSuppressed(var14);
                  }
               } else {
                  in.close();
               }
            }

         }

         return sb.toString();
      } catch (Exception var17) {
         var17.printStackTrace();
         return "";
      }
   }

   public static void openUrl(String url) {
      try {
         Desktop.getDesktop().browse(new URI(url));
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public static String[] getStringArrayFromJsonArray(JsonArray jsonArray) {
      int arraySize = jsonArray.size();
      String[] stringArray = new String[arraySize];

      for(int i = 0; i < arraySize; ++i) {
         stringArray[i] = jsonArray.get(i).getAsString();
      }

      return stringArray;
   }

   public static List<String> getStringListFromJsonArray(JsonArray jsonArray) {
      List<String> list = new ArrayList();

      for(int i = 0; i < jsonArray.size(); ++i) {
         list.add(jsonArray.get(i).getAsString());
      }

      return list;
   }

   public static Set<String> getStringSetFromJsonArray(JsonArray jsonArray) {
      Set<String> strings = new HashSet();

      for(int i = 0; i < jsonArray.size(); ++i) {
         strings.add(jsonArray.get(i).getAsString());
      }

      return strings;
   }

   public static Vec3 scaleVec(Vec3 vec, double scale) {
      return new Vec3(vec.field_72450_a * scale, vec.field_72448_b * scale, vec.field_72449_c * scale);
   }

   public static Vec3 divideVec(Vec3 vec, double scale) {
      return new Vec3(vec.field_72450_a / scale, vec.field_72448_b / scale, vec.field_72449_c / scale);
   }

   public static boolean isJar() {
      try {
         Minecraft.class.getDeclaredMethod("func_147121_ag");
         return true;
      } catch (Exception var1) {
         return false;
      }
   }

   public static JsonElement getJson(String jsonUrl) {
      return (new JsonParser()).parse(getInputStream(jsonUrl));
   }

   public static JsonElement getJson(File file) {
      return (new JsonParser()).parse(readFile(file));
   }

   public static JsonElement getJson(URL url) {
      try {
         URLConnection conn = url.openConnection();
         conn.setRequestProperty("User-Agent", "Mozilla/5.0");
         return (new JsonParser()).parse(new InputStreamReader(conn.getInputStream()));
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static InputStreamReader getInputStream(String url) {
      try {
         URLConnection conn = (new URL(url)).openConnection();
         conn.setRequestProperty("User-Agent", "Mozilla/5.0");
         return new InputStreamReader(conn.getInputStream());
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static InputStreamReader getInputStream(URL url) {
      try {
         URLConnection conn = url.openConnection();
         conn.setRequestProperty("User-Agent", "Mozilla/5.0");
         return new InputStreamReader(conn.getInputStream());
      } catch (Exception var2) {
         var2.printStackTrace();
         return null;
      }
   }

   public static String formatValue(long amount) {
      if (amount >= 1000000000L) {
         return formatValue(amount, 1000000000L, 'b');
      } else if (amount >= 1000000L) {
         return formatValue(amount, 1000000L, 'm');
      } else {
         return amount >= 1000L ? formatValue(amount, 1000L, 'k') : NumberFormat.getInstance().format(amount);
      }
   }

   private static String formatValue(long amount, long div, char suffix) {
      return (new DecimalFormat(".##")).format((double)amount / (double)div) + suffix;
   }

   public static boolean isGoldenGoblin(EntityOtherPlayerMP entityIn) {
      ItemStack[] var1 = entityIn.field_71071_by.field_70460_b;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ItemStack item = var1[var3];
         if (item == null) {
            return false;
         }

         Item armor = item.func_77973_b();
         if (armor != Items.field_151169_ag && armor != Items.field_151171_ah && armor != Items.field_151149_ai && armor != Items.field_151151_aj) {
            return false;
         }
      }

      return true;
   }

   public static AxisAlignedBB getBlockBoundingBox(BlockPos pos) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      return block.func_180646_a(PizzaClient.mc.field_71441_e, pos);
   }

   public static double getXandZDistance(double x1, double x2, double z1, double z2) {
      double diffX = x1 - x2;
      double diffZ = z1 - z2;
      return Math.sqrt(diffX * diffX + diffZ * diffZ);
   }

   public static double getXandZDistanceSquared(double x1, double x2, double z1, double z2) {
      double diffX = x1 - x2;
      double diffZ = z1 - z2;
      return diffX * diffX + diffZ * diffZ;
   }

   public static double getXandZDistance(Vec3 position, Vec3 goal) {
      double diffX = position.field_72450_a - goal.field_72450_a;
      double diffZ = position.field_72449_c - goal.field_72449_c;
      return Math.sqrt(diffX * diffX + diffZ * diffZ);
   }

   public static double getXandZDistanceSquared(Vec3 position, Vec3 goal) {
      double diffX = position.field_72450_a - goal.field_72450_a;
      double diffZ = position.field_72449_c - goal.field_72449_c;
      return diffX * diffX + diffZ * diffZ;
   }

   public static double getXandZDistance(double x, double z) {
      double diffX = PizzaClient.mc.field_71439_g.field_70165_t - x;
      double diffZ = PizzaClient.mc.field_71439_g.field_70161_v - z;
      return Math.sqrt(diffX * diffX + diffZ * diffZ);
   }

   public static double getXandZDistance(Vec3 vec) {
      double diffX = PizzaClient.mc.field_71439_g.field_70165_t - vec.field_72450_a;
      double diffZ = PizzaClient.mc.field_71439_g.field_70161_v - vec.field_72449_c;
      return Math.sqrt(diffX * diffX + diffZ * diffZ);
   }

   public static double getXandZDistanceSquared(double x, double z) {
      double diffX = PizzaClient.mc.field_71439_g.field_70165_t - x;
      double diffZ = PizzaClient.mc.field_71439_g.field_70161_v - z;
      return diffX * diffX + diffZ * diffZ;
   }

   public static double getXandZDistanceSquared(Vec3 vec) {
      double diffX = PizzaClient.mc.field_71439_g.field_70165_t - vec.field_72450_a;
      double diffZ = PizzaClient.mc.field_71439_g.field_70161_v - vec.field_72449_c;
      return diffX * diffX + diffZ * diffZ;
   }

   public static Vec3 getMiddleOfAABB(AxisAlignedBB aabb) {
      return new Vec3((aabb.field_72336_d + aabb.field_72340_a) / 2.0D, (aabb.field_72337_e + aabb.field_72338_b) / 2.0D, (aabb.field_72334_f + aabb.field_72339_c) / 2.0D);
   }

   public static Vec3 getMiddleOfAABB(BlockPos pos) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      return getMiddleOfAABB(block.func_180646_a(PizzaClient.mc.field_71441_e, pos));
   }

   public static AxisAlignedBB getBlockAABB(BlockPos pos) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      return block.func_180646_a(PizzaClient.mc.field_71441_e, pos);
   }

   public static AxisAlignedBB getBlockAABB(Vec3 vec) {
      BlockPos pos = new BlockPos(vec);
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      return block.func_180646_a(PizzaClient.mc.field_71441_e, pos);
   }

   public static boolean isAdjacentToWater(BlockPos pos) {
      return PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n() - 1, pos.func_177956_o(), pos.func_177952_p())).func_177230_c() == Blocks.field_150355_j || PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n() + 1, pos.func_177956_o(), pos.func_177952_p())).func_177230_c() == Blocks.field_150355_j || PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() - 1)).func_177230_c() == Blocks.field_150355_j || PizzaClient.mc.field_71441_e.func_180495_p(new BlockPos(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p() + 1)).func_177230_c() == Blocks.field_150355_j;
   }

   public static BlockPos getBlockFromHit(MovingObjectPosition hit) {
      switch(hit.field_178784_b) {
      case UP:
         return new BlockPos(hit.field_72307_f.field_72450_a, hit.field_72307_f.field_72448_b - 1.0D, hit.field_72307_f.field_72449_c);
      case EAST:
         return new BlockPos(hit.field_72307_f.field_72450_a - 1.0D, hit.field_72307_f.field_72448_b, hit.field_72307_f.field_72449_c);
      case SOUTH:
         return new BlockPos(hit.field_72307_f.field_72450_a, hit.field_72307_f.field_72448_b, hit.field_72307_f.field_72449_c - 1.0D);
      default:
         return new BlockPos(hit.field_72307_f);
      }
   }

   public static EnumFacing getRightEnumfacing(EnumFacing facing) {
      switch(facing) {
      case EAST:
         return EnumFacing.SOUTH;
      case SOUTH:
         return EnumFacing.WEST;
      case NORTH:
         return EnumFacing.EAST;
      case WEST:
         return EnumFacing.NORTH;
      default:
         return EnumFacing.NORTH;
      }
   }

   public static EnumFacing getLeftEnumfacing(EnumFacing facing) {
      switch(facing) {
      case EAST:
         return EnumFacing.NORTH;
      case SOUTH:
         return EnumFacing.EAST;
      case NORTH:
         return EnumFacing.WEST;
      case WEST:
         return EnumFacing.SOUTH;
      default:
         return EnumFacing.NORTH;
      }
   }

   public static String getEnumfacingString(EnumFacing enumfacing) {
      switch(enumfacing) {
      case EAST:
         return "Towards positive X";
      case SOUTH:
         return "Towards positive Z";
      case NORTH:
         return "Towards negative Z";
      case WEST:
         return "Towards negative X";
      default:
         return "Invalid";
      }
   }

   public static void addToggleMessage(String name, boolean condition) {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + name + " is now " + (condition ? EnumChatFormatting.GREEN + "On" : EnumChatFormatting.RED + "Off")));
   }

   public static boolean isBlockInAir(BlockPos pos) {
      pos = pos.func_177977_b();
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
      block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
      return uncollidables.contains(block);
   }

   public static boolean isBlockBlocked(BlockPos pos) {
      Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c();
      if (block.func_149730_j() && !uncollidables.contains(block)) {
         block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c();
         if (block.func_149730_j() && !uncollidables.contains(block)) {
            block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c();
            if (block.func_149730_j() && !uncollidables.contains(block)) {
               block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c();
               if (block.func_149730_j() && !uncollidables.contains(block)) {
                  block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c();
                  if (block.func_149730_j() && !uncollidables.contains(block)) {
                     block = PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c();
                     return block.func_149730_j() && !uncollidables.contains(block);
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean isAdjacentToUncollidable(BlockPos pos) {
      if (PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177978_c()).func_177230_c() != Blocks.field_150350_a) {
         return true;
      } else if (PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177974_f()).func_177230_c() != Blocks.field_150350_a) {
         return true;
      } else if (PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177968_d()).func_177230_c() != Blocks.field_150350_a) {
         return true;
      } else {
         return PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177976_e()).func_177230_c() != Blocks.field_150350_a;
      }
   }

   public static String getContainerName() {
      return ((ContainerChest)PizzaClient.mc.field_71439_g.field_71070_bA).func_85151_d().func_145748_c_().func_150260_c();
   }

   public static String getContainerNameTrimmed() {
      return StringUtils.func_76338_a(((ContainerChest)PizzaClient.mc.field_71439_g.field_71070_bA).func_85151_d().func_145748_c_().func_150260_c().trim());
   }

   public static String getFormattedDate() {
      Date date = new Date();
      return (new SimpleDateFormat("dd/MM/yyyy")).format(date) + ", at " + (new SimpleDateFormat("HH:mm aa")).format(date);
   }

   public static InputStream getResourceInputStream(ResourceLocation location) throws IOException {
      return PizzaClient.mc.func_110442_L().func_110536_a(location).func_110527_b();
   }

   public static boolean isChatOpen() {
      return PizzaClient.mc.field_71462_r instanceof GuiChat;
   }

   public static int getChatWidth() {
      return calculateChatboxWidth(PizzaClient.mc.field_71474_y.field_96692_F);
   }

   public static int getChatHeight() {
      return calculateChatboxHeight(isChatOpen() ? PizzaClient.mc.field_71474_y.field_96694_H : PizzaClient.mc.field_71474_y.field_96693_G);
   }

   public static float getChatScale() {
      return PizzaClient.mc.field_71474_y.field_96691_E;
   }

   public static int calculateChatboxWidth(float scale) {
      int i = 320;
      int j = 40;
      return MathHelper.func_76141_d(scale * (float)(i - j) + (float)j);
   }

   public static int calculateChatboxHeight(float scale) {
      int i = 180;
      int j = 20;
      return MathHelper.func_76141_d(scale * (float)(i - j) + (float)j);
   }

   public static int getLineCount() {
      return getChatHeight() / 9;
   }

   public static BufferedImage readBufferedImage(ResourceLocation location) throws IOException {
      return TextureUtil.func_177053_a(PizzaClient.mc.func_110442_L().func_110536_a(location).func_110527_b());
   }

   public static Vec3 toVec(BlockPos pos) {
      return new Vec3((double)pos.func_177958_n() + 0.5D, (double)pos.func_177956_o() + 0.5D, (double)pos.func_177952_p() + 0.5D);
   }

   /** @deprecated */
   @Deprecated
   public static boolean onGround() {
      if (!PizzaClient.mc.field_71439_g.field_70122_E) {
         return false;
      } else {
         Vec3 player = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
         BlockPos pos = (new BlockPos(player.field_72450_a, (double)MathUtil.ceil(player.field_72448_b), player.field_72449_c)).func_177977_b();
         Block block = PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c();
         block.func_180654_a(PizzaClient.mc.field_71441_e, pos);
         return !uncollidables.contains(block);
      }
   }

   public static int getSkyblockYear() {
      return MathUtil.floor((double)System.currentTimeMillis() / 1000.0D - 1.560276E9D) / 446400 + 1;
   }

   public static <T> T getLastElement(List<T> list) {
      return list.get(list.size() - 1);
   }

   public static double formatDouble(double value) {
      return (double)MathUtil.round(value * 10000.0D) / 10000.0D;
   }

   public static String takeScreenshot(String screenshotName) {
      try {
         Framebuffer buffer = PizzaClient.mc.func_147110_a();
         int width = PizzaClient.mc.field_71443_c;
         int height = PizzaClient.mc.field_71440_d;
         File directory = new File(PizzaClient.modDir, "screenshots");
         if (!directory.exists()) {
            directory.mkdir();
         }

         if (OpenGlHelper.func_148822_b()) {
            width = buffer.field_147622_a;
            height = buffer.field_147620_b;
         }

         int i = width * height;
         IntBuffer pixelBuffer = BufferUtils.createIntBuffer(i);
         int[] pixelValues = new int[i];
         GL11.glPixelStorei(3333, 1);
         GL11.glPixelStorei(3317, 1);
         pixelBuffer.clear();
         if (OpenGlHelper.func_148822_b()) {
            GlStateManager.func_179144_i(buffer.field_147617_g);
            GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
         } else {
            GL11.glReadPixels(0, 0, width, height, 32993, 33639, pixelBuffer);
         }

         pixelBuffer.get(pixelValues);
         TextureUtil.func_147953_a(pixelValues, width, height);
         BufferedImage bufferedimage;
         if (OpenGlHelper.func_148822_b()) {
            bufferedimage = new BufferedImage(buffer.field_147621_c, buffer.field_147618_d, 1);
            int j = buffer.field_147620_b - buffer.field_147618_d;

            for(int k = j; k < buffer.field_147620_b; ++k) {
               for(int l = 0; l < buffer.field_147621_c; ++l) {
                  bufferedimage.setRGB(l, k - j, pixelValues[k * buffer.field_147622_a + l]);
               }
            }
         } else {
            bufferedimage = new BufferedImage(width, height, 1);
            bufferedimage.setRGB(0, 0, width, height, pixelValues, 0, width);
         }

         File file2 = screenshotName == null ? getTimestampedPNGFileForDirectory(directory) : new File(directory, screenshotName);
         ImageIO.write(bufferedimage, "png", file2);
         return file2.getAbsolutePath();
      } catch (Exception var12) {
         var12.printStackTrace();
         return null;
      }
   }

   public static File getTimestampedPNGFileForDirectory(File gameDirectory) {
      String s = DATE_FORMAT.format(new Date());
      int i = 1;

      while(true) {
         File file1 = new File(gameDirectory, s + (i == 1 ? "" : "_" + i) + ".png");
         if (!file1.exists()) {
            return file1;
         }

         ++i;
      }
   }

   public static float formatFloat(float value) {
      return (float)MathUtil.round(value * 1000.0F) / 1000.0F;
   }

   public static Set<String> getModIDs() {
      Set<String> ids = new HashSet();
      Iterator var1 = Loader.instance().getActiveModList().iterator();

      while(var1.hasNext()) {
         ModContainer container = (ModContainer)var1.next();
         ids.add(container.getModId());
      }

      return ids;
   }

   public static List<BlockPos> getAdjacents(BlockPos pos) {
      List<BlockPos> adjacents = new ArrayList();
      adjacents.add(pos.func_177984_a());
      adjacents.add(pos.func_177977_b());
      adjacents.add(pos.func_177978_c());
      adjacents.add(pos.func_177974_f());
      adjacents.add(pos.func_177968_d());
      adjacents.add(pos.func_177976_e());
      return adjacents;
   }

   public static Vec3 getHittableAdjacent(BlockPos pos) {
      Vec3 hit = VecUtil.getHittableHitVec(pos.func_177984_a());
      if (hit != null) {
         return hit;
      } else {
         hit = VecUtil.getHittableHitVec(pos.func_177978_c());
         if (hit != null) {
            return hit;
         } else {
            hit = VecUtil.getHittableHitVec(pos.func_177974_f());
            if (hit != null) {
               return hit;
            } else {
               hit = VecUtil.getHittableHitVec(pos.func_177968_d());
               if (hit != null) {
                  return hit;
               } else {
                  hit = VecUtil.getHittableHitVec(pos.func_177976_e());
                  return hit != null ? hit : VecUtil.getHittableHitVec(pos.func_177977_b());
               }
            }
         }
      }
   }

   public static MovingObjectPosition getHittablePosition(BlockPos pos) {
      MovingObjectPosition hit = VecUtil.getHittableMovingObjectPosition(pos.func_177984_a());
      if (hit != null) {
         return hit;
      } else {
         hit = VecUtil.getHittableMovingObjectPosition(pos.func_177978_c());
         if (hit != null) {
            return hit;
         } else {
            hit = VecUtil.getHittableMovingObjectPosition(pos.func_177974_f());
            if (hit != null) {
               return hit;
            } else {
               hit = VecUtil.getHittableMovingObjectPosition(pos.func_177968_d());
               if (hit != null) {
                  return hit;
               } else {
                  hit = VecUtil.getHittableMovingObjectPosition(pos.func_177976_e());
                  return hit != null ? hit : VecUtil.getHittableMovingObjectPosition(pos.func_177977_b());
               }
            }
         }
      }
   }

   public static double squareDistanceToBlockPos(Vec3 vec, BlockPos pos) {
      double d0 = vec.field_72450_a - (double)pos.func_177958_n();
      double d1 = vec.field_72448_b - (double)pos.func_177956_o();
      double d2 = vec.field_72449_c - (double)pos.func_177952_p();
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public static double squareDistanceToBlockPos(BlockPos pos1, BlockPos pos) {
      double d0 = (double)(pos1.func_177958_n() - pos.func_177958_n());
      double d1 = (double)(pos1.func_177956_o() - pos.func_177956_o());
      double d2 = (double)(pos1.func_177952_p() - pos.func_177952_p());
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public static float getExactDay() {
      return (float)PizzaClient.mc.field_71441_e.func_72820_D() / 24000.0F;
   }

   public static BlockPos getClosestInRange(BlockPos original) {
      if (uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(original).func_177230_c())) {
         return original;
      } else {
         Vec3 player = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
         BlockPos closest = null;
         double bestDist = 9.99999999E8D;
         Iterator var5 = BlockPos.func_177980_a(original.func_177982_a(-3, -3, -3), original.func_177982_a(3, 3, 3)).iterator();

         while(true) {
            BlockPos pos;
            double dist;
            do {
               do {
                  do {
                     do {
                        if (!var5.hasNext()) {
                           return closest == null ? original : closest;
                        }

                        pos = (BlockPos)var5.next();
                     } while(!uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c()));
                  } while(!uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c()));
               } while(uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c()));

               dist = squareDistanceToBlockPos(player, pos);
            } while(closest != null && !(dist < bestDist));

            closest = pos;
            bestDist = dist;
         }
      }
   }

   public static BetterBlockPos getClosestInRange(BetterBlockPos original) {
      if (uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(original).func_177230_c())) {
         return original;
      } else {
         Vec3 player = new Vec3(PizzaClient.mc.field_71439_g.field_70165_t, PizzaClient.mc.field_71439_g.field_70163_u, PizzaClient.mc.field_71439_g.field_70161_v);
         BlockPos closest = null;
         double bestDist = 9.99999999E8D;
         Iterator var5 = BlockPos.func_177980_a(original.add(-3, -3, -3), original.add(3, 3, 3)).iterator();

         while(true) {
            BlockPos pos;
            double dist;
            do {
               do {
                  do {
                     do {
                        if (!var5.hasNext()) {
                           return closest == null ? original : new BetterBlockPos(closest);
                        }

                        pos = (BlockPos)var5.next();
                     } while(!uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos).func_177230_c()));
                  } while(!uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177984_a()).func_177230_c()));
               } while(uncollidables.contains(PizzaClient.mc.field_71441_e.func_180495_p(pos.func_177977_b()).func_177230_c()));

               dist = squareDistanceToBlockPos(player, pos);
            } while(closest != null && !(dist < bestDist));

            closest = pos;
            bestDist = dist;
         }
      }
   }

   public static byte[] toByteArray(BufferedInputStream inputStream) throws IOException {
      byte[] var1;
      try {
         var1 = IOUtils.toByteArray(inputStream);
      } finally {
         inputStream.close();
      }

      return var1;
   }

   static {
      uncollidables = new HashSet(Arrays.asList(Blocks.field_150350_a, Blocks.field_180393_cK, Blocks.field_180394_cL, Blocks.field_150456_au, Blocks.field_150443_bT, Blocks.field_150445_bS, Blocks.field_150452_aw, Blocks.field_150430_aB, Blocks.field_150471_bO, Blocks.field_150480_ab, Blocks.field_150442_at, Blocks.field_150353_l, Blocks.field_150356_k, Blocks.field_150355_j, Blocks.field_150358_i, Blocks.field_150427_aO, Blocks.field_150448_aq, Blocks.field_150408_cc, Blocks.field_150319_E, Blocks.field_150318_D, Blocks.field_150488_af, Blocks.field_150436_aH, Blocks.field_150472_an, Blocks.field_150444_as, Blocks.field_150478_aa, Blocks.field_150395_bd, Blocks.field_150329_H, Blocks.field_150330_I, Blocks.field_150337_Q, Blocks.field_150338_P, Blocks.field_150464_aj, Blocks.field_150388_bm, Blocks.field_150459_bM, Blocks.field_150469_bN, Blocks.field_150345_g, Blocks.field_150393_bb, Blocks.field_150394_bc));
      ERROR_MESSAGE = "PizzaClient > " + EnumChatFormatting.RED;
      SUCCESS_MESSAGE = "PizzaClient > " + EnumChatFormatting.GREEN;
      DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
   }
}
