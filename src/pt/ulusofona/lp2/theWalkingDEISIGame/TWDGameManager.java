package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TWDGameManager {

    ArrayList<Humano> humans = new ArrayList<>();
    ArrayList<Zombie> zombies = new ArrayList<>();
    ArrayList<Equipamento> equipamentos = new ArrayList<>();
    int initialTeam;
    int currentTeam;
    int[][] map;
    int[] worldSize;
    boolean isDay = true;
    int tamanhoDiaNoite = 2;
    int turnos = 0;

    //Construtor Vazio
    public TWDGameManager() {}

    // pronto
    public boolean startGame(File ficheiroInicial) {
        try {
            //Leitor para o ficheiro do jogo
            BufferedReader leitor = new BufferedReader(new FileReader(ficheiroInicial));
            //String para ler linha a linha
            String linha;
            int key = 1;
            HashMap<Integer,String> conteudo = new HashMap<>();

            //While de leitura
            while (((linha = leitor.readLine()))!= null) {
                String[] info = linha.split(" ");
                conteudo.put(key, String.join(" ",info));
                key++;
            }

            //Criação do Mapa
            String[] cxl = conteudo.get(1).split(" ");
            int linhas = Integer.parseInt(cxl[0]);
            int colunas = Integer.parseInt(cxl[1]);
            map = new int[colunas][linhas];
            worldSize = new int[]{linhas,colunas};

            //Equipe inicial
            initialTeam = Integer.parseInt(conteudo.get(2));
            currentTeam = initialTeam;

            //Quantidade de criaturas
            int nCriaturas = Integer.parseInt(conteudo.get(3));

            int linhaCriaturas = 4;

            for(int i = 0;i < nCriaturas ;i++){
                String[] infoCriaturas = conteudo.get(linhaCriaturas).split(":");
                int id = Integer.parseInt(infoCriaturas[0].trim());
                int tipo = Integer.parseInt(infoCriaturas[1].trim());
                String nome = infoCriaturas[2].trim();
                int posicaoX = Integer.parseInt(infoCriaturas[3].trim());
                int posicaoY = Integer.parseInt(infoCriaturas[4].trim());
                if (tipo == 0 && (posicaoX < colunas && posicaoY < linhas)) {
                    Zombie z = new Zombie(id, tipo, nome, new int[]{posicaoX, posicaoY});
                    zombies.add(z);
                    map[posicaoX][posicaoY] = id;
                } else if(tipo == 1 && (posicaoX < colunas && posicaoY < linhas)){
                    Humano h = new Humano(id, tipo, nome, new int[]{posicaoX, posicaoY});
                    humans.add(h);
                    map[posicaoX][posicaoY] = id;
                }

                linhaCriaturas++;
            }

            //Quantidade de equipamentos
            int nEquimamentos = Integer.parseInt(conteudo.get(linhaCriaturas));
            int linhaEquipamento = linhaCriaturas+1;

            //Criação de Equipamentos

            for(int i = 0;i < nEquimamentos ;i++){
                String[] infoEquipamento = conteudo.get(linhaEquipamento).split(":");
                int id = Integer.parseInt(infoEquipamento[0].trim());
                int tipo = Integer.parseInt(infoEquipamento[1].trim());
                int posicaoX = Integer.parseInt(infoEquipamento[2].trim());
                int posicaoY = Integer.parseInt(infoEquipamento[3].trim());

                Equipamento e = new Equipamento(id, tipo, new int[]{posicaoX,posicaoY});

                equipamentos.add(e);
                map[posicaoX][posicaoY] = id;

                linhaEquipamento++;
            }

            return true;


        }catch(IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    // pronto
    public int[] getWorldSize() {
        return worldSize;
    }

    // pronto
    public int getInitialTeam() {
        return initialTeam;
    }

    // pronto
    public List<Humano> getHumans() {
        return humans;
    }

    //pronto
    public List<Zombie> getZombies() {
        return zombies;
    }

    public boolean move(int xO, int yO, int xD, int yD) {
        boolean droparItem = false;
        int itemDropado = 0;

        if(xD < 0 || xD > worldSize[1] || yD < 0 || yD > worldSize[0]){
            return false;
        }

        int peca = getElementId(xO,yO);
        int destino = getElementId(xD,yD);

        if(validaTime(peca,currentTeam)){
            return false;
        }

        if(xO != xD && yO != yD) {
            return false;
        }

        if(yO == yD) {
            if(Math.abs(xO - xD) > 1) {
                return false;
            }
        }

        if(xO == xD) {
            if(Math.abs(yO - yD) > 1) {
                return false;
            }
        }

        if(!(destino == 0 || destino < 0 || destino == peca)){
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
        }

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
}