package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class TitleBar extends JPanel {
    private final Color BG_COLOR = new Color(50, 50, 50);
    private final Color HOVER_COLOR = new Color(75, 75, 75);

    public TitleBar(int width, int height){
        setBackground(BG_COLOR);
        setPreferredSize(new Dimension(width, height));
        setLayout(new BorderLayout(0, 0));

        JMenu options = new JMenu("Options");

        add(createCloseButton(), BorderLayout.EAST);
    }

    private JButton createCloseButton() {
        JButton closeButton = new JButton("✖");
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false); // Remove default background
        closeButton.setOpaque(true); // Allow custom color
        closeButton.setBackground(BG_COLOR); // Red
        closeButton.setForeground(Color.WHITE);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add hover effect
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(HOVER_COLOR); // Darker red on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(BG_COLOR); // Normal color
            }
        });

        // Close action
        closeButton.addActionListener(e -> System.exit(0));

        return closeButton;
    }
}
