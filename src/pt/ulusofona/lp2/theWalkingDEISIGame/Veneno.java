package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Veneno extends Equipamento{
    /*Como funciona o Veneno
    Protecao = 2 turnos (começa a contar depois q ele usa)
    > Se ficar envenendado por 3 turnos morre
    > Zombies não podem ir aonde tem veneno
     */

    //Da protecao de 2 turnos
    int protecaoTurnos = 2;
    int frasco = 1;

    public Veneno(int id, int idTipo, int[] posicao){
        super(id,idTipo,posicao);
    }

    //Só uma uma vez
    public void tomar(){
        frasco = 0;
    }

    @Override
    public String toString() {
        return "Veneno | " + frasco;
    }
}