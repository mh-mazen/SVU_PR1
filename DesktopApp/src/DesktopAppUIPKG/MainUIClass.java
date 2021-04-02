package DesktopAppUIPKG;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import java.text.MessageFormat;

class Server implements Runnable {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private MainUIForm form;

    private void start(int port) throws IOException, AWTException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        serverSocket = new ServerSocket(port, 50, inetAddress);
        form.appendLog(MessageFormat.format("Server Started on IP {0}:{1}", inetAddress.getHostAddress(), String.valueOf(port)));
        int count = 0;
        try {
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while (!Thread.currentThread().isInterrupted()) {
                String greeting = in.readLine();
                if (greeting != null) {
                    System.out.println(greeting);
                    form.setStatus("Connected");
                    Robot r = new Robot();
                    if (greeting.contains("open_file")) {
                        form.setLastCommand("Open File");
                        form.appendLog("Open File");
                        int Id = Integer.parseInt(greeting.split("\\|")[1]);
                        open(MainUIForm.Files.get(Id).getAbsolutePath());
                        out.println("hello client " + count);
                    } else if (greeting.contains("move_mouse")) {
                        form.setLastCommand("Move Mouse");
                        form.appendLog("Move Mouse");
                        String[] params = greeting.split("\\|");
                        float X = Float.parseFloat(params[1]);
                        float Y = Float.parseFloat(params[2]);
                        r.mouseMove((int) X, (int) Y);
                        out.println("hello client " + count);
                    } else
                        switch (greeting) {
                            case "prev_click":
                                form.setLastCommand("Previous");
                                form.appendLog("Previous");
                                r.keyPress(KeyEvent.VK_RIGHT);
                                r.keyRelease(KeyEvent.VK_RIGHT);

                                out.println("hello client " + count);
                                break;
                            case "nxt_click":
                                form.setLastCommand("Next");
                                form.appendLog("Next");
                                r.keyPress(KeyEvent.VK_LEFT);
                                r.keyRelease(KeyEvent.VK_LEFT);
                                out.println("hello client " + count);
                                break;
                            case "ppt_list":
                                form.setLastCommand("Powerpoint List");
                                form.appendLog("Powerpoint List");
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
            stop();
        } catch (SocketException socketException) {
            count = 0;
        }
    }

    public void setForm(MainUIForm f) {
        form = f;
    }

    public void stop() throws IOException {
        if (clientSocket != null)
            clientSocket.close();
        if (serverSocket != null)
            serverSocket.close();

        System.out.println("Server Stopped");
    }

    private static void open(String targetFilePath) throws IOException {
        Desktop desktop = Desktop.getDesktop();

        desktop.open(new File(targetFilePath));
    }

    public void run() {
        try {
            start(6666);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}

public class MainUIClass {

    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        SwingUtilities.invokeLater(() -> {

            MainUIForm mainUIForm = new MainUIForm();
            mainUIForm.setVisible(true);
        });
    }

}

