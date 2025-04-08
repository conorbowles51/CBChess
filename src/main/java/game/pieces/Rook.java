package game.pieces;

import game.board.Board;
import game.board.BoardUtils;
import game.move.*;
import ui.PieceIconManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    private final static int[] CANDIDATE_MOVE_DIRECTIONS = { -8, -1, 1, 8};

    public Rook(int location, PieceColor color, Board board) {
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
    public Rook cloneToNewBoard(Board board){
        Rook newRook = new Rook(location, color, board);
        newRook.hasMoved = this.hasMoved;
        return newRook;
    }

    @Override
    public ImageIcon getIcon() {
        return color == PieceColor.WHITE ? PieceIconManager.whiteRook : PieceIconManager.blackRook;
    }

    private static boolean isFirstColumnExclusion(int location, int direction){
        return BoardUtils.A_FILE[location] && direction == -1;
    }

    private static boolean isEighthColumnExclusion(int location, int direction){
        return BoardUtils.H_FILE[location] && direction == 1;
    }

    @Override
    public int value(){
        return 5;
    }

    @Override
    public String toString() {
        return this.color == PieceColor.WHITE ? "R" : "r";
    }
}
