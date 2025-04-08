package game;

import Player.Player;
import game.board.Board;
import game.board.BoardUtils;
import game.move.Move;
import game.move.MoveExecutor;
import game.move.MoveType;
import game.move.MoveValidator;
import game.pieces.Piece;
import game.pieces.PieceColor;
import ui.CBChessUI;
import ui.PieceIconManager;
import Player.PlayerType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Game {
    private static Board board;
    private static CBChessUI ui;

    private static Player whitePlayer;
    private static Player blackPlayer;
    private static Player nextToMove;

    private static Piece selectedPiece = null;

    public static void run() {
        PieceIconManager.loadPieceIcons();

        whitePlayer = new Player(PieceColor.WHITE, PlayerType.HUMAN);
        blackPlayer = new Player(PieceColor.BLACK, PlayerType.AI);

        nextToMove = whitePlayer;

        board = Board.createStandardBoard();
        ui = new CBChessUI();

        ui.show();
        ui.displayBoard(board);

        nextToMove.requestMove(board);
    }

    public static void onTileClicked(int tileId){
        if(selectedPiece == null) {
            Piece clickedPiece = board.getPiece(tileId);

            if(clickedPiece != null){
                selectedPiece = clickedPiece;
                highlightLegalMoves(selectedPiece);
            } else {
                removeHighlights();
            }

            return;
        }

        Move move = selectedPiece.findLegalMoveByDestination(tileId);

        if(move == null){
            removeHighlights();
            selectedPiece = null;
            return;
        }

        executeMove(move);
    }

    public static void executeMove(Move move){
        board = MoveExecutor.execute(board, move);
        MoveValidator.calculateLegalMoves(board);
        ui.displayBoard(board);

        removeHighlights();
        selectedPiece = null;

        if(nextToMove == whitePlayer){
            nextToMove = blackPlayer;
        } else {
            nextToMove = whitePlayer;
        }

        nextToMove.requestMove(board);
    }

    private static void highlightLegalMoves(Piece piece){

        List<Move> legalMoves = piece == null ? new ArrayList<>() : piece.getLegalMoves();
        List<Integer> tileIds = new ArrayList<>();

        for(Move move : legalMoves){
            tileIds.add(move.getDestination());
        }

        ui.highlight(tileIds);
    }

    private static void removeHighlights(){
        ui.highlight(new ArrayList<>());
    }
}
