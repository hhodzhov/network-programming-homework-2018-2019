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

import static com.fmi.mpr.hw.chat.readers.Reader.MAX_READ_SIZE;

public class ImageSender extends Sender implements ISendable {

//    private static final int MAX_READ_SIZE = 1024;
    public static String fullPath;

    public ImageSender(String host, int port) {
        super(host, port);
    }

    @Override
    public void send() {

        System.out.println("You are about to send images");
        Thread imageReader = new Thread(new
                ImageReader(socket, group, port));
        imageReader.start();

        Scanner input = new Scanner(System.in);
        try {
            while(true) {
                System.out.println("Enter full path of the image you want to send!");
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

                byte[] buffer = new byte[MAX_READ_SIZE];
                int bytesRead = 0;

                while ((bytesRead = in.read(buffer, 0, MAX_READ_SIZE)) > 0) {
                    System.out.println("bytes send " + bytesRead);
                    DatagramPacket informationToSend = new DatagramPacket(new byte[bytesRead], bytesRead);
                    informationToSend.setAddress(to);
                    informationToSend.setPort(port);
                    informationToSend.setData(buffer, 0, bytesRead);

                    socket.send(informationToSend);
                }
            }
        } catch (FileNotFoundException fileNotFound) {
            System.out.println("Image not found");
        } catch (UnknownHostException unknownHost) {
            System.out.println("Host not found");
        } catch (IOException ioException) {
            System.out.println("IOException in ImageSender ");
        }
    }
}
