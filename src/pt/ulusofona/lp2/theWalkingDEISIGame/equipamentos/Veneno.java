package pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public class Veneno extends Equipamento {
    /*Como funciona o Veneno
    Protecao = 2 turnos (começa a contar depois q ele usa)
    > Se ficar envenendado por 3 turnos morre
    > Zombies não podem ir aonde tem veneno
     */

    private int conteudo = 1;

    public Veneno(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }


    //Só uma vez
    public void tomar(){
        this.conteudo = 0;
    }

    public int getConteudo(){
        return conteudo;
    }

    @Override
    public String toString() {
        return "Veneno | " + conteudo;
    }
}