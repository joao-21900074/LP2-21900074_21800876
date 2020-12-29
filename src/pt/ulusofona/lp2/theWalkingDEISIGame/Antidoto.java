package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Antidoto extends Equipamento{
    /*Como funciona o Antidoto
    Cura se envenedado
    > Só pode pegar Humano envenedado
    > Consome insta
     */

    private boolean frasco = true;

    public Antidoto(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    public boolean getFrasco(){
        return frasco;
    }

    //Só uma uma vez, só se tiver envenedado
    public void tomar(){
        frasco = false;
    }

    @Override
    public String toString() {
        return "Antidoto | " + frasco;
    }
}