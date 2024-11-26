package FrameManage;

import common.Message;
import common.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class MainFrame extends JFrame {
    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException
                 | IllegalAccessException
                 | InstantiationException |
                 UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
    }

    static String username;
    static Socket socket;
    int friends_x = 0;
    int friends_y = 0;
    JPanel friends;
    JPanel tittlePanel;

    public JPanel getFriends() {
        return friends;
    }

    public void setFriends(JPanel friends) {
        this.friends = friends;
    }

    public JPanel getTittlePanel() {
        return tittlePanel;
    }

    public void setTittlePanel(JPanel tittlePanel) {
        this.tittlePanel = tittlePanel;
    }

    public int getFriends_x() {
        return friends_x;
    }

    public void setFriends_x(int friends_x) {
        this.friends_x = friends_x;
    }

    public int getFriends_y() {
        return friends_y;
    }

    public void setFriends_y(int friends_y) {
        this.friends_y = friends_y;
    }

    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(Socket socket) {
        MainFrame.socket = socket;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MainFrame.username = username;
    }

    public MainFrame() {
        String userMessage = "用户:" + username + "   登陆时间:" + LocalDateTime.now();

        setSize(730, 630);
        setLocationRelativeTo(null);
        Container container = getContentPane();
        container.setBackground(Color.white);
        container.setLayout(null);


        //设置标题信息
        tittlePanel = new JPanel();
        tittlePanel.setLayout(null);
        tittlePanel.setBounds(0, 0, 720, 30);
        Font tittleFont = new Font("楷书", Font.PLAIN, 10);
        JLabel tittle = new JLabel("    " + userMessage);
        tittle.setFont(tittleFont);
        tittle.setBounds(0, 0, 720, 30);
        tittlePanel.add(tittle);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choose = JOptionPane.showConfirmDialog(MainFrame.this,
                        "是否要退出", "提示", JOptionPane.YES_NO_OPTION);
                if(choose==JOptionPane.YES_NO_OPTION){
                    Message message = new Message();
                    message.setMessageType(MessageType.MESSAGE_EXIT);
                    try {
                        ObjectOutputStream objectOutputStream =
                                new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(message);
                        Thread.sleep(300);
                        System.exit(0);
                    } catch (IOException
                             | InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        container.add(tittlePanel);


        //初始化按钮列表

        friends = new JPanel();
        friends.setLayout(null);
        friends.setBounds(0,30,150,570);
        friends.setBackground(Color.cyan);

        container.add(friends);
        setVisible(true);
        repaint();
    }
}