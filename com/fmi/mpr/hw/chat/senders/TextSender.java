package com.fmi.mpr.hw.chat.senders;

import com.fmi.mpr.hw.chat.readers.TextReader;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.util.Scanner;

public class TextSender extends Sender implements ISendable {
    public TextSender(String host, int port) {
        super(host, port);
    }

    @Override
    public void send() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        name = sc.nextLine();


        Thread threadReader = new Thread(new
                TextReader(socket, group, port));

        threadReader.start();


        Scanner input = new Scanner(System.in);
        System.out.println("You are connected and you can start typing messages now...\n");
        try {
            while (true) {
                String messageToSend;
                messageToSend = input.nextLine();
                if (messageToSend.equalsIgnoreCase("Exit")) {
                    finished = true;
                    socket.leaveGroup(group);
                    socket.close();
                    break;
                }
                messageToSend = name + ": " + messageToSend;
                byte[] buffer = messageToSend.getBytes();
                DatagramPacket datagram = new
                        DatagramPacket(buffer, buffer.length, group, port);

                socket.send(datagram);
            }
        } catch (SocketException socketException) {
            System.out.println("Error occured while creating socket");
        } catch (IOException ie) {
            System.out.println("Error occured while reading from socket");
        }
    }
}
