package TEST;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClickSimulation {
    public static void main(String[] args) {
        // 创建 JFrame 实例
        JFrame frame = new JFrame("Button Click Simulation");
        frame.setSize(300, 200);

        // 创建 JButton 实例
        JButton button = new JButton("Click Me");

        // 添加按钮点击事件的监听器
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button Clicked!");
            }
        });

        // 将按钮添加到 JFrame 中
        frame.add(button);

        // 设置关闭操作
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 显示窗口
        frame.setVisible(true);

        // 在程序中模拟按钮点击
        button.doClick();
    }
}
