package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Lixivia extends Equipamento {
    //Protege se usar
    float litros = 1;

    public Lixivia(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    //A cada utilização gasta 0.3 litros, ou seja da pra usar 3 vezes
    public void usar(){
        litros -= 0.3;
    }

    @Override
    public String toString() {
        return "Garrafa de Lixívia (1 litro) | " + litros;
    }
}