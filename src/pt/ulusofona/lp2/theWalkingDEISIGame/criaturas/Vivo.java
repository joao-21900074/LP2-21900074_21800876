package pt.ulusofona.lp2.theWalkingDEISIGame.criaturas;

import pt.ulusofona.lp2.theWalkingDEISIGame.*;
import pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos.*;

public class Vivo extends Creature {

    protected Equipamento equipamento;
    private String nomeTipo;
    private boolean safe = false;
    private boolean envenenado = false;
    private int protecaoVeneno = 3;
    private int nKills = 0;

    //Construtor vazio
    public Vivo() {
    }

    public Vivo(int id, int idTipo, String nome, int[] posicao) {
        super(id, idTipo, nome, posicao);
        nomeTipo = retornaNomeTipo(idTipo);
    }

    @Override
    public boolean validaMove(int xD, int yD, boolean isDay, int idDestino, int idTipoDestino) {
        /*
        Valida se um Vivo esta tentando se movimentar para
        um lugar onde tem outro vivo
        */
        if (idTipoDestino >= 5 && idTipoDestino <= 9 && idDestino > 0) {
            System.out.println("move() Vivo");
            return false;
        }

        //Antitodo no chão e não envenenado, da false
        if (idTipoDestino == 9 && idDestino < 0 && !envenenado) {
            return false;
        }

        return true;
    }

    @Override
    public int getEquipe() {
        return 10;
    }

    //Função chamada quando um vivo entrar no safe heaven
    public void salvar() {
        safe = true;
    }

    //Função usada para determinar o nomeTipo que vai ser usado no toString
    private String retornaNomeTipo(int idTipo) {
        switch (idTipo) {
            case 5:
                return "Criança (Vivo)";
            case 6:
                return "Adulto (Vivo)";
            case 7:
                return "Militar (Vivo)";
            case 8:
                return "Idoso (Vivo)";
            case 9:
                return "Cão";
            default:
                return "ERRO";
        }
    }

    public boolean equiparEquipamento(Equipamento equipamento) {
        if (equipamento == null) {
            this.equipamento = null;
            return false;
        }

        this.equipamento = equipamento;

        //Caso seja um veneno, humano ganha proteçao por 3 turnos + fica envenenado
        if (equipamento.getIdTipo() == 8) {
            Veneno v = (Veneno) equipamento;
            v.tomar();
            //Incrementa o numero de usos do veneno
            //ADICIONADO NA BATALHA
            //getEquipamento().addNrUsos();
            this.envenenado = true;
        }

        if (equipamento.getIdTipo() == 9) {
            assert equipamento instanceof Antidoto;
            Antidoto a = (Antidoto) equipamento;
            a.tomar();
            //Incrementa o numero de usos do Antidoto
            getEquipamento().addNrUsos();
            protecaoVeneno = 0;
            this.envenenado = false;
        }

        //Lembrar de implementar a situação onde o jogador tem um equipamento
        //derruba ele na posicao anterior e equipa o novo

        super.addNEquipamentos();
        return true;
    }

    public boolean temEquipamento() {
        return equipamento != null;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public int getIdEquipamento() {
        if (equipamento == null) {
            return 0;
        }
        return equipamento.getId();
    }

    public int getIdTipoEquipamento() {
        if (equipamento == null) {
            return 0;
        }
        return equipamento.getIdTipo();
    }

    public int protecao() {
        return protecaoVeneno;
    }

    public void danificaProtecao() {
        protecaoVeneno--;
    }

    public boolean estaEnvenenado() {
        return envenenado;
    }

    public boolean estaSalvo() {
        return safe;
    }

    public int getNKills() {
        return nKills;
    }

    public void addNKills() {
        nKills++;
    }

    @Override
    public String toString() {
        if (safe) {
            return id + " | " + nomeTipo + " | Os Vivos | " + nome + " " + getNEquipamentos() + " @ A salvo";

        }

        return id + " | " + nomeTipo + " | Os Vivos | " + nome + " " + getNEquipamentos() + " @ (" + super.getPosicao()[0] + ", " + super.getPosicao()[1] + ")";
    }
}