package com.fmi.mpr.hw.chat;

import com.fmi.mpr.hw.chat.senders.ImageSender;
import com.fmi.mpr.hw.chat.senders.Sender;
import com.fmi.mpr.hw.chat.senders.TextSender;
import com.fmi.mpr.hw.chat.senders.VideoSender;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Interpreter {

    private Map<String, Sender> sendFactory;

    public Interpreter(Map<String, Sender> sendFactory) {
        this.sendFactory = sendFactory;
    }

    private void interpretCommand(String typeOfSendingCommand) throws IOException {
        Sender sender = sendFactory.get(typeOfSendingCommand);
//        if(sender instanceof TextSender){
//            sender.initTextGroup();
//        }
//        else if(sender instanceof ImageSender){
//            sender.initImageGroup();
//        }
//        else if(sender instanceof VideoSender){
//            sender.initVideoGroup();
//        }
        sender.init();
        sender.send();
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter type of sending data: text, image, video");
        String typeOfSendingData = scanner.nextLine();
        interpretCommand(typeOfSendingData);
    }
}
