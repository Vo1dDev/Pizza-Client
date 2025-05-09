package qolskyblockmod.pizzaclient.features.dungeons;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.world.WorldEvent.Load;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.core.events.TickStartEvent;
import qolskyblockmod.pizzaclient.util.Utils;

public class QuizAura {
   public static final Map<String, List<String>> solutions = new HashMap();
   public static EntityArmorStand correctAnswer;
   public static Thread fetcher = new Thread(QuizAura::loadSolutions);
   public static List<EntityArmorStand> armorStands = new ArrayList();
   private static final List<String> unansweredQuestions = new ArrayList();
   private static boolean checkQuestion;
   private static String question;
   private static long timeUntilClick;
   public static boolean isQuizActive;

   public static void onChat(String msg) {
      if (msg.contains(" answered Question ")) {
         correctAnswer = null;
         timeUntilClick = 0L;
      } else if (checkQuestion) {
         question = msg.trim();
         checkQuestion = false;
      } else {
         if (question != null) {
            Iterator var1;
            String names;
            if (msg.contains("ⓐ")) {
               if (isEmpty()) {
                  if (unansweredQuestions.size() == 3) {
                     unansweredQuestions.clear();
                  }

                  unansweredQuestions.add(msg);
                  return;
               }

               if (msg.contains("Year ")) {
                  if (msg.contains("Year " + Utils.getSkyblockYear())) {
                     correctAnswer = (EntityArmorStand)armorStands.get(0);
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: ⓐ"));
                  }

                  return;
               }

               var1 = ((List)solutions.get(question)).iterator();

               while(var1.hasNext()) {
                  names = (String)var1.next();
                  if (msg.contains(names)) {
                     correctAnswer = (EntityArmorStand)armorStands.get(0);
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: ⓐ"));
                     break;
                  }
               }

               return;
            }

            if (msg.contains("ⓑ")) {
               if (isEmpty()) {
                  if (unansweredQuestions.size() == 3) {
                     unansweredQuestions.clear();
                  }

                  unansweredQuestions.add(msg);
                  return;
               }

               if (msg.contains("Year ")) {
                  if (msg.contains("Year " + Utils.getSkyblockYear())) {
                     correctAnswer = (EntityArmorStand)armorStands.get(1);
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: ⓑ"));
                  }

                  return;
               }

               var1 = ((List)solutions.get(question)).iterator();

               while(var1.hasNext()) {
                  names = (String)var1.next();
                  if (msg.contains(names)) {
                     correctAnswer = (EntityArmorStand)armorStands.get(1);
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: ⓑ"));
                     break;
                  }
               }

               return;
            }

            if (msg.contains("ⓒ")) {
               if (isEmpty()) {
                  if (unansweredQuestions.size() == 3) {
                     unansweredQuestions.clear();
                  }

                  unansweredQuestions.add(msg);
                  return;
               }

               if (msg.contains("Year ")) {
                  if (msg.contains("Year " + Utils.getSkyblockYear())) {
                     correctAnswer = (EntityArmorStand)armorStands.get(2);
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: ⓒ"));
                  }

                  return;
               }

               var1 = ((List)solutions.get(question)).iterator();

               while(var1.hasNext()) {
                  names = (String)var1.next();
                  if (msg.contains(names)) {
                     correctAnswer = (EntityArmorStand)armorStands.get(2);
                     PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Found Quiz Solution! Solution: ⓒ"));
                     break;
                  }
               }

               return;
            }
         }

         if (msg.contains(" answered the final question correctly!")) {
            isQuizActive = false;
            armorStands.clear();
            question = null;
            unansweredQuestions.clear();
         } else {
            if (msg.contains("Question ")) {
               timeUntilClick = System.currentTimeMillis();
               checkQuestion = true;
            }

         }
      }
   }

   @SubscribeEvent
   public void onTick(TickStartEvent event) {
      if (isQuizActive) {
         if (isEmpty()) {
            Iterator var2 = PizzaClient.mc.field_71441_e.field_72996_f.iterator();

            while(var2.hasNext()) {
               Entity entity = (Entity)var2.next();
               if (entity instanceof EntityArmorStand && !armorStands.contains(entity) && entity.func_145818_k_()) {
                  String name = entity.func_95999_t();
                  if (name.contains("ⓐ")) {
                     armorStands.set(0, (EntityArmorStand)entity);
                     if (!isEmpty()) {
                        sortArmorStands();
                        return;
                     }
                  } else if (name.contains("ⓑ")) {
                     armorStands.set(1, (EntityArmorStand)entity);
                     if (!isEmpty()) {
                        sortArmorStands();
                        return;
                     }
                  } else if (name.contains("ⓒ")) {
                     armorStands.set(2, (EntityArmorStand)entity);
                     if (!isEmpty()) {
                        sortArmorStands();
                        return;
                     }
                  }
               }
            }
         } else if (correctAnswer != null && timeUntilClick != 0L) {
            if (System.currentTimeMillis() - timeUntilClick >= 4350L) {
               timeUntilClick += 1500L;
               PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, correctAnswer);
               PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Clicking Quiz!"));
            } else {
               EntityArmorStand armorStand = (EntityArmorStand)armorStands.get(2);
               if (armorStand.func_145818_k_() && armorStand.func_95999_t().contains("ⓒ")) {
                  PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, correctAnswer);
                  PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Clicking Quiz"));
                  correctAnswer = null;
                  timeUntilClick = 0L;
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void onWorldLoad(Load event) {
      if (solutions.size() == 0 && !fetcher.isAlive()) {
         fetcher = new Thread(QuizAura::loadSolutions);
         fetcher.start();
      }

      correctAnswer = null;
      armorStands.clear();
      isQuizActive = false;
      question = null;
      unansweredQuestions.clear();
   }

   public static void loadSolutions() {
      try {
         JsonObject creditsToSkytils = Utils.getJson("https://cdn.jsdelivr.net/gh/Skytils/SkytilsMod-Data@main/solvers/oruotrivia.json").getAsJsonObject();
         Iterator var1 = creditsToSkytils.entrySet().iterator();

         while(var1.hasNext()) {
            Entry<String, JsonElement> entry = (Entry)var1.next();
            solutions.put(entry.getKey(), Utils.getStringListFromJsonArray(((JsonElement)entry.getValue()).getAsJsonArray()));
         }
      } catch (Exception var3) {
         var3.printStackTrace();
         System.out.println("Something went wrong when trying to load the quiz solutions.");
         if (PizzaClient.mc.field_71439_g != null) {
            PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Couldn't load the quiz solutions."));
         }
      }

   }

   private static void sortArmorStands() {
      PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.SUCCESS_MESSAGE + "Loaded in all armor stands, using inf reach now."));
      if (unansweredQuestions.size() != 0) {
         if (question != null) {
            if (question.contains("What SkyBlock year")) {
               String year = String.valueOf(Utils.getSkyblockYear());
               Iterator var5 = unansweredQuestions.iterator();

               while(var5.hasNext()) {
                  String msg = (String)var5.next();
                  if (msg.contains("ⓐ")) {
                     if (msg.contains(year)) {
                        PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, (Entity)armorStands.get(0));
                        break;
                     }
                  } else if (msg.contains("ⓑ")) {
                     if (msg.contains(year)) {
                        PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, (Entity)armorStands.get(1));
                        break;
                     }
                  } else if (msg.contains("ⓒ") && msg.contains(year)) {
                     PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, (Entity)armorStands.get(2));
                     break;
                  }
               }
            } else {
               Iterator var0 = unansweredQuestions.iterator();

               label89:
               while(true) {
                  while(true) {
                     while(true) {
                        if (!var0.hasNext()) {
                           break label89;
                        }

                        String msg = (String)var0.next();
                        Iterator var2;
                        String names;
                        if (msg.contains("ⓐ")) {
                           var2 = ((List)solutions.get(question)).iterator();

                           while(var2.hasNext()) {
                              names = (String)var2.next();
                              if (msg.contains(names)) {
                                 PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, (Entity)armorStands.get(0));
                                 break;
                              }
                           }
                        } else if (msg.contains("ⓑ")) {
                           var2 = ((List)solutions.get(question)).iterator();

                           while(var2.hasNext()) {
                              names = (String)var2.next();
                              if (msg.contains(names)) {
                                 PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, (Entity)armorStands.get(1));
                                 break;
                              }
                           }
                        } else if (msg.contains("ⓒ")) {
                           var2 = ((List)solutions.get(question)).iterator();

                           while(var2.hasNext()) {
                              names = (String)var2.next();
                              if (msg.contains(names)) {
                                 PizzaClient.mc.field_71442_b.func_78768_b(PizzaClient.mc.field_71439_g, (Entity)armorStands.get(2));
                                 break;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         unansweredQuestions.clear();
      }

   }

   private static boolean isEmpty() {
      if (armorStands.size() != 3) {
         armorStands = new ArrayList(Arrays.asList());
         return true;
      } else {
         return armorStands.get(0) == null || armorStands.get(1) == null || armorStands.get(2) == null;
      }
   }
}
