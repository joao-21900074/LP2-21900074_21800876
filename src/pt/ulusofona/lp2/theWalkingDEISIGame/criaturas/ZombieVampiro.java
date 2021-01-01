package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

public class ZombieVampiro extends Zombie {
    public ZombieVampiro(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {
        //Valida
        if(!super.validaMove(xD,yD,isDay,idDestino,idTipoDestino)) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[0] - yD) > 2) || (Math.abs(super.getPosicao()[1] - xD) > 2)) {
            return false;
        }

        //Valida se o turno é noturno (Zumbi Vampiro só pode se movimentar de noite)
        if(isDay){
            return false;
        }

        //Valida se no destino tem uma Cabeça de Alho
        if(idTipoDestino == 5) {
            return false;
        }

        return true;
    }
}
