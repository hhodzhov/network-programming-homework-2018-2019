package com.fmi.mpr.hw.chat.readers;

import java.net.InetAddress;
import java.net.MulticastSocket;

public abstract class Reader {
    protected MulticastSocket socket;
    protected InetAddress group;
    protected int port;
    protected static final int MAX_LEN = 1000;

    public Reader(MulticastSocket socket, InetAddress group, int port)
    {
        this.socket = socket;
        this.group = group;
        this.port = port;
    }
}
