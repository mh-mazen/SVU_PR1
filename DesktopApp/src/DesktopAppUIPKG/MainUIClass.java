package DesktopAppUIPKG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;

public class MainUIClass {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException, AWTException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        serverSocket = new ServerSocket(port, 50, inetAddress);

        System.out.println(MessageFormat.format("Server Started on IP {0}:{1}", inetAddress.getHostAddress(), String.valueOf(port)));
        int count = 0;
        clientSocket = serverSocket.accept();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        while (true) {
            String greeting = in.readLine();
            if (greeting != null) {
                System.out.println(greeting);

                Robot r = new Robot();
                if (greeting.contains("open_file")) {
                    int Id = Integer.parseInt(greeting.split("\\|")[1]);
                    open(MainUIForm.Files.get(Id).getAbsolutePath());
                    out.println("hello client " + count);
                } else if (greeting.contains("move_mouse")) {
                    String[] params = greeting.split("\\|");
                    float X = Float.parseFloat(params[1]);
                    float Y = Float.parseFloat(params[2]);
                    r.mouseMove((int) X, (int) Y);
                    out.println("hello client " + count);
                } else
                    switch (greeting) {
                        case "prev_click":
                            r.keyPress(KeyEvent.VK_RIGHT);
                            r.keyRelease(KeyEvent.VK_RIGHT);

                            out.println("hello client " + count);
                            break;
                        case "nxt_click":
                            r.keyPress(KeyEvent.VK_LEFT);
                            r.keyRelease(KeyEvent.VK_LEFT);
                            out.println("hello client " + count);
                            break;
                        case "ppt_list":
                            String rt = "";
                            for (File f : MainUIForm.Files) {
                                rt += f.getName() + "|";
                            }
                            out.println(rt);
                            break;
                    }

            }
            count++;
        }

    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }

    public static void open(String targetFilePath) throws IOException {
        Desktop desktop = Desktop.getDesktop();

        desktop.open(new File(targetFilePath));
    }

    public static void main(String[] args) throws IOException, AWTException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                MainUIForm mainUIForm = new MainUIForm();
                mainUIForm.setVisible(true);

            }
        });

        MainUIClass main = new MainUIClass();
        main.start(6666);

    }

}

