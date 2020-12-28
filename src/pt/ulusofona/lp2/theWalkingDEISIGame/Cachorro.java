package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Cachorro extends Creature{

    public Cachorro(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int currentTeam) {
        //Verifica se o cachorro esta tentando andar para cima/baixo
        if(posicao[1] == xD && posicao[0] != yD) {
            return false;
        }

        //Verifica se o cachorro esta tentando andar para esquerda/direita
        if(posicao[1] != xD && posicao[0] == yD) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(posicao[0] - yD) > 2) || (Math.abs(posicao[1] - xD) > 2)) {
            return false;
        }

        return true;
    }
}
