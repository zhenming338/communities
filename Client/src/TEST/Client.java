package TEST;

import java.io.*;
import java.net.*;
public class Client {
    public static void main(String[] args) {
        try {
            // 连接到服务器的8888端口
            Socket socket = new Socket("1b56-221-176-129-167.ngrok-free.app", 8888);

            // 获取输入流和输出流
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // 向服务器发送消息
            output.println("Hello, Server!");

            // 从服务器接收响应并打印
            String response = input.readLine();
            System.out.println("Server response: " + response);

            // 关闭连接
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
