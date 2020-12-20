package pt.ulusofona.lp2.theWalkingDEISIGame;

public class ZombieVampiro extends Zombie{
    public ZombieVampiro(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int currentTeam) {
        //Valida
        if(!super.validaMove(xD,yD,isDay,idDestino,currentTeam)) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(posicao[0] - yD) > 2) || (Math.abs(posicao[1] - xD) > 2)) {
            return false;
        }

        //Valida se o turno é noturno
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
