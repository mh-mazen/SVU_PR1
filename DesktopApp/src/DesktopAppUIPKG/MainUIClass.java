package DesktopAppUIPKG;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class MainUIClass {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                MainUIForm mainUIForm = new MainUIForm();
                mainUIForm.setVisible(true);
            }
        });
    }

}
