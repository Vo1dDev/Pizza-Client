package qolskyblockmod.pizzaclient.plugins;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.MixinEnvironment;

@Name(" ")
public class PizzaClientLoadingPlugin implements IFMLLoadingPlugin {
   private static final String badMixinVersionMessage = "<html><p>Pizza Client has detected an older version of Mixin.<br>Many of my features require Mixin 0.7 or later!<br>In order to resolve this conflict you must update<br>any mods with a Mixin version below 0.7.<br>If you have already done this and are still getting this error,<br>ask me for support.";

   public PizzaClientLoadingPlugin() {
      if (!StringUtils.startsWithAny(MixinEnvironment.getCurrentEnvironment().getVersion(), new CharSequence[]{"0.7", "0.8"})) {
         this.showMessage("<html><p>Pizza Client has detected an older version of Mixin.<br>Many of my features require Mixin 0.7 or later!<br>In order to resolve this conflict you must update<br>any mods with a Mixin version below 0.7.<br>If you have already done this and are still getting this error,<br>ask me for support.<br>The Problem seems to be " + (new File(MixinEnvironment.class.getProtectionDomain().getCodeSource().getLocation().toString())).getName() + "</p></html>");
      }

   }

   public String[] getASMTransformerClass() {
      return new String[0];
   }

   public String getModContainerClass() {
      return null;
   }

   public String getSetupClass() {
      return null;
   }

   public void injectData(Map<String, Object> data) {
   }

   public String getAccessTransformerClass() {
      return null;
   }

   private void showMessage(String errorMessage) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      JFrame frame = new JFrame();
      frame.setUndecorated(true);
      frame.setAlwaysOnTop(true);
      frame.setLocationRelativeTo((Component)null);
      frame.setVisible(true);
      JButton close = new JButton("Close");
      close.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            PizzaClientLoadingPlugin.this.exit();
         }
      });
      JButton[] totalOptions = new JButton[]{close};
      JOptionPane.showOptionDialog(frame, errorMessage, "PizzaClient Error", -1, 0, (Icon)null, totalOptions, totalOptions[0]);
      this.exit();
   }

   private void exit() {
      try {
         Class<?> clazz = Class.forName("java.lang.Shutdown");
         Method m_exit = clazz.getDeclaredMethod("exit", Integer.TYPE);
         m_exit.setAccessible(true);
         m_exit.invoke((Object)null, 0);
      } catch (Exception var3) {
         var3.printStackTrace();
         System.exit(1);
      }

   }
}
