package pt.ulusofona.lp2.theWalkingDEISIGame;

public class EscudoTactico extends Equipamento {
    //Defesa infinita
    public EscudoTactico(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    @Override
    public String toString() {
        return "Escudo Táctico";
    }
}