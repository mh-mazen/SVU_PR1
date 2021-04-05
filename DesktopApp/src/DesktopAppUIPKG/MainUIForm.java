package DesktopAppUIPKG;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
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
    private JLabel PP_LBL;
    private JPanel TopSelectorPanel;
    private JPanel BottomRightPanel;
    private JPanel BottomLeftPanel;
    private JLabel FolderSelector_LBL;
    private JLabel Browse_LBL;
    private JLabel Start_Stop_LBL;
    private JLabel ServerIP_LBL;
    private JLabel Port_LBL;
    private JTextArea Logs_TXT;
    private JLabel Status_LBL;
    private JLabel LastCommand_LBL;
    private JLabel ServerName_LBL;
    private JScrollPane LogsPanel;
    private JSplitPane BottomSPanel;
    public static List<File> Files;
    private Thread serverThread;
    private Server server;
    private boolean isStarted;
    private MainUIForm mainForm;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public MainUIForm() {

        setTitle("Server Application");
        setSize(900, 500);
        setLocationRelativeTo(null);
        mainForm = this;
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/Icons/box.png"));
        BackgroundPanel panel = new BackgroundPanel(backgroundImage.getImage(), BackgroundPanel.SCALED, 1.0f, 0.5f);
        GradientPaint paint = new GradientPaint(0, 0, Color.decode("#DB3C8F"), 600, 0, Color.decode("#4137D6"));
        panel.setPaint(paint);
        TopPanel.setBackground(new Color(0, 0, 0, 125));
        TopSelectorPanel.setBackground(new Color(0, 0, 0, 0));
        BottomPanel.setBackground(new Color(0, 0, 0, 0));
        BottomSPanel.setBackground(new Color(0, 0, 0, 0));
        BottomLeftPanel.setBackground(new Color(0, 0, 0, 50));
        BottomRightPanel.setBackground(new Color(0, 0, 0, 50));
        Logs_TXT.setBackground(Color.decode("#4137D3"));
        panel.add(RootPanel);
        add(panel);

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
                appendLog("Folder Selected, Powerpoint files found: ");
                for (File f : Array_Of_files) {
                    if (f.isDirectory()) {
                        return;
                    } else if (f.isFile()) {
                        if (f.getName().endsWith(".ppt") || f.getName().endsWith(".pptx")) {
                            Files.add(f);
                            appendLog(f.getName());
                        }
                    }
                }
            }
        });

        Start_Stop_LBL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                appendLog("starting...");
                if (!isStarted)
                    Start_Stop_LBL.setIcon(new ImageIcon(getClass().getResource("/Icons/button_stop.png")));
                else
                    Start_Stop_LBL.setIcon(new ImageIcon(getClass().getResource("/Icons/button_start.png")));

                InetAddress ip = null;
                try {
                    ip = InetAddress.getLocalHost();
                } catch (UnknownHostException unknownHostException) {
                    unknownHostException.printStackTrace();
                }

                if (!isStarted) {
                    isStarted = true;

                    server = new Server();
                    server.setForm(mainForm);
                    serverThread = new Thread(server);
                    serverThread.start();
                    ServerIP_LBL.setText(String.valueOf(ip.getHostAddress()));
                    ServerName_LBL.setText(String.valueOf(ip.getHostName()));
                    Port_LBL.setText("6666");
                    Status_LBL.setText("Waiting");

                } else {
                    isStarted = false;
                    ServerIP_LBL.setText("");
                    ServerName_LBL.setText("");
                    Port_LBL.setText("");
                    Status_LBL.setText("");
                    try {
                        server.stop();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
                mainForm.repaint();
            }
        });


    }

    public void setStatus(String status) {
        Status_LBL.setText(status);
        mainForm.repaint();
    }

    public void setLastCommand(String command) {
        LastCommand_LBL.setText(command);
        mainForm.repaint();
    }

    public void appendLog(String log) {
        Logs_TXT.append("\n- " + log);
        mainForm.repaint();
    }

}