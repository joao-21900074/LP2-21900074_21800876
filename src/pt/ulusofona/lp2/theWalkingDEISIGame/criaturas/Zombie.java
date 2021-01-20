package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.Creature;

public abstract class Zombie extends Creature {
    private String nomeTipo;
    private boolean dead = false;
    private int nTransformacoes = 0;

    public Zombie(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "zombie.png";
        nomeTipo = retornaNomeTipo(idTipo);
    }

    public void destruirIten() {
        super.addNItensDestruidos();
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

        /* Destruir item tirando Veneno*/
        if(idDestino < 0 && !(idTipoDestino == 8)){
            //Não contar quando zombie vampiro vai pra cima de alho
            if(idTipo == 4 && idTipoDestino == 5) {
                super.nItensDestruidos--;
            }
            destruirIten();
        }

        return true;
}

    public String retornaNomeTipo(int idTipo) {
        switch (idTipo) {
            case 0:
                return "Criança";
            case 1:
                return "Adulto";
            case 2:
                return "Militar";
            case 3:
                return "Idoso";
            case 4:
                return "Zombie Vampiro";
            default:
                return "ERRO";
        }
    }

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public int getnTransformacoes() {
        return nTransformacoes;
    }

    public void addNTranformacoes() {
        nTransformacoes++;
    }


    @Override
    public int getEquipe(){return 20;}

    public abstract void addTotalEquipDestruidos();

    public abstract int getTotalEquipDestruidos();

    //Mudar para cada tipo de Zombie
    public String toString() {

        if(dead) {
            return id + " | " + nomeTipo + " | Os Outros | " + nome + " " + super.getNItensDestruidos() + " @ RIP";
        }

        return id + " | " + nomeTipo + " | Os Outros | " + nome + " " + super.getNItensDestruidos() + " @ (" + super.getPosicao()[0] + ", " + super.getPosicao()[1] + ")";
    }
}