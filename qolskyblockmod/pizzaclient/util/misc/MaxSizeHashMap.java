package qolskyblockmod.pizzaclient.util.misc;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class MaxSizeHashMap<K, V> extends LinkedHashMap<K, V> {
   private final int maxSize;

   public MaxSizeHashMap(int maxSize) {
      this.maxSize = maxSize;
   }

   protected boolean removeEldestEntry(Entry<K, V> eldest) {
      return this.size() > this.maxSize;
   }
}
