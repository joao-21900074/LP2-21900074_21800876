package pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos;

import pt.ulusofona.lp2.theWalkingDEISIGame.Equipamento;

public class Veneno extends Equipamento {
    /*Como funciona o Veneno
    Protecao = 2 turnos (começa a contar depois q ele usa)
    > Se ficar envenendado por 3 turnos morre
    > Zombies não podem ir aonde tem veneno
     */

    //Da protecao de 2 turnos
    private int protecaoTurnos = 2;
    private boolean frasco = true;

    public Veneno(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    public boolean getFrasco(){
        return frasco;
    }

    public int getProtecaoTurnos(){
        return protecaoTurnos;
    }

    //Só uma vez
    public void tomar(){
        frasco = false;
    }

    //Proteger 2 turnos
    public void protecao(){
        protecaoTurnos--;
    }

    @Override
    public String toString() {
        return "Veneno | " + frasco;
    }
}