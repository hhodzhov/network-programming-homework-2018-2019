package com.fmi.mpr.hw.chat.senders;

import com.fmi.mpr.hw.chat.readers.ImageReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ImageSender extends Sender implements ISendable {

    private static final int MAX_READ_SIZE = 1024;
    public static String fullPath;

    public ImageSender(String host, int port) {
        super(host, port);
    }

    @Override
    public void send() {
        Thread threadReader = new Thread(new
                ImageReader(socket, group, port));

        threadReader.start();

        Scanner input = new Scanner(System.in);
        try {
            while(true) {
                System.out.println("Enter full path of the file you want to send!");
                String fullPath = input.nextLine();

                if(fullPath.equals("exit")){
                    finished = true;
                    socket.leaveGroup(group);
                    socket.close();
                    break;
                }

                InetAddress to = InetAddress.getByName(host);

                File fileToSend = new File(fullPath);
                FileInputStream in = new FileInputStream(fileToSend);

                byte[] buffer = new byte[1024];
                int bytesRead = 0;

                while ((bytesRead = in.read(buffer, 0, 1024)) > 0) {
                    System.out.println("bytes send " + bytesRead);
                    DatagramPacket packet = new DatagramPacket(new byte[bytesRead], bytesRead);
                    packet.setAddress(to);
                    packet.setPort(port);
                    packet.setData(buffer, 0, bytesRead);

                    socket.send(packet);
                }
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
