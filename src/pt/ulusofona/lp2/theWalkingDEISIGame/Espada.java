package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Espada extends Equipamento {
    //Ataque infinito
    public Espada(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    @Override
    public String toString() {
        return "Espada Hattori Hanzo";
    }
}