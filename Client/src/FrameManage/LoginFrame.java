package FrameManage;

import MainSever.ConnectSeverThread;
import common.Message;
import common.MessageType;
import common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class LoginFrame extends JFrame {
    static Socket socket;
    static String username = "";

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

    String severHost;
    int severPort;

    String password = "";


    public LoginFrame() {

        try {
            severHost = InetAddress.getLocalHost().getHostAddress();
            severPort = 8888;
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        System.out.println(severHost);
        setSize(430, 300);
        Container container = getContentPane();
        container.setBackground(Color.white);
        container.setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        JButton button1 = new JButton("set");
        button1.setBounds(350, 245, 70, 20);
        container.add(button1);
        button1.setContentAreaFilled(false);
        button1.setFocusPainted(false);
        button1.setBorderPainted(false);
        Font setFont = new Font("Arial", Font.PLAIN, 15);
        button1.setFont(setFont);
        button1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dialog setSeverDialog = new Dialog(LoginFrame.this);
                setSeverDialog.setSize(330, 170);
                setSeverDialog.setLocationRelativeTo(null);
                setSeverDialog.setBackground(Color.white);
                setSeverDialog.setLayout(null);

                JTextField severHost01 = new JTextField(severHost);
                severHost01.setBounds(150, 50, 150, 20);
                JTextField severPort01 = new JTextField(severPort + "");
                severPort01.setBounds(150, 80, 150, 20);
                JLabel host01 = new JLabel("服务器地址:");
                host01.setBounds(50, 50, 180, 20);
                JLabel port01 = new JLabel("端口号:");
                port01.setBounds(50, 80, 180, 20);

                JButton confirm = new JButton("确定");
                JButton cancel = new JButton("取消");
                confirm.setBounds(80, 120, 75, 25);
                cancel.setBounds(200, 120, 75, 25);

                confirm.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        severHost = severHost01.getText();
                        severPort = Integer.parseInt(severPort01.getText());
                        setSeverDialog.dispose();
                    }
                });
                cancel.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setSeverDialog.dispose();
                    }
                });

                setSeverDialog.add(severHost01);
                setSeverDialog.add(severPort01);
                setSeverDialog.add(host01);
                setSeverDialog.add(port01);
                setSeverDialog.add(confirm);
                setSeverDialog.add(cancel);
                setSeverDialog.setResizable(false);
                setSeverDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        setSeverDialog.dispose();
                    }
                });
                setSeverDialog.setVisible(true);
            }
        });

        Font titleFont = new Font("楷书", Font.BOLD, 25);
        JTextField[] textFields = new JTextField[2];
        JLabel jLabel1 = new JLabel("welcome");
        jLabel1.setFont(titleFont);
        jLabel1.setBounds(160, 30, 200, 40);
        container.add(jLabel1);
        String[] data = {"用户名:", "密码:"};
        for (int i = 0; i < 2; i++) {
            JLabel jLabel = new JLabel(data[i]);
            textFields[i] = new JTextField();
            jLabel.setBounds(70, 80 + (50 * i), 50, 30);
            textFields[i].setBounds(120, 80 + (50 * i), 200, 25);
            container.add(textFields[i]);
            container.add(jLabel);
        }
        JButton button = new JButton("登录");
        button.setBounds(110, 190, 220, 35);
        container.add(button);

        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = textFields[0].getText();
                password = textFields[1].getText();
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "账号或密码不能为空");
                } else {
                    try {
                        socket = new Socket(severHost, severPort);
                        User user = new User(username, password);
                        ObjectOutputStream objectOutputStream =
                                new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(user);//发送用户对象进行验证

                        ObjectInputStream objectInputStream =
                                new ObjectInputStream(socket.getInputStream());

                        Message message = (Message) objectInputStream.readObject();
                        if (message.getMessageType() == MessageType.MESSAGE_LOGIN_SUCCESS) {
                            dispose();
                            MainFrame.setUsername(username);
                            MainFrame.setSocket(socket);
                            MainFrame mainFrame = new MainFrame();
                            ObjectOutputStream objectOutputStream1 =
                                    new ObjectOutputStream(socket.getOutputStream());
                            Message message1 = new Message();
                            message1.setMessageType(MessageType.MESSAGE_GET_ONLINE);
                            objectOutputStream1.writeObject(message1);
                            ConnectSeverThread connectSeverThread =
                                    new ConnectSeverThread(socket, username,mainFrame);
                            connectSeverThread.start();
                        } else if (message.getMessageType() == MessageType.MESSAGE_LOGIN_FAIL) {
                            String log=message.getData();
                            JOptionPane.showMessageDialog(LoginFrame.this
                                    , log);
                        } else {
                            JOptionPane.showMessageDialog(LoginFrame.this
                                    , "服务器错误");
                        }
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "服务器无响应");
                    } catch (ClassNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        getRootPane().setDefaultButton(button);
        setVisible(true);
    }
}
