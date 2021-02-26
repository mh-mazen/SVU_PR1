package DesktopAppUIPKG;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;


public class MainUIForm extends JFrame {
    private JPanel RootPanel;
    private JPanel TopPanel;
    private JPanel BottomPanel;
    private JTextField FolderPath_TXT;
    private JLabel Cloudimg_LBL;
    private JPanel MiddlePanel;
    private JPanel TopSelectorPanel;
    private JPanel BottomRightPanel;
    private JPanel BottomLeftPanel;
    private JLabel Information_LBL;
    private JLabel FolderSelector_LBL;
    private JLabel PP_LBL;
    private JLabel Exit_LBL;
    private JLabel Browse_LBL;
    private JLabel Start_Stop_LBL;
    private JTable Connection_Information_TBL;
    private JTextArea Logs_TXT;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public MainUIForm() {
        add(RootPanel);
        setTitle("Server Application");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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



               /* JFileChooser file_chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                file_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                file_chooser.setAcceptAllFileFilterUsed(false);
                FileFilter file_filter = new FileNameExtensionFilter("PowerPoint Files", "ppt", "pptx");
                file_chooser.addChoosableFileFilter(file_filter);
                file_chooser.setFileFilter(file_filter);
                int file_chooser_value = file_chooser.showOpenDialog(null);
                if (file_chooser_value == JFileChooser.APPROVE_OPTION) {
                File file = file_chooser.getSelectedFile();
                    FolderPath_TXT.setText(file.getAbsolutePath());
             JOptionPane.showMessageDialog(RootPanel,file_chooser.getSelectedFile().getAbsoluteFile());
             }
                } else {
                    FolderPath_TXT.setText("");
                }*/



                JFileChooser file_chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

//                file_chooser.setAcceptAllFileFilterUsed(false);
//                FileFilter all_files_filter=file_chooser.getAcceptAllFileFilter();
//                FileNameExtensionFilter file_filter = new FileNameExtensionFilter("PowerPoint Files", "ppt", "pptx");
//                file_chooser.removeChoosableFileFilter(all_files_filter);
//                file_chooser.addChoosableFileFilter(file_filter);
//                file_chooser.addChoosableFileFilter(all_files_filter);
//                file_chooser.setFileFilter(file_filter);
                file_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                               int file_chooser_value = file_chooser.showOpenDialog(null);
                    String file_path=file_chooser.getSelectedFile().getAbsolutePath();
                    FolderPath_TXT.setText(file_path);
                    File file=new File(file_path);
                    File[] Array_Of_files=file.listFiles();
                    for(File f:Array_Of_files){
                        if(f.isDirectory()){
                            return;
                        }else if(f.isFile()){
                            if(f.getName().endsWith(".ppt")||f.getName().endsWith(".pptx")) {
                                System.out.println(f.getName());
                            }



                        }
                    }


               /* JFileChooser file_chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                file_chooser.resetChoosableFileFilters();
                file_chooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.isDirectory()) {

                            return true ;
                        }
                        else if(f.isFile()){
                            String file_name=f.getName();
                            return file_name.endsWith(".ppt")||file_name.endsWith(".pptx");
                        }
                        return true;
                    }

                    @Override
                    public String getDescription() {
                        return null;
                    }
                });
                file_chooser.setFileSelectionMode(file_chooser.FILES_AND_DIRECTORIES);
                int file_chooser_value = file_chooser.showOpenDialog(null);
                String file_path=file_chooser.getSelectedFile().getAbsolutePath();
                FolderPath_TXT.setText(file_path);*/

            /*    JFileChooser file_chooser=new JFileChooser();
                FileFilter all_files_filter=file_chooser.getAcceptAllFileFilter();
                file_chooser.removeChoosableFileFilter(all_files_filter);
                FileFilter pp_file_filter=new PPFileFilter();
                file_chooser.addChoosableFileFilter(pp_file_filter);
                file_chooser.addChoosableFileFilter(all_files_filter);
                file_chooser.setFileFilter(pp_file_filter);
                file_chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int chooser_return_value=file_chooser.showOpenDialog(getParent());
                if(chooser_return_value==JFileChooser.APPROVE_OPTION){
                    System.out.println("The selected file"+file_chooser.getSelectedFile().getName());
                }*/




            }
        });




        Start_Stop_LBL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
      /*      TopSelectorPanel.remove(Start_Stop_LBL);
             Start_Stop_LBL=new JLabel(new ImageIcon(image));
             TopSelectorPanel.add(Start_Stop_LBL);
             TopSelectorPanel.revalidate();
             TopSelectorPanel.repaint();*/




               /* BufferedImage image= null;
                try {
                    image = ImageIO.read(new File("DesktopAppUIPKG/Icons/button_stop.png"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                JLabel Stop_LBL=new JLabel(new ImageIcon(image));
                TopSelectorPanel.add(Stop_LBL);*/

            }
        });


    }





}