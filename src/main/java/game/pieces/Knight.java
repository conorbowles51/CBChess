package game.pieces;

import game.board.Board;
import game.board.BoardUtils;
import game.move.*;
import ui.PieceIconManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private final static int[] CANDIDATE_MOVE_OFFSETS =  { -17, -15, -10, -6, 6, 10, 15, 17 };

    public Knight(int location, PieceColor color, Board board) {
        super(location, color, board);
    }

    @Override
    public void calculateCandidateMoves() {
        super.calculateCandidateMoves();

        for(final int offset : CANDIDATE_MOVE_OFFSETS){
            final int destination = this.location + offset;

            if(!BoardUtils.isValidIndex(destination)){
                continue;
            }

            if(isFirstColumnExclusion(this.location, offset) ||
                    isSecondColumnExclusion(this.location, offset) ||
                    isSeventhColumnExclusion(this.location, offset) ||
                    isEighthColumnExclusion(this.location, offset)){
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
    public Knight cloneToNewBoard(Board board){
        return new Knight(location, color, board);
    }

    @Override
    public ImageIcon getIcon() {
        return color == PieceColor.WHITE ? PieceIconManager.whiteKnight : PieceIconManager.blackKnight;
    }

    private static boolean isFirstColumnExclusion(final int location, final int offset){
        return BoardUtils.A_FILE[location] &&
                (offset == -17 || offset == -10 || offset == 6 || offset == 15);
    }

    private static boolean isSecondColumnExclusion(final int location, final int offset){
        return BoardUtils.B_FILE[location] &&
                (offset == -10 || offset == 6);
    }

    private static boolean isSeventhColumnExclusion(final int location, final int offset){
        return BoardUtils.G_FILE[location] &&
                (offset == -6 || offset == 10);
    }

    private static boolean isEighthColumnExclusion(final int location, final int offset){
        return BoardUtils.H_FILE[location] &&
                (offset == 17 || offset == 10 || offset == -6 || offset == -15);
    }

    @Override
    public int value(){
        return 3;
    }

    @Override
    public String toString() {
        return this.color == PieceColor.WHITE ? "N" : "n";
    }
}
