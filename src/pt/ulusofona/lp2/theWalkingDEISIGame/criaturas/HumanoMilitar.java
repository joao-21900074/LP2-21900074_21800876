package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.criaturas.Humano;

public class HumanoMilitar extends Humano {
    public HumanoMilitar(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {
        //Valida condições gerais de Humano
        if(!super.validaMove(xD, yD, isDay, idDestino, idTipoDestino)){
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[1] - yD) > 3) || (Math.abs(super.getPosicao()[0] - xD) > 3)) {
            return false;
        }

        return true;
    }
}
