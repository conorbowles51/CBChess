package game.move;

import game.pieces.Piece;

public class Move {
    private final int location;
    private final int destination;
    private MoveType type;

    public Move(int location, int destination, MoveType type) {
        this.location = location;
        this.destination = destination;
        this.type = type;
    }

    public int getDestination(){
        return destination;
    }

    public int getLocation(){
        return location;
    }

    public MoveType getType(){
        return type;
    }

    @Override
    public String toString() {
        return "Move{" +
                "location=" + location +
                ", destination=" + destination +
                ", type=" + type +
                '}';
    }
}
