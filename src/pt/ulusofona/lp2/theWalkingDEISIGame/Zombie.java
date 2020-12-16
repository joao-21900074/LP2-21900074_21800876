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

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int currentTeam) {

        /* Valida se tem outro Zombie no destino */
        if(idDestino >= 0 && idDestino <= 4) {
            return false;
        }

        /* Valida se tem um cachorro no destino */
        if(idDestino == 9) {
            return false;
        }

        return true;
    }

    //Mudar para cada tipo de Zombie
    public String toString() {
        return id + " | Zombie | Os Outros | " + nome + " " + nItensDestruido + " @ (" + posicao[0] + ", " + posicao[1] + ")";
    }
}
