package pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public class Alho extends Equipamento {

    private static int nrUsos = 0;

    //Só protege contra ZombieVampiro
    public Alho(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    @Override
    public int getNrUsos() {
        return nrUsos;
    }

    @Override
    public void addNrUsos() {
        nrUsos++;
    }

    @Override
    public void resetNrUsos() {
        nrUsos = 0;
    }

    @Override
    public String toString() {
        return "Cabeça de Alho";
    }
}