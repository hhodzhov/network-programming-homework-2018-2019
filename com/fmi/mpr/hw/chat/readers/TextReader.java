package com.fmi.mpr.hw.chat.readers;

import com.fmi.mpr.hw.chat.senders.Sender;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class TextReader extends Reader implements Runnable, IReadable {

    public TextReader(MulticastSocket socket, InetAddress group, int port) {
        super(socket, group, port);
    }

    @Override
    public void run() {
        while(!Sender.finished)
        {
            byte[] buffer = new byte[Reader.MAX_LEN];
            DatagramPacket datagram = new
                    DatagramPacket(buffer,buffer.length,group,port);
            String message;
            try
            {
                socket.receive(datagram);
                message = new
                        String(buffer,0,datagram.getLength(),"UTF-8");
                if(!message.startsWith(Sender.name))
                    System.out.println(message);
            }
            catch(IOException e)
            {
                System.out.println("Socket is closed now");
            }
        }
    }

    @Override
    public void read() {
        run();
    }
}
