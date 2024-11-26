package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Message implements Serializable {
    String data;
    String sender;
    String receiver;
    int MessageType;

    ArrayList<String> online;

    public ArrayList<String> getOnline() {
        return online;
    }

    public void setOnline(ArrayList<String> online) {
        this.online = online;
    }

    public Message(String data, int messageType) {
        this.data = data;
        MessageType = messageType;
    }

    public Message() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(data, message.data) &&
                Objects.equals(sender, message.sender) &&
                Objects.equals(receiver, message.receiver) &&
                Objects.equals(MessageType, message.MessageType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, sender, receiver, MessageType);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int messageType) {
        MessageType = messageType;
    }
}
