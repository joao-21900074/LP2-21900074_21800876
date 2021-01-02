package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

public class HumanoAdulto extends Humano {

    //Construtor
    public HumanoAdulto(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {
        //Valida condições gerais de Humano
        if(!super.validaMove(xD, yD, isDay, idDestino, idTipoDestino)){
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[1] - yD) > 2) || (Math.abs(super.getPosicao()[0] - xD) > 2)) {
            System.out.println("move() Adulto");
            return false;
        }

        return true;
    }
}
