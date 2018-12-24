package com.fmi.mpr.hw.chat;

import com.fmi.mpr.hw.chat.senders.ImageSender;
import com.fmi.mpr.hw.chat.senders.Sender;
import com.fmi.mpr.hw.chat.senders.TextSender;
import com.fmi.mpr.hw.chat.senders.VideoSender;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ChatMain {

	public static final String MULTICAST_HOST = "230.0.0.1";
	public static final int PORT_NUMBER = 8888;

	public static void main(String[] args) throws IOException {
		System.out.println("Test");


//        LocalDateTime date  = LocalDateTime.now();
//        System.out.println(date.getMinute() + ":" + date.getSecond());

		Map<String, Sender> sendFactory = new HashMap<>();
		sendFactory.put("text", new TextSender(MULTICAST_HOST, PORT_NUMBER));
		sendFactory.put("image", new ImageSender(MULTICAST_HOST, PORT_NUMBER));
		sendFactory.put("video", new VideoSender(MULTICAST_HOST, PORT_NUMBER));
		Interpreter interpreter = new Interpreter(sendFactory);
		interpreter.start();
	}
}