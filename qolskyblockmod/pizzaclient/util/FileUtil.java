package qolskyblockmod.pizzaclient.util;

import java.net.URL;
import java.net.URLDecoder;

public class FileUtil {
   public static String getExecutor() {
      try {
         String className = (new Exception()).getStackTrace()[4].getClassName();
         return className.equals("sun.reflect.NativeMethodAccessorImpl") ? (new Exception()).getStackTrace()[8].getClassName() : className;
      } catch (Exception var1) {
         var1.printStackTrace();
         return "";
      }
   }

   public static String getExecutor(int line) {
      try {
         String className = (new Exception()).getStackTrace()[line].getClassName();
         return className.equals("sun.reflect.NativeMethodAccessorImpl") ? (new Exception()).getStackTrace()[line + 4].getClassName() : className;
      } catch (Exception var2) {
         var2.printStackTrace();
         return "";
      }
   }

   public static String getExecutorAndMethod() {
      try {
         StackTraceElement[] elements = (new Exception()).getStackTrace();
         StackTraceElement element = elements[4];
         if (element.getClassName().equals("sun.reflect.NativeMethodAccessorImpl")) {
            element = elements[8];
         }

         return element.getClassName() + "." + element.getMethodName() + "()";
      } catch (Exception var2) {
         var2.printStackTrace();
         return "";
      }
   }

   public static String getStackTrace() {
      try {
         StringBuilder sb = new StringBuilder("\n");
         StackTraceElement[] elements = (new Exception()).getStackTrace();

         for(int i = 1; i < elements.length; ++i) {
            sb.append(elements[i]).append(" [").append(i - 1).append("]\n");
         }

         return sb.toString();
      } catch (Exception var3) {
         var3.printStackTrace();
         return "";
      }
   }

   public static String getJarName() {
      try {
         String className = (new Exception()).getStackTrace()[4].getClassName();
         if (className.equals("sun.reflect.NativeMethodAccessorImpl")) {
            className = (new Exception()).getStackTrace()[8].getClassName();
         }

         URL location = Class.forName(className).getProtectionDomain().getCodeSource().getLocation();
         String[] message = location.getFile().split("!")[0].split("/");
         return URLDecoder.decode(message[message.length - 1], "UTF-8");
      } catch (Exception var3) {
         var3.printStackTrace();
         return "";
      }
   }

   public static String getJarName(int line) {
      try {
         String className = (new Exception()).getStackTrace()[line].getClassName();
         if (className.equals("sun.reflect.NativeMethodAccessorImpl")) {
            className = (new Exception()).getStackTrace()[line + 4].getClassName();
         }

         URL location = Class.forName(className).getProtectionDomain().getCodeSource().getLocation();
         String[] message = location.getFile().split("!")[0].split("/");
         return URLDecoder.decode(message[message.length - 1], "UTF-8");
      } catch (Exception var4) {
         var4.printStackTrace();
         return "";
      }
   }

   public static String getFileLocation() {
      try {
         return Class.forName(getExecutor()).getProtectionDomain().getCodeSource().getLocation().toString().split("!")[0];
      } catch (Exception var1) {
         var1.printStackTrace();
         return "";
      }
   }

   public static String getFileLocation(String executor) {
      try {
         return Class.forName(executor).getProtectionDomain().getCodeSource().getLocation().toString().split("!")[0];
      } catch (Exception var2) {
         var2.printStackTrace();
         return "";
      }
   }
}
