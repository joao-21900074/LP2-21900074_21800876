package pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public class Antidoto extends Equipamento {
    /*Como funciona o Antidoto
    Cura se envenedado
    > Só pode pegar Humano envenedado
    > Consome insta
     */

    private int conteudo = 1;

    public Antidoto(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    //Só uma uma vez, só se tiver envenedado
    public void tomar(){
        this.conteudo = 0;
    }

    @Override
    public String toString() {
        return "Antidoto | " + conteudo;
    }
}