package qolskyblockmod.pizzaclient.core.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import qolskyblockmod.pizzaclient.PizzaClient;

public abstract class ConfigFile {
   public ConfigFile(File file) {
      if (!file.exists()) {
         try {
            if (!file.getParentFile().exists()) {
               file.getParentFile().mkdirs();
            }

            file.createNewFile();
         } catch (IOException var4) {
            var4.printStackTrace();
            System.out.println("Something went wrong when trying to create a new config file.");
         }
      }

      try {
         this.read();
      } catch (Exception var3) {
      }

   }

   public static void write(Map<?, ?> obj, File configFile) {
      try {
         FileWriter writer = new FileWriter(configFile);
         Throwable var3 = null;

         try {
            PizzaClient.gson.toJson(obj, writer);
         } catch (Throwable var13) {
            var3 = var13;
            throw var13;
         } finally {
            if (writer != null) {
               if (var3 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (IOException var15) {
         var15.printStackTrace();
      }

   }

   public static void write(List<?> obj, File configFile) {
      try {
         FileWriter writer = new FileWriter(configFile);
         Throwable var3 = null;

         try {
            PizzaClient.gson.toJson(obj, writer);
         } catch (Throwable var13) {
            var3 = var13;
            throw var13;
         } finally {
            if (writer != null) {
               if (var3 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (IOException var15) {
         var15.printStackTrace();
      }

   }

   public static void write(String obj, File configFile) {
      try {
         FileWriter writer = new FileWriter(configFile);
         Throwable var3 = null;

         try {
            PizzaClient.gson.toJson(obj, writer);
         } catch (Throwable var13) {
            var3 = var13;
            throw var13;
         } finally {
            if (writer != null) {
               if (var3 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (IOException var15) {
         var15.printStackTrace();
      }

   }

   public static void write(JsonObject obj, File configFile) {
      try {
         FileWriter writer = new FileWriter(configFile);
         Throwable var3 = null;

         try {
            PizzaClient.gson.toJson(obj, writer);
         } catch (Throwable var13) {
            var3 = var13;
            throw var13;
         } finally {
            if (writer != null) {
               if (var3 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (IOException var15) {
         var15.printStackTrace();
      }

   }

   public static void write(JsonArray obj, File configFile) {
      try {
         FileWriter writer = new FileWriter(configFile);
         Throwable var3 = null;

         try {
            PizzaClient.gson.toJson(obj, writer);
         } catch (Throwable var13) {
            var3 = var13;
            throw var13;
         } finally {
            if (writer != null) {
               if (var3 != null) {
                  try {
                     writer.close();
                  } catch (Throwable var12) {
                     var3.addSuppressed(var12);
                  }
               } else {
                  writer.close();
               }
            }

         }
      } catch (IOException var15) {
         var15.printStackTrace();
      }

   }

   public abstract void read();
}
