package MainSever;

import FrameManage.ChatPanel;
import FrameManage.MainFrame;
import MyJFrame.MyButton;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Friend {
    int messageIndex;
    String friendsName;
    MainFrame frame;
    MyButton button;
    ChatPanel panel;
    String[] messageHistory;

    public Friend() {
    }

    public Friend(MainFrame frame) {
        messageHistory=new String[1000];
        messageIndex=0;
        this.frame = frame;
    }
    public void setButton(){
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(true);
                frame.getContentPane().setComponentZOrder(panel,0);
                FriendsManage.setButtonFocus(friendsName);
                frame.getContentPane().repaint();
            }
        });
    }
    public void updateMessage(){
        panel.setMessageHistory(messageHistory);
        panel.setMessageIndex(getMessageIndex());
        panel.updateMessage();
    }

    public void addMessage(String data){
        messageHistory[messageIndex++]=data;
        updateMessage();
    }

    public int getMessageIndex() {
        return messageIndex;
    }

    public void setMessageIndex(int messageIndex) {
        this.messageIndex = messageIndex;
    }

    public MainFrame getFrame() {
        return frame;
    }

    public void setFrame(MainFrame frame) {
        this.frame = frame;
    }

    public MyButton getButton() {
        return button;
    }

    public void setButton(MyButton button) {
        this.button = button;
    }

    public ChatPanel getPanel() {
        return panel;
    }

    public void setPanel(ChatPanel panel) {
        this.panel = panel;
    }

    public String[] getMessageHistory() {
        return messageHistory;
    }

    public void setMessageHistory(String[] messageHistory) {
        this.messageHistory = messageHistory;
    }

    public String getFriendsName() {
        return friendsName;
    }

    public void setFriendsName(String friendsName) {
        this.friendsName = friendsName;
    }
}
