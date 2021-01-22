package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

public class ZombieMilitar extends Zombie {

    public static int totalEquipDestruidos = 0;

    public ZombieMilitar(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    public void addTotalEquipDestruidos() {
        totalEquipDestruidos++;
    }

    public int getTotalEquipDestruidos() {
        return totalEquipDestruidos;
    }

    @Override
    public void resetTotalEquipDestruidos() {
        totalEquipDestruidos = 0;
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {
        //Valida
        System.out.println("Hello");
        if(!super.validaMove(xD,yD,isDay,idDestino,idTipoDestino)) {
            return false;
        }
        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[1] - yD) > 3) || (Math.abs(super.getPosicao()[0] - xD) > 3)) {
            return false;
        }

        return true;
    }

}
