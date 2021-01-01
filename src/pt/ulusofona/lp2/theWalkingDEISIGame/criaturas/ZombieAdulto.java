package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

public class ZombieAdulto extends Zombie {

    public ZombieAdulto(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino) {
        //Valida
        if(!super.validaMove(xD,yD,isDay,idDestino)) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[0] - yD) > 2) || (Math.abs(super.getPosicao()[1] - xD) > 2)) {
            return false;
        }

        return true;
    }

}
