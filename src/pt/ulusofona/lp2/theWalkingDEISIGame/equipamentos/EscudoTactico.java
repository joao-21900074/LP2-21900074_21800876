package pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public class EscudoTactico extends Equipamento {

    private static int nrUsos = 0;

    //Defesa infinita
    public EscudoTactico(int id, int idTipo, int[] posicao){
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
        return "Escudo Táctico";
    }

    @Override
    public void resetNrUsos() {
        nrUsos = 0;
    }
}