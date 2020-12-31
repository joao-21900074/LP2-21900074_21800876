package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.*;

public class Cachorro extends Creature {
    int equipe = 10;
    Equipamento equipamento;
    int nEquipamentos;
    boolean safe = false;

    public Cachorro(int id, int idTipo, String nome, int[] posicao) {
        super(id,idTipo,nome,posicao);
    }

    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino) {
        //Verifica se o cachorro esta tentando andar para cima/baixo
        if(super.getPosicao()[1] == xD && super.getPosicao()[0] != yD) {
            return false;
        }

        //Verifica se o cachorro esta tentando andar para esquerda/direita
        if(super.getPosicao()[1] != xD && super.getPosicao()[0] == yD) {
            return false;
        }

        //Verifica movimento no range valido
        if((Math.abs(super.getPosicao()[0] - yD) > 2) || (Math.abs(super.getPosicao()[1] - xD) > 2)) {
            return false;
        }

        return true;
    }

    public boolean equiparEquipamento(Equipamento equipamento) {
        if(equipamento == null) {
            return false;
        }

        //Lembrar de implementar a situação onde o jogador tem um equipamento
        //derruba ele na posicao anterior e equipa o novo

        this.equipamento = equipamento;
        nEquipamentos++;
        return true;
    }

    public void salvar() {
        safe = true;
    }

    @Override
    public int getEquipe(){return equipe;}

    @Override
    public String toString() {
        if(safe) {
            return id + " | Cão | Os Vivos | " + nome + " " + nEquipamentos + " @ A salvo";
        }

        return id + " | Cão | Os Vivos | " + nome + " " + nEquipamentos + " @ (" + super.getPosicao()[0] + ", " + super.getPosicao()[1] + ")";
    }
}
