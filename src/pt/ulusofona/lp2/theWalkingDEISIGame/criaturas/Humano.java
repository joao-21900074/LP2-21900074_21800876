package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Creature;
import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public abstract class Humano extends Creature {
    Equipamento equipamento;
    int nEquipamentos = 0;
    int equipe = 10;
    String nomeTipo;
    boolean safe = false;

    public Humano(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "human.png";
        this.nomeTipo = retornaNomeTipo(idTipo);
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

    public int getIdTipoEquipamento(){
        if(equipamento == null){
            return 0;
        }
        return equipamento.getIdTipo();
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino) {

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

    //Função usada para determinar o nomeTipo que vai ser usado no toString
    private String retornaNomeTipo(int idTipo) {
        switch(idTipo) {
            case 5:
                return "Criança (Vivo)";
            case 6:
                return "Adulto (Vivo)";
            case 7:
                return "Militar (Vivo)";
            case 8:
                return "Idoso (Vivo)";
            default:
                return "ERRO";
        }
    }

    public void salvar() {
        safe = true;
    }

    @Override
    public int getEquipe(){return equipe;}

    @Override
    public String toString() {
        if(safe) {
            return id + " | " + nomeTipo + " | Os Vivos | " + nome + " " + nEquipamentos + " @ A salvo";

        }

        return id + " | " + nomeTipo + " | Os Vivos | " + nome + " " + nEquipamentos + " @ (" + super.getPosicao()[0] + ", " + super.getPosicao()[1] + ")";
    }
}