package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Zombie {

    int id;
    int idTipo;
    String nome;
    final String imagePng = "";
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

    public String getImagePng() {
        return imagePng;
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
}
