package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.criaturas.Zombie;

public class ZombieMilitar extends Zombie {

    public ZombieMilitar(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino) {
        //Valida
        if(!super.validaMove(xD,yD,isDay,idDestino)) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[0] - yD) > 3) || (Math.abs(super.getPosicao()[1] - xD) > 3)) {
            return false;
        }

        return true;
    }

}
