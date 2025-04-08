package game.move;

import game.board.Board;
import game.pieces.King;
import game.pieces.Piece;
import game.pieces.PieceColor;
import game.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class MoveValidator {
    private static final int WHITE_A_ROOK_LOC = 56;
    private static final int WHITE_H_ROOK_LOC = 63;
    private static final int BLACK_A_ROOK_LOC = 0;
    private static final int BLACK_H_ROOK_LOC = 7;
    private static final int WHITE_KING_LOC = 60;
    private static final int BLACK_KING_LOC = 4;

    public static void calculateLegalMoves(Board b){
        for(Piece p : b.getPieces().values()){
            List<Move> legalMoves = new ArrayList<>();

            for(Move m : p.getCandidateMoves()){
                if(validateMove(b, m)){
                    legalMoves.add(m);
                }
            }

            p.setLegalMoves(legalMoves);
        }
    }

    public static boolean validateMove(Board b, Move move){
        PieceColor myColor = b.getPiece(move.getLocation()).getColor();

        if(!isValidPreExecution(b, move, myColor)){
            return false;
        }

        Board board = MoveExecutor.execute(b, move);

        return isValidPostExecution(board, move, myColor);
    }

    private static boolean isValidPreExecution(Board board, Move move, PieceColor myColor){
        List<Piece> myPieces = new ArrayList<>();
        List<Piece> opponentPieces = new ArrayList<>();

        for(Piece piece : board.getPieces().values()){
            if(piece.getColor() == myColor){
                myPieces.add(piece);
            } else {
                opponentPieces.add(piece);
            }
        }

        King myKing = findKing(myPieces, move);

        return isValidCastle(board, move, myKing, myColor, opponentPieces);
    }

    private static boolean isValidPostExecution(Board board, Move move, PieceColor myColor){
        List<Piece> myPieces = new ArrayList<>();
        List<Piece> opponentPieces = new ArrayList<>();

        for(Piece piece : board.getPieces().values()){
            if(piece.getColor() == myColor){
                myPieces.add(piece);
            } else {
                opponentPieces.add(piece);
            }
        }

        King myKing = findKing(myPieces, move);

        return !isInCheck(board, myKing, opponentPieces);
    }

    private static boolean isInCheck(Board board, King myKing, List<Piece> opponentPieces){
        return isTileAttacked(board, myKing.getLocation(), opponentPieces);
    }

    private static boolean isValidCastle(Board board, Move move, King myKing, PieceColor myColor, List<Piece> opponentPieces){
        // Just continue if move is not a castle move
        if(move.getType() != MoveType.KING_SIDE_CASTLE &&
            move.getType() != MoveType.QUEEN_SIDE_CASTLE) {
            return true;
        }
        // If king has moved, invalid castle
        if(myKing.hasMoved()){
            return false;
        }

        // Cant castle from  check
        if(isInCheck(board, myKing, opponentPieces)){
            return false;
        }


        // Cannot castle through, or into check
        int kingLoc = myKing.getLocation();

        if(move.getType() == MoveType.KING_SIDE_CASTLE) {
            for (int i = kingLoc + 1; i <= kingLoc + 2; i++) {
                if (isTileAttacked(board, i, opponentPieces) || board.isTileOccupied(i)) {
                    return false;
                }
            }
        } else {
            for (int i = kingLoc - 1; i >= kingLoc - 2; i--) {
                if (isTileAttacked(board, i, opponentPieces) || board.isTileOccupied(i)) {
                    return false;
                }
            }

            // This additional check is needed to ensure the knight has moved, and it is an empty tile,
            // although the square can be attacked
            if(board.isTileOccupied(kingLoc - 3)){
                return false;
            }
        }



        // Get the rook we are dealing with
        int rookLoc;
        if(move.getType() == MoveType.KING_SIDE_CASTLE){
            rookLoc = myColor.isWhite() ? WHITE_H_ROOK_LOC : BLACK_H_ROOK_LOC;
        } else {
            rookLoc = myColor.isWhite() ? WHITE_A_ROOK_LOC : BLACK_A_ROOK_LOC;
        }

        Piece piece = board.getPiece(rookLoc);

        // If there is no piece, or it is not a rook, invalid castle
        Rook rook;
        if(!(piece instanceof Rook)){
            return false;
        } else {
            rook = (Rook) piece;
        }

        // If the rook has moved before, invalid castle
        if(rook.hasMoved()){
            return false;
        }

        return true;
    }

    private static boolean isTileAttacked(Board board, int location, List<Piece> opponentPieces){
        for(Piece p : opponentPieces){
            for(Move m: p.getCandidateMoves()){
                if(m.getDestination() == location){
                    return true;
                }
            }
        }

        return false;
    }

    private static King findKing(List<Piece> pieces, Move m){
        King myKing = null;
        for(Piece p : pieces){
            if(p instanceof King){
                myKing = (King)p;
            }
        }

        if(myKing == null){
            throw new RuntimeException("Could not find king when validating move: " + m.toString());
        }

        return myKing;
    }
}
