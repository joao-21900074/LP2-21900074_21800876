package pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public class Beskar extends Equipamento {

    private static int nrUsos;

    //Defende e ataca, infinito
    public Beskar(int id, int idTipo, int[] posicao){
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
    public String toString() {
        return "Beskar Helmet";
    }
}