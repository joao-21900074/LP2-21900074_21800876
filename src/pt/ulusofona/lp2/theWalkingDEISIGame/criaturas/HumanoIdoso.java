package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.criaturas.Humano;

public class HumanoIdoso extends Humano {
    public HumanoIdoso(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {

        //Validações comum para todos os Humanos
        if(!super.validaMove(xD,yD,isDay,idDestino, idTipoDestino)) {
            return false;
        }

        //Valida diagonal (Idoso não pode andar na diagonal)
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

        //Valida se é noite (Humano idoso só pode se movimentar de dia)
        if(!isDay) {
            System.out.println("to dormindo");
            return false;
        }

        return true;
    }
}
