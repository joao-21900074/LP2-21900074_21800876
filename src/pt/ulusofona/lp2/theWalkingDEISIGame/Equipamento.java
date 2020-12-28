package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Equipamento {
    int id;
    int idTipo;
    String nome;
    String info;
    int usos;
    int[] posicao = new int[2];

    public Equipamento() {}

    public Equipamento(int id, int idTipo, int[] posicao) {
        this.id = id;
        this.idTipo = idTipo;
        this.posicao = posicao;
    }

    public int getId() {
        return id;
    }

    public int getIdTipo() {
        return idTipo;
    }

}