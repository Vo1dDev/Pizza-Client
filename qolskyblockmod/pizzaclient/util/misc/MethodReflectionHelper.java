package qolskyblockmod.pizzaclient.util.misc;

import java.lang.reflect.Method;
import net.minecraft.util.ChatComponentText;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.util.Utils;

public class MethodReflectionHelper {
   private final Method method;

   public MethodReflectionHelper(Class<?> clazz, String methodName, Class<?>... parameter) {
      try {
         this.method = clazz.getDeclaredMethod(methodName, parameter);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new RuntimeException(var5);
      }
   }

   public void invoke(Object instance) {
      try {
         this.method.setAccessible(true);
         this.method.invoke(instance);
      } catch (Exception var3) {
         var3.printStackTrace();
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to invoke the method " + this.method.getName() + ". See logs for more info."));
      }

   }

   public void invoke(Object instance, int amt) {
      try {
         this.method.setAccessible(true);

         for(int i = 0; i < amt; ++i) {
            this.method.invoke(instance);
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to invoke the method " + this.method.getName() + ". See logs for more info."));
      }

   }

   public void invokeStatic() {
      try {
         this.method.setAccessible(true);
         this.method.invoke((Object)null);
      } catch (Exception var2) {
         var2.printStackTrace();
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to invoke the method " + this.method.getName() + ". See logs for more info."));
      }

   }

   public void invokeStatic(int amt) {
      try {
         this.method.setAccessible(true);

         for(int i = 0; i < amt; ++i) {
            this.method.invoke((Object)null);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to invoke the method " + this.method.getName() + ". See logs for more info."));
      }

   }

   public void invokeMc() {
      try {
         this.method.setAccessible(true);
         this.method.invoke(PizzaClient.mc);
      } catch (Exception var2) {
         var2.printStackTrace();
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to invoke the method " + this.method.getName() + ". See logs for more info."));
      }

   }

   public void invokeMc(int amt) {
      try {
         this.method.setAccessible(true);

         for(int i = 0; i < amt; ++i) {
            this.method.invoke(PizzaClient.mc);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText(Utils.ERROR_MESSAGE + "Failed to invoke the method " + this.method.getName() + ". See logs for more info."));
      }

   }
}
