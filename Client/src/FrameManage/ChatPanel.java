package FrameManage;

import MainSever.Friend;
import MainSever.FriendsManage;
import MyJFrame.MyButton;
import common.Message;
import common.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatPanel extends JPanel {
    MainFrame frame;
    int messageIndex;
    String friendname;
    String username;
    Socket socket;
    MyButton button;

    JPanel messagePanel;
    JPanel sendPanel;
    String[] messageHistory;
    JTextArea jTextArea;
    JScrollPane jScrollPane;
    JTextArea jTextArea1;
    JScrollPane jScrollPane1;
    MyButton sendButton;

    public ChatPanel() {

    }

    public String[] getMessageHistory() {
        return messageHistory;
    }

    public int getMessageIndex() {
        return messageIndex;
    }

    public MyButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(MyButton sendButton) {
        this.sendButton = sendButton;
    }

    public void setMessageIndex(int messageIndex) {
        this.messageIndex = messageIndex;
    }

    public void setMessageHistory(String[] messageHistory) {
        this.messageHistory = messageHistory;
    }

    public ChatPanel(String frendname, Socket socket, MainFrame frame) {
        this.friendname = frendname;
        this.socket = socket;
        messageHistory = new String[100];
        setBounds(150, 30, 570, 570);
        setVisible(false);
        setLayout(null);
        setBackground(Color.GRAY);


        messagePanel = new JPanel();
        messagePanel.setBackground(Color.lightGray);
        sendPanel = new JPanel();
        sendPanel.setBackground(Color.lightGray);


        messagePanel.setBounds(0, 0, 570, 370);
        sendPanel.setBounds(0, 370, 570, 200);
        messagePanel.setLayout(null);
        sendPanel.setLayout(null);


        JLabel jLabel = new JLabel("       当前用户 : " + friendname);
        jLabel.setBounds(0, 0, 570, 20);
        messagePanel.add(jLabel);

        jTextArea = new JTextArea(10, 10);
//        jTextArea.setBounds(0, 20, 570, 300);
        jScrollPane = new JScrollPane(jTextArea);
        jScrollPane.setBounds(0, 20, 570, 300);
        messagePanel.add(jScrollPane);
//        messagePanel.add(jTextArea);

        jTextArea1 = new JTextArea();
//        jTextArea1.setBounds(0, 0, 570, 180);
        jScrollPane1 = new JScrollPane(jTextArea1);
        jScrollPane1.setBounds(0, 0, 570, 150);
        sendPanel.add(jScrollPane1);
//        sendPanel.add(jTextArea1);


        sendButton = new MyButton("发送", Color.cyan, Color.lightGray, 9);
        sendButton.setBounds(500, 150, 50, 25);
        sendPanel.add(sendButton);

        sendButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String data = jTextArea1.getText();
                if (data.isEmpty()) {
                    JOptionPane.showConfirmDialog(frame,
                            "请勿发送空内容", "提示", JOptionPane.DEFAULT_OPTION);
                } else {
                    Message message = new Message();
                    message.setMessageType(MessageType.MESSAGE_SEND_DATA);
                    message.setData(data);
                    message.setReceiver(frendname);
                    try {
                        ObjectOutputStream objectOutputStream =
                                new ObjectOutputStream(MainFrame.getSocket().getOutputStream());
                        objectOutputStream.writeObject(message);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    String messageAdd = "我:  " + data;
                    messageAdd=messageAdd.replaceAll("[\\n\\r]+$","");
                    Friend friendByname = FriendsManage.getFriendByname(friendname);
                    friendByname.addMessage(messageAdd);
                    updateMessage();
                    jTextArea1.setText("");
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    sendButton.doClick();
                }
            }
        });


        add(messagePanel);
        add(sendPanel);
        System.out.println("已加载" + friendname + "聊天界面");
    }

    public void updateMessage() {
        StringBuilder data = new StringBuilder();
        for (int i = 0; i < messageIndex; i++) {
            messageHistory[i]=
                    messageHistory[i].replaceAll("[\\n\\r]+$","");
            data.append(messageHistory[i] + "\n");
        }
        jTextArea.setText(data.toString());
        messagePanel.repaint();
    }

    public String getFriendname() {
        return friendname;
    }

    public void setFriendname(String frendname) {
        this.friendname = frendname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public MyButton getButton() {
        return button;
    }

    public void setButton(MyButton button) {
        this.button = button;
    }
}
