package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Zombie {

    int id;
    int idTipo;
    String nome;
    final String imagePNG = "zombie.png";
    int[] posicao = new int[2];
    boolean vivo = true;


    public Zombie() {}

    public Zombie(int id, int idTipo, String nome, int[] posicao) {
        this.id = id;
        this.idTipo = idTipo;
        this.nome = nome;
        this.posicao = posicao;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getImagePNG() {
        return imagePNG;
    }

    public boolean mudarPosicao(int[] newPosicao)  {
        if(newPosicao != null && newPosicao.length != 2) {
            return false;
        }

        return true;
    }

    public void die() {
        vivo = false;
    }

    public String toString() {
        return id + " | Zombie | Os Outros | " + nome + " 0 @ (" + posicao[0] + ", " + posicao[1] + ")";
    }
}
