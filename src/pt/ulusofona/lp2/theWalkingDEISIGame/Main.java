package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        TWDGameManager testStart = new TWDGameManager();
        File ficheiro = new File("Game_sample.txt");
        testStart.startGame(ficheiro);
    }
}