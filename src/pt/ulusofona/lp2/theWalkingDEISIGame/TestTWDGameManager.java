package pt.ulusofona.lp2.theWalkingDEISIGame;

import org.junit.Test;
import java.io.File;
import static org.junit.Assert.*;

public class TestTWDGameManager {

    @Test
    public void testMoveDireita(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para Direita",testStart.move(1,1,2,1));
    }

    @Test
    public void testMoveEsquerda(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para Esquerda",testStart.move(1,1,0,1));
    }

    @Test
    public void testMoveBasicoCima(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para Cima",testStart.move(1,1,1,0));
    }

    @Test
    public void testMoveBasicoBaixo(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para Baixo",testStart.move(1,1,1,2));
    }

    /*@Test
    public void testMoveDiagonal(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento na Diagonal",testStart.move(1,1,2,2));
    }*/

    @Test
    public void testMoveOutOfBounds(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para fora do mapa",testStart.move(4,4,5,4));
    }

    @Test
    public void testStartGameBonecoForaDoMapa(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        assertTrue("Boneco nasce fora do mapa",testStart.startGame(ficheiroTest));
    }

}