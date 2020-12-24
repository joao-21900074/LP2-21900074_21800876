package pt.ulusofona.lp2.theWalkingDEISIGame;

public class EscudoMadeira extends Equipamento {
    /*VER SITUAÇÃO DO MILITAR
    Defesa = 2
    > Usou 1 vez largou o escudo mantem defesa = 1
    > Usou 1 vez largou e pegou de volta mantem defesa = 1
    > Usou 1 vez largou outro pegou mantem defesa = 1

    BASICAMENTE o escudo de madeira só vai dar upgrade para Defesa 1 vez
     */

    //1 de defesa (GERAL)
    int defesa = 1;

    public EscudoMadeira(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    //Após utilizar ele quebra
    public void defender(){
        defesa = 0;
    }

    //Se um militar pegar ele da upgrade
    public void upgrade(){
        defesa = 2;
    }

    @Override
    public String toString() {
        return "Escudo de Madeira | " + defesa;
    }
}