package qolskyblockmod.pizzaclient.features.keybinds;

public enum KeybindAction {
   LEFT,
   RIGHT,
   UNKNOWN;

   public static KeybindAction getActionFromString(String s) {
      return s.equalsIgnoreCase("left") ? LEFT : (s.equalsIgnoreCase("right") ? RIGHT : UNKNOWN);
   }

   public static String getString(KeybindAction action) {
      return action == LEFT ? "left" : (action == RIGHT ? "right" : "null");
   }
}
