package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Zombie {

    int id;
    int idTipo;
    String nome;
    final String imagePNG = "zombie.png";
    int[] posicao = new int[2];
    int nItensDestruido = 0;

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

    public int getIdTipo() {
        return idTipo;
    }

    public String getNome() {
        return nome;
    }

    public String getImagePNG() {
        return imagePNG;
    }

    public void destruirIten() {
        nItensDestruido++;
    }

    public void setPosicao(int[] newPosicao) {
        posicao = newPosicao;
    }

    public String toString() {
        return id + " | Zombie | Os Outros | " + nome + " " + nItensDestruido + " @ (" + posicao[0] + ", " + posicao[1] + ")";
    }
}
