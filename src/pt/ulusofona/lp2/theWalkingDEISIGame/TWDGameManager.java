package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TWDGameManager {
    ArrayList<Creature> creatures = new ArrayList<>();
    ArrayList<Humano> humans = new ArrayList<>();
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
                                humans.add((Humano) c);
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

    /*Não precisa nessa segunda parte
    public List<Humano> getHumans() {
        return humans;
    }

    public List<Zombie> getZombies() {
        return zombies;
    }*/

    public boolean move(int xO, int yO, int xD, int yD) {
        boolean droparItem = false;
        int itemDropado = 0;

        //Valida se o destino esta fora do mapa
        if(xD < 0 || xD > worldSize[1] || yD < 0 || yD > worldSize[0]){
            return false;
        }

        /*Old movimentos, ainda pode ser útil
        //Valida diagonal
        if(xO != xD && yO != yD) {
            return false;
        }

        //Valida movimento esquerda/direita
        if(yO == yD) {
            if(Math.abs(xO - xD) > 1) {
                return false;
            }
        }

        //Valida movimento baixo/cima
        if(xO == xD) {
            if(Math.abs(yO - yD) > 1) {
                return false;
            }
        }*/

        int peca = getElementId(xO,yO);
        int destino = getElementId(xD,yD);

        //Valida o time que joga
        if(!validaTime(peca,currentTeam)){
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
                    //Talvez precise tirar ele dos humans(Para não afetar o Save)
                //Invalida caso "Outro" foi para a safe
                }else{
                    return false;
                }
            }else{
                //Atualiza o mapa
                map[xD][yD] = peca;
            }
        }else{
            //Vivo para Vivo e Zombie para Zombie
            if(getCreatureById(peca).getEquipe() == getCreatureById(destino).getEquipe()){
                return false;
            }else{//Vivo para Zombie + Zombie para Vivo
                //VER O QUE O JOÃO TEM DENTRO DE CADA
                if(getCreatureById(peca).getEquipe() == 10){
                    Humano vivo = getHumanoById(peca);
                    vivo.validaMove(xD,yD,isDay,destino,currentTeam);
                }else{//Zombie que vai mover
                    Zombie outro = getZombieById(peca);
                    outro.validaMove(xD,yD,isDay,destino,currentTeam);
                }
            }
            //Atualiza o mapa
            map[xD][yD] = peca;
        }

        /*if(!(destino == 0 || destino < 0 || destino == peca)){
            return false;
        }

        if(getElementId(xD,yD) < 0 && currentTeam == 0) {
            Equipamento equipEscolhido = new Equipamento();
            for(Equipamento e : equipamentos) {
                if(e.getId() == getElementId(xD,yD)){
                    equipEscolhido = e;
                }
            }

            for(Humano h : humans) {
                if(h.getId() == getElementId(xO,yO)){
                    if(!h.temEquipamento()){
                        h.equiparEquipamento(equipEscolhido);
                    } else {
                        droparItem = true;
                        itemDropado = h.getEquipamento().getId();
                        h.equiparEquipamento(equipEscolhido);
                    }
                }
            }
        }*/

        if(getElementId(xD,yD) < 0 && currentTeam == 20) {
            for(Zombie z : zombies) {
                if(z.getId() == getElementId(xO,yO)){
                    z.destruirIten();
                }
            }
        }

        if(currentTeam == 10) {
            Humano h = getHumanoById(peca);
            if(h != null) {
                h.setPosicao(new int[]{xD,yD});
            }
        } else if(currentTeam == 20) {
            Zombie z = getZombieById(peca);
            if(z != null) {
                z.setPosicao(new int[]{xD,yD});
            }
        }

        if(droparItem) {
            map[xO][yO] = itemDropado;
        } else if(destino != peca) {
            map[xO][yO] = 0;
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

        turnos++;

        return true;
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
        return turnos >= 12;
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
    public List<String> getSurvivors() {
        List<String> retorno = new ArrayList<>();
        String listHumanos = "";
        String listaZombie = "";

        for(int i = 0; i < humans.size(); i++){
            listHumanos += humans.get(i).getId() + " " + humans.get(i).getNome() + "\n";
        }

        for(int i = 0; i < zombies.size(); i++){
            listaZombie += zombies.get(i).getId() + " (antigamente conhecido como " + zombies.get(i).getNome() + ")";
            if(i == zombies.size() - 1){
                listaZombie += "\n";
            }
        }

        String infoTurno = "Nr. de turnos terminados:\n" + turnos + "\n\n" + "OS VIVOS\n" + listHumanos + "\n" +
                "OS OUTROS\n" + listaZombie;

        retorno.add(infoTurno);

        return retorno;
    }

    // pronto
    public boolean isDay() {
        return isDay;
    }

    // pronto
    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        for(Humano h : humans) {
            if(h.getEquipamento() == null){
                continue;
            }
            if(h.getId() == creatureId && h.getEquipamento().getIdTipo() == equipmentTypeId) {
                return true;
            }
        }
        return false;
    }

    public Humano getHumanoById(int id) {
        for(Humano h : humans) {
            if(h.getId() == id) {
                return h;
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
        for(Humano h : humans){
            if(h.getId() == creatureId){
                return h.getIdEquipamento();
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
            escrita.write(initialTeam + "\n");
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
        return null;
    }
}