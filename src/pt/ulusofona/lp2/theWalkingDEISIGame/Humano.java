package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Humano{

    int id;
    int idTipo;
    String nome;
    final String imagePng = "";
    int[] posicao = new int[2];
    boolean vivo = true;
    Equipamento equipamento;

    public Humano() {}

    public Humano(int id, int idTipo, String nome, int[] posicao) {
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

    public boolean equiparEquipamento(Equipamento equipamento) {
        if(equipamento == null) {
            return false;
        }

        //Lembrar de implementar a situação onde o jogador tem um equipamento
        //derruba ele na posicao anterior e equipa o novo

        this.equipamento = equipamento;
        return true;
    }

    public void desequiparEquipamento() {
        this.equipamento = null;
    }
}
