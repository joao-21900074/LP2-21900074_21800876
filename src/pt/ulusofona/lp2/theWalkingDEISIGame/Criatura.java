package pt.ulusofona.lp2.theWalkingDEISIGame;

// Teste
// Batata

public class Criatura {

    public int id;
    public int idTipo;
    public String nome;
    public String image;

    public Criatura() {}

    public Criatura(int id, int idTipo, String nome) {
        this.id = id;
        this.idTipo = idTipo;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getImagePNG() {
        return image;
    }

}