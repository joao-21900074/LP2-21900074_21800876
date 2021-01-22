package pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public class Espada extends Equipamento {

    private static int nrUsos = 0;

    //Ataque infinito
    public Espada(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    @Override
    public void addNrUsos() {
        nrUsos++;
    }

    @Override
    public int getNrUsos() {
        return nrUsos;
    }

    @Override
    public void resetNrUsos() {
        nrUsos = 0;
    }

    @Override
    public String toString() {
        return "Espada Hattori Hanzo";
    }
}