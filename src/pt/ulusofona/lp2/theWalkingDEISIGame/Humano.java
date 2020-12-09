package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Humano extends Creature{


    Equipamento equipamento;
    int nEquipamentos = 0;

    public Humano() {}

    public Humano(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        imagePng = "humano.png";
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

    public boolean temEquipamento() {
        return equipamento != null;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }


    @Override
    public String toString() {
        return id + " | Humano | Os Vivos | " + nome + " " + nEquipamentos + " @ (" + posicao[0] + ", " + posicao[1] + ")";
    }
}
