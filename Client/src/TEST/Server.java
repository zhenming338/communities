package TEST;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            // 创建服务器套接字，监听8888端口
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Server started. Waiting for clients...");

            // 等待客户端连接
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket);

            // 获取输入流和输出流
            BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

            // 从客户端接收消息并发送响应
            String message = input.readLine();
            System.out.println("Received message from client: " + message);
            output.println("Server received: " + message);

            // 关闭连接
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
