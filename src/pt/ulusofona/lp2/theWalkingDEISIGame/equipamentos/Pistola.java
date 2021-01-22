package pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public class Pistola extends Equipamento {
    //Vem com 3 balas
    private int balas = 3;

    //private static int nrUsos = 0;

    public Pistola(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    public int getBalas(){
        return balas;
    }

    //Após usar perde 1 bala, quando chegar a 0 balas deixa de ter efeito
    public void atirar(){
        balas--;
    }
/*
    @Override
    public void addNrUsos() {
        nrUsos++;
    }

    @Override
    public int getNrUsos() {
        return nrUsos;
    }
*/
    @Override
    public String toString() {
        return "Pistola Walther PPK | " + balas;
    }
}
