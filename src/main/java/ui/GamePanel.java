package ui;

import game.board.BoardUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private final Color LIGHT_COLOR = new Color(240, 217, 181);
    private final Color DARK_COLOR = new Color(181, 136, 99);
    private final Color HIGHLIGHT_COLOR = new Color(194, 240, 181);
    private final int SIZE;
    private final Tile[] tiles;

    public GamePanel(int size) {
        SIZE = size;
        tiles = new Tile[BoardUtils.SIZE * BoardUtils.SIZE];
        setPreferredSize(new Dimension(size, size));
        setLayout(new GridLayout(BoardUtils.SIZE, BoardUtils.SIZE, 0, 0));
        initTiles();
    }

    public Tile getTile(int tileId){
        return tiles[tileId];
    }

    private void initTiles(){
        for(int i = 0; i < BoardUtils.SIZE * BoardUtils.SIZE; i++){
            Color tileColor = isLightTile(i) ? LIGHT_COLOR : DARK_COLOR;

            Tile tile = new Tile(i, SIZE / BoardUtils.SIZE, tileColor);

            tiles[i] = tile;
            add(tile);
        }
    }

    public void highlight(List<Integer> tileIds){
        for(Tile tile : tiles){
            if(!tileIds.contains(tile.ID)){
                tile.setColor(tile.COLOR);
                continue;
            }

            Color col = HIGHLIGHT_COLOR;
            if(!isLightTile(tile.ID)){
                col = col.darker();
            }

            tile.setColor(col);
        }
    }

    private boolean isLightTile(int tileId){
        int rowNumber = tileId / 8;
        int colNumber = tileId % 8;

        return (rowNumber + colNumber) % 2 == 0;
    }
}
