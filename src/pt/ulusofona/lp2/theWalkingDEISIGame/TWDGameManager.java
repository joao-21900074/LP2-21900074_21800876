package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.*;
import java.util.List;

public class TWDGameManager {

    List<Humano> humans;
    List<Zombie> zombies;
    int initialTeam;
    int[] worldSize = new int[2];



    /* Deve fazer a leitura do ficheiro de texto e
       carregar para a memória a informação
       relevante. */
    public boolean startGame(File ficheiroInicial) {
        return false;
    }

    //pronto
    public int[] getWorldSize() {
        return worldSize;
    }

    // pronto
    public int getInitialTeam() {
        return initialTeam;
    }

    // pronto
    public List<Humano> getHumans() {
        return humans;
    }

    //pronto
    public List<Zombie> getZombies() {
        return zombies;
    }

    /* Deve tentar executar uma jogada,
       considerando que (xO, yO) representa a
       origem a jogada e (xD, yD) representa o
       destino da jogada. */
    public boolean move(int xO, int yO, int xD, int yD) {
        return false;
    }

    /* Deve devolver true caso já tenha sido
       alcançada uma das condições de paragem
       do jogo e false em caso contrário. */
    public boolean gameIsOver() {
        return false;
    }

    /* Devolve uma lista de Strings com os
       nomes dos autores do projecto. */
    public List<String> getAuthors() {
        return null;
    }

    /* Deve devolver o ID da equipa que está
       activa no turno actual. */
    public int getCurrentTeamId() {
        return 0;
    }

    /* Deve devolver o ID do objecto/elemento
       que se encontra na posição indicada pelas
       coordenadas (x,y) passadas por
       argumento. */
    public int getElementId(int x, int y) {
        return 0;
    }

    /* Devolve uma lista de Strings que
       representam as criaturas sobreviventes do
       jogo, conforme descrito na secção dos
       “Resultados da execução …”. */
    public List<String> getSurvivors() {
        return null;
    }

    /* Dever retornar true caso o turno actual
       corresponda a um turno diurno e false
       caso o turno actual corresponda um turno
       nocturno. */
    public boolean isDay() {
        return false;
    }

    /* Deve retornar true caso a criatura
       identificada pelo 1º argumento tenha em
       sua posse um equipamento do tipo cujo ID
       for passado como 2º argumento.
       Em caso contrário, deve retornar false. */
    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        return false;
    }


}
