package MyJFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class MyButton extends JButton {
    private Color defaultBackgroundColor;
    private Color hoverBackgroundColor;
    private Color clickBackgroundColor;
    private int cornerRadius;

    public MyButton(String text, Color defaultBackgroundColor,
                    Color clickBackgroundColor,
                    int cornerRadius) {
        super(text);
        this.defaultBackgroundColor = defaultBackgroundColor;
        this.clickBackgroundColor = clickBackgroundColor;
        this.cornerRadius = cornerRadius;
        hoverBackgroundColor=defaultBackgroundColor.brighter();
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        addMouseListener(new MyButtonMouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // 根据按钮状态绘制不同背景
        if (getModel().isPressed()) {
            g2.setColor(clickBackgroundColor);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverBackgroundColor);
        } else {
            g2.setColor(defaultBackgroundColor);
        }

        g2.fill(new RoundRectangle2D.Double(0, 0, width,
                height, cornerRadius, cornerRadius));

        // 绘制按钮文本
        g2.setColor(getForeground());
        FontMetrics fm = g2.getFontMetrics();
        int textX = (width - fm.stringWidth(getText())) / 2;
        int textY = (height - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(getText(), textX, textY);

        g2.dispose();
    }

    private class MyButtonMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            repaint(); // 鼠标悬浮时重绘按钮以切换背景
        }

        @Override
        public void mouseExited(MouseEvent e) {
            repaint(); // 鼠标离开时重绘按钮以切换背景
        }
    }

    public Color getDefaultBackgroundColor() {
        return defaultBackgroundColor;
    }

    public void setDefaultBackgroundColor(Color defaultBackgroundColor) {
        this.defaultBackgroundColor = defaultBackgroundColor;
    }

    public Color getHoverBackgroundColor() {
        return hoverBackgroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public Color getClickBackgroundColor() {
        return clickBackgroundColor;
    }

    public void setClickBackgroundColor(Color clickBackgroundColor) {
        this.clickBackgroundColor = clickBackgroundColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }
}