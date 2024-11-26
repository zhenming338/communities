package TEST;

import javax.swing.*;

public class testJScrollPane {
    public static void main(String[] args) {
        JFrame frame = new JFrame("TextArea with Scrollbar Example");
        frame.setSize(300, 200);

        JTextArea textArea = new JTextArea(5, 20); // 5行，20列
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 20, 260, 120); // 设置滚动面板的位置和大小

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(scrollPane);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
