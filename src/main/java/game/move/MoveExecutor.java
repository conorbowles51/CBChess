package game.move;

import game.board.Board;
import game.pieces.Pawn;
import game.pieces.Piece;
import game.pieces.Queen;

public class MoveExecutor {
    public static Board execute(Board b, Move m){
        if(m.getType() == MoveType.KING_SIDE_CASTLE ||
            m.getType() == MoveType.QUEEN_SIDE_CASTLE) {
            return executeCastleMove(b, m);
        }

        // Includes PASSIVE, PAWN_TWO_TILES, CAPTURE
        return executeNormalMove(b, m);
    }

    private static Board executeNormalMove(Board b, Move m){
        // Clone the board
        Board board = b.clone();

        // Reset the possibilities of en passant if a move is made
        for(Piece p : board.getPieces().values()){
            if(p instanceof Pawn pawn){
                pawn.setJustMovedTwoTiles(false);
            }
        }

        // Get the moved and remove it from the board
        Piece piece = board.removePiece(m.getLocation());

        if(m.getType() == MoveType.PROMOTION){
            piece = new Queen(m.getDestination(), piece.getColor(), board);
        }

        piece.setLocation(m.getDestination());

        // Place piece on the new square
        board.placePiece(piece, m.getDestination());

        // If a pawn moves two squares, open up the possibility of en passant
        if(piece instanceof Pawn pawn){
            pawn.setJustMovedTwoTiles(m.getType() == MoveType.PAWN_TWO_TILE);
        }

        board.calculateAllCandidateMoves();

        return board;
    }

    private static Board executeCastleMove(Board b, Move m){
        // Clone the board
        Board board = b.clone();

        // Get the king and remove it from the board
        Piece king = board.removePiece(m.getLocation());
        int kingLoc = king.getLocation();
        king.setLocation(m.getDestination());

        // Place king on the new square
        board.placePiece(king, m.getDestination());

        int rookLocationOffset = m.getType() == MoveType.KING_SIDE_CASTLE ? 3 : -4;
        int rookDestinationOffset = m.getType() == MoveType.KING_SIDE_CASTLE ? 1 : -1;

        int rookLocation = kingLoc + rookLocationOffset;
        int rookDestination = kingLoc + rookDestinationOffset;

        try {
            Piece rook = board.removePiece(rookLocation);

            rook.setLocation(rookDestination);
            board.placePiece(rook, rookDestination);
        } catch (Exception e){
            System.out.println("Could not find rook involved in castle move: " + king.getColor() + ", " + m.getType());
            System.out.println("Looked for rook at index: " + rookLocation);
        }



        board.calculateAllCandidateMoves();

        return board;
    }
}
