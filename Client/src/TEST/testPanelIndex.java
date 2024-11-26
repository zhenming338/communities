package TEST;

import javax.swing.*;

public class testPanelIndex {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Set Top Panel");
        frame.setSize(300, 200);

        JPanel panel1 = new JPanel();
        panel1.setBounds(50, 50, 200, 100);
        panel1.setBackground(java.awt.Color.RED);

        JPanel panel2 = new JPanel();
        panel2.setBounds(100, 100, 200, 100);
        panel2.setBackground(java.awt.Color.BLUE);

        frame.add(panel1);
        frame.add(panel2);

        // 设置 panel2 在最顶层显示
        frame.getContentPane().setComponentZOrder(panel2, 0);
        frame.getContentPane().setComponentZOrder(panel1,0);

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
