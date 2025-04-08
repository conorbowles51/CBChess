package Player;

import engine.Minimax;
import game.Game;
import game.board.Board;
import game.move.Move;
import game.pieces.Piece;
import game.pieces.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private PieceColor color;
    private PlayerType type;

    public Player(PieceColor color, PlayerType type) {
        this.color = color;
        this.type = type;
    }

    public void requestMove(Board board){
        if (type != PlayerType.AI){
            return;
        }

        // TODO: GET AI MOVES!!!!!!
        Move m = Minimax.best(3, board, color);
        Game.executeMove(m);
    }

    public PieceColor getColor() {
        return color;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }

    public PlayerType getType() {
        return type;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }
}
