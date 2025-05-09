package qolskyblockmod.pizzaclient.util.handler;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.StringUtils;
import qolskyblockmod.pizzaclient.PizzaClient;

public class ScoreboardHandler {
   public static String cleanSB(String scoreboard) {
      char[] nvString = StringUtils.func_76338_a(scoreboard).toCharArray();
      StringBuilder cleaned = new StringBuilder();
      char[] var3 = nvString;
      int var4 = nvString.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         char c = var3[var5];
         if (c > 20 && c < 127) {
            cleaned.append(c);
         }
      }

      return cleaned.toString();
   }

   public static Set<String> getSidebarLines() {
      Set<String> lines = new HashSet();
      Scoreboard scoreboard = PizzaClient.mc.field_71441_e.func_96441_U();
      if (scoreboard == null) {
         return lines;
      } else {
         ScoreObjective objective = scoreboard.func_96539_a(1);
         if (objective == null) {
            return lines;
         } else {
            Collection<Score> scores = scoreboard.func_96534_i(objective);
            List<Score> list = (List)scores.stream().filter((input) -> {
               return input != null && input.func_96653_e() != null && !input.func_96653_e().startsWith("#");
            }).collect(Collectors.toList());
            Collection<Score> scores = list.size() > 15 ? Lists.newArrayList(Iterables.skip(list, scores.size() - 15)) : list;
            Iterator var5 = ((Collection)scores).iterator();

            while(var5.hasNext()) {
               Score score = (Score)var5.next();
               ScorePlayerTeam team = scoreboard.func_96509_i(score.func_96653_e());
               lines.add(ScorePlayerTeam.func_96667_a(team, score.func_96653_e()));
            }

            return lines;
         }
      }
   }

   public static boolean isScoreboardEmpty() {
      return PizzaClient.mc.field_71441_e.func_96441_U().func_96539_a(1) == null;
   }
}
