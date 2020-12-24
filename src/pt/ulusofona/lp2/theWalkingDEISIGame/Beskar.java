package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Beskar extends Equipamento {
    //Defende e ataca, infinito
    public Beskar(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    @Override
    public String toString() {
        return "Beskar Helmet";
    }
}