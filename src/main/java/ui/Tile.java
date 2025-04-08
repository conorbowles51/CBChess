package ui;

import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tile extends JPanel {
    public final int ID;
    public final Color COLOR;

    public Tile(int id, int size, Color color){
        ID = id;
        COLOR = color;

        setSize(new Dimension(size, size));
        setColor(COLOR);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Game.onTileClicked(ID); // -1 will reset selected tile in HumanPlayer if right-clicked
            }
        });

    }

    public void setIcon(ImageIcon icon){
        removeAll();

        if(icon != null) {
            add(new JLabel(icon));
        }

        revalidate();
        repaint();
    }

    public void setColor(Color color){
        setBackground(color);
    }
}
