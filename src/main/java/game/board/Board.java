package game.board;

import game.move.*;
import game.pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    protected HashMap<Integer, Piece> pieces;

    public static Board createStandardBoard(){
        Board board = new Board();
        board.resetBoard();
        board.calculateAllCandidateMoves();
        MoveValidator.calculateLegalMoves(board);
        return board;
    }

    private Board(){}

    public Board clone() {
        Board newBoard = new Board();

        newBoard.pieces = new HashMap<>();

        for (Map.Entry<Integer, Piece> entry : this.pieces.entrySet()){
            int k = entry.getKey();
            Piece v = entry.getValue().cloneToNewBoard(newBoard);

            newBoard.pieces.put(k, v);
        }

        newBoard.calculateAllCandidateMoves();

        return newBoard;
    }

    public boolean isTileOccupied(int location){
        return pieces.containsKey(location);
    }

    public HashMap<Integer, Piece> getPieces(){
        return pieces;
    }

    public List<Piece> getPiecesByColor(PieceColor color){
        List<Piece> piecesByColor = new ArrayList<>();
        for(Piece p : this.pieces.values()) {
            if(p.getColor() == color){
                piecesByColor.add(p);
            }
        }
        return piecesByColor;
    }

    public Piece getPiece(int location){
        return pieces.get(location);
    }

    public Piece removePiece(int location){
        Piece piece = getPiece(location);
        pieces.remove(location);
        return piece;
    }

    public void placePiece(Piece piece, int location){
        piece.setLocation(location);
        pieces.put(location, piece);
    }

    public King getKing(PieceColor color){
        for(Piece p : pieces.values()) {
            if(p instanceof King && p.getColor() == color){
                return (King)p;
            }
        }

        return null;
    }

    public void resetBoard(){
        pieces = new HashMap<>();

        pieces.put(0, new Rook(0, PieceColor.BLACK, this));
        pieces.put(1, new Knight(1, PieceColor.BLACK, this));
        pieces.put(2, new Bishop(2, PieceColor.BLACK, this));
        pieces.put(3, new Queen(3, PieceColor.BLACK, this));
        pieces.put(4, new King(4, PieceColor.BLACK, this));
        pieces.put(5, new Bishop(5, PieceColor.BLACK, this));
        pieces.put(6, new Knight(6, PieceColor.BLACK, this));
        pieces.put(7, new Rook(7, PieceColor.BLACK, this));

        pieces.put(8, new Pawn(8, PieceColor.BLACK, this));
        pieces.put(9, new Pawn(9, PieceColor.BLACK, this));
        pieces.put(10, new Pawn(10, PieceColor.BLACK, this));
        pieces.put(11, new Pawn(11, PieceColor.BLACK, this));
        pieces.put(12, new Pawn(12, PieceColor.BLACK, this));
        pieces.put(13, new Pawn(13, PieceColor.BLACK, this));
        pieces.put(14, new Pawn(14, PieceColor.BLACK, this));
        pieces.put(15, new Pawn(15, PieceColor.BLACK, this));

        pieces.put(48, new Pawn(48, PieceColor.WHITE, this));
        pieces.put(49, new Pawn(49, PieceColor.WHITE, this));
        pieces.put(50, new Pawn(50, PieceColor.WHITE, this));
        pieces.put(51, new Pawn(51, PieceColor.WHITE, this));
        pieces.put(52, new Pawn(52, PieceColor.WHITE, this));
        pieces.put(53, new Pawn(53, PieceColor.WHITE, this));
        pieces.put(54, new Pawn(54, PieceColor.WHITE, this));
        pieces.put(55, new Pawn(55, PieceColor.WHITE, this));

        pieces.put(56, new Rook(56, PieceColor.WHITE, this));
        pieces.put(57, new Knight(57, PieceColor.WHITE, this));
        pieces.put(58, new Bishop(58, PieceColor.WHITE, this));
        pieces.put(59, new Queen(59, PieceColor.WHITE, this));
        pieces.put(60, new King(60, PieceColor.WHITE, this));
        pieces.put(61, new Bishop(61, PieceColor.WHITE, this));
        pieces.put(62, new Knight(62, PieceColor.WHITE, this));
        pieces.put(63, new Rook(63, PieceColor.WHITE, this));
    }

    public void calculateAllCandidateMoves(){
        for(Piece piece : pieces.values()){
            piece.calculateCandidateMoves();
        }
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < BoardUtils.NUM_TILES; i++){

            String s = pieces.containsKey(i) ? pieces.get(i).toString() : "-";

            sb.append(String.format("%3s", s));

            if((i + 1) % 8 == 0 ){
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
