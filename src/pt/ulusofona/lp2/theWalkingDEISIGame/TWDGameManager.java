package pt.ulusofona.lp2.theWalkingDEISIGame;

import org.w3c.dom.ls.LSOutput;
import pt.ulusofona.lp2.theWalkingDEISIGame.criaturas.*;
import pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos.*;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TWDGameManager {
    private ArrayList<Creature> creatures = new ArrayList<>();
    ArrayList<Vivo> vivos = new ArrayList<>();
    ArrayList<Zombie> zombies = new ArrayList<>();
    private ArrayList<Equipamento> equipamentos = new ArrayList<>();
    private ArrayList<int[]> safeHavens = new ArrayList<>();
    private ArrayList<Integer> salvos = new ArrayList<>();
    private ArrayList<Creature> mortos = new ArrayList<>();
    private int initialTeam;
    private int currentTeam;
    private int[][] map;
    private int[] worldSize;
    private boolean isDay = true;
    private int tamanhoDiaNoite = 2;
    private int turnos = 0;
    private int turnosTerminados = 0;
    private HashMap<Integer,Integer> infoSave = new HashMap<>();

    //Construtor Vazio
    public TWDGameManager() {}

    //Leitura do Ficheiro, onde pretende-se iniciar o jogo
    //MUDAR O RETORNO PRA SER VOID
    public void startGame(File ficheiroInicial) throws InvalidTWDInitialFileException, FileNotFoundException{
        //Leitor para o ficheiro do jogo
        Scanner leitor = new Scanner(new FileReader(ficheiroInicial));
        String[] info;
        int nLinha = 0;

        //Reset de variaveis
        equipamentos.clear();
        vivos.clear();
        zombies.clear();
        creatures.clear();
        salvos.clear();
        mortos.clear();
        safeHavens.clear();

        //Reset váriaveis staticas dos Equipamentos
        resetVarsStatics();

        //While de leitura
        while(leitor.hasNextLine()) {
            info = leitor.nextLine().split(":");
            nLinha++;
            switch (nLinha) {

                //Criação do Mapa + worldSize
                case 1:
                    String[] cxl = info[0].split(" ");
                    int linhas = Integer.parseInt(cxl[0]);
                    int colunas = Integer.parseInt(cxl[1]);
                    map = new int[colunas][linhas];
                    worldSize = new int[]{linhas, colunas};
                    break;

                //Equipe Inicial
                case 2:
                    initialTeam = Integer.parseInt(info[0]);
                    currentTeam = initialTeam;
                    break;

                //Quantidade de Criaturas
                case 3:
                    int nCriaturas = Integer.parseInt(info[0]);
                    int hold = nCriaturas;

                    //Criação das Criaturas
                    while (nCriaturas != 0) {
                        String linhaCriatura = leitor.nextLine();
                        info = linhaCriatura.split(" : ");

                        //Necssário pelo menos 2 criaturas para jogar o jogo
                        if(hold < 2){
                            throw new InvalidTWDInitialFileException(hold, info.length, linhaCriatura);
                        }

                        //Dados das criaturas devem estar completos
                        if(info.length != 5){
                            throw new InvalidTWDInitialFileException(hold, info.length, linhaCriatura);
                        }

                        int id = Integer.parseInt(info[0]);
                        int idTipo = Integer.parseInt(info[1]);
                        String nome = info[2];
                        int posicaoX = Integer.parseInt(info[3]);
                        int posicaoY = Integer.parseInt(info[4]);

                        //Usando o Factory do Creature para criar objetos
                        Creature c = Creature.criarCreature(id, idTipo, nome, new int[]{posicaoX,posicaoY});

                        creatures.add(c);
                        map[posicaoX][posicaoY] = id;

                        if(idTipo > 4 && idTipo < 10){
                            vivos.add((Vivo) c);
                        }else if(idTipo <= 4){
                            zombies.add((Zombie) c);
                        }

                        nCriaturas--;
                    }
                    break;

                //Quantidade de Equipamentos
                case 4:
                    int nEquimamentos = Integer.parseInt(info[0]);

                    //Criação dos Equipamentos
                    while (nEquimamentos != 0) {
                        info = leitor.nextLine().split(" : ");
                        int id = Integer.parseInt(info[0]);
                        int idTipo = Integer.parseInt(info[1]);
                        int posicaoX = Integer.parseInt(info[2]);
                        int posicaoY = Integer.parseInt(info[3]);

                        //Usando o Factory do Equipamento para criar objetos
                        Equipamento e = Equipamento.criarEquipamento(id, idTipo, new int[]{posicaoX,posicaoY});

                        equipamentos.add(e);
                        map[posicaoX][posicaoY] = id;

                        nEquimamentos--;
                    }
                    break;

                //Quantidade de Safe Havens
                case 5:
                    int nSafeHaven = Integer.parseInt(info[0]);

                    while (nSafeHaven != 0) {
                        info = leitor.nextLine().split(" : ");
                        int posicaoX = Integer.parseInt(info[0]);
                        int posicaoY = Integer.parseInt(info[1]);

                        safeHavens.add(new int[]{posicaoX,posicaoY});
                        map[posicaoX][posicaoY] = 0;

                        nSafeHaven--;
                    }
                    break;
            }
        }

    }

    //Tamanho do mapa (PRONTO)
    public int[] getWorldSize() {
        return worldSize;
    }

    //Equipe inicial (PRONTO)
    public int getInitialTeam() {
        return initialTeam;
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        boolean fight = false;
        int peca; //Peça que o jogador vai mover
        int destino; //Lugar,Equipamento ou inimigo para onde o jogador vai se mover
        Creature creature; //Criatura que vai se movimentada

        //Valida se o destino esta fora do mapa
        if(xD < 0 || xD > worldSize[1] || yD < 0 || yD > worldSize[0]){
            return false;
        }

        //Define o valor de peca e destino (não pode ser feito antes pq pode dar problemas)
        peca = getElementId(xO,yO);
        destino = getElementId(xD,yD);

        //Valida o time que joga
        if(!validaTime(peca,currentTeam)) {
            return false;
        }

        //Define o valor de criatura que vai ser movimentada
        creature = getCreatureById(peca);

        //Verifica o que tem no destino e faz uma validação certa
        if(getCreatureById(destino) != null) {
            Creature creatureDestino = getCreatureById(destino);
            if(!creature.validaMove(xD,yD,isDay,creatureDestino.getId(), creatureDestino.getIdTipo())) {
                return false;
            }
        } else if(getEquipmentById(destino) != null) { //Verifica se tem um equipamento no destino
            Equipamento equipDestino = getEquipmentById(destino); //Guarda o equipamento do destino
            if(equipDestino.getIdTipo() == 8 && creature.getEquipe() == 20){
                //Valida se tem veneno no chão
                Veneno v = (Veneno) equipDestino;
                if(!(v.getConteudo() == 0)){
                    return false;
                }
            }
            if(!creature.validaMove(xD,yD,isDay,equipDestino.getId(), equipDestino.getIdTipo())) {
                return false;
            }
            if(creature.getEquipe() == 20) {
                destruirEquipamentoNoDestino(destino);
                getZombieById(creature.getId()).addTotalEquipDestruidos();
            }
        }

        //Verifica se a peça esta pulando sobre outra peça ou equipamento
        if(!validaPuloCriatura(xO,yO,xD,yD)) {
            return false;
        }

        //Situação para destino vazio/safe
        if(destino == 0){
            //Valida é uma Safe
            if(isDoorToSafeHaven(xD,yD)){
                //Valida se é um Vivo indo para Safe
                if(getCreatureById(peca).getEquipe() == 10){
                    //Deixa o icone na porta e some com o humano adicionado ele aos "Salvos"
                    map[xD][yD] = 0;
                    salvos.add(peca);
                    getHumanoById(peca).salvar();
                    //Tirando dos humanos
                    //vivos.removeIf(v -> v == getHumanoById(peca));
                //Invalida caso "Outro" foi para a safe
                } else {
                    return false;
                }
            }else{
                if(!creature.validaMove(xD,yD,isDay,0,-1)){
                    System.out.println("move() TWD");
                    return false;
                }
            }
        } else if(destino > 0) {
            //Confirmando que temos 2 criaturas lutando
            if(getCreatureById(destino) != null) {
                Creature creatureDestino = getCreatureById(destino);
                //Caso especifico criança
                if(creature.getIdTipo() == 5){
                    Vivo crianca = getHumanoById(creature.getId());
                    if(crianca.getEquipamento().getIdTipo() == 1 && creatureDestino.getIdTipo() != 0) {
                        return false;
                    }
                }
                if(creature.getEquipe() == 10 && creatureDestino.getEquipe() == 20){
                    Humano h = (Humano) getHumanoById(creature.getId());
                    batalha(h,getZombieById(creatureDestino.getId()));
                    //Humano pega posição do zombie, quando equipamento ofensivo
                    if(vivos.contains(h) && !h.getEquipamento().isDefensivo()){
                        h.setPosicao(new int[]{xD, yD});
                        map[xD][yD] = peca;
                        map[xO][yO] = 0;
                    }
                }else if(creature.getEquipe() == 20 && creatureDestino.getEquipe() == 10){
                    Humano h = (Humano) getHumanoById(creatureDestino.getId());
                    batalha(h, getZombieById(creature.getId()));
                    //quando zombie, vai pra cima de humano(ofensivo) ele some
                }
                fight = true;
            }
        }

        //Muda a posição da criatura
        if(creatures.contains(creature) && !fight) {
            creature.setPosicao(new int[]{xD, yD});
            if (!(isDoorToSafeHaven(xD, yD))) {
                map[xD][yD] = peca;
                map[xO][yO] = 0;
            }
        }

        if(!fight) {
            map[xO][yO] = 0;
        }

        //Humano Equipar
        if(destino < 0 && creature.getEquipe() == 10){
            Vivo v = (Vivo) getCreatureById(peca);
            if (v.getEquipamento() == null) {
                v.equiparEquipamento(getEquipmentById(destino));
            } else {
                map[xO][yO] = v.getEquipamento().getId();
                v.equiparEquipamento(getEquipmentById(destino));
            }
        }else if(destino == 0 && creature.getIdTipo() == 8){
            Vivo v = (Vivo) getCreatureById(peca);
            map[xO][yO] = v.getEquipamento().getId();
            v.equiparEquipamento(null);
        }

        //Muda o time depois da jogada
        currentTeam = (currentTeam == 10) ? 20 : 10;
        //verifica a váriavel que vai definir a mudança de dia ou noite
        if(tamanhoDiaNoite != 0) {
            tamanhoDiaNoite--;
            if(tamanhoDiaNoite == 0) {//Muda o isDay depois de dois turnos
                tamanhoDiaNoite = 2; //Reseta a váriavel
                isDay = !isDay; //Inverte o valor de isDay
            }
        }

        for(Vivo v : vivos){
            if(v.estaEnvenenado()) {
                v.danificaProtecao();
            }
            if (v.protecao() == 0 && v.estaEnvenenado()) {
                map[v.getPosicao()[0]][v.getPosicao()[1]] = 0;
                morreu(v);
                mortos.add(v);
            }
        }

        turnos++; //Essa variavel é zerada toda vez que alguém foi transformado
        turnosTerminados++; //Essa varival representa o numero de turnos terminados

        //printaArrays();

        return true;
    }

    private boolean validaPuloCriatura(int xO, int yO, int xD, int yD) {
        int intervalo = 0;
        if(xO == xD && Math.abs(yO - yD) >= 2) {//Pulo cima/baixo
            intervalo = Math.abs(yO - yD);
            if(yO > yD) {
                for(int i = 1; i < intervalo; i++) {
                    if(getElementId(xD, yD + i) != 0) {
                        return false;
                    }
                }
            } else if(yO < yD) {
                for(int i = 1; i < intervalo; i++) {
                    if(getElementId(xD, yD - i) != 0) {
                        return false;
                    }
                }
            }
        } else if(yO == yD && Math.abs(xO - xD) >= 2) {//Movimento esquerda direita
            intervalo = Math.abs(xO - xD);
            if(xO > xD) {
                for(int i = 1; i < intervalo; i++) {
                    if(getElementId(xD + i, yD) != 0) {
                        return false;
                    }
                }
            } else if(xO < xD) {
                for(int i = 1; i < intervalo; i++) {
                    if(getElementId(xD - 1, yD) != 0) {
                        return false;
                    }
                }
            }
        } else if(xD != xO && yD != yO && Math.abs(xO - xD) >= 2 && Math.abs(yO - yD) >= 2) {//Movimento Diagonal
            intervalo = Math.abs(xO - xD);
            if(xO > xD && yO < yD) {//Diagonal inferior esquerda
                for(int i = 1; i < intervalo; i++) {
                    if(getElementId(xD + i, yD - i) != 0) {
                        return false;
                    };
                }
            }

            if(xO < xD && yO < yD) {//Diagonal inferior direita
                for(int i = 1; i < intervalo; i++) {
                    if(getElementId(xD - i, yD - i) != 0) {
                        return false;
                    }
                }
            }

            if(xO > xD && yO > yD) {//Diagonal superior esquerda
                for(int i = 1; i < intervalo; i++) {
                    if(getElementId(xD + i, yD + i) != 0) {
                        return false;
                    }
                }
            }

            if(xO < xD && yO > yD) {//Diagonal sueperior direita
                for(int i = 1; i < intervalo; i++) {
                    if(getElementId(xD - i, yD + i) != 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    //Função para luta entre Humano e Zumbi
    public void batalha(Humano humano, Zombie zombie){
        if(!humano.temEquipamento()){
            morreu(humano);
            zombie.addNTranformacoes();
        }else{
            if(!(humano.protecao() > 0)){
                morreu(humano);
            }
            switch(humano.getIdTipoEquipamento()){
                case 0:
                    //Escudo de Madeira
                    EscudoMadeira escudoMadeira = (EscudoMadeira) humano.getEquipamento();
                    //Militar aumenta a defesa do Escudo para 2
                    if(humano.getIdTipo() == 7){
                        escudoMadeira.upgrade();
                    }
                    if(escudoMadeira.getDefesa() > 0){
                        //Defendeu + Quebrar escudo ou Diminiuir 1 defesa(CASO MILITAR)
                        escudoMadeira.defender();
                        //Incrementa o número total de usos da classe "Escudo de Madeira"
                        escudoMadeira.addNrUsos();
                        if(escudoMadeira.getDefesa() <= 0) {//Destroe escudo quando as defesas acabarem
                            //Escudo é destruido
                            humano.getEquipamento().destruirEquipamento();
                            humano.equiparEquipamento(null);
                        }
                    } else {//Humano perde porque o escudo quebra (Acho que ele nunca entra nesse if)
                        //Escudo é destruido porque humano é transformado
                        humano.getEquipamento().destruirEquipamento();
                        morreu(humano);
                        zombie.addNTranformacoes();
                    }
                    break;

                case 1:
                    //Espada, matam zombies
                    //Caso especifico criança só mata criança
                    if(humano.getIdTipo() == 5 && !(zombie.getIdTipo() == 0)){
                        //Espada é destruida porque o humano é transformado
                        humano.getEquipamento().destruirEquipamento();
                        morreu(humano);
                        zombie.addNTranformacoes();
                        zombie.addTotalEquipDestruidos();
                    } else {
                        matou(zombie);
                        humano.addNKills();
                        humano.getEquipamento().addNrUsos();
                    }
                    break;

                case 2:
                    //Pistola
                    Pistola pistola = (Pistola) humano.getEquipamento();
                    if(pistola.getBalas() > 0) {//Verifica se a pistola ainda tem munição
                        //Não funciona contra ZombieVampiro
                        if(!(zombie.getIdTipo() == 4)) {
                            pistola.atirar();
                            //Incrementa o número total de usos da classe "Pistola"
                            pistola.addNrUsos();
                            matou(zombie);
                            humano.addNKills();
                            if(pistola.getBalas() <= 0) {//Destroe pistola quando acaba bala
                                //Destroe a pistola porque acabaram as balas
                                humano.getEquipamento().destruirEquipamento();
                                humano.equiparEquipamento(null);
                            }
                        }else {//Humano perde porque a pistola não funciona contra ZumbiVampiro
                            //Destroe a Pistola porque o humano morrreu
                            humano.getEquipamento().destruirEquipamento();
                            morreu(humano);
                            zombie.addTotalEquipDestruidos();
                            zombie.addNTranformacoes();
                        }
                    } else {//Humano perde porque não tinha mais munição na pistola
                        morreu(humano);
                        zombie.addNTranformacoes();
                    }
                    break;

                case 3:
                    humano.getEquipamento().addNrUsos();
                    break;

                case 8:
                    //Veneno
                    //Escudo Táctio, só defende então não mata nem morre
                    humano.getEquipamento().addNrUsos();
                    break;

                case 4:
                    //Revista Maria, só defende contra ZombieIdoso
                    if(!(zombie.getIdTipo() == 3)){
                        //Destroe a Revista Maria que o Humano esta segurando
                        humano.getEquipamento().destruirEquipamento();
                        morreu(humano);
                        zombie.addNTranformacoes();
                        zombie.addTotalEquipDestruidos();
                    } else {
                        humano.getEquipamento().addNrUsos();
                    }
                    break;

                case 5:
                    //Cabeça de Alho, só defende contra ZombieVampiro
                    if(!(zombie.getIdTipo() == 4)){
                        //Destroe o Alho que o Humano esta segurando
                        humano.getEquipamento().destruirEquipamento();
                        morreu(humano);
                        zombie.addNTranformacoes();
                        zombie.addTotalEquipDestruidos();
                    } else {
                        humano.getEquipamento().addNrUsos();
                    }
                    break;

                case 6:
                case 10:
                    //Estaca de Madeira + Beskar Helmet, matam zombies
                    matou(zombie);
                    humano.addNKills();
                    humano.getEquipamento().addNrUsos();
                    break;

                case 7:
                    //Garrafa de Lixivia
                    Lixivia lixivia = (Lixivia) humano.getEquipamento();
                    if(lixivia.getLitros() > 0) {//Verifica se a Lixivia ainda tem carga
                        lixivia.usar();
                        //Incrementa o número de usos
                        lixivia.addNrUsos();
                        if(lixivia.getLitros() <= 0) {//Destroe lixivia quando acaba a carga
                            humano.getEquipamento().destruirEquipamento();
                            humano.equiparEquipamento(null);
                        }
                    } else {//Humano é transformado se a Lixivia n tiver mais carga
                        morreu(humano);
                        zombie.addNTranformacoes();
                    }
                    break;

                case 9:
                    //Antidoto
                    //Destroe o Antidoto que o Humano esta segurando
                    humano.getEquipamento().destruirEquipamento();
                    morreu(humano);
                    zombie.addNTranformacoes();
                    break;

                default:
                    break;
            }
        }
    }

    //Auxiliares para batalha
    //Muda o id do Humano para id do Zombie
    public int transformar(int idHumano){
        turnos = 0;
        switch (idHumano){
            case 5:
                return 0;
            case 6:
                return 1;
            case 7:
                return 2;
            case 8:
                return 3;
        }
        return -1;
    }

    public void morreu(Vivo vivo){
        if(!vivo.estaEnvenenado()){
            //Humano vira Zombie + botar na lsita dos zombies
            Creature novoZombie = Creature.criarCreature(vivo.getId(),transformar(vivo.getIdTipo()),
                    vivo.getNome(),vivo.getPosicao());
            zombies.add((Zombie) novoZombie);
            creatures.removeIf(c -> c == vivo);
            creatures.add(novoZombie);
        }
        //Humano morreu + tirar da lista dos humanos
        vivos.removeIf(v -> v == vivo);
    }

    public void matou(Zombie zombie){
        map[zombie.getPosicao()[0]][zombie.getPosicao()[1]] = 0;
        zombie.die();
        zombies.removeIf(z -> z == zombie);
        mortos.add(zombie);
    }

    //Alterado para usar apenas a Lista das Criaturas
    public boolean validaTime(int id, int currentTeam) {
        for(Creature c : creatures){
            if(c.getId() == id){
                return c.getEquipe() == currentTeam;
            }
        }
        return false;
    }

    // pronto (acho que vai ter mais condições nas proximas entregas
    public boolean gameIsOver() {
        if(turnos > 12 || vivos. size() == 0) {
            return true;
        }
        int nSalvos = 0;

        for(Vivo v : vivos) {
            if(!v.estaSalvo()) {
                nSalvos++;
            }
        }

        if(nSalvos <= 0) {
            return true;
        }

        return false;
    }

    // pronto
    public List<String> getAuthors() {
        ArrayList<String> creditos = new ArrayList<>();
        creditos.add("João Barreiros");
        creditos.add("Matheus Barcelos");
        return creditos;
    }

    // pronto
    public int getCurrentTeamId() {
        return currentTeam;
    }

    // pronto
    public int getElementId(int x, int y) {
        return map[x][y];
    }

    // pronto
    public boolean isDay() {
        return isDay;
    }

    // pronto
    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        for(Vivo v : vivos) {
            if(v.getEquipamento() == null){
                continue;
            }
            if(v.getId() == creatureId && v.getEquipamento().getIdTipo() == equipmentTypeId) {
                return true;
            }
        }
        return false;
    }

    public Vivo getHumanoById(int id) {
        for(Vivo v : vivos) {
            if(v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    public Zombie getZombieById(int id) {
        for(Zombie z : zombies) {
            if(z.getId() == id) {
                return z;
            }
        }
        return null;
    }

    public Creature getCreatureById(int id){
        for(Creature c : creatures) {
            if(c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public Equipamento getEquipmentById(int id){
        for(Equipamento e : equipamentos) {
            if(e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    public void destruirEquipamentoNoDestino(int idEquipamento) {
        getEquipmentById(idEquipamento).destruirEquipamento();
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    //ID do equipamento que o vivo carrega (Considera humanos e câes)
    public int getEquipmentId(int creatureId) {
        for(Vivo v : vivos){
            if(v.getId() == creatureId){
                return v.getIdEquipamento();
            }
        }
        return 0;
    }

    //Lista com os ID's dos vivos salvos
    public List<Integer> getIdsInSafeHaven() {
        return salvos;
    }

    //True caso a safe esteja na lista de safeHavens
    public boolean isDoorToSafeHaven(int x, int y) {
        int[] check = new int[]{x,y};
        for(int[] lugar : safeHavens){
            if(Arrays.equals(lugar, check)){
                return true;
            }
        }
        return false;
    }

    //Tipo do Equipamento usando o id
    public int getEquipmentTypeId(int equipmentId) {
        Equipamento e = getEquipmentById(equipmentId);
        return e.getIdTipo();
    }

    //Info do Equipamento usando o id
    public String getEquipmentInfo(int equipmentId) {
        Equipamento e = getEquipmentById(equipmentId);
        return e.toString();
    }

    //Funções auxiliares saveGame
    public String getEquipmentAtributes(int equipId){
        Equipamento e = getEquipmentById(equipId);
        return e.getId() + " : " + e.getIdTipo() + " : " + e.getPosicao()[0] +
                " : "  + e.getPosicao()[1];
    }

    public String getCreatureAtributes(int creatureId){
        Creature c = getCreatureById(creatureId);
        return c.getId() + " : " + c.getIdTipo() + " : " +
                c.getNome() + " : " + c.getPosicao()[0] + " : "  + c.getPosicao()[1];
    }

    public String getAllCreaturesAtributes(){
        StringBuilder allAtributes = new StringBuilder();
        for(int i=0; i < creatures.size(); i++){
            allAtributes.append(getCreatureAtributes(creatures.get(i).getId())).append("\n");
            if(creatures.get(i).getEquipe() == 20){
                Zombie z = (Zombie) creatures.get(i);
                infoSave.put(z.getId(),z.getNItensDestruidos());
            }
        }
        return allAtributes.toString();
    }

    public String getAllEquipmentAtributes() {
        StringBuilder allAtributes = new StringBuilder();
        for(int i=0; i < equipamentos.size(); i++) {
            allAtributes.append(getEquipmentAtributes(equipamentos.get(i).getId())).append("\n");
        }

        return allAtributes.toString();
    }

    public String getSafePosicao() {
        StringBuilder retorno = new StringBuilder();
        for(int[] s : safeHavens) {
            retorno.append(s[0]).append(" : ").append(s[1]).append("\n");
        }
        return retorno.toString();
    }

    public boolean saveGame(File fich) {
        try {
            FileWriter escrita = new FileWriter(fich);
            escrita.write(getWorldSize()[0] + " " + getWorldSize()[1] + "\n");
            escrita.write(currentTeam + "\n");
            escrita.write(creatures.size() + "\n");
            escrita.write(getAllCreaturesAtributes());
            escrita.write(equipamentos.stream().filter((e) -> !e.getDestruido()).count() + "\n");
            //escrita.write(getAllEquipmentAtributes());

            StringBuilder allEquipaments = new StringBuilder();
            equipamentos.stream().filter((e) -> !e.getDestruido())
                    .map((e) -> e.getId() + " : " + e.getIdTipo() + " : " + e.getPosicao()[0] +
                            " : "  + e.getPosicao()[1] + "\n")
                    .forEach(allEquipaments::append);

            escrita.write(allEquipaments.toString());

            escrita.write(safeHavens.size() + "\n");
            escrita.write(getSafePosicao().trim());

            escrita.close();
            return true;
        } catch (IOException e) {
            System.out.println("Falha no save");
        }
        return false;
    }

    public boolean loadGame(File fich) {
        try {
            //Reset de variaveis
            equipamentos.clear();
            vivos.clear();
            zombies.clear();
            creatures.clear();
            salvos.clear();
            mortos.clear();
            safeHavens.clear();

            //Leitor para o ficheiro do jogo
            Scanner leitor = new Scanner(new FileReader(fich));
            String[] info;
            int nLinha = 0;

            //While de leitura
            while(leitor.hasNextLine()) {
                info = leitor.nextLine().split(":");
                nLinha++;
                switch (nLinha) {

                    //Criação do Mapa + worldSize
                    case 1:
                        String[] cxl = info[0].split(" ");
                        int linhas = Integer.parseInt(cxl[0]);
                        int colunas = Integer.parseInt(cxl[1]);
                        map = new int[colunas][linhas];
                        worldSize = new int[]{linhas, colunas};
                        break;

                    //Equipe Inicial
                    case 2:
                        initialTeam = Integer.parseInt(info[0]);
                        currentTeam = initialTeam;
                        break;

                    //Quantidade de Criaturas
                    case 3:
                        int nCriaturas = Integer.parseInt(info[0]);

                        //Criação das Criaturas
                        while (nCriaturas != 0) {
                            info = leitor.nextLine().split(" : ");
                            int id = Integer.parseInt(info[0]);
                            int idTipo = Integer.parseInt(info[1]);
                            String nome = info[2];
                            int posicaoX = Integer.parseInt(info[3]);
                            int posicaoY = Integer.parseInt(info[4]);

                            //Usando o Factory do Creature para criar objetos
                            Creature c = Creature.criarCreature(id, idTipo, nome, new int[]{posicaoX,posicaoY});

                            creatures.add(c);
                            map[posicaoX][posicaoY] = id;


                            if(idTipo > 4 && idTipo < 10){
                                vivos.add((Vivo) c);
                            }else if(idTipo <= 4){
                                zombies.add((Zombie) c);
                                if(infoSave.get(c.id) != null) {
                                    c.setNItensDestruidos(infoSave.get(c.id));
                                } else {
                                    c.setNItensDestruidos(0);
                                }
                            }

                            nCriaturas--;
                        }
                        break;

                    //Quantidade de Equipamentos
                    case 4:
                        int nEquimamentos = Integer.parseInt(info[0]);

                        //Criação dos Equipamentos
                        while (nEquimamentos != 0) {
                            info = leitor.nextLine().split(" : ");
                            int id = Integer.parseInt(info[0]);
                            int idTipo = Integer.parseInt(info[1]);
                            int posicaoX = Integer.parseInt(info[2]);
                            int posicaoY = Integer.parseInt(info[3]);

                            //Usando o Factory do Equipamento para criar objetos
                            Equipamento e = Equipamento.criarEquipamento(id, idTipo, new int[]{posicaoX,posicaoY});

                            equipamentos.add(e);
                            //Equip só aparece se não tiver nenhuma criatura no lugar
                            if(map[posicaoX][posicaoY] == 0) {
                                map[posicaoX][posicaoY] = id;
                            }else if (map[posicaoX][posicaoY] > 0){
                                //Tem alguem ja nesse lugar, então equipa
                                Creature c = getCreatureById(map[posicaoX][posicaoY]);
                                if(c.getEquipe() == 10){
                                    Vivo v = (Vivo) c;
                                    v.equiparEquipamento(e);
                                }
                            }

                            nEquimamentos--;
                        }
                        break;

                    //Quantidade de Safe Havens
                    case 5:
                        int nSafeHaven = Integer.parseInt(info[0]);

                        while (nSafeHaven != 0) {
                            info = leitor.nextLine().split(" : ");
                            int posicaoX = Integer.parseInt(info[0]);
                            int posicaoY = Integer.parseInt(info[1]);

                            safeHavens.add(new int[]{posicaoX,posicaoY});
                            map[posicaoX][posicaoY] = 0;

                            nSafeHaven--;
                        }
                        break;
                }
            }

            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado");
        }
        return false;
    }

    //ainda faltam algumas repostas
    public String[] popCultureExtravaganza() {
        String[] retorno = new String[14];

        // Em que filme sobre Zombies entra a personagem “Alice” do nosso ficheiro exemplo ?
        retorno[0] = "Resident Evil";
        //Em que filme aparecia o “Ash” ?
        retorno[1] = "Evil Dead";
        //E o "Sam" ?
        retorno[2] = "I Am Legend";
        //Em que filme era usada a líxivia como repelente contra criaturas estranhas?
        retorno[3] = "";
        //Em que série animada nos inspirámos para a técnica especial do Militar quando empunha um
        //escudo de madeira?
        retorno[4] = "";
        //Em que filme foi usada um veneno/doença (e respectivo antídoto) como forma de repelir Zombies?
        retorno[5] = "World War Z";
        //Qual o nome do grupo de nómadas que usavam o “ Beskar helmet”?
        retorno[6] = "Mandaloarian";
        //Considere o actor que aparece com ID=2 no ficheiro de exemplo. Qual o
        // ano em que esse actor contracenou pela primeira vez com o Bruce Lee?
        retorno[7] = "1972";
        //Em que filme aparece a Espada Hattori Hanzo?
        retorno[8] = "Kill Bill";
        //Em que ano foi pela primeira vez publicada a Revista Maria?
        retorno[9] = "1978";
        //Qual o nome do personagem britânico que usava uma pistola Walther PPK?
        retorno[10] = "James Bond";
        //“Os Outros” é uma referência à melhor série de televisão de sempre. Que série é essa?
        retorno[11] = "Lost";
        //Qual é a expressão tuga que muitas vezes acompanha a “Cabeça de alho”?
        retorno[12] = "Cabeça de alho choco";
        //Qual é o verdadeiro nome do Freddie M. ?
        retorno[13] = "Farrokh Bulsara";

        return retorno;
    }

    //Pronto
    public List<String> getGameResults(){
        ArrayList<String> resultado = new ArrayList<>();

        resultado.add("Nr. de turnos terminados:");
        resultado.add("" + turnosTerminados);
        resultado.add("");
        resultado.add("Ainda pelo bairo:");
        resultado.add("");
        resultado.add("OS VIVOS");
        for(Vivo v : vivos) {
            if(!v.estaSalvo()) {
                resultado.add(v.getId() + " " + v.getNome());
            }
        }
        resultado.add("");
        resultado.add("OS OUTROS");
        for(Zombie z : zombies) {
            resultado.add(z.getId() + " (antigamente conhecido como " + z.getNome() + ")");
        }
        resultado.add("");
        resultado.add("Num safe haven:");
        resultado.add("");
        resultado.add("OS VIVOS");
        for(Vivo v : vivos) {
            if(v.estaSalvo()) {
                resultado.add(v.getId() + " " + v.getNome());
            }
        }
        resultado.add("");
        resultado.add("Envenenados / Destruidos");
        resultado.add("");
        resultado.add("OS VIVOS");
        for(Creature m : mortos) {
            switch(m.getIdTipo()) {
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    resultado.add(m.getId() + " " + m.getNome());
                    break;
            }
        }
        resultado.add("");
        resultado.add("OS OUTROS");
        for(Creature m : mortos) {
            switch (m.getIdTipo()) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    resultado.add(m.getId() + " (antigamente conhecido como " + m.getNome() + ")");
            }
        }

        return resultado;
    }

    public Map<String, List<String>> getGameStatistics() {
        Map<String, List<String>> retorno = new HashMap<String, List<String>>();

        //Quais os 3 zombies que mais Vivos transformaram ?
        retorno.put("os3ZombiesMaisTramados",
                zombies.stream()
                        .filter((z) -> z.getnTransformacoes() > 0)
                        .filter((z) -> !z.isDead())
                        .sorted(Comparator.comparingInt(Zombie::getnTransformacoes))
                        .limit(3)
                        .map((z) -> z.getId() + ":" + z.getNome() + ":" + z.getnTransformacoes())
                        .collect(Collectors.toList()));

        //Quais os 3 vivos que mais zombies destruiram ?
        retorno.put("os3VivosMaisDuros",
                vivos.stream()
                        .filter((v) -> v.getNKills() > 0)
                        .filter((v) -> !v.estaSalvo())
                        .sorted(Comparator.comparingInt(Vivo::getNKills).reversed())
                        .limit(3)
                        .map((v) -> v.getId() + ":" + v.getNome() + ":" + v.getNKills())
                        .collect(Collectors.toList()));

        //Quais os equipamentos que mais safaram os Vivos ?
        //Devem ser considerados quer os esquipamentos ofensivos
        //quer os defensivos.
        retorno.put("tiposDeEquipamentoMaisUteis",
                equipamentos.stream()
                            .filter((e) -> e.getNrUsos() > 0)
                            .sorted(Comparator.comparingInt(Equipamento::getNrUsos))
                            .map((e) -> e.getIdTipo() + " " + e.getNrUsos())
                            .distinct()
                            .limit(5)
                            .collect(Collectors.toList()));

        //Qual o total de equipamentos destruido por cada tipo
        //de Zombie ?


        retorno.put("tiposDeZombieESeusEquipamentosDestruidos",
                zombies.stream()
                        .filter((z) -> !z.isDead())
                        .sorted(Comparator.comparingInt(Zombie::getTotalEquipDestruidos).reversed())
                        .map((z) -> z.retornaNomeTipoSimples(z.getIdTipo()) + ":" +
                        (int) zombies.stream().filter((i) -> i.getIdTipo() == z.getIdTipo()).count() + ":" +
                        z.getTotalEquipDestruidos())
                        .distinct().collect(Collectors.toList()));

        //Quais as 5 criaturas que mais equipamentos
        //apanharam/destruiram que ainda estão em jogo
        //(isto é não foram destruidas nem foram para
        //o safe haven)?

        List<Creature> auxCreature = zombies.stream().filter((z) -> !z.isDead()).collect(Collectors.toList());

        auxCreature.addAll(vivos.stream().filter((v) -> !v.estaSalvo()).collect(Collectors.toList()));

        retorno.put("criaturasMaisEquipadas",
                auxCreature.stream()
                        .sorted(Comparator.comparingInt(Creature::getScore).reversed())
                        .map((a) -> a.getId() + ":" + a.getNome() + ":" + a.getScore())
                        .limit(5)
                        .collect(Collectors.toList()));

        return retorno;
    }

    public void printaArrays() {
        Map<String, List<String>> map = getGameStatistics();

        System.out.println("\nEquipamentos");
        equipamentos.stream().filter((e) -> !e.getDestruido()).forEach(System.out::println);

        //System.out.println("\nos3ZombiesMaisTramados");
        //System.out.println(map.get("os3ZombiesMaisTramados").size());
        //map.get("os3ZombiesMaisTramados").forEach(System.out::println);

        //System.out.println("\nose3VivosMaisDuros");
        //System.out.println(map.get("os3VivosMaisDuros").size());
        //map.get("os3VivosMaisDuros").forEach(System.out::println);

        System.out.println("\ntiposDeEquipamentoMaisUteis");
        System.out.println(map.get("tiposDeEquipamentoMaisUteis").size());
        map.get("tiposDeEquipamentoMaisUteis").forEach(System.out::println);

        System.out.println("\ntiposDeZombieESeusEquipamentosDestruidos");
        System.out.println(map.get("tiposDeZombieESeusEquipamentosDestruidos").size());
        map.get("tiposDeZombieESeusEquipamentosDestruidos").forEach(System.out::println);

        System.out.println("\ncriaturaMaisEquipadas");
        System.out.println(map.get("criaturasMaisEquipadas").size());
        map.get("criaturasMaisEquipadas").forEach(System.out::println);
    }

    public void resetVarsStatics() {
        ArrayList<Equipamento> allEquips = new ArrayList<>();

        allEquips.add(Equipamento.criarEquipamento(-20,0, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-21,1, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-22,2, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-23,3, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-24,4, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-25,5, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-26,6, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-27,7, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-28,8, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-29,9, new int[]{0, 0}));
        allEquips.add(Equipamento.criarEquipamento(-30,10, new int[]{0, 0}));

        allEquips.forEach(Equipamento::resetNrUsos);

        ArrayList<Zombie> allZombies = new ArrayList<>();
        allZombies.add((Zombie) Creature.criarCreature(20,0,"Fulano", new int[]{0,0}));
        allZombies.add((Zombie) Creature.criarCreature(21,1,"Fulano", new int[]{0,0}));
        allZombies.add((Zombie) Creature.criarCreature(22,2,"Fulano", new int[]{0,0}));
        allZombies.add((Zombie) Creature.criarCreature(23,3,"Fulano", new int[]{0,0}));
        allZombies.add((Zombie) Creature.criarCreature(24,4,"Fulano", new int[]{0,0}));

        allZombies.forEach(Zombie::resetTotalEquipDestruidos);

    }
}