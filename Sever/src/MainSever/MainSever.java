package MainSever;

import common.Message;
import common.MessageType;
import common.User;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
@SuppressWarnings("all")
public class MainSever {
    public static void main(String[] args) throws IOException {
        String configFile = "config.properties";
        File config = new File(configFile);
        if (!config.exists()) {
            try {
                config.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(config));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            String log = "";
            Socket socket = serverSocket.accept();
            System.out.println("客户端开始链结，开始验证");
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(socket.getInputStream());
            try {
                User user = (User) objectInputStream.readObject();
                System.out.println("访问用户为:"+user.getUsername());
                Object password = properties.get(user.getUsername());

                boolean status = false;
                if (password == null) {
                    log = "用户不存在";
                    System.out.println(log);
                } else {
                    System.out.println("该用户正确密码为:"+password);
                    System.out.println("该用户发送密码为:"+user.getPassword());
                    if (password.toString().equals(user.getPassword())) {
                        ConnectThread threadByName =
                                ManageConnectThread.getThreadByName(user.getUsername());
                        if(threadByName==null){
                            Message message = new Message();
                            message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCESS);
                            ObjectOutputStream objectOutputStream =
                                    new ObjectOutputStream(socket.getOutputStream());
                            objectOutputStream.writeObject(message);
                            status = true;
                            System.out.println("验证成功");
                            ConnectThread connectThread =
                                    new ConnectThread(socket, user.getUsername());
                            ManageConnectThread.addConnectThread(connectThread,user.getUsername());
                            connectThread.start();
                        }else{
                            log="该用户已登录";
                        }
                    } else {
                        log = "密码错误";
                    }
                }
                if(!status){
                    Message message = new Message();
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAIL);
                    message.setData(log);
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);

                    objectOutputStream.close();
                    socket.close();
                    System.out.println("验证失败,"+log);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
