package game.pieces;

public enum PieceColor {
    WHITE {

        @Override
        public int getPawnDirection() {
            return -1;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public PieceColor other() {
            return BLACK;
        }
    },
    BLACK {
        @Override
        public int getPawnDirection() {
            return 1;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public PieceColor other() {
            return WHITE;
        }
    };

    public abstract int getPawnDirection();
    public abstract boolean isWhite();
    public abstract boolean isBlack();
    public abstract PieceColor other();
}