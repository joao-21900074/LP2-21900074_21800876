package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Creature;

public abstract class Zombie extends Creature {
    private int nItensDestruido = 0;
    private int equipe = 20;
    private String nomeTipo;

    public Zombie(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "zombie.png";
        nomeTipo = retornaNomeTipo(idTipo);
    }

    public void destruirIten() {
        nItensDestruido++;
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino) {

        /* Valida se tem outro Zombie no destino */
        if(idDestino >= 0 && idDestino <= 4) {
            return false;
        }

        /* Valida se tem um cachorro no destino */
        if(idDestino == 9) {
            return false;
        }

        return true;
    }

    private String retornaNomeTipo(int idTipo) {
        switch (idTipo) {
            case 0:
                return "Criança (Zombie)";
            case 1:
                return "Adulto (Zombie)";
            case 2:
                return "Militar (Zombie)";
            case 3:
                return "Idoso (Zombie)";
            case 4:
                return "Zombie Vampiro";
            default:
                return "ERRO";
        }
    }

    @Override
    public int getEquipe(){return equipe;}

    //Mudar para cada tipo de Zombie
    public String toString() {
        return id + " | " + nomeTipo + " | Os Outros | " + nome + " " + nItensDestruido + " @ (" + super.getPosicao()[0] + ", " + super.getPosicao()[1] + ")";
    }
}