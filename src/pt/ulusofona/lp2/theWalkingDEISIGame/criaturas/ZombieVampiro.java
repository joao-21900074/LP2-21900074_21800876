package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

public class ZombieVampiro extends Zombie {

    private static int totalEquipDestruidos = 0;

    public ZombieVampiro(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "zombie_vampiro.png";
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
        if(!super.validaMove(xD,yD,isDay,idDestino,idTipoDestino)) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[1] - yD) > 2) || (Math.abs(super.getPosicao()[0] - xD) > 2)) {
            return false;
        }

        //Valida se o turno é noturno (Zumbi Vampiro só pode se movimentar de noite)
        if(isDay){
            return false;
        }

        //Valida se no destino tem uma Cabeça de Alho
        if(idTipoDestino == 5 && idDestino < 0) {
            return false;
        }

        return true;
    }
}
