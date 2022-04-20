package com.example.calsakay_driver;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConversationModel {

    PrettyTime p = new PrettyTime();
    private String messageContent, messageTimestamp, messageType;

    @Override
    public String toString() {
        return "ConversationModel{" +
                "messageContent='" + messageContent + '\'' +
                ", messageTimestamp='" + messageTimestamp + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }

    public String getMessageContent() {
        return messageContent;
    }

    public String getMessageTimestamp() {
        return messageTimestamp;
    }


    public String getMessageType() {
        return messageType;
    }

    public ConversationModel(String messageContent, String messageTimestamp, String messageType) {
        this.messageContent = messageContent;
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(messageTimestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.messageTimestamp = p.format(date);
        this.messageType = messageType;
    }
}
