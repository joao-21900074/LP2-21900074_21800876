package pt.ulusofona.lp2.theWalkingDEISIGame;

public class HumanoIdoso extends Humano{
    public HumanoIdoso(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int currentTeam) {

        //Validações comum para todos os Zombies
        if(!super.validaMove(xD,yD,isDay,idDestino,currentTeam)) {
            return false;
        }

        //Valida diagonal
        if(posicao[1] != xD && posicao[0] != yD) {
            return false;
        }

        //Valida movimento esquerda/direita
        if(posicao[0] == yD) {
            if(Math.abs(posicao[1] - xD) > 1) {
                return false;
            }
        }

        //Valida movimento baixo/cima
        if(posicao[1] == xD) {
            if(Math.abs(posicao[0] - yD) > 1) {
                return false;
            }
        }

        //Valida se é noite (Humano idoso só pode se movimentar de noite)
        if(!isDay) {
            return false;
        }

        return true;
    }
}
