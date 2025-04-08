package engine;

import game.board.Board;
import game.move.Move;
import game.pieces.Knight;
import game.pieces.Pawn;
import game.pieces.Piece;
import game.pieces.PieceColor;

import java.util.ArrayList;
import java.util.List;

public class BoardEvaluator {
    public static float evaluate(Board board){
        if(isCheckMated(PieceColor.WHITE, board)){
            return -Integer.MAX_VALUE;
        } else if(isCheckMated(PieceColor.BLACK, board)){
            return Integer.MAX_VALUE;
        }

        float evaluation = 0;

        for(Piece p : board.getPieces().values()){
            evaluation += getMaterialScore(p);
            evaluation += getPositionalScore(p);
        }
        System.out.println(evaluation);
        return evaluation;
    }

    private static int getMaterialScore(Piece p){
        return -p.value() * p.getColor().getPawnDirection();
    }

    private static float getPositionalScore(Piece p){
        float score = 0;

        if(p instanceof Pawn){
            score += p.getColor() == PieceColor.WHITE ? EngineUtils.WHITE_PAWN_POS_SCORES[p.getLocation()] : -EngineUtils.BLACK_PAWN_SCORES[p.getLocation()];
        } else if(p instanceof Knight){
            score += EngineUtils.KNIGHT_SCORES[p.getLocation()] / 2f * -p.getColor().getPawnDirection();
        }

        return score;
    }

    private static boolean isCheckMated(PieceColor color, Board b){
        List<Move> move = new ArrayList<>();

        int kingLocation = b.getKing(color).getLocation();

        for(Piece p : b.getPiecesByColor(color)){
            if (!p.getLegalMoves().isEmpty()){
                return false;
            }
        }

        for(Piece p : b.getPiecesByColor(color.other())){
            for(Move m : p.getLegalMoves()){
                if(m.getDestination() == kingLocation){
                    return true;
                }
            }
        }

        return false;
    }
}
