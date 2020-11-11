package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Equipamento {
    int id;
    int idTipo;
    boolean equipado;

    public Equipamento() {}

    public Equipamento(int id, int idTipo, boolean equipado) {
        this.id = id;
        this.idTipo = idTipo;
        this.equipado = equipado;
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
}
