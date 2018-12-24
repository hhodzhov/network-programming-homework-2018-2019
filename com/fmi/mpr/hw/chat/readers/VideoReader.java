package com.fmi.mpr.hw.chat.readers;

import com.fmi.mpr.hw.chat.senders.Sender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalDateTime;

public class VideoReader extends Reader implements Runnable, IReadable {
    public VideoReader(MulticastSocket socket, InetAddress group, int port) {
        super(socket, group, port);
    }

    @Override
    public void read() {
        run();
    }

    @Override
    public void run() {
        while (!Sender.finished) {
            try {

                LocalDateTime date = LocalDateTime.now();
                String file = String.valueOf(date.getMinute()) + "_" + String.valueOf(date.getSecond()) + ".mp4";
                System.out.println("Name of file : " + file);
                File incomingFile = new File(file);
                FileOutputStream fileOutputStream = new FileOutputStream(incomingFile);

                byte[] buffer = new byte[MAX_READ_SIZE];
                int lastBytesReceived = 0;
                do {

                    DatagramPacket request = new DatagramPacket(buffer, MAX_READ_SIZE);
                    socket.receive(request);
                    lastBytesReceived = request.getLength();

                    fileOutputStream.write(request.getData(), 0, lastBytesReceived);
                    System.out.println("bytes received: " + lastBytesReceived);
                } while (lastBytesReceived == MAX_READ_SIZE);

                fileOutputStream.flush();

            } catch (FileNotFoundException f) {
                System.out.println("Video not found");
            } catch (IOException io) {
                System.out.println("IOException in video reader");
            }
        }
    }


}
