package qolskyblockmod.pizzaclient.util.misc;

import net.minecraft.crash.CrashReport;

public class CustomCrashReport extends CrashReport {
   public CustomCrashReport(String descriptionIn) {
      super(descriptionIn, new Exception());
   }
}
