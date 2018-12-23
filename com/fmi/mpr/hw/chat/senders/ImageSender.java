package com.fmi.mpr.hw.chat.senders;

import com.fmi.mpr.hw.chat.readers.ImageReader;
import com.fmi.mpr.hw.chat.readers.TextReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ImageSender extends Sender implements ISendable {
    public ImageSender(String host, int port) {
        super(host, port);
    }

    @Override
    public void send() {

        Thread threadReader = new Thread(new
                ImageReader(socket, group, port));

        threadReader.start();


        try {
            File fileToSend = new File("C:\\Users\\Halmi\\Documents\\NetworkProgrammingHomework\\network-programming-homework-2018-2019\\com\\fmi\\mpr\\hw\\chat\\senders\\images\\apple1.jpg");
            FileInputStream in = new FileInputStream(fileToSend);
            byte[] buffer = new byte[1024];
            int bytesRead = 0;

            InetAddress to = InetAddress.getByName("localhost");
            int port = 8888;

            while ((bytesRead = in.read(buffer, 0, 1024)) > 0) {
                System.out.println("bytes send " + bytesRead);
                DatagramPacket packet = new DatagramPacket(new byte[bytesRead], bytesRead);
                packet.setAddress(to);
                packet.setPort(port);
                packet.setData(buffer, 0, bytesRead);

                socket.send(packet);
            }
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("File not found");
        } catch (UnknownHostException unknownHost) {
            System.out.println("Host not found");
        } catch (IOException ioException) {
            System.out.println("IOException");
        }
    }
}
