package qolskyblockmod.pizzaclient.util.exceptions;

public class Ratio extends RuntimeException {
   public Ratio(String s) {
      super(s);
   }

   public Ratio() {
      super("Ratio");
   }
}
