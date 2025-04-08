package game.pieces;

import game.board.Board;
import game.board.BoardUtils;
import game.move.Move;
import game.move.MoveType;
import ui.PieceIconManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    private final static int[] CANDIDATE_MOVE_DIRECTIONS = { -9, -7, 7, 9};

    public Bishop(int location, PieceColor color, Board board) {
        super(location, color, board);
    }

    @Override
    public void calculateCandidateMoves() {
        super.calculateCandidateMoves();
        for(final int direction : CANDIDATE_MOVE_DIRECTIONS){
            int destination = this.location + direction;

            while(BoardUtils.isValidIndex(destination)){
                if(isFirstColumnExclusion(destination - direction, direction) ||
                        isEighthColumnExclusion(destination - direction, direction)){
                    break;
                }

                if(!board.isTileOccupied(destination)){
                    candidateMoves.add(new Move(location, destination, MoveType.PASSIVE));
                } else {
                    final Piece pieceAtDestination = board.getPiece(destination);

                    if(pieceAtDestination.getColor() != this.color){
                        candidateMoves.add(new Move(location, destination, MoveType.CAPTURE));
                    }

                    break; // If we found a piece no more moves are legal in this direction
                }

                destination += direction;
            }
        }
    }

    @Override
    public Bishop cloneToNewBoard(Board board){
        return new Bishop(location, color, board);
    }

    @Override
    public ImageIcon getIcon() {
        return color == PieceColor.WHITE ? PieceIconManager.whiteBishop : PieceIconManager.blackBishop;
    }

    private static boolean isFirstColumnExclusion(final int location, final int direction){
        return BoardUtils.A_FILE[location] &&
                (direction == -9 || direction == 7);
    }

    private static boolean isEighthColumnExclusion(final int location, final int direction){
        return BoardUtils.H_FILE[location] &&
                (direction == 9 || direction == -7);
    }

    @Override
    public int value(){
        return 3;
    }

    @Override
    public String toString() {
        return this.color == PieceColor.WHITE ? "B" : "b";
    }
}
