package engine;

import game.board.Board;
import game.move.Move;
import game.move.MoveExecutor;
import game.move.MoveValidator;
import game.pieces.Piece;
import game.pieces.PieceColor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Minimax {
    public static Move best(int depth, Board board, PieceColor turn) {
        Move bestMove = null;
        float bestScore = turn.isWhite() ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        List<Move> moves = getMoves(board, turn);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Callable<MoveEvaluation>> tasks = new ArrayList<>();

        for (Move move : moves) {
            tasks.add(() -> {
                Board newBoard = MoveExecutor.execute(board, move);
                MoveValidator.calculateLegalMoves(newBoard);
                float score = minimax(depth - 1, newBoard, turn.other(), Integer.MIN_VALUE, Integer.MAX_VALUE);
                return new MoveEvaluation(move, score);
            });
        }

        try {
            List<Future<MoveEvaluation>> results = executorService.invokeAll(tasks);
            for (Future<MoveEvaluation> future : results) {
                MoveEvaluation evaluation = future.get();
                if (turn.isWhite()) {
                    if (evaluation.score > bestScore) {
                        bestScore = evaluation.score;
                        bestMove = evaluation.move;
                    }
                } else {
                    if (evaluation.score < bestScore) {
                        bestScore = evaluation.score;
                        bestMove = evaluation.move;
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }

        return bestMove;
    }

    private static float minimax(int depth, Board board, PieceColor turn, float alpha, float beta) {
        if (depth == 0) {
            return BoardEvaluator.evaluate(board);
        }

        List<Move> moves = getMoves(board, turn);
        if (turn.isWhite()) {
            float max = Integer.MIN_VALUE;
            for (Move move : moves) {
                Board b = MoveExecutor.execute(board, move);
                MoveValidator.calculateLegalMoves(b);
                float score = minimax(depth - 1, b, turn.other(), alpha, beta);
                max = Math.max(max, score);
                alpha = Math.max(alpha, score);
                if (beta <= alpha) break; // Alpha-beta pruning
            }
            return max;
        } else {
            float min = Integer.MAX_VALUE;
            for (Move move : moves) {
                Board b = MoveExecutor.execute(board, move);
                MoveValidator.calculateLegalMoves(b);
                float score = minimax(depth - 1, b, turn.other(), alpha, beta);
                min = Math.min(min, score);
                beta = Math.min(beta, score);
                if (beta <= alpha) break; // Alpha-beta pruning
            }
            return min;
        }
    }

    private static List<Move> getMoves(Board board, PieceColor color) {
        List<Move> moves = new ArrayList<>();
        for (Piece p : board.getPiecesByColor(color)) {
            moves.addAll(p.getLegalMoves());
        }
        return moves;
    }

    private static class MoveEvaluation {
        Move move;
        float score;

        MoveEvaluation(Move move, float score) {
            this.move = move;
            this.score = score;
        }
    }
}
