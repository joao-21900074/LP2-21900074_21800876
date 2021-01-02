package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Creature;

public abstract class Zombie extends Creature {
    private int nItensDestruido = 0;
    private String nomeTipo;
    private boolean dead = false;

    public Zombie(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "zombie.png";
        nomeTipo = retornaNomeTipo(idTipo);
    }

    public void destruirIten() {
        nItensDestruido++;
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {
        /* Valida se tem outro Zombie no destino */
        if(idTipoDestino >= 0 && idTipoDestino <= 4 && idDestino > 0) {
            return false;
        }

        /* Valida se tem um cachorro no destino */
        if(idTipoDestino == 9 && idDestino > 0) {
            return false;
        }

        /* Destruir item - Veneno*/
        if(idDestino < 0 && !(idTipoDestino == 8)){
            //Não contar quando zombie vampiro vai pra cima de alho
            if(idTipo == 4 && idTipoDestino == 5) {
                nItensDestruido--;
            }
            destruirIten();
        }

        //Valida se tem veneno no chão
        if(idTipoDestino == 8 && idDestino < 0){
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

    public void die() {
        dead = true;
    }

    @Override
    public int getEquipe(){return 20;}

    //Mudar para cada tipo de Zombie
    public String toString() {

        if(dead) {
            return id + " | " + nomeTipo + " | Os Outros | " + nome + " " + nItensDestruido + " @ RIP";
        }

        return id + " | " + nomeTipo + " | Os Outros | " + nome + " " + nItensDestruido + " @ (" + super.getPosicao()[0] + ", " + super.getPosicao()[1] + ")";
    }
}