package qolskyblockmod.pizzaclient.util.misc;

import java.util.Random;

public class RandomString {
   public static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
   public static final int alphabetLength;

   public static String nextString(int lenght, Random random) {
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < lenght; ++i) {
         sb.append(alphabet[random.nextInt(alphabet.length)]);
      }

      return sb.toString();
   }

   public static String nextString(int lenght) {
      Random random = new Random();
      StringBuilder sb = new StringBuilder();

      for(int i = 0; i < lenght; ++i) {
         sb.append(alphabet[random.nextInt(alphabet.length)]);
      }

      return sb.toString();
   }

   static {
      alphabetLength = alphabet.length;
   }
}
