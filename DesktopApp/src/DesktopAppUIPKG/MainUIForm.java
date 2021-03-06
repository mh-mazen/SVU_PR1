package DesktopAppUIPKG;

import com.sun.security.ntlm.Server;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


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
    private JTextArea Logs_TXT;
    private JLabel Minimize_LBL;
    private JLabel ServerIP_LBL;
    private JLabel Port_LBL;
    private JLabel Status_LBL;
    private JLabel LastCommand_LBL;
    private JLabel ServerName_LBL;
    public static List<File> Files;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public MainUIForm() {
        add(RootPanel);
        setTitle("Server Application");
        setSize(700, 500);
        setLocationRelativeTo(null);
        //   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                Files = new ArrayList<>();
                for (File f : Array_Of_files) {
                    if (f.isDirectory()) {
                        return;
                    } else if (f.isFile()) {
                        if (f.getName().endsWith(".ppt") || f.getName().endsWith(".pptx")) {
                            Files.add(f);
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

                InetAddress ip = null;
                try {
                    ip = InetAddress.getLocalHost();
                } catch (UnknownHostException unknownHostException) {
                    unknownHostException.printStackTrace();
                }
                ServerIP_LBL.setText(String.valueOf(ip.getHostAddress()));
                ServerName_LBL.setText(String.valueOf(ip.getHostName()));

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