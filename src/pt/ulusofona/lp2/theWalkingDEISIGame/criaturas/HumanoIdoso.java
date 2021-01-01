package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

public class HumanoIdoso extends Humano {
    public HumanoIdoso(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {

        //Validações comum para todos os Humanos
        if(!super.validaMove(xD,yD,isDay,idDestino, idTipoDestino)) {
            return false;
        }

        //Valida diagonal (Idoso não pode andar na diagonal)
        if(super.getPosicao()[0] != xD && super.getPosicao()[1] != yD) {
            return false;
        }

        //Valida movimento esquerda/direita
        if(super.getPosicao()[1] == yD) {
            if(Math.abs(super.getPosicao()[0] - xD) > 1) {
                return false;
            }
        }

        //Valida movimento baixo/cima
        if(super.getPosicao()[0] == xD) {
            if(Math.abs(super.getPosicao()[1] - yD) > 1) {
                return false;
            }
        }

        //Valida se é noite (Humano idoso só pode se movimentar de dia)
        if(!isDay) {
            System.out.println("to dormindo");
            return false;
        }

        return true;
    }
}
