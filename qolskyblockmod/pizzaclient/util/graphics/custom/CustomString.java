package qolskyblockmod.pizzaclient.util.graphics.custom;

import java.util.regex.Pattern;

public interface CustomString {
   Pattern RANK_PATTERN = Pattern.compile("\\[.*?]\\s");

   int render(String var1, int var2, int var3, int var4, boolean var5);
}
