package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Zombie {

    public int id;
    public int idTipo;
    public String nome;
    public String imagePng;

    public Zombie() {}

    public Zombie(int id, int idTipo, String nome, String imagePng) {
        this.id = id;
        this.idTipo = idTipo;
        this.nome = nome;
        this.imagePng = imagePng;
    }

    public int getId() {
        return id;
    }

    public String getImagePng() {
        return imagePng;
    }
}
