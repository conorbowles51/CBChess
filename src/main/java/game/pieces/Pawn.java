package game.pieces;

import game.board.Board;
import game.board.BoardUtils;
import game.move.*;
import ui.PieceIconManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private final static int[] CANDIDATE_MOVE_DIRECTIONS = { 8, 16, 7, 9 };


    private boolean justMovedTwoTiles;

    public Pawn(int location, PieceColor color, Board board) {
        super(location, color, board);
        this.justMovedTwoTiles = false;
    }


    @Override
    public void calculateCandidateMoves() {
        super.calculateCandidateMoves();

        for(final int direction : CANDIDATE_MOVE_DIRECTIONS){
            final int destination = this.location + direction * this.color.getPawnDirection();

            if(!BoardUtils.isValidIndex(destination)){
                continue;
            }

            final Piece destinationPiece = board.getPiece(destination);

            // PASSIVE MOVES
            if(!board.isTileOccupied(destination)) {
                if (direction == 8) {
                    // TODO: More to do here! Promotions!
                    MoveType type = destination < 8 || destination > 55 ? MoveType.PROMOTION : MoveType.PASSIVE;
                    candidateMoves.add(new Move(location, destination, type));
                } else if (direction == 16 && !this.hasMoved()) {
                    final int jumpedOverTileLocation = destination - 8 * this.color.getPawnDirection();

                    if(!board.isTileOccupied(jumpedOverTileLocation)) {
                        candidateMoves.add(new Move(location, destination, MoveType.PAWN_TWO_TILE));
                    }
                }
                // EN PASSANT CAPTURES
                else if(direction == 7 && !((BoardUtils.H_FILE[this.location] && this.color.isWhite()) ||
                        (BoardUtils.A_FILE[this.location] && this.color.isBlack())) ){
                    Piece enpassantPiece = board.getPiece(this.location - this.color.getPawnDirection());

                    if(!(enpassantPiece instanceof Pawn enpassantPawn)){
                        continue;
                    }

                    if(enpassantPawn.color == color){
                        continue;
                    }

                    if(enpassantPawn.justMovedTwoTiles()){
                        candidateMoves.add(new Move(location, destination, MoveType.EN_PASSANT));
                    }
                }
                else if(direction == 9 && !((BoardUtils.A_FILE[this.location] && this.color.isWhite()) ||
                        (BoardUtils.H_FILE[this.location] && this.color.isBlack())) ){
                    Piece enpassantPiece = board.getPiece(this.location + this.color.getPawnDirection());

                    if(!(enpassantPiece instanceof Pawn enpassantPawn)){
                        continue;
                    }

                    if(enpassantPawn.justMovedTwoTiles()){
                        candidateMoves.add(new Move(location, destination, MoveType.EN_PASSANT));
                    }

                }
                // NORMAL CAPTURES
            } else if(destinationPiece.color != this.color){
                if(direction == 7 && !((BoardUtils.H_FILE[this.location] && this.color.isWhite()) ||
                        (BoardUtils.A_FILE[this.location] && this.color.isBlack())) ){
                    MoveType type = destination < 8 || destination > 55 ? MoveType.PROMOTION : MoveType.CAPTURE;
                    candidateMoves.add(new Move(location, destination, type));
                }
                else if(direction == 9 && !((BoardUtils.A_FILE[this.location] && this.color.isWhite()) ||
                        (BoardUtils.H_FILE[this.location] && this.color.isBlack())) ){
                    MoveType type = destination < 8 || destination > 55 ? MoveType.PROMOTION : MoveType.CAPTURE;
                    candidateMoves.add(new Move(location, destination, type));
                }
            }
        }
    }

    @Override
    public Pawn cloneToNewBoard(Board board){
        Pawn newPawn = new Pawn(location, color, board);
        newPawn.justMovedTwoTiles = this.justMovedTwoTiles;
        newPawn.hasMoved = this.hasMoved;
        return newPawn;
    }

    @Override
    public ImageIcon getIcon() {
        return color == PieceColor.WHITE ? PieceIconManager.whitePawn : PieceIconManager.blackPawn;
    }

    public void setJustMovedTwoTiles(boolean justMovedTwoTiles){
        this.justMovedTwoTiles = justMovedTwoTiles;
    }

    public boolean justMovedTwoTiles(){
        return this.justMovedTwoTiles;
    }

    @Override
    public int value(){
        return 1;
    }

    @Override
    public String toString() {
        return this.color == PieceColor.WHITE ? "P" : "p";
    }
}
