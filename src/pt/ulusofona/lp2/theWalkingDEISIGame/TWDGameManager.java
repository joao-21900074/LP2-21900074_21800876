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
    int initialTeam;
    int currentTeam;
    int[][] map;
    int[] worldSize;
    boolean isDay = true;
    int tamanhoDiaNoite = 2;
    int turnos = 0;

    //Construtor Vazio
    public TWDGameManager() {}

    //Leitura do Ficheiro, onde pretende-se iniciar o jogo (Precisa criar os Objetos ainda)
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

        int peca = getElementId(xO,yO);
        int destino = getElementId(xD,yD);

        //Valida se é o turno do time da criatura
        if(validaTime(peca,currentTeam)){
            return false;
        }
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
        }

        if(!(destino == 0 || destino < 0 || destino == peca)){
            return false;
        }

        /*if(getElementId(xD,yD) < 0 && currentTeam == 0) {
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

        if(getElementId(xD,yD) < 0 && currentTeam == 1) {
            for(Zombie z : zombies) {
                if(z.getId() == getElementId(xO,yO)){
                    z.destruirIten();
                }
            }
        }

        if(currentTeam == 0) {
            Humano h = getHumanoById(peca);
            if(h != null) {
                h.setPosicao(new int[]{xD,yD});
            }
        } else if(currentTeam == 1) {
            Zombie z = getZombieById(peca);
            if(z != null) {
                z.setPosicao(new int[]{xD,yD});
            }
        }

        map[xD][yD] = peca;
        if(droparItem) {
            map[xO][yO] = itemDropado;
        } else if(destino != peca) {
            map[xO][yO] = 0;
        }


        //Muda o time depois da jogada
        currentTeam = (currentTeam == 0) ? 1 : 0;
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

    public boolean validaTime(int id, int currentTeam) {
        if(id <= 0) {
            return true;
        }

        for(Humano h : humans){
            if(h.getId() == id) {
                return h.getIdTipo() == currentTeam;
            }
        }

        for(Zombie z : zombies) {
            if(z.getId() == id) {
                return z.getIdTipo() == currentTeam;
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

    public List<Integer> getIdsInSafeHaven() {
        return null;
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

    //Tipo do Equipamento usando o id (Da pra melhorar?)
    public int getEquipmentTypeId(int equipmentId) {
        int retorno = 0;
        for(Equipamento e : equipamentos){
            if(e.getId() == equipmentId){
                retorno = e.getIdTipo();
            }
        }
        return retorno;
    }

    //Info do Equipamento usando o id (Da pra melhorar?)
    public String getEquipmentInfo(int equipmentId) {
        String retorno = "";
        for(Equipamento e : equipamentos){
            if(e.getId() == equipmentId){
                retorno = e.toString();
            }
        }
        return retorno;
    }

    public boolean saveGame(File fich) {
        return false;
    }

    public boolean loadGame(File fich) {
        return false;
    }

    public String[] popCultureExtravaganza() {
        return null;
    }
}