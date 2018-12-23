package com.fmi.mpr.hw.chat.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class ImageReader extends Reader implements Runnable, IReadable {
    private static final int MAX_READ_SIZE = 1024;

    public ImageReader(MulticastSocket socket, InetAddress group, int port) {
        super(socket, group, port);
    }

    @Override
    public void read() {
        run();
    }

    @Override
    public void run() {
        try {
            System.out.println("Enter a name to incomming file");
            Scanner input = new Scanner(System.in);
            String newFileName = input.nextLine();
            File incomingFile = new File(newFileName + ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(incomingFile);

            byte[] buffer = new byte[MAX_READ_SIZE];
            int lastBytesReceived = 1;
            do {

                DatagramPacket request = new DatagramPacket(buffer, MAX_READ_SIZE);
                socket.receive(request);
                lastBytesReceived = request.getLength();
                fileOutputStream.write(request.getData(), 0, lastBytesReceived);
                System.out.println("received: " + lastBytesReceived);

            } while (lastBytesReceived == MAX_READ_SIZE);

            fileOutputStream.flush();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (FileNotFoundException f) {
            System.out.println("File not found");
        } catch (IOException io) {
            System.out.println("IOException");
        }
    }
}
