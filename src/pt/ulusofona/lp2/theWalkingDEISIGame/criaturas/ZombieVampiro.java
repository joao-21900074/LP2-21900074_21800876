package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.criaturas.Zombie;

public class ZombieVampiro extends Zombie {
    public ZombieVampiro(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino) {
        //Valida
        if(!super.validaMove(xD,yD,isDay,idDestino)) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[0] - yD) > 2) || (Math.abs(super.getPosicao()[1] - xD) > 2)) {
            return false;
        }

        //Valida se o turno é noturno (Zumbi Vampiro só pode se movimentar de noite)
        if(isDay) {
            return false;
        }

        //Valida se no destino tem uma Cabeça de Alho
        if(idDestino == -5) {
            return false;
        }

        return true;
    }
}
