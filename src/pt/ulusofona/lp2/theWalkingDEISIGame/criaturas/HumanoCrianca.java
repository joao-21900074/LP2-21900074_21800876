package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.criaturas.Humano;

public class HumanoCrianca extends Humano {
    public HumanoCrianca(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {
        //Validações comum para todos os Zombies
        if(!super.validaMove(xD,yD,isDay,idDestino, idTipoDestino)) {
            return false;
        }

        //Valida diagonal
        if(super.getPosicao()[1] != xD && super.getPosicao()[0] != yD) {
            return false;
        }

        //Valida movimento esquerda/direita
        if(super.getPosicao()[0] == yD) {
            if(Math.abs(super.getPosicao()[1] - xD) > 1) {
                return false;
            }
        }

        //Valida movimento baixo/cima
        if(super.getPosicao()[1] == xD) {
            if(Math.abs(super.getPosicao()[0] - yD) > 1) {
                return false;
            }
        }

        return true;
    }
}
