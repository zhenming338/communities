package MainSever;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
@SuppressWarnings("all")
public class ConnectThread extends Thread {
    Socket socket;
    String username;

    public ConnectThread(Socket socket, String username) {
        this.socket = socket;
        this.username = username;
    }

    public void run() {
        boolean loop=true;
        while (loop) {
            try {
                Message remessage = new Message();
                ArrayList<String> online = new ArrayList<String>();
                ObjectInputStream objectInputStream =
                        new ObjectInputStream(socket.getInputStream());
                Message message = (Message) objectInputStream.readObject();
                if (message.getMessageType() == MessageType.MESSAGE_GET_ONLINE) {
                    System.out.println("接受到" + username + "的获取在线用户列表请求");
                    updateOnline(online,remessage);
//                    HashMap<String, ConnectThread> threadList =
//                            ManageConnectThread.getThreadList();
//                    for (Map.Entry<String, ConnectThread> entry01 : threadList.entrySet()) {
//                        online.add(entry01.getKey());
//                        System.out.println(entry01.getKey());
//                    }
//                    remessage.setOnline(online);
//                    for(String name:online){
//                        System.out.println("==============");
//                        System.out.println(name);
//                        System.out.println("===============");
//                    }
//                    remessage.setMessageType(MessageType.MESSAGE_RET_ONLINE);
//                    for (Map.Entry<String, ConnectThread> entry : threadList.entrySet()) {
//                        ConnectThread value = entry.getValue();
//                        Socket connect = value.getSocket();
//                        ObjectOutputStream objectOutputStream =
//                                new ObjectOutputStream(connect.getOutputStream());
//                        objectOutputStream.writeObject(remessage);
//                    }
//                    System.out.println("已返回在线用户列表");
                }else if(message.getMessageType() == MessageType.MESSAGE_SEND_DATA){
                    String receiver=message.getReceiver();
                    String sender=username;
                    ConnectThread receiverThread =
                            ManageConnectThread.getThreadByName(receiver);
                    Socket receiverSocket = receiverThread.getSocket();
                    message.setMessageType(MessageType.MESSAGE_RECEIVER_DATA);
                    message.setSender(sender);
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(receiverSocket.getOutputStream());
                    objectOutputStream.writeObject(message);
                    System.out.println("已向用户"+receiver+"发送用户"+sender+"发送的内容");
                }else if(message.getMessageType()==MessageType.MESSAGE_EXIT){
                    Message message1 = new Message();
                    message1.setMessageType(MessageType.MESSAGE_EXIT);
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);
                    loop=false;
                    ManageConnectThread.removeThreadByName(username);
                    updateOnline(online,remessage);
                    socket.close();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void updateOnline(ArrayList<String> online,
                             Message remessage) throws IOException {
        HashMap<String, ConnectThread> threadList =
                ManageConnectThread.getThreadList();
        for (Map.Entry<String, ConnectThread> entry01 : threadList.entrySet()) {
            online.add(entry01.getKey());
            System.out.println(entry01.getKey());
        }
        remessage.setOnline(online);
        for(String name:online){
            System.out.println("==============");
            System.out.println(name);
            System.out.println("===============");
        }
        remessage.setMessageType(MessageType.MESSAGE_RET_ONLINE);
        for (Map.Entry<String, ConnectThread> entry : threadList.entrySet()) {
            ConnectThread value = entry.getValue();
            Socket connect = value.getSocket();
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(connect.getOutputStream());
            objectOutputStream.writeObject(remessage);
        }
        System.out.println("已返回在线用户列表");
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
}
