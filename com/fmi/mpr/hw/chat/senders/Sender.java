package com.fmi.mpr.hw.chat.senders;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public abstract class Sender implements ISendable {

    public static volatile boolean finished = false;
    protected String host;
    protected int port;
    public static String name;
    protected MulticastSocket socket;
    protected InetAddress group;

    public Sender(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void init() throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        name = sc.nextLine();
        socket = new MulticastSocket(port);
        group = InetAddress.getByName(host);
        socket.joinGroup(group);
    }
}
