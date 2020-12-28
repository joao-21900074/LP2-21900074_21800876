package pt.ulusofona.lp2.theWalkingDEISIGame;

public abstract class Creature {
    protected int id;
    protected int idTipo;
    protected String nome;
    protected String imagePng;
    int[] posicao = new int[2];

    public Creature(int id, int idTipo, String nome, int[] posicao) {
        this.id = id;
        this.idTipo = idTipo;
        this.nome = nome;
        this.posicao = posicao;
    }

    public int getId() {
        return id;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public String getNome() {
        return nome;
    }

    public String getImagePNG() {
        return imagePng;
    }

    public int[] getPosicao(){return posicao;}

    public void setPosicao(int[] newPosicao) {
        posicao = newPosicao;
    }

    //Factory
    static Creature criarCreature(int id, int idTipo, String nome, int[]posicao){
        switch (idTipo){
            case 0:
                return new ZombieCrianca(id, idTipo, nome, posicao);
            case 1:
                return new ZombieAdulto(id, idTipo, nome, posicao);
            case 2:
                //return new ZombieMilitar(id, idTipo, nome, posicao);
            case 3:
                return new ZombieIdoso(id, idTipo, nome, posicao);
            case 4:
                return new ZombieVampiro(id, idTipo, nome, posicao);
            case 5:
                return new HumanoCrianca(id, idTipo, nome, posicao);
            case 6:
                return new HumanoAdulto(id, idTipo, nome, posicao);
            case 7:
                //return new HumanoMilitar(id, idTipo, nome, posicao);
            case 8:
                return new HumanoIdoso(id, idTipo, nome, posicao);
            case 9:
                //return new Cao(id, idTipo, nome, posicao);
            case 10:
                //return new ZombieFilme(id, idTipo, nome, posicao);
            default:
                throw new IllegalArgumentException("Tipo de Criatura desconhecido");
        }
    }

    abstract public int getEquipe();

    @Override
    abstract public String toString();
}