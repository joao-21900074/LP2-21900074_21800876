package pt.ulusofona.lp2.theWalkingDEISIGame;

import pt.ulusofona.lp2.theWalkingDEISIGame.criaturas.*;
import pt.ulusofona.lp2.theWalkingDEISIGame.equipamentos.*;

import java.io.*;
import java.util.*;

public class TWDGameManager {
    ArrayList<Creature> creatures = new ArrayList<>();
    ArrayList<Vivo> vivos = new ArrayList<>();
    ArrayList<Zombie> zombies = new ArrayList<>();
    ArrayList<Equipamento> equipamentos = new ArrayList<>();
    ArrayList<int[]> safeHavens = new ArrayList<>();
    ArrayList<Integer> salvos = new ArrayList<>();
    int initialTeam;
    int currentTeam;
    int[][] map;
    int[] worldSize;
    boolean isDay = true;
    int tamanhoDiaNoite = 2;
    int turnos = 0;

    //Construtor Vazio
    public TWDGameManager() {}

    //Leitura do Ficheiro, onde pretende-se iniciar o jogo
    public boolean startGame(File ficheiroInicial) {
        try {
            //Leitor para o ficheiro do jogo
            Scanner leitor = new Scanner(new FileReader(ficheiroInicial));
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

            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado");
        }
        return false;
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
        boolean droparItem = false;
        int itemDropado = 0;
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
                System.out.println("Vou testar para ver se tem um criatura");
                return false;
            }
        } else if(getEquipmentById(destino) != null) {
            Equipamento equipDestino = getEquipmentById(destino);
            if(!creature.validaMove(xD,yD,isDay,equipDestino.getId(), equipDestino.getIdTipo())) {
                return false;
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
                    vivos.removeIf(v -> v == getHumanoById(peca));
                //Invalida caso "Outro" foi para a safe
                }else{
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
                if(creature.getEquipe() == 10){
                    batalha((Humano) getHumanoById(creature.getId()),getZombieById(creatureDestino.getId()));
                }else if(creature.getEquipe() == 20){
                    batalha((Humano) getHumanoById(creatureDestino.getId()), getZombieById(creature.getId()));
                }
                fight = true;
            }
        }

        //Muda a posição da criatura
        if(creatures.contains(creature)) {
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
                System.out.println("mudei para " + ((isDay) ? "Dia" : "Noite"));
            }
        }

        turnos++;

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
                    }else{
                        morreu(humano);
                    }
                    break;

                case 1:
                    //Espada, matam zombies
                    //Caso especifico criança só mata criança
                    if(humano.getIdTipo() == 5 && !(zombie.getIdTipo() == 0)){
                        morreu(humano);
                    }else{
                        matou(zombie);
                    }
                    break;

                case 2:
                    //Pistola
                    Pistola pistola = (Pistola) humano.getEquipamento();
                    if(pistola.getBalas() > 0){
                        //Não funciona contra ZombieVampiro
                        if(!(zombie.getIdTipo() == 4)) {
                            pistola.atirar();
                            matou(zombie);
                        }else{
                            morreu(humano);
                        }
                    }else{
                        morreu(humano);
                    }
                    break;

                case 3:
                    //Escudo Táctio, só defende então não mata nem morre
                    break;

                case 4:
                    //Revista Maria, só defende contra ZombieIdoso
                    if(!(zombie.getIdTipo() == 3)){
                        morreu(humano);
                    }
                    break;

                case 5:
                    //Cabeça de Alho, só defende contra ZombieVampiro
                    if(!(zombie.getIdTipo() == 4)){
                        morreu(humano);
                    }
                    break;

                case 6:
                case 10:
                    //Estaca de Madeira + Beskar Helmet, matam zombies
                    matou(zombie);
                    break;

                case 7:
                    //Garrafa de Lixivia
                    Lixivia lixivia = (Lixivia) humano.getEquipamento();
                    if(lixivia.getLitros() > 0){
                        //Proteger + Gastou 0.3Litros
                        lixivia.usar();
                    }else{
                        morreu(humano);
                    }
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
        return 0;
    }

    public void morreu(Vivo vivo){
        //Humano morreu + tirar da lista dos humanos
        vivos.removeIf(v -> v == vivo);
        creatures.removeIf(c -> c == vivo);
        //Humano vira Zombie + botar na lsita dos zombies
        Creature novoZombie = Creature.criarCreature(vivo.getId(),transformar(vivo.getIdTipo()),
                vivo.getNome(),vivo.getPosicao());
        zombies.add((Zombie) novoZombie);
        creatures.add(novoZombie);
    }

    public void matou(Zombie zombie){
        map[zombie.getPosicao()[0]][zombie.getPosicao()[1]] = 0;
        zombie.die();
        zombies.removeIf(z -> z == zombie);
        creatures.removeIf(c -> c == zombie);
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
        return turnos >= 12 || vivos.size() == 0;
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

    // ++++MÉTODOS NOVOS+++
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
        }
        return allAtributes.toString();
    }

    public String getAllEquipmentAtributes(){
        StringBuilder allAtributes = new StringBuilder();
        for(int i=0; i < equipamentos.size(); i++){
            allAtributes.append(getEquipmentAtributes(equipamentos.get(i).getId())).append("\n");
        }
        return allAtributes.toString();
    }

    public String getSafePosicao(){
        StringBuilder retorno = new StringBuilder();
        for(int[] s : safeHavens){
            retorno.append(s[0]).append(" : ").append(s[1]).append("\n");
        }
        return retorno.toString();
    }

    public boolean saveGame(File fich) {
        try{
            FileWriter escrita = new FileWriter(fich);
            escrita.write(getWorldSize()[0] + " " + getWorldSize()[1] + "\n");
            escrita.write(currentTeam + "\n");
            escrita.write(creatures.size() + "\n");
            escrita.write(getAllCreaturesAtributes());
            escrita.write(equipamentos.size() + "\n");
            escrita.write(getAllEquipmentAtributes());
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
        return startGame(fich);
    }

    public String[] popCultureExtravaganza() {
        return new String[0];
    }

    public List<String> getGameResults(){
        return null;
    }


}