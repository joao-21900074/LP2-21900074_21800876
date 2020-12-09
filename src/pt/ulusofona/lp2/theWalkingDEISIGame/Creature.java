package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Creature {

    int id;
    int idTipo;
    String nome;
    String imagePng;
    int[] posicao = new int[2];

    public Creature() {};

    public Creature(int id, int idTipo, String nome, int[] posicao) {
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
        return imagePng;
    }

    public void setPosicao(int[] newPosicao) {
        posicao = newPosicao;
    }

    @Override
    public String toString() {
        return "";
    };

}
