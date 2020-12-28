package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Alho extends Equipamento {
    //Só protege contra ZombieVampiro
    public Alho(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    @Override
    public String toString() {
        return "Cabeça de Alho";
    }
}