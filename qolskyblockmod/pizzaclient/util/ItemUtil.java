package qolskyblockmod.pizzaclient.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.StringUtils;

public class ItemUtil {
   public static final int NBT_STRING = 8;

   public static String getDisplayName(ItemStack item) {
      String s = item.func_77973_b().func_77653_i(item);
      if (item.func_77978_p() != null && item.func_77978_p().func_150297_b("display", 10)) {
         NBTTagCompound nbttagcompound = item.func_77978_p().func_74775_l("display");
         if (nbttagcompound.func_150297_b("Name", 8)) {
            s = nbttagcompound.func_74779_i("Name");
         }
      }

      return s;
   }

   public static String getSkyBlockItemID(ItemStack item) {
      if (item == null) {
         return "";
      } else {
         NBTTagCompound extraAttributes = getExtraAttributes(item);
         if (extraAttributes == null) {
            return "";
         } else {
            return !extraAttributes.func_150297_b("id", 8) ? "" : extraAttributes.func_74779_i("id");
         }
      }
   }

   public static NBTTagCompound getExtraAttributes(ItemStack item) {
      return !item.func_77942_o() ? null : item.func_179543_a("ExtraAttributes", false);
   }

   public static String getSkyBlockItemID(NBTTagCompound extraAttributes) {
      if (extraAttributes != null) {
         String itemId = extraAttributes.func_74779_i("id");
         if (!itemId.equals("")) {
            return itemId;
         }
      }

      return "";
   }

   public static Set<String> getItemLore(ItemStack itemStack) {
      if (itemStack.func_77942_o() && itemStack.func_77978_p().func_150297_b("display", 10)) {
         NBTTagCompound display = itemStack.func_77978_p().func_74775_l("display");
         if (display.func_150297_b("Lore", 9)) {
            NBTTagList lore = display.func_150295_c("Lore", 8);
            Set<String> loreAsList = new HashSet();

            for(int lineNumber = 0; lineNumber < lore.func_74745_c(); ++lineNumber) {
               loreAsList.add(lore.func_150307_f(lineNumber));
            }

            return Collections.unmodifiableSet(loreAsList);
         }
      }

      return Collections.emptySet();
   }

   public static boolean hasRightClickAbility(ItemStack itemStack) {
      Iterator var1 = getItemLore(itemStack).iterator();

      String stripped;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         String line = (String)var1.next();
         stripped = StringUtils.func_76338_a(line);
      } while(!stripped.startsWith("Ability:") || !stripped.endsWith("RIGHT CLICK"));

      return true;
   }
}
