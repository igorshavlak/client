package org.example;

import java.io.*;
import java.net.Socket;

public class Client {
    private String serverIp;
    private int serverPort;

    public Client(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            Socket socket = new Socket(serverIp, serverPort);
            System.out.println("Підключено до сервера.");


            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));


            Thread readerThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = reader.readLine();
                        if (message != null) {
                            System.out.println("Отримано повідомлення від сервера: " + message);
                        }

                    }
                } catch (IOException e) {git 
                    e.printStackTrace();
                }
            });
            readerThread.start();

            while (true) {
                System.out.print("Введіть повідомлення: ");
                String message = consoleReader.readLine();
                writer.write(message);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
