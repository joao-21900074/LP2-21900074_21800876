package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.*;

public class Cachorro extends Vivo {

    public Cachorro(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "dog.png";
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {

        if(!super.validaMove(xD,yD,isDay,idDestino,idTipoDestino)) {
            return false;
        }

        //Verifica se o cachorro esta tentando andar para cima/baixo
        if(super.getPosicao()[0] == xD && super.getPosicao()[1] != yD) {
            return false;
        }

        //Verifica se o cachorro esta tentando andar para esquerda/direita
        if(super.getPosicao()[0] != xD && super.getPosicao()[1] == yD) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[1] - yD) > 2) || (Math.abs(super.getPosicao()[0] - xD) > 2)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        if(super.estaSalvo()) {
            return id + " | Cão | Os Vivos | " + nome + " " + nEquipamentos + " @ A salvo";
        }

        return id + " | Cão | Os Vivos | " + nome + " " + nEquipamentos + " @ (" + super.getPosicao()[0] + ", " + super.getPosicao()[1] + ")";
    }
}




