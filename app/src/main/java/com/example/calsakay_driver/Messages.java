package com.example.calsakay_driver;

import java.io.Serializable;
import java.util.Date;

public class Messages implements Serializable {

    private int senderId;
    private int recieverId;
    private Date timestamp;
    private String message;
    private int id;
    private String threadName;
    private String messageType;
    private boolean isRead;
    private int chatMateId;
    private int passengerId;

    public Messages(int senderId, int recieverId, Date timestamp, String message, int id, String threadName, String messageType, boolean isRead, int chatMateId, int passengerId) {
        this.senderId = senderId;
        this.recieverId = recieverId;
        this.timestamp = timestamp;
        this.message = message;
        this.id = id;
        this.threadName = threadName;
        this.messageType = messageType;
        this.isRead = isRead;
        this.chatMateId = chatMateId;
        this.passengerId = passengerId;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "senderId=" + senderId +
                ", recieverId=" + recieverId +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", id=" + id +
                ", threadName='" + threadName + '\'' +
                ", messageType='" + messageType + '\'' +
                ", isRead=" + isRead +
                ", chatMateId=" + chatMateId +
                ", passengerId=" + passengerId +
                '}';
    }

    public int getSenderId() {
        return senderId;
    }

    public int getRecieverId() {
        return recieverId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getId() {
        return id;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getMessageType() {
        return messageType;
    }

    public boolean isRead() {
        return isRead;
    }

    public int getChatMateId() {
        return chatMateId;
    }

    public int getPassengerId() {
        return passengerId;
    }

}
