package qolskyblockmod.pizzaclient.core.config;

import gg.essential.vigilance.data.Category;
import gg.essential.vigilance.data.SortingBehavior;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ConfigSorting extends SortingBehavior {
   private final List<String> categories = Arrays.asList("Dungeons", "F7 Boss", "Macros", "Player", "Mining", "Keybinds", "Slayer", "Visuals", "Jokes", "Other");

   public Comparator<Category> getCategoryComparator() {
      return Comparator.comparingInt((o) -> {
         return this.categories.indexOf(o.getName());
      });
   }
}
