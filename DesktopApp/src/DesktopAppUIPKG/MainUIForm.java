package DesktopAppUIPKG;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;



public class MainUIForm extends JFrame {
    private JPanel RootPanel;
    private JPanel TopPanel;
    private JPanel BottomPanel;
    private JTextField FolderPath_TXT;
    private JLabel Cloudimg_LBL;
    private JPanel TopSelectorPanel;
    private JPanel BottomRightPanel;
    private JPanel BottomLeftPanel;
    private JLabel FolderSelector_LBL;
    private JLabel PP_LBL;
    private JLabel Exit_LBL;
    private JLabel Browse_LBL;
    private JLabel Start_Stop_LBL;
    private JTable Connection_Information_TBL;
    private JTextArea Logs_TXT;
    private JLabel Minimize_LBL;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public MainUIForm() {
        add(RootPanel);
        setTitle("Server Application");
        setSize(700, 500);
        setLocationRelativeTo(null);
//      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);




        Exit_LBL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
        Browse_LBL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                JFileChooser file_chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                file_chooser.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter file_filter = new FileNameExtensionFilter("PowerPoint Files", "ppt", "pptx");
                file_chooser.addChoosableFileFilter(file_filter);
                file_chooser.setFileFilter(file_filter);
                file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int file_chooser_value = file_chooser.showOpenDialog(null);
                String file_path = file_chooser.getSelectedFile().getAbsolutePath();
                FolderPath_TXT.setText(file_path);
                File file = new File(file_path);
                File[] Array_Of_files = file.listFiles();
                for (File f : Array_Of_files) {
                    if (f.isDirectory()) {
                        return;
                    } else if (f.isFile()) {
                        if (f.getName().endsWith(".ppt") || f.getName().endsWith(".pptx")) {
                            System.out.println(f.getName());
                        }


                    }
                }
            }
        });


        Start_Stop_LBL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Start_Stop_LBL.setIcon(new ImageIcon(getClass().getResource("/icons/button_stop.png")));

            }
        });


        Minimize_LBL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setState(Frame.ICONIFIED);
                // To restore setState(Frame.NORMAL)
            }
        });
    }


}