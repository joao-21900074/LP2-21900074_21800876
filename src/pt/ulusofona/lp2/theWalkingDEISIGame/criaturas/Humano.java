package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

public abstract class Humano extends Vivo {
    public Humano(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "human.png";
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {
        if(!super.validaMove(xD,yD,isDay,idDestino,idTipoDestino)) {
            return false;
        }
        /*
        Valida se o Humano tem equipamento quando for para cima de
        um zumbi
        */
        if(idTipoDestino >= 0 && idTipoDestino <= 4 && idDestino > 0) { //Valida se tem Zumbi no Destino
            //Valida se o Humano esta sem equipamento
            if(equipamento == null) {
                return false;
            }
            /*
            Valida se tem o equipamento certo para enfrentar um
            Zumbi Vampiro
             */
            if(equipamento.getIdTipo() != -6 && idTipoDestino == 4) {
                System.out.println("move() Humano");
                return false;
            }
        }



        return true;
    }
}