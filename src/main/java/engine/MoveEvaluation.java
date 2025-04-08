package engine;

import game.move.*;

public class MoveEvaluation {
    public Move move;
    public float score;

    public MoveEvaluation(Move move, float score) {
        this.move = move;
        this.score = score;
    }
}
