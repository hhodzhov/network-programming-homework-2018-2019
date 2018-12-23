package com.fmi.mpr.hw.chat.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ImageReader extends Reader implements Runnable, IReadable {
    private static int fileCounter = 1;

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
            File incomingFile = new File("file" + fileCounter++  + ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(incomingFile);

            byte[] buffer = new byte[1024];
            int lastBytesReceived = 0;
            do {

                DatagramPacket request = new DatagramPacket(buffer, 1024);
                socket.receive(request);
                lastBytesReceived = request.getLength();
                fileOutputStream.write(request.getData(), 0, lastBytesReceived);
                System.out.println("received: " + lastBytesReceived);
            } while (lastBytesReceived == 1024);

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
