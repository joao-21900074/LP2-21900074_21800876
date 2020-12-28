package pt.ulusofona.lp2.theWalkingDEISIGame;

public class ZombieMilitar extends Zombie{

    public ZombieMilitar(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int currentTeam) {
        //Valida
        if(!super.validaMove(xD,yD,isDay,idDestino,currentTeam)) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(posicao[0] - yD) > 3) || (Math.abs(posicao[1] - xD) > 3)) {
            return false;
        }

        return true;
    }

}
