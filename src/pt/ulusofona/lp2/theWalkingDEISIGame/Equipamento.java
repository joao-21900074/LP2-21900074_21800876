package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Equipamento {
    int id;
    int idTipo;
    int[] posicao = new int[2];
    boolean equipado = false;
    final String imagePNG = "equipment.png";

    public Equipamento() {}

    public Equipamento(int id, int idTipo, int[] posicao) {
        this.id = id;
        this.idTipo = idTipo;
        this.posicao = posicao;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setEquipado(boolean status) {
        equipado = status;
    }

    /* Verifica se o equipamento esta equipado e equipa se não estiver */
    public boolean estaEquipado() {
        return equipado;
    }

    public String getImagePNG() {
        return imagePNG;
    }
}
