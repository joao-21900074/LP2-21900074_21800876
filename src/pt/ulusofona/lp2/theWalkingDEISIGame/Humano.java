package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Humano extends Creature {
    Equipamento equipamento;
    int nEquipamentos = 0;
    int equipe = 10;

    public Humano(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "human.png";
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

    public int getIdEquipamento(){
        if(equipamento == null){
            return 0;
        }
        return equipamento.getId();
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int currentTeam) {

        /*
        Valida se um Vivo esta tentando se movimentar para
        um lugar onde tem outro vivo
        */
        if(idDestino >= 5 && idDestino <= 9) {
            return false;
        }

        /*
        Valida se o Humano tem equipamento quando for para cima de
        um zumbi
        */
        if(idDestino >= 0 && idDestino <= 4) { //Valida se tem Zumbi no Destino
            //Valida se o Humano esta sem equipamento
            if(equipamento == null) {
                return false;
            }
            /*
            Valida se tem o equipamento certo para enfrentar um
            Zumbi Vampiro
             */
            if(equipamento.getIdTipo() != -6 && idDestino == 4) {
                return false;
            }
        }



        return true;
    }

    public int getEquipe(){return equipe;}

    @Override
    public String toString() {
        return id + " | Humano | Os Vivos | " + nome + " " + nEquipamentos + " @ (" + posicao[0] + ", " + posicao[1] + ")";
    }
}