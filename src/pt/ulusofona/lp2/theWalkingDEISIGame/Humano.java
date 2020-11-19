package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Humano{

    int id;
    int idTipo;
    String nome;
    final String imagePNG = "human.png";
    int[] posicao = new int[2];
    boolean vivo = true;
    Equipamento equipamento;
    int nEquipamentos = 0;

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

    public boolean equiparEquipamento(Equipamento equipamento) {
        if(equipamento == null) {
            return false;
        }

        //Lembrar de implementar a situação onde o jogador tem um equipamento
        //derruba ele na posicao anterior e equipa o novo

        this.equipamento = equipamento;
        nEquipamentos++;
        return true;
    }

    public void desequiparEquipamento() {
        this.equipamento = null;
    }

    public boolean temEquipamento() {
        return equipamento != null;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setPosicao(int[] newPosicao) {
        posicao = newPosicao;
    }

    @Override
    public String toString() {
        return id + " | Humano | Os Vivos | " + nome + " " + nEquipamentos + " @ (" + posicao[0] + ", " + posicao[1] + ")";
    }
}
