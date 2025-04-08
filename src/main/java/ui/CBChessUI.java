package ui;

import game.board.Board;
import game.board.BoardUtils;
import game.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashMap;
import java.util.List;

public class CBChessUI {
    private final JFrame FRAME;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 840;
    public static final int TILE_SIZE = WIDTH / BoardUtils.SIZE;
    public static final int TITLE_BAR_HEIGHT = 40;

    private GamePanel gamePanel;

    private int mouseX, mouseY;

    public CBChessUI() {
        FRAME = new JFrame();

        initFrame();

        FRAME.pack();
    }

    public void show() {
        FRAME.setVisible(true);
    }

    private void initFrame() {
        FRAME.setTitle("CB Chess");
        FRAME.setSize(WIDTH, HEIGHT);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setLocationRelativeTo(null);                          // centers the frame
        FRAME.setUndecorated(true);                                 // Removes default styling
        FRAME.setResizable(false);
        FRAME.setLayout(new BorderLayout(0, 0));

        TitleBar titleBar = new TitleBar(WIDTH, TITLE_BAR_HEIGHT);

        // Make the window draggable
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int x = e.getXOnScreen() - mouseX;
                int y = e.getYOnScreen() - mouseY;
                FRAME.setLocation(x, y);
            }
        });

        FRAME.add(titleBar, BorderLayout.NORTH);

        gamePanel = new GamePanel(WIDTH);
        FRAME.add(gamePanel, BorderLayout.CENTER);
    }

    public void displayBoard(Board board){
        HashMap<Integer, Piece> pieces = board.getPieces();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++){
            Piece piece = pieces.get(i);
            Tile tile = gamePanel.getTile(i);

            ImageIcon icon = piece == null ? null : piece.getIcon();

            tile.setIcon(icon);
        }
    }

    public void highlight(List<Integer> tileIds){
        gamePanel.highlight(tileIds);
    }

    private void setTileIcon(int tileId, ImageIcon icon){
        gamePanel.getTile(tileId).setIcon(icon);
    }
}
