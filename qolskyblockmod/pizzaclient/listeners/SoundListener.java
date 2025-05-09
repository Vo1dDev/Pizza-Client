package qolskyblockmod.pizzaclient.listeners;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import qolskyblockmod.pizzaclient.PizzaClient;
import qolskyblockmod.pizzaclient.commands.SilencerCommand;

public class SoundListener {
   public static boolean onPlaySound(ISound p_sound, String sound) {
      if (PizzaClient.config.getCurrentSound) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.GREEN + "Current Sound Playing: " + EnumChatFormatting.AQUA + sound + ", pitch: " + p_sound.func_147655_f() + ", volume: " + p_sound.func_147653_e()));
      }

      if (PizzaClient.config.autoHarpSolver && sound.equals("note.bassattack") && PizzaClient.mc.field_71462_r instanceof GuiChest && ((ContainerChest)PizzaClient.mc.field_71439_g.field_71070_bA).func_85151_d().func_145748_c_().func_150260_c().trim().startsWith("Harp ")) {
         PizzaClient.mc.field_71439_g.func_145747_a(new ChatComponentText("PizzaClient > " + EnumChatFormatting.RED + "The Harp Macro clicked too early/late. Try changing the delay."));
      }

      return SilencerCommand.silencedSounds.contains(sound) || PizzaClient.config.silenceEnderman && sound.startsWith("mob.endermen");
   }
}
