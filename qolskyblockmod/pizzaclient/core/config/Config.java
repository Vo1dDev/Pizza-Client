package qolskyblockmod.pizzaclient.core.config;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.JVMAnnotationPropertyCollector;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import java.awt.Color;
import java.io.File;
import net.minecraft.util.EnumChatFormatting;

public class Config extends Vigilant {
   public final String commandsList;
   private final String WIP;
   private final String risky;
   @Property(
      type = PropertyType.SWITCH,
      name = "Funny Terminals",
      description = "Haha, Terminals go brrrrr",
      category = "F7 Boss",
      subcategory = "Terminals",
      searchTags = {"Auto Terminals", "Funny Terminals", "Terminal", "0 Sleep", "Stop Sending Click Packets Index"}
   )
   public boolean funnyTerminals;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Funny Terminals Click",
      description = "Change the click used on terminals.",
      category = "F7 Boss",
      subcategory = "Terminals",
      options = {"Left Click", "Right Click", "Middle Click"}
   )
   public int terminalClickMode;
   @Property(
      type = PropertyType.SLIDER,
      name = "Terminal Sleepamount",
      description = "Terminal Sleepamount.",
      category = "F7 Boss",
      subcategory = "Terminals",
      max = 150,
      min = 100
   )
   public int terminalSleepAmount;
   @Property(
      type = PropertyType.SLIDER,
      name = "Terminal Fix Time",
      description = "Change the time required to fix terminals. If it's too low, the terminals will be slower.",
      category = "F7 Boss",
      subcategory = "Terminals",
      min = 200,
      max = 500
   )
   public int terminalFixTime;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Aligment Device",
      description = "Automatically clicks the amounts required on the alignment device.",
      category = "F7 Boss",
      subcategory = "Device"
   )
   public boolean autoAlignment;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Aiming Device (4 Device)",
      description = "Automatically aims to the emerald block and left clicks.",
      category = "F7 Boss",
      subcategory = "Device"
   )
   public boolean autoAimingDevice;
   @Property(
      type = PropertyType.SWITCH,
      name = "Clip Ghostblocks",
      description = "Creates clip ghostblocks.",
      category = "F7 Boss",
      subcategory = "Other"
   )
   public boolean autoClipGhostblocks;
   @Property(
      type = PropertyType.SWITCH,
      name = "Spirit Bear Aura",
      description = "Once a spirit bear spawns, Rotates to the bear server side and left clicks afterwards",
      category = "Dungeons",
      subcategory = "Aura",
      searchTags = {"Bear Aura"}
   )
   public boolean spiritBearAura;
   @Property(
      type = PropertyType.SWITCH,
      name = "Spirit Bear Aura Always Aim",
      description = "Always aim at the spirit bear server side instead of only when it spawns.",
      category = "Dungeons",
      subcategory = "Aura",
      searchTags = {"Bear Aura"}
   )
   public boolean alwaysAimAtSpiritBear;
   @Property(
      type = PropertyType.SWITCH,
      name = "Livid Aura",
      description = "Always face the correct livid server side.",
      category = "Dungeons",
      subcategory = "Aura"
   )
   public boolean lividAura;
   @Property(
      type = PropertyType.SWITCH,
      name = "§b[WIP] §fSuperboom Aura",
      description = "Automatically place super/infiniboom on cracked walls (not on crypts yet).",
      category = "Dungeons",
      subcategory = "Aura"
   )
   public boolean superboomAura;
   @Property(
      type = PropertyType.SWITCH,
      name = "Quiz Aura (Blatant)",
      description = "Quiz aura. Works for inf range once all armor stands were loaded once. Also known as NecronIsBad aura. If this doesn't work, make sure that no mod is removing the names of the armor stands.",
      category = "Dungeons",
      subcategory = "Aura",
      searchTags = {"NecronIsBad Aura"}
   )
   public boolean quizAura;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Close Chests",
      description = "Automatically close chests in dungeon.",
      category = "Dungeons",
      subcategory = "Macros"
   )
   public boolean autoCloseDungChest;
   @Property(
      type = PropertyType.SWITCH,
      name = "Blood Trigger Bot",
      description = "Auto left clicks once you're facing a blood mob",
      category = "Dungeons",
      subcategory = "Macros"
   )
   public boolean bloodTriggerBot;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Aotv When Sneak",
      description = "Automatically use aotv when sneaking.",
      category = "Dungeons",
      subcategory = "Macros"
   )
   public boolean sneakAotv;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Ready",
      description = "Auto readies (not blatant).",
      category = "Dungeons",
      subcategory = "Macros"
   )
   public boolean autoReady;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Leap To Door Opener",
      description = "Auto leaps to door opener when left clicking spirit leap.",
      category = "Dungeons",
      subcategory = "Macros"
   )
   public boolean autoLeapDoor;
   @Property(
      type = PropertyType.SWITCH,
      name = "Terminator Autoclicker",
      description = "Spams right click when terminator is held and right click is pressed (delay per click is changeable). Also works for juju.",
      category = "Dungeons",
      subcategory = "Macros"
   )
   public boolean terminatorClicker;
   @Property(
      type = PropertyType.SLIDER,
      name = "Terminator Autoclicker Delay",
      description = "Change the time until clicking terminator again. Values might be a bit inaccurate with low fps.",
      category = "Dungeons",
      subcategory = "Macros",
      min = 10,
      max = 75
   )
   public int terminatorClickerDelay;
   @Property(
      type = PropertyType.SWITCH,
      name = "Qol Ice Fill",
      description = "§b[WIP] §fMakes ice fill never break and faster. (just walk through the ice, even if the current block below you isn't packed ice)",
      category = "Dungeons",
      subcategory = "Macros"
   )
   public boolean qolIceFill;
   @Property(
      type = PropertyType.SWITCH,
      name = "Bat Esp",
      description = "Creates an esp on dungeon bats.",
      category = "Dungeons",
      subcategory = "Esp"
   )
   public boolean batEsp;
   @Property(
      type = PropertyType.COLOR,
      name = "Bat Esp Color",
      description = "Change the bat esp color.",
      category = "Dungeons",
      subcategory = "Esp"
   )
   public Color batEspColor;
   @Property(
      type = PropertyType.SWITCH,
      name = "Correct Livid Esp",
      description = "Finds correct livid and gives it an colored esp.",
      category = "Dungeons",
      subcategory = "Esp"
   )
   public boolean lividEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Dungeon Key Esp",
      description = "Gives dungeon keys (wither keys and blood keys) an esp.",
      category = "Dungeons",
      subcategory = "Esp"
   )
   public boolean dungeonKeyEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Starred Mobs Esp",
      description = "Gives starred mobs an esp.",
      category = "Dungeons",
      subcategory = "Esp"
   )
   public boolean starredMobsEsp;
   @Property(
      type = PropertyType.SLIDER,
      name = "Starred Mobs Esp Range",
      description = "Change the range of the starred mobs esp.",
      category = "Dungeons",
      subcategory = "Esp",
      min = 8,
      max = 128
   )
   public int starredMobsEspRange;
   @Property(
      type = PropertyType.COLOR,
      name = "Starred Mobs Esp Color",
      description = "Change the starred mob esp color.",
      category = "Dungeons",
      subcategory = "Esp"
   )
   public Color starredMobsEspColor;
   @Property(
      type = PropertyType.SWITCH,
      name = "Superboom Name Hider",
      description = "Hides the name of superboom tnt.",
      category = "Dungeons",
      subcategory = "Other"
   )
   public boolean hideSuperboom;
   @Property(
      type = PropertyType.SWITCH,
      name = "Show Hidden Mobs",
      description = "Shows hidden fels and shadow assassins.",
      category = "Dungeons",
      subcategory = "Other"
   )
   public boolean showHiddenMobs;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Ghostblocks",
      description = "Change the speed and range of ghost blocks",
      category = "Dungeons",
      subcategory = "Other",
      options = {"Normal", "Instant w/ 6 Reach", "BEST MINER"}
   )
   public int fasterGhostblocks;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Activate Soulcry",
      description = "Automatically activate soulcry when a voidgloom spawned.",
      category = "Slayer",
      subcategory = "Macros"
   )
   public boolean autoSoulcry;
   @Property(
      type = PropertyType.SWITCH,
      name = "Miniboss Esp",
      description = "Gives all slayer minibosses an esp.",
      category = "Slayer",
      subcategory = "Esp"
   )
   public boolean minibossEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Slayer Boss Esp",
      description = "Gives all slayer bosses but endermen an esp. (good for boss trading)",
      category = "Slayer",
      subcategory = "Esp"
   )
   public boolean bossEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Dark Monolith Esp",
      description = "Gives Monoliths an esp.",
      category = "Mining",
      subcategory = "Esp"
   )
   public boolean monolithEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Bal Esp",
      description = "Gives Bal an esp and a notification. Short - Normal detection range.",
      category = "Mining",
      subcategory = "Esp"
   )
   public boolean balEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Butterfly Esp",
      description = "Gives Butterflies an esp. Short detection range.",
      category = "Mining",
      subcategory = "Esp"
   )
   public boolean butterflyEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Corleone Esp",
      description = "Gives Corleone an ping when detected. To get an esp, enable \"Team Treasurite Esp\".",
      category = "Mining",
      subcategory = "CommissionEsp"
   )
   public boolean corleonePing;
   @Property(
      type = PropertyType.SWITCH,
      name = "Team Tresurite Esp",
      description = "Gives all of Team Tresurite an esp (including corleone).",
      category = "Mining",
      subcategory = "CommissionEsp"
   )
   public boolean teamTresuriteEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Automaton Esp",
      description = "Gives Automaton an esp.",
      category = "Mining",
      subcategory = "CommissionEsp"
   )
   public boolean ironGolemEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Goblin Esp",
      description = "Gives Goblins an esp.",
      category = "Mining",
      subcategory = "CommissionEsp"
   )
   public boolean goblinEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Sludge Esp",
      description = "Gives Sludges an esp. Applies to all slimes in crystal hollows.",
      category = "Mining",
      subcategory = "CommissionEsp"
   )
   public boolean sludgeEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Yog Esp",
      description = "Gives Yogs an esp. Applies to all magma cubes in crystal hollows.",
      category = "Mining",
      subcategory = "CommissionEsp"
   )
   public boolean yogEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Gemstone ESP",
      description = "Scans for gemstones while in crystal hollows. If you do not want it to scan, turn this off.",
      category = "Mining",
      subcategory = "GemstoneEsp"
   )
   public boolean gemstoneScanner;
   @Property(
      type = PropertyType.SLIDER,
      name = "Gemstone Scanner Width",
      description = "Limit the range the gemstone scanner can go.",
      category = "Mining",
      subcategory = "GemstoneEsp",
      min = 16,
      max = 128
   )
   public int scannerWidth;
   @Property(
      type = PropertyType.SLIDER,
      name = "Gemstone Scanner Delay",
      description = "Sets the gemstone scanner delay (in seconds). Default is 5.",
      category = "Mining",
      subcategory = "GemstoneEsp",
      min = 1,
      max = 15
   )
   public int scannerDelay;
   @Property(
      type = PropertyType.SLIDER,
      name = "Gemstone Esp Size",
      description = "Change the outline of the gemstone Esp",
      category = "Mining",
      subcategory = "GemstoneEsp",
      max = 100,
      min = 1
   )
   public int gemstoneEspSize;
   @Property(
      type = PropertyType.SWITCH,
      name = "Ruby Esp",
      description = "Gives Ruby an esp.",
      category = "Mining",
      subcategory = "GemstoneEsp"
   )
   public boolean redGlassEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Amber Esp",
      description = "Gives Amber an esp.",
      category = "Mining",
      subcategory = "GemstoneEsp"
   )
   public boolean orangeGlassEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Sapphire Esp",
      description = "Gives Sapphire an esp.",
      category = "Mining",
      subcategory = "GemstoneEsp"
   )
   public boolean lightBlueGlassEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Jade Esp",
      description = "Gives Jade an esp.",
      category = "Mining",
      subcategory = "GemstoneEsp"
   )
   public boolean limeGlassEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Amethyst Esp",
      description = "Gives Amethyst an esp.",
      category = "Mining",
      subcategory = "GemstoneEsp"
   )
   public boolean purpleGlassEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Topaz Esp",
      description = "Gives Topaz an esp.",
      category = "Mining",
      subcategory = "GemstoneEsp"
   )
   public boolean yellowGlassEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Jasper Esp",
      description = "Gives Jasper an esp.",
      category = "Mining",
      subcategory = "GemstoneEsp"
   )
   public boolean pinkGlassEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Glass Panes Esp",
      description = "Gives glass panes an esp too.",
      category = "Mining",
      subcategory = "GemstoneEsp"
   )
   public boolean glassPanesEsp;
   @Property(
      type = PropertyType.SLIDER,
      name = "Glass Panes Esp Size",
      description = "Change the size of the glass panes esp.",
      category = "Mining",
      subcategory = "GemstoneEsp",
      max = 100,
      min = 1
   )
   public int pinkGlassPanesEspSize;
   @Property(
      type = PropertyType.SWITCH,
      name = "Hide Golden Goblins",
      description = "Removes all golden goblin entities.",
      category = "Mining",
      subcategory = "Misc"
   )
   public boolean hideGoldenGoblin;
   @Property(
      type = PropertyType.SWITCH,
      name = "Special Coords List",
      description = "Note down the special coords on a list.",
      category = "Mining",
      subcategory = "Other"
   )
   public boolean coordsList;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Close Loot Chests",
      description = "Automatically Close Loot Chests.",
      category = "Mining",
      subcategory = "Other"
   )
   public boolean autoCloseLootChest;
   @Property(
      type = PropertyType.SWITCH,
      name = "Powder Chest Macro",
      description = "Auto completes powder chests. Rotates server side only.",
      category = "Mining",
      subcategory = "Other"
   )
   public boolean powderChestMacro;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Swap To Pickonimbus",
      description = "Auto swaps to the next pickonimbus after the current one breaking. Can be anywhere in the inventory, but hotbar is faster",
      category = "Player",
      subcategory = "Macro"
   )
   public boolean autoPickonimbus;
   @Property(
      type = PropertyType.SWITCH,
      name = "Shoot Terminator With Valk",
      description = "Shoots terminator when left clicking with valk.",
      category = "Player",
      subcategory = "Other"
   )
   public boolean terminatorValk;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Jerry Box",
      description = "Auto click and close jerry boxes when holding them.",
      category = "Player",
      subcategory = "Other"
   )
   public boolean autoJerryBox;
   @Property(
      type = PropertyType.SLIDER,
      name = "Auto Jerry Box Extra Delay",
      description = "Add extra delay to the auto jerry box opening.",
      category = "Player",
      subcategory = "Other",
      max = 400
   )
   public int autoJerryBoxDelay;
   @Property(
      type = PropertyType.SLIDER,
      name = "Auto Jerry Box Extra Closing Delay",
      description = "Add extra delay to the closing of the jerry box. Useful for higher ping.",
      category = "Player",
      subcategory = "Other",
      max = 400
   )
   public int autoJerryBoxClosingDelay;
   @Property(
      type = PropertyType.SLIDER,
      name = "Auto Jerry Box Extra Open Delay",
      description = "Add extra delay before opening the jerry box.",
      category = "Player",
      subcategory = "Other",
      max = 400
   )
   public int autoJerryBoxOpenDelay;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Jerry Box Swap Slot",
      description = "Auto Swap to a jerry box slot.",
      category = "Player",
      subcategory = "Other"
   )
   public boolean autoJerryBoxSwapSlot;
   @Property(
      type = PropertyType.SWITCH,
      name = "Funny Enchanting",
      description = "Haha, Enchanting go brrrrrr. Increase the delay if it breaks sometimes",
      category = "Player",
      subcategory = "Gui",
      searchTags = {"Enchanting", "Auto Enchanting", "Experiment", "Auto Experiment"}
   )
   public boolean funnyEnchanting;
   @Property(
      type = PropertyType.SWITCH,
      name = "Funny Enchanting 3 Superpair Clicks",
      description = "Auto closes the gui after you get 3 superpair clicks in the experiment.",
      category = "Player",
      subcategory = "Gui",
      searchTags = {"Enchanting", "Auto Enchanting", "Experiment", "Auto Experiment"}
   )
   public boolean funnyEnchantingCloseChest;
   @Property(
      type = PropertyType.SLIDER,
      name = "Funny Enchanting Delay",
      description = "Change the amount of delay when using funny enchanting. Default is 115.",
      category = "Player",
      subcategory = "Gui",
      min = 100,
      max = 160,
      searchTags = {"Enchanting", "Auto Enchanting", "Experiment", "Auto Experiment"}
   )
   public int funnyEnchantingDelay;
   @Property(
      type = PropertyType.SLIDER,
      name = "Auto Wardrobe Slot",
      description = "Change the slot the auto wardrobe clicks. Put to 0 if you don't want it to click a slot.",
      category = "Player",
      subcategory = "Gui",
      max = 18
   )
   public int autoWardrobeSlot;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Harp Macro",
      description = "Automatically complete harp (ping offset killed it :cry:).",
      category = "Player",
      subcategory = "Gui"
   )
   public boolean autoHarpSolver;
   @Property(
      type = PropertyType.SLIDER,
      name = "Harp Macro Delay",
      description = "Change the delay of the harp macro. Since the harp macro is made different it needs a delay.",
      category = "Player",
      subcategory = "Gui",
      min = 50,
      max = 300
   )
   public int harpSolverDelay;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Book Combine",
      description = "Auto combine books. Will automatically combine if the anvil gui is open.",
      category = "Player",
      subcategory = "Gui"
   )
   public boolean autoBookCombine;
   @Property(
      type = PropertyType.SLIDER,
      name = "Auto Book Combine Delay",
      description = "Adds extra delay to auto book combiner. Useful for higher ping",
      category = "Player",
      subcategory = "Gui",
      max = 300
   )
   public int autoBookCombineDelay;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Salvage",
      description = "Auto salvage useless items. Will automatically combine if the salvaging gui is open.",
      category = "Player",
      subcategory = "Gui"
   )
   public boolean autoSalvage;
   @Property(
      type = PropertyType.SLIDER,
      name = "Auto Salvager Delay",
      description = "Adds extra delay to auto salvager. Useful for higher ping",
      category = "Player",
      subcategory = "Gui",
      max = 300
   )
   public int autoSalvageDelay;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Macro Keybind",
      description = "Set the macro keybind to do that specific task. Only works with macro key, the item keybind is something completely different.",
      category = "Macros",
      subcategory = "Macro",
      options = {"None", "Farming", "Mithril Macro", "Foraging Macro", "Powder Macro", "Nuker", "Crop Aura", "Commission Macro", "§b[WIP] §fFishing Macro"},
      searchTags = {"Farming", "Mithril Macro", "Foraging Macro", "Gold Macro", "Hardstone Macro", "Powder Macro", "Nuker", "Crop Aura", "Crop Placer Aura", "Commission Macro", "Fishing Macro"}
   )
   public int macroKey;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Farming Macro Keybind",
      description = "Change the farming macro keybind. You still need to set your macro keybind to \"Farming\".",
      category = "Macros",
      subcategory = "Macro",
      options = {"None", "F11", "Sugar Cane Macro", "S Shaped Farm", "Cocoa Bean"},
      searchTags = {"F11", "AutoF11", "Sugar Cane Macro", "S Shaped Farm", "Cocoa Bean"}
   )
   public int farmingMacroKey;
   @Property(
      type = PropertyType.SWITCH,
      name = "Sneak While Using Macros",
      description = "Sneak while using the macros.",
      category = "Macros",
      subcategory = "Macro"
   )
   public boolean sneakWhenUsingMacro;
   @Property(
      type = PropertyType.SLIDER,
      name = "AOTV TP Movement Wait",
      description = "Change the cooldown of the aotv tping for all of the aotv tp's. Useful if you have low mana. (measured in ms)",
      category = "Macros",
      subcategory = "Macro - Commisssion",
      min = 500,
      max = 5000,
      searchTags = {"Commission Macro", "Mithril Macro"}
   )
   public int commissionMacroTpWait;
   @Property(
      type = PropertyType.SLIDER,
      name = "Foraging Macro Extra Delay",
      description = "Sets the extra delay of the foraging macro. Use if monkey isn't lvl 100.",
      category = "Macros",
      subcategory = "Macro",
      max = 1500
   )
   public int foragingMacroDelay;
   @Property(
      type = PropertyType.SLIDER,
      name = "Powder Macro Ticks",
      description = "Change the amount of ticks the powder macro uses for each hardstone block.",
      category = "Macros",
      subcategory = "Macro",
      min = 1,
      max = 5
   )
   public int powderMacroTicks;
   @Property(
      type = PropertyType.SLIDER,
      name = "Mithril Macro Rotate Amount",
      description = "Change the rotate amount of the mithril and gold macro. Hypixel doesn't ban for rotations, but 10 should be safe either way.",
      category = "Macros",
      subcategory = "Macro - Mithril",
      min = 1,
      max = 20
   )
   public int mithrilRotateAmount;
   @Property(
      type = PropertyType.SLIDER,
      name = "Mithril Macro Max Rotation",
      description = "Change the max amount of yaw and pitch rotation of the mithril macro. Lower makes you look more legit, but means you're gonna earn less. 100 is prob the best.",
      category = "Macros",
      subcategory = "Macro - Mithril",
      min = 75,
      max = 200
   )
   public int mithrilMaxRotation;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Mithril Macro Auto Refuel",
      description = "Change the mode of the mithril auto refuel.",
      category = "Macros",
      subcategory = "Macro - Mithril",
      options = {"Off", "Normal", "Blatant"}
   )
   public int mithrilAutoRefuel;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Mithril Macro Priority",
      description = "Change the highest mithril macro priority.",
      category = "Macros",
      subcategory = "Macro - Mithril",
      options = {"Wool", "Prismarine", "Clay", "Titanium", "Gold"}
   )
   public int mithrilPriority;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Mithril Macro Priority #2",
      description = "Change the 2nd highest mithril macro priority.",
      category = "Macros",
      subcategory = "Macro - Mithril",
      options = {"Wool", "Prismarine", "Clay", "Titanium", "Gold"}
   )
   public int mithrilPriority2;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Mithril Macro Priority #3",
      description = "Change the 3rd highest mithril macro priority.",
      category = "Macros",
      subcategory = "Macro - Mithril",
      options = {"Wool", "Prismarine", "Clay", "Titanium", "Gold"}
   )
   public int mithrilPriority3;
   @Property(
      type = PropertyType.SLIDER,
      name = "Mithril & Gold Macro Fix Time",
      description = "Change the amount of fix time for mithril and gold macro (in seconds). Use high fix times with low mining speed.",
      category = "Macros",
      subcategory = "Macro - Mithril",
      min = 2,
      max = 15
   )
   public int mithrilMacroFixTime;
   @Property(
      type = PropertyType.SLIDER,
      name = "Crop Aura Extra BPS",
      description = "Change the amount of bps when using crop aura. 0 is 20 bps, 20 is 40 bps. Apparently this doesn't ban but not sure.",
      category = "Macros",
      subcategory = "Macro - Crop Aura",
      max = 20
   )
   public int cropAuraBPS;
   @Property(
      type = PropertyType.SLIDER,
      name = "Crop Aura Smooth Rotation",
      description = "Change the smooth rotation amount of crop aura. Too high might flag watchdog. Default is 45.",
      category = "Macros",
      subcategory = "Macro - Crop Aura",
      min = 30,
      max = 100
   )
   public int cropAuraSmoothRotation;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Crop Aura Mode",
      description = "Change the crop aura mode.",
      category = "Macros",
      subcategory = "Macro - Crop Aura",
      options = {"Default", "Sugar Cane", "Cactus", "Cocoa Beans", "Nether Wart"}
   )
   public int cropAuraMode;
   @Property(
      type = PropertyType.SWITCH,
      name = "Crop Aura Place Through Blocks",
      description = "Allows the crop aura to place through blocks. Shouldn't be beamable but idk tbh.",
      category = "Macros",
      subcategory = "Macro - Crop Aura"
   )
   public boolean placeThroughBlocks;
   @Property(
      type = PropertyType.SWITCH,
      name = "Nuker",
      description = "Anticheat safe. This is just a failsafe to only enable nuker if this is activated, so you'd still need to click the macro key.",
      category = "Macros",
      subcategory = "Macro - Nuker"
   )
   public boolean nuker;
   @Property(
      type = PropertyType.SWITCH,
      name = "Nuker Fake Rotation",
      description = "Changes the rotation packets of the nuker to be the rotation of the block. Use if using nuker on hypixel.",
      category = "Macros",
      subcategory = "Macro - Nuker"
   )
   public boolean nukerRotationPackets;
   @Property(
      type = PropertyType.SWITCH,
      name = "Nuker Only Nuke Reachable Blocks",
      description = "Only nuke blocks that are actually reachable to the player. Hypixel has no checks for that tho.",
      category = "Macros",
      subcategory = "Macro - Nuker"
   )
   public boolean nukeReachableBlocks;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Nuker Type",
      description = "Change the type of the nuker.",
      category = "Macros",
      subcategory = "Macro - Nuker",
      options = {"All", "Gemstone", "Crops", "Gold", "Bed", "Foraging", "Sand", "Ores"},
      searchTags = {"Gemstone Nuker"}
   )
   public int nukerType;
   @Property(
      type = PropertyType.SLIDER,
      name = "§cNuker Extra BPS",
      description = "§c(Use at own risk!) §fChance to send extra packets per second. Normal amount are 20/tick. (realistically a few extra packets are undetectable but idk). Would not recommend going over 20 at all unless you're using bed aura.",
      category = "Macros",
      subcategory = "Macro - Nuker",
      max = 80
   )
   public int nukerExtraPackets;
   @Property(
      type = PropertyType.SWITCH,
      name = "Gemstone Nuker Glass Panes",
      description = "Makes the gemstone nuker also mine glass panes.",
      category = "Macros",
      subcategory = "Macro - Nuker"
   )
   public boolean gemstoneNukerGlassPanes;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Gemstone Nuker Gemstone Type",
      description = "Change the gemstone type the nuker should focus.",
      category = "Macros",
      subcategory = "Macro - Nuker",
      options = {"All", "Ruby", "Jade", "Amber", "Topaz", "Sapphire", "Amethyst", "Jasper"}
   )
   public int gemstoneNukerType;
   @Property(
      type = PropertyType.SWITCH,
      name = "Captcha Warning",
      description = "Warns the player if a captcha happens.",
      category = "Macros",
      subcategory = "Macro - Failsafe"
   )
   public boolean captchaWarning;
   @Property(
      type = PropertyType.SWITCH,
      name = "Failsafe Webhook",
      description = "Sends a message to the webhook when a failsafe is performed and which failsafe was performed.",
      category = "Macros",
      subcategory = "Macro - Failsafes"
   )
   public boolean failsafeWebhook;
   @Property(
      type = PropertyType.SWITCH,
      name = "Failsafe Webhook Ping",
      description = "Pings @everyone when a failsafe is performed.",
      category = "Macros",
      subcategory = "Macro - Failsafes"
   )
   public boolean failsafeWebhookPing;
   @Property(
      type = PropertyType.TEXT,
      name = "Failsafe Webhook URL",
      description = "Change the url of the failsafe webhook.",
      category = "Macros",
      subcategory = "Macro - Failsafes"
   )
   public String failsafeWebhookName;
   @Property(
      type = PropertyType.SWITCH,
      name = "Use Best Failsafes",
      description = "Always use the best failsafes for the macro. Does not apply for player detection and rotation packet failsafes. I'd recommend keeping this on.",
      category = "Macros",
      subcategory = "Macro - Failsafe"
   )
   public boolean optimalFailsafes;
   @Property(
      type = PropertyType.SWITCH,
      name = "Disable Macro On Switch World",
      description = "Disables the current macro when switching world.",
      category = "Macros",
      subcategory = "Macro - Failsafes"
   )
   public boolean disableOnWorldLoad;
   @Property(
      type = PropertyType.SWITCH,
      name = "Rotation Packet Failsafe",
      description = "Temporarily disables the macro when receiving a rotation packet.",
      category = "Macros",
      subcategory = "Macro - Failsafes"
   )
   public boolean rotationFailsafe;
   @Property(
      type = PropertyType.SWITCH,
      name = "TP Failsafe",
      description = "Temporarily disables the macro when receiving a TP Packet.",
      category = "Macros",
      subcategory = "Macro - Failsafes"
   )
   public boolean positionChangeFailsafe;
   @Property(
      type = PropertyType.SWITCH,
      name = "Bedrock Box Failsafe",
      description = "Disables, then reenables the macro if you're trapped in a bedrock box. Use Position Change Failsafe for mithril.",
      category = "Macros",
      subcategory = "Macro - Failsafes"
   )
   public boolean bedrockBoxFailsafe;
   @Property(
      type = PropertyType.SWITCH,
      name = "Player Detection",
      description = "Warps out of the lobby if a player is in x range of you.",
      category = "Macros",
      subcategory = "Macro - Failsafes"
   )
   public boolean stopWhenNearPlayer;
   @Property(
      type = PropertyType.SLIDER,
      name = "Player Detection Range Slider",
      description = "Change the range of the player detection.",
      category = "Macros",
      subcategory = "Macro - Failsafes",
      min = 5,
      max = 35
   )
   public int stopWhenNearPlayerRange;
   @Property(
      type = PropertyType.SLIDER,
      name = "Player Detection Timeout Slider",
      description = "Change the timeout after detecting a player (in seconds).",
      category = "Macros",
      subcategory = "Macro - Failsafes",
      min = 1,
      max = 20
   )
   public int stopWhenNearPlayerTimeout;
   @Property(
      type = PropertyType.SWITCH,
      name = "Farming Macros Lock Yaw And Pitch",
      description = "Once toggled, saves the yaw and pitch and will always rotate to that yaw and pitch.",
      category = "Macros",
      subcategory = "Macro - Farming"
   )
   public boolean lockYawAndPitch;
   @Property(
      type = PropertyType.SWITCH,
      name = "AutoF11 Go Back To Island",
      description = "Goes back to island if autof11 is toggled.",
      category = "Macros",
      subcategory = "Macro - F11"
   )
   public boolean goBackToIs;
   @Property(
      type = PropertyType.SWITCH,
      name = "Hold \"W\"",
      description = "Holds \"W\" (forward) when using AutoF11.",
      category = "Macros",
      subcategory = "Macro - F11"
   )
   public boolean holdW;
   @Property(
      type = PropertyType.SWITCH,
      name = "Hold \"A\"",
      description = "Holds \"A\" (left) when using AutoF11.",
      category = "Macros",
      subcategory = "Macro - F11"
   )
   public boolean holdA;
   @Property(
      type = PropertyType.SWITCH,
      name = "Hold \"D\"",
      description = "Holds \"D\" (right) when using AutoF11.",
      category = "Macros",
      subcategory = "Macro - F11"
   )
   public boolean holdD;
   @Property(
      type = PropertyType.SWITCH,
      name = "Hold \"S\"",
      description = "Holds \"S\" (back) when using AutoF11.",
      category = "Macros",
      subcategory = "Macro - F11"
   )
   public boolean holdS;
   @Property(
      type = PropertyType.SWITCH,
      name = "Hold Leftclick",
      description = "Holds Leftclick when using AutoF11.",
      category = "Macros",
      subcategory = "Macro - F11"
   )
   public boolean holdLeftClick;
   @Property(
      type = PropertyType.SWITCH,
      name = "Toggle Auto Item Key",
      description = "Toggle the auto item keybind. Can also be done with a keybind.",
      category = "Keybinds",
      subcategory = "Auto Item Keybind"
   )
   public boolean autoMacroKeyToggle;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Macro Key Sync Held Item",
      description = "Syncs the currently held item by the player with the server instead of waiting 1 tick. Having this off looks less blatant.",
      category = "Keybinds",
      subcategory = "Auto Item Keybind"
   )
   public boolean autoMacroKeyToggleSync;
   @Property(
      type = PropertyType.TEXT,
      name = "Any Item With Anything Name",
      description = "Change the item name of the any item with anything keybind. Only has to be part of the display name and isn't case sensitive.",
      category = "Keybinds",
      subcategory = "Auto Item Keybind"
   )
   public String anyItemWithAnythingName;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Any Item With Anything Action",
      description = "Change the action of the any item with anything keybind.",
      category = "Keybinds",
      subcategory = "Auto Item Keybind",
      options = {"Right", "Left"}
   )
   public int anyItemWithAnythingAction;
   @Property(
      type = PropertyType.SLIDER,
      name = "Any Item With Anything Delay",
      description = "Change the delay per action. Anything below 50 won't help.",
      category = "Keybinds",
      subcategory = "Auto Item Keybind",
      max = 500
   )
   public int anyItemWithAnythingDelay;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Right Click Spammer Speed",
      description = "Change the speed of the right click spammer",
      category = "Keybinds",
      subcategory = "Right Click Spammer",
      options = {"None", "20 CPS", "60-100 CPS", "Clickset", "20 CPS Use Item", "60-100 CPS Use Item"}
   )
   public int rightClickSpammerMode;
   @Property(
      type = PropertyType.SLIDER,
      name = "Right Click Spammer Clickamount",
      description = "Change the clickamount of the clickset. Only works if the clickset option is used.",
      category = "Keybinds",
      subcategory = "Right Click Spammer",
      min = 25,
      max = 400
   )
   public int clickAmount;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auschwitz Simulator",
      description = "Want to see auschwitz in minecraft? say no more!",
      category = "Jokes",
      subcategory = "Skin"
   )
   public boolean auschwitzSimulator;
   @Property(
      type = PropertyType.SWITCH,
      name = "Pizzaboi Skin",
      description = "Change all the skins to pizzaboi skin",
      category = "Jokes",
      subcategory = "Skin"
   )
   public boolean replaceSkin;
   @Property(
      type = PropertyType.SWITCH,
      name = "Funny Puzzle Fail Msg",
      description = "Shows ur ip in chat when u fail a puzzle. Doesn't send it, only shows it. Wouldn't recommend if ur screensharing or smth",
      category = "Jokes",
      subcategory = "Dungeons"
   )
   public boolean funnyDungeonFail;
   @Property(
      type = PropertyType.SWITCH,
      name = "Funny Loading Screen",
      description = "Ever think the loading screen is a bit boring?",
      category = "Jokes",
      subcategory = "Loading Screen"
   )
   public boolean funnyLoadingScreen;
   @Property(
      type = PropertyType.SWITCH,
      name = "Funny Loading Screen Arabic Text",
      description = "لا أعرف ما يفترض أن يقوله هذا النص ، أريد فقط الحصول على نص عربي هنا",
      category = "Jokes",
      subcategory = "Loading Screen"
   )
   public boolean funnyLoadingScreenArab;
   @Property(
      type = PropertyType.SWITCH,
      name = "Funny Loading Screen Trollface",
      description = "⣀⣠⣤⣤⣤⣤⢤⣤⣄⣀⣀⣀⣀⡀⡀⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄\n⠄⠉⠹⣾⣿⣛⣿⣿⣞⣿⣛⣺⣻⢾⣾⣿⣿⣿⣶⣶⣶⣄⡀⠄⠄⠄\n⠄⠄⠠⣿⣷⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣯⣿⣿⣿⣿⣿⣿⣆⠄⠄\n⠄⠄⠘⠛⠛⠛⠛⠋⠿⣷⣿⣿⡿⣿⢿⠟⠟⠟⠻⠻⣿⣿⣿⣿⡀⠄\n⠄⢀⠄⠄⠄⠄⠄⠄⠄⠄⢛⣿⣁⠄⠄⠒⠂⠄⠄⣀⣰⣿⣿⣿⣿⡀\n⠄⠉⠛⠺⢶⣷⡶⠃⠄⠄⠨⣿⣿⡇⠄⡺⣾⣾⣾⣿⣿⣿⣿⣽⣿⣿\n⠄⠄⠄⠄⠄⠛⠁⠄⠄⠄⢀⣿⣿⣧⡀⠄⠹⣿⣿⣿⣿⣿⡿⣿⣻⣿\n⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠉⠛⠟⠇⢀⢰⣿⣿⣿⣏⠉⢿⣽⢿⡏\n⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠠⠤⣤⣴⣾⣿⣿⣾⣿⣿⣦⠄⢹⡿⠄\n⠄⠄⠄⠄⠄⠄⠄⠄⠒⣳⣶⣤⣤⣄⣀⣀⡈⣀⢁⢁⢁⣈⣄⢐⠃⠄\n⠄⠄⠄⠄⠄⠄⠄⠄⠄⣰⣿⣛⣻⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡯⠄⠄\n⠄⠄⠄⠄⠄⠄⠄⠄⠄⣬⣽⣿⣻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠁⠄⠄\n⠄⠄⠄⠄⠄⠄⠄⠄⠄⢘⣿⣿⣻⣛⣿⡿⣟⣻⣿⣿⣿⣿⡟⠄⠄⠄\n⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠛⢛⢿⣿⣿⣿⣿⣿⣿⣷⡿⠁⠄⠄⠄\n⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠄⠉⠉⠉⠉⠈⠄⠄⠄⠄⠄⠄\n",
      category = "Jokes",
      subcategory = "Loading Screen"
   )
   public boolean funnyLoadingScreenTrollFace;
   @Property(
      type = PropertyType.SWITCH,
      name = "Chest Esp",
      description = "Creates an esp on chests. Esp will be removed after clicking on the chest.",
      category = "Visuals",
      subcategory = "Esp"
   )
   public boolean secretChestEsp;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Chest Esp Type",
      description = "Change the type of esp of the chest.",
      category = "Visuals",
      subcategory = "Esp",
      options = {"Filled", "Outlined"}
   )
   public int chestEspMode;
   @Property(
      type = PropertyType.COLOR,
      name = "Chest Esp Color",
      description = "Changes the color of the chest esp.",
      category = "Visuals",
      subcategory = "Esp"
   )
   public Color secretChestEspColor;
   @Property(
      type = PropertyType.SWITCH,
      name = "All Mob Esp",
      description = "Creates an esp on all mob armor stands.",
      category = "Visuals",
      subcategory = "Esp"
   )
   public boolean allMobEsp;
   @Property(
      type = PropertyType.SWITCH,
      name = "Remove Annoying Potion Effects",
      description = "Removes Nausea and all fog like blindness and lava fog.",
      category = "Visuals",
      subcategory = "Render",
      searchTags = {"Anti Blindness", "Anti Nausea"}
   )
   public boolean antiBlindness;
   @Property(
      type = PropertyType.DECIMAL_SLIDER,
      name = "RGB Brightness",
      description = "Change the brightness of both the mod message and rgb names.",
      category = "Visuals",
      subcategory = "RGB",
      minF = 0.5F,
      maxF = 1.0F,
      decimalPlaces = 2
   )
   public float rgbBrightness;
   @Property(
      type = PropertyType.SELECTOR,
      name = "Custom Mod Message RGB Color",
      description = "Change the color gradient of the mod message.",
      category = "Visuals",
      subcategory = "Mod Message",
      options = {"Rainbow", "More Red", "More Blue", "More Green"}
   )
   public int modMessageColor;
   @Property(
      type = PropertyType.SWITCH,
      name = "Toggle ToggleSprint",
      description = "Toggle ToggleSprint",
      category = "Visuals",
      subcategory = "QoL"
   )
   public boolean toggleSprint;
   @Property(
      type = PropertyType.TEXT,
      name = "Custom ToggleSprint Name",
      description = "Custom ToggleSprint Name",
      category = "Visuals",
      subcategory = "QoL"
   )
   public String customToggleSprintName;
   @Property(
      type = PropertyType.COLOR,
      name = "Custom ToggleSprint Color",
      description = "Custom ToggleSprint Color",
      category = "Visuals",
      subcategory = "QoL"
   )
   public Color customToggleSprintColor;
   @Property(
      type = PropertyType.SWITCH,
      name = "Stop Renderering Hand",
      description = "Doesn't render the player's hand.",
      category = "Visuals",
      subcategory = "QoL"
   )
   public boolean stopRenderingHand;
   @Property(
      type = PropertyType.SWITCH,
      name = "Anti Furry Filter",
      description = "Filters out all furry words.",
      category = "Visuals",
      subcategory = "Chat"
   )
   public boolean antiFurry;
   @Property(
      type = PropertyType.TEXT,
      name = "Anti Furry Filter Censor",
      description = "Change the censor text of the anti furry filter.",
      category = "Visuals",
      subcategory = "Chat"
   )
   public String antiFurryCensor;
   @Property(
      type = PropertyType.SWITCH,
      name = "Session Protection",
      description = "Enable session protection. Having this enabled might interfere with ner and other ct modules. Only disables after restarting game. \n§cNOTE: Allthough it's really good, i can't gurantee a 100% chance of having your sessionid protected.",
      category = "Other",
      subcategory = "General"
   )
   public boolean sessionProtection;
   @Property(
      type = PropertyType.SWITCH,
      name = "Keep Focus While Tabbed Out",
      description = "Cancels the esc menu when tabbed out.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean noEscMenu;
   @Property(
      type = PropertyType.SWITCH,
      name = "Hide Horses",
      description = "Hides horses.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean hideHorses;
   @Property(
      type = PropertyType.SWITCH,
      name = "Ignore Entities Bounding Box",
      description = "Ignores all entities so you'll always be facing a block. Useful for building n mining. If you can't interact with entities, this is the reason.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean ignoreEntities;
   @Property(
      type = PropertyType.SWITCH,
      name = "Change Cocoa Bean Size",
      description = "Changes the size of fully grown cocoa beans to 1 block and the size of not fully grown cocoa beans to 0.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean cocoaBeanSize;
   @Property(
      type = PropertyType.SWITCH,
      name = "Ignore Block Bounding Box",
      description = "Hits entites through blocks if there's an entity in range.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean hitThroughBlocks;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Reconnect",
      description = "Auto reconnect to hypixel when getting disconnected. The macros always have this feature on.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean autoReconnect;
   @Property(
      type = PropertyType.SWITCH,
      name = "Auto Join Skyblock",
      description = "Automatically join skyblock when in hypixel. ",
      category = "Other",
      subcategory = "Misc",
      searchTags = {"Auto Skyblock"}
   )
   public boolean autoSkyblock;
   @Property(
      type = PropertyType.SWITCH,
      name = "Block Useless Zombie Swords",
      description = "Blocks useless zombie swords.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean blockUselessZombieSword;
   @Property(
      type = PropertyType.SWITCH,
      name = "Block Alignment",
      description = "Blocks you from using alignment.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean blockAlignment;
   @Property(
      type = PropertyType.SWITCH,
      name = "Silence Enderman",
      description = "Silences endermen.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean silenceEnderman;
   @Property(
      type = PropertyType.SWITCH,
      name = "Get Current Sound",
      description = "Get current sound playing. Helps to get the name of the sound to silence.",
      category = "Other",
      subcategory = "Misc"
   )
   public boolean getCurrentSound;
   @Property(
      type = PropertyType.TEXT,
      name = "Chat Spammer",
      description = "/p [ign] /p disband /p [ign] /p disband /p [ign] /p disband /p [ign] /p disband /p [ign] /p disband",
      category = "Other",
      subcategory = "Misc"
   )
   public String chatSpammerName;
   @Property(
      type = PropertyType.SLIDER,
      name = "Chat Spammer Delay",
      description = "Changes the chat spammer delay for each action.",
      category = "Other",
      subcategory = "Misc",
      min = 10,
      max = 500
   )
   public int chatSpammerDelay;

   public Config() {
      super(new File("./config/pizzaclient/config.toml"), "PizzaClient", new JVMAnnotationPropertyCollector(), new ConfigSorting());
      this.commandsList = EnumChatFormatting.AQUA + "List of commands:\n" + EnumChatFormatting.GREEN + "- /arabfunny: opens a random r/arabfunny video\n" + EnumChatFormatting.GREEN + "- /aotvtest: test the current mithril markers. /aotvtest clear clears all markers\n" + EnumChatFormatting.GREEN + "- /autopet [number or pet name]: opens pets menu and clicks the slot of the number\n" + EnumChatFormatting.GREEN + "- /blockability: blocks the right click ability of the held item\n" + EnumChatFormatting.GREEN + "- /chatspammer: /p [ign] /p disband /p [ign] /p disband /p [ign] /p disband...\n" + EnumChatFormatting.GREEN + "- /customtogglesprint: custom togglesprint message (can also be set in config)\n" + EnumChatFormatting.GREEN + "- /itemmacro: changes the custom item macro key option to use the held item as the macrokey\n" + EnumChatFormatting.GREEN + "- /pathfind [x y z]: pathfinds to those coords\n" + EnumChatFormatting.GREEN + "- /reloadnames: reloads the rgb names\n" + EnumChatFormatting.GREEN + "- /setyaw [yaw or yaw and pitch]: sets your rotation to the yaw you entered in\n" + EnumChatFormatting.GREEN + "- /silencer (alias: silence): Silences a specific sound (get the sound name by a feature in \"Other\")\n";
      this.WIP = "§b[WIP] §f";
      this.risky = "§cUse at own risk! §f";
      this.funnyTerminals = false;
      this.terminalClickMode = 0;
      this.terminalSleepAmount = 130;
      this.terminalFixTime = 350;
      this.autoAlignment = false;
      this.autoAimingDevice = false;
      this.autoClipGhostblocks = false;
      this.spiritBearAura = false;
      this.alwaysAimAtSpiritBear = false;
      this.lividAura = false;
      this.superboomAura = false;
      this.quizAura = false;
      this.autoCloseDungChest = false;
      this.bloodTriggerBot = false;
      this.sneakAotv = false;
      this.autoReady = false;
      this.autoLeapDoor = false;
      this.terminatorClicker = false;
      this.terminatorClickerDelay = 25;
      this.qolIceFill = false;
      this.batEsp = false;
      this.batEspColor = Color.MAGENTA;
      this.lividEsp = false;
      this.dungeonKeyEsp = false;
      this.starredMobsEsp = false;
      this.starredMobsEspRange = 32;
      this.starredMobsEspColor = new Color(255, 150, 0);
      this.hideSuperboom = false;
      this.showHiddenMobs = false;
      this.fasterGhostblocks = 0;
      this.autoSoulcry = false;
      this.minibossEsp = false;
      this.bossEsp = false;
      this.monolithEsp = false;
      this.balEsp = false;
      this.butterflyEsp = false;
      this.corleonePing = false;
      this.teamTresuriteEsp = false;
      this.ironGolemEsp = false;
      this.goblinEsp = false;
      this.sludgeEsp = false;
      this.yogEsp = false;
      this.gemstoneScanner = false;
      this.scannerWidth = 32;
      this.scannerDelay = 5;
      this.gemstoneEspSize = 40;
      this.redGlassEsp = false;
      this.orangeGlassEsp = false;
      this.lightBlueGlassEsp = false;
      this.limeGlassEsp = false;
      this.purpleGlassEsp = false;
      this.yellowGlassEsp = false;
      this.pinkGlassEsp = false;
      this.glassPanesEsp = false;
      this.pinkGlassPanesEspSize = 30;
      this.hideGoldenGoblin = false;
      this.coordsList = false;
      this.autoCloseLootChest = false;
      this.powderChestMacro = false;
      this.autoPickonimbus = false;
      this.terminatorValk = false;
      this.autoJerryBox = false;
      this.autoJerryBoxDelay = 0;
      this.autoJerryBoxClosingDelay = 0;
      this.autoJerryBoxOpenDelay = 0;
      this.autoJerryBoxSwapSlot = false;
      this.funnyEnchanting = false;
      this.funnyEnchantingCloseChest = false;
      this.funnyEnchantingDelay = 115;
      this.autoWardrobeSlot = 0;
      this.autoHarpSolver = false;
      this.harpSolverDelay = 110;
      this.autoBookCombine = false;
      this.autoBookCombineDelay = 0;
      this.autoSalvage = false;
      this.autoSalvageDelay = 0;
      this.macroKey = 0;
      this.farmingMacroKey = 0;
      this.sneakWhenUsingMacro = false;
      this.commissionMacroTpWait = 2200;
      this.foragingMacroDelay = 0;
      this.powderMacroTicks = 2;
      this.mithrilRotateAmount = 10;
      this.mithrilMaxRotation = 100;
      this.mithrilAutoRefuel = 0;
      this.mithrilPriority = 0;
      this.mithrilPriority2 = 1;
      this.mithrilPriority3 = 3;
      this.mithrilMacroFixTime = 4;
      this.cropAuraBPS = 0;
      this.cropAuraSmoothRotation = 45;
      this.cropAuraMode = 0;
      this.placeThroughBlocks = false;
      this.nuker = true;
      this.nukerRotationPackets = true;
      this.nukeReachableBlocks = false;
      this.nukerType = 0;
      this.nukerExtraPackets = 0;
      this.gemstoneNukerGlassPanes = true;
      this.gemstoneNukerType = 0;
      this.captchaWarning = false;
      this.failsafeWebhook = false;
      this.failsafeWebhookPing = false;
      this.failsafeWebhookName = "";
      this.optimalFailsafes = true;
      this.disableOnWorldLoad = false;
      this.rotationFailsafe = false;
      this.positionChangeFailsafe = false;
      this.bedrockBoxFailsafe = false;
      this.stopWhenNearPlayerRange = 20;
      this.stopWhenNearPlayerTimeout = 3;
      this.lockYawAndPitch = true;
      this.goBackToIs = true;
      this.holdW = false;
      this.holdA = false;
      this.holdD = false;
      this.holdS = false;
      this.holdLeftClick = false;
      this.autoMacroKeyToggle = false;
      this.autoMacroKeyToggleSync = false;
      this.anyItemWithAnythingName = "Aspect of the Void";
      this.anyItemWithAnythingAction = 0;
      this.anyItemWithAnythingDelay = 0;
      this.rightClickSpammerMode = 0;
      this.clickAmount = 50;
      this.auschwitzSimulator = false;
      this.replaceSkin = false;
      this.funnyDungeonFail = false;
      this.funnyLoadingScreen = true;
      this.funnyLoadingScreenArab = true;
      this.funnyLoadingScreenTrollFace = true;
      this.secretChestEsp = false;
      this.chestEspMode = 0;
      this.secretChestEspColor = new Color(200, 10, 10);
      this.allMobEsp = false;
      this.antiBlindness = false;
      this.rgbBrightness = 0.9F;
      this.modMessageColor = 2;
      this.toggleSprint = false;
      this.customToggleSprintName = "qol";
      this.customToggleSprintColor = Color.WHITE;
      this.stopRenderingHand = false;
      this.antiFurry = false;
      this.antiFurryCensor = "***";
      this.sessionProtection = true;
      this.noEscMenu = false;
      this.hideHorses = false;
      this.ignoreEntities = false;
      this.cocoaBeanSize = false;
      this.hitThroughBlocks = false;
      this.autoReconnect = false;
      this.autoSkyblock = false;
      this.blockUselessZombieSword = false;
      this.blockAlignment = false;
      this.silenceEnderman = false;
      this.getCurrentSound = false;
      this.chatSpammerName = "";
      this.chatSpammerDelay = 300;
      String[] terminals = new String[]{"terminalClickMode", "terminalSleepAmount", "terminalFixTime"};
      String[] gemstone = new String[]{"gemstoneEspSize", "scannerWidth", "scannerDelay", "redGlassEsp", "orangeGlassEsp", "lightBlueGlassEsp", "limeGlassEsp", "purpleGlassEsp", "yellowGlassEsp", "pinkGlassEsp", "glassPanesEsp", "pinkGlassPanesEspSize"};
      String[] var3 = terminals;
      int var4 = terminals.length;

      int var5;
      String s;
      for(var5 = 0; var5 < var4; ++var5) {
         s = var3[var5];
         this.addDependency(s, "funnyTerminals");
      }

      var3 = gemstone;
      var4 = gemstone.length;

      for(var5 = 0; var5 < var4; ++var5) {
         s = var3[var5];
         this.addDependency(s, "gemstoneScanner");
      }

      this.addDependency("batEspColor", "batEsp");
      this.addDependency("starredMobsEspColor", "starredMobsEsp");
      this.addDependency("starredMobsEspRange", "starredMobsEsp");
      this.addDependency("harpSolverDelay", "autoHarpSolver");
      this.addDependency("nukerType", "nuker");
      this.addDependency("nukerRotationPackets", "nuker");
      this.addDependency("nukerExtraPackets", "nuker");
      this.addDependency("nukeReachableBlocks", "nuker");
      this.addDependency("gemstoneNukerGlassPanes", "nuker");
      this.addDependency("secretChestEspColor", "secretChestEsp");
      this.addDependency("chestEspMode", "secretChestEsp");
      this.addDependency("stopWhenNearPlayerRange", "stopWhenNearPlayer");
      this.addDependency("funnyEnchantingDelay", "funnyEnchanting");
      this.addDependency("funnyEnchantingCloseChest", "funnyEnchanting");
      this.addDependency("alwaysAimAtSpiritBear", "spiritBearAura");
      this.addDependency("customToggleSprintName", "toggleSprint");
      this.addDependency("customToggleSprintColor", "toggleSprint");
      this.addDependency("autoBookCombineDelay", "autoBookCombine");
      this.addDependency("autoSalvageDelay", "autoSalvage");
      this.addDependency("funnyLoadingScreenTrollFace", "funnyLoadingScreen");
      this.addDependency("funnyLoadingScreenArab", "funnyLoadingScreen");
      this.addDependency("autoJerryBoxDelay", "autoJerryBox");
      this.addDependency("autoJerryBoxClosingDelay", "autoJerryBox");
      this.addDependency("autoJerryBoxSwapSlot", "autoJerryBox");
      this.addDependency("failsafeWebhookName", "failsafeWebhook");
      this.addDependency("failsafeWebhookPing", "failsafeWebhook");
      this.addDependency("terminatorClickerDelay", "terminatorClicker");
      this.addDependency("antiFurryCensor", "antiFurry");
      this.addDependency("stopWhenNearPlayerTimeout", "stopWhenNearPlayer");
      this.init();
   }

   private void init() {
      this.initialize();
      this.markDirty();
      this.preload();
   }
}
