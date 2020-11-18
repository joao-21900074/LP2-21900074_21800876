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
        //Teste
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
            map = new int[linhas][colunas];
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
                 if (tipo == 0) {
                     Zombie z = new Zombie(id, tipo, nome, new int[]{posicaoX, posicaoY});
                     zombies.add(z);
                     map[posicaoX][posicaoY] = id;
                 } else {
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
                int posicaoX = Integer.parseInt(infoEquipamento[2].trim());//Adicionar no mapa
                int posicaoY = Integer.parseInt(infoEquipamento[3].trim());//Adicionar no mapa

                Equipamento e = new Equipamento(id, tipo, new int[]{posicaoX,posicaoY});//Ver o q fazer com o "equipado"

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

    /* Deve tentar executar uma jogada,
       considerando que (xO, yO) representa a
       origem a jogada e (xD, yD) representa o
       destino da jogada. */
    public boolean move(int xO, int yO, int xD, int yD) {

        boolean droparItem = false;
        int itemDropado = 0;

        //Teste João
        
        //Escrevi aqui
        int peca = getElementId(xO,yO);
        int destino = getElementId(xD,yD);

        if(gameIsOver()) {
            return false;
        }

        if(validaTime(peca,currentTeam)){
            return false;
        }

        if(xD > worldSize[1] || yD > worldSize[0]){
            return false;
        }

        //Meter isso dentro de uma função
        //Verificar quadrados possíveis
        if(!(((xD == xO+1 && yD == yO) || (xD == xO && yD == yO+1)) || ((xD == xO-1 && yD == yO) || (xD == xO && yD == yO-1)))){
            return false;
        }

        if(!(destino == 0 || destino < 0)){
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
                    z.nItensDestruido++;
                }
            }
        }

        map[xD][yD] = peca;
        if(droparItem) {
            map[xO][yO] = itemDropado;
        } else {
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
            if(h.id == id) {
                return h.idTipo == currentTeam;
            }
        }

        for(Zombie z : zombies) {
            if(z.id == id) {
                return z.idTipo == currentTeam;
            }
        }

        return false;
    }

    /* Deve devolver true caso já tenha sido
       alcançada uma das condições de paragem
       do jogo e false em caso contrário. */
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

    /* Devolve uma lista de Strings que
       representam as criaturas sobreviventes do
       jogo, conforme descrito na secção dos
       “Resultados da execução …”. */
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

    /* Dever retornar true caso o turno actual
       corresponda a um turno diurno e false
       caso o turno actual corresponda um turno
       nocturno. */
    public boolean isDay() {
        return isDay;
    }

    /* Deve retornar true caso a criatura
       identificada pelo 1º argumento tenha em
       sua posse um equipamento do tipo cujo ID
       for passado como 2º argumento.
       Em caso contrário, deve retornar false. */
    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        for(Humano h : humans) {
            if(h.equipamento == null){
                continue;
            }
            if(h.id == creatureId && h.equipamento.idTipo == equipmentTypeId) {
                return true;
            }
        }
        return false;
    }


}