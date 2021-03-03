package DesktopAppUIPKG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        serverSocket = new ServerSocket(port);
        System.out.println(MessageFormat.format("Server Started on IP {0}:{1}", "" , String.valueOf(port)));
        int count = 0;
        while (true) {
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String greeting = in.readLine();
            System.out.println(greeting);
            out.println("hello client " + count);
            Robot r = new Robot();
            if (greeting.equals("two")) {
                r.keyPress(KeyEvent.VK_LEFT);
                r.keyRelease(KeyEvent.VK_LEFT);
            } else if (greeting.equals("one")) {
                r.keyPress(KeyEvent.VK_RIGHT);
                r.keyRelease(KeyEvent.VK_RIGHT);
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





    public static void main(String[] args) throws IOException, AWTException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {


        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                MainUIForm mainUIForm = new MainUIForm();
                mainUIForm.setVisible(true);

            }
        });
        MainUIClass main=new MainUIClass();
        main.start(6666);


        }

    }

