package pt.ulusofona.lp2.theWalkingDEISIGame;

public abstract class Equipamento {
    private int id;
    private int idTipo;
    int[] posicao = new int[2];

    public Equipamento() {}

    public Equipamento(int id, int idTipo, int[] posicao) {
        this.id = id;
        this.idTipo = idTipo;
        this.posicao = posicao;
    }

    public int getId() {
        return id;
    }

    public int getIdTipo() {
        return idTipo;
    }

    //Factory
    static Equipamento criarEquipamento(int id, int idTipo, int[]posicao){
        switch (idTipo){
            case 0:
                return new EscudoMadeira(id,idTipo, posicao);
            case 1:
                return new Espada(id, idTipo, posicao);
            case 2:
                return new Pistola(id, idTipo, posicao);
            case 3:
                return new EscudoTactico(id, idTipo, posicao);
            case 4:
                return new Revista(id, idTipo, posicao);
            case 5:
                return new Alho(id, idTipo, posicao);
            case 6:
                return new Estaca(id, idTipo, posicao);
            case 7:
                return new Lixivia(id, idTipo, posicao);
            case 8:
                return new Veneno(id, idTipo, posicao);
            case 9:
                return new Antidoto(id, idTipo, posicao);
            case 10:
                return new Beskar(id, idTipo, posicao);
            default:
                throw new IllegalArgumentException("Tipo de Equipamento desconhecido");
        }
    }

    abstract public String toString();

}