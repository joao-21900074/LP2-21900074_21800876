package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Zombie extends Creature {

    int nItensDestruido = 0;

    public Zombie() {}

    public Zombie(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "zombie.pgn";
    }


    public void destruirIten() {
        nItensDestruido++;
    }

    //Mudar para cada tipo de Zombie
    public String toString() {
        return id + " | Zombie | Os Outros | " + nome + " " + nItensDestruido + " @ (" + posicao[0] + ", " + posicao[1] + ")";
    }
}
