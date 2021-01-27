package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

public class ZombieIdoso extends Zombie {

    public static int totalEquipDestruidos = 0;

    public ZombieIdoso(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
        this.imagePng = "zombie_idoso.png";
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

        //Validações comum para todos os Zombies
        if(!super.validaMove(xD,yD,isDay,idDestino,idTipoDestino)) {
            return false;
        }

        //Valida diagonal
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

        return true;
    }
}
