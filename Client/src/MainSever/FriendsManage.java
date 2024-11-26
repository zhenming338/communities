package MainSever;

import FrameManage.ChatPanel;
import FrameManage.MainFrame;
import MyJFrame.MyButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("all")
public class FriendsManage {
    static ArrayList<String> online;
    static HashMap<String, Friend> frends;

    static {
        online = new ArrayList<>();
        frends = new HashMap<>();
    }

    public static void addOnline(String name, Friend friend) {
        frends.put(name, friend);
        online.add(name);
    }

    public static Friend getFriendByname(String name) {
        return frends.get(name);
    }

    public static void updateFrends(MainFrame frame,
                                    ArrayList<String> newOnline) {
        JPanel friends = frame.getFriends();
        int x = 0;
        int y = 0;
        for (String name : online) {
            if (!(newOnline.contains(name))) {
                frame.setFriends_y(y - 50);
                System.out.println("删除了用户" + name);
                friends.remove(getFriendByname(name).getButton());
                friends.repaint();
                online.remove(name);
                frends.remove(name);
            }
        }
        for (String name : newOnline) {
            if (!(online.contains(name))) {
                Friend friend = new Friend(frame);
                x = frame.getFriends_x();
                y = frame.getFriends_y();
                Font buttonFont = new Font("微软雅黑", Font.PLAIN, 20);
                MyButton button =
                        new MyButton(name, Color.lightGray, Color.DARK_GRAY, 5);
                friend.setFriendsName(name);
                button.setFont(buttonFont);
                if (name.equals(MainFrame.getUsername())) {
                    button.setText("我");
                }
                button.setBounds(x, y, 150, 50);
                frame.setFriends_y(y + 50);
                friend.setButton(button);
                ChatPanel chatPanel = new ChatPanel(name, MainFrame.getSocket(), frame);
                addOnline(name, friend);
                friend.button = button;
                friend.panel = chatPanel;
                friends.add(button);
                friend.setButton();
                frame.getContentPane().add(chatPanel);
                frame.getContentPane().repaint();
                System.out.println("加入了用户" + name);
                System.out.println(frame.getFriends_y());
            }
        }
    }

    public static void setButtonFocus(String name) {
        Set<Map.Entry<String, Friend>> entries = frends.entrySet();
        for (Map.Entry<String, Friend> entry : entries) {
            MyButton button = entry.getValue().button;
            button.setDefaultBackgroundColor(Color.lightGray);
            button.repaint();
        }
        Friend friend = frends.get(name);
        friend.button.setDefaultBackgroundColor(Color.WHITE);
        friend.button.repaint();
    }

    public static ArrayList<String> getOnline() {
        return online;
    }

    public static void setOnline(ArrayList<String> online) {
        online = online;
    }
}
