package qolskyblockmod.pizzaclient.core.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class SendChatMessageEvent extends Event {
   public String message;

   public SendChatMessageEvent(String message) {
      this.message = message;
   }
}
