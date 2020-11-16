package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        File ficheiro = new File("Game_sample.txt");
        TWDGameManager.startGame(ficheiro);
    }
}