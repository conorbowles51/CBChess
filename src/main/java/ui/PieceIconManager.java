package ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PieceIconManager {
    private static final String basePath = System.getProperty("user.dir") + "/src/main/resources/images/";

    public static ImageIcon whitePawn, blackPawn;
    public static ImageIcon whiteRook, blackRook;
    public static ImageIcon whiteKnight, blackKnight;
    public static ImageIcon whiteBishop, blackBishop;
    public static ImageIcon whiteKing, blackKing;
    public static ImageIcon whiteQueen, blackQueen;

    public static void loadPieceIcons() {
        System.out.println(System.getProperty("user.dir"));

        whitePawn = loadPieceIcon("white-pawn");
        blackPawn = loadPieceIcon("black-pawn");
        whiteRook = loadPieceIcon("white-rook");
        blackRook = loadPieceIcon("black-rook");
        whiteKnight = loadPieceIcon("white-knight");
        blackKnight = loadPieceIcon("black-knight");
        whiteBishop = loadPieceIcon("white-bishop");
        blackBishop = loadPieceIcon("black-bishop");
        whiteKing = loadPieceIcon("white-king");
        blackKing = loadPieceIcon("black-king");
        whiteQueen = loadPieceIcon("white-queen");
        blackQueen = loadPieceIcon("black-queen");
    }

    public static ImageIcon loadPieceIcon(String pieceName){
        try {
            final BufferedImage image = ImageIO.read(new File(basePath + pieceName + ".png"));
            Image scaledImage = image.getScaledInstance(CBChessUI.TILE_SIZE, CBChessUI.TILE_SIZE, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}