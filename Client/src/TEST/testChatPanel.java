package TEST;

import java.awt.Container;
import java.net.Socket;

import FrameManage.ChatPanel;
import FrameManage.MainFrame;

public class testChatPanel {
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setLayout(null);
        frame.setVisible(true);
        Container contentPane = frame.getContentPane();
        ChatPanel chatPanel = new ChatPanel((String) null, (Socket) null, frame);
        frame.getContentPane().add(chatPanel);
        frame.getContentPane().repaint();
    }
}
