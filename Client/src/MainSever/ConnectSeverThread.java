package MainSever;

import FrameManage.MainFrame;
import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ConnectSeverThread extends Thread {
    Socket socket ;
    String username;
    ArrayList<String> online;
    Message message;

    MainFrame frame;
    static boolean loop = true;

    public ConnectSeverThread(Socket socket, String username, MainFrame frame) {
        this.socket = socket;
        this.username = username;
        this.frame = frame;
    }

    public ConnectSeverThread(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
        online = new ArrayList<>();
        message = new Message();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getOnline() {
        return online;
    }

    public void setOnline(ArrayList<String> online) {
        this.online = online;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void run() {
        while (loop) {
            try {
                ObjectInputStream objectInputStream =
                        new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                if (message.getMessageType() == MessageType.MESSAGE_RET_ONLINE) {
                    online = message.getOnline();
                    System.out.println("===================");
                    for (String name : online) {
                        System.out.println(name);
                    }
                    System.out.println("===================");
                    FriendsManage.updateFrends(frame, online);
                } else if (message.getMessageType() == MessageType.MESSAGE_RECEIVER_DATA) {
                    String sender = message.getSender();
                    String data = message.getData();
                    String message_data = sender + ":   " + data;
                    System.out.println(message_data);
                    Friend friend = FriendsManage.getFriendByname(sender);
                    friend.addMessage(message_data);
                    friend.updateMessage();
                }else if(message.getMessageType() == MessageType.MESSAGE_EXIT){
                    objectInputStream.close();
                    socket.close();
                    break;
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
