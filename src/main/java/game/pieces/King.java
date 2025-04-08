package game.pieces;

import game.board.*;
import game.move.*;
import ui.PieceIconManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    private final static int[] CANDIDATE_MOVE_DIRECTIONS = { -9, -8, -7, -1, 1, 7, 8, 9 };

    public King(int location, PieceColor color, Board board) {
        super(location, color, board);
    }

    @Override
    public void calculateCandidateMoves() {
        super.calculateCandidateMoves();

        if(!hasMoved){
            // King side castle
            candidateMoves.add(new Move(location, this.location + 2, MoveType.KING_SIDE_CASTLE));
            // Queen side castle
            candidateMoves.add(new Move(location, this.location - 2, MoveType.QUEEN_SIDE_CASTLE));
        }

        for(final int direction : CANDIDATE_MOVE_DIRECTIONS){
            int destination = this.location + direction;

            if(!BoardUtils.isValidIndex(destination)){
                continue;
            }

            if(isFirstColumnExclusion(this.location, direction) ||
                    isEighthColumnExclusion(this.location, direction)){
                continue;
            }

            if(!board.isTileOccupied(destination)){
                candidateMoves.add(new Move(location, destination, MoveType.PASSIVE));
            } else {
                final Piece pieceAtDestination = board.getPiece(destination);

                if(pieceAtDestination.getColor() != this.color){
                    candidateMoves.add(new Move(location, destination, MoveType.CAPTURE));
                }
            }
        }
    }

    @Override
    public King cloneToNewBoard(Board board){
        King newKing = new King(location, color, board);
        newKing.hasMoved = this.hasMoved;
        return newKing;
    }

    @Override
    public ImageIcon getIcon() {
        return color == PieceColor.WHITE ? PieceIconManager.whiteKing : PieceIconManager.blackKing;
    }

    private static boolean isFirstColumnExclusion(final int location, final int direction){
        return BoardUtils.A_FILE[location] &&
                (direction == -9 || direction == 7 || direction == -1);
    }

    private static boolean isEighthColumnExclusion(final int location, final int direction){
        return BoardUtils.H_FILE[location] &&
                (direction == 9 || direction == -7 || direction == 1);
    }

    @Override
    public int value(){
        return 0;
    }

    @Override
    public String toString() {
        return this.color == PieceColor.WHITE ? "K" : "k";
    }
}
