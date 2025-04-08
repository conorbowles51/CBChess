package game.pieces;

import game.board.Board;
import game.move.Move;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    protected int location;
    protected final PieceColor color;
    protected boolean hasMoved;

    protected final Board board;
    protected List<Move> candidateMoves;
    protected List<Move> legalMoves;

    public Piece(int location, PieceColor color, Board board){
        this.location = location;
        this.color = color;
        this.hasMoved = false;
        this.board = board;
        this.candidateMoves = new ArrayList<>();
        this.legalMoves = new ArrayList<>();
    }

    public List<Move> getCandidateMoves(){
        return candidateMoves;
    }

    public List<Move> getLegalMoves(){
        return legalMoves;
    }

    public Move findLegalMoveByDestination(int destination){
        for(Move m : legalMoves){
            if(m.getDestination() == destination){
                return m;
            }
        }

        return null;
    }

    public void setLegalMoves(List<Move> moves){
        this.legalMoves = moves;
    }

    public void calculateCandidateMoves(){
        this.candidateMoves = new ArrayList<>();
    }

    public abstract Piece cloneToNewBoard(Board board);

    public int getLocation(){
        return this.location;
    }

    public PieceColor getColor(){
        return this.color;
    }

    public boolean hasMoved(){
        return this.hasMoved;
    }

    public abstract ImageIcon getIcon();

    public void setLocation(int location) {
        this.location = location;
        this.hasMoved = true;
    }

    public abstract int value();
}