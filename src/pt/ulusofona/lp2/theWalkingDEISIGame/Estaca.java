package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Estaca extends Equipamento {
    //Ataque infinito
    public Estaca(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    @Override
    public String toString() {
        return "Estaca de Madeira";
    }
}