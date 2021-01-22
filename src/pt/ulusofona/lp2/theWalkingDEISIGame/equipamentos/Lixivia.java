package pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public class Lixivia extends Equipamento {
    //Protege se usar
    private int litros = 3;

    private static int nrUsos = 0;

    public Lixivia(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    public int getLitros(){
        return litros;
    }

    //A cada utilização gasta 0.3 litros, ou seja da pra usar 3 vezes
    public void usar(){
        litros--;
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
        return "Garrafa de Lixívia (1 litro) | " + litros;
    }
}