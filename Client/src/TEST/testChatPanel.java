package TEST;

import FrameManage.ChatPanel;
import FrameManage.MainFrame;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class testChatPanel {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setLayout(null);
        frame.setVisible(true);
        Container contentPane = frame.getContentPane();
        ChatPanel chatPanel = new ChatPanel((String) null, (Socket) null,  frame);
        frame.getContentPane().add(chatPanel);
        frame.getContentPane().repaint();
    }
}
