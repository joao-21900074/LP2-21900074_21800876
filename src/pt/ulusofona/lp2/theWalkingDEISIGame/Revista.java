package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Revista extends Equipamento {
    //Só protege contra ZombieIdoso
    public Revista(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    @Override
    public String toString() {
        return "Revista Maria";
    }
}