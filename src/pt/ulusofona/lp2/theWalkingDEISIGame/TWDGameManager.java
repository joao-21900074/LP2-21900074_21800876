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
            //ArrayList<Equipamento> equipamentos = new ArrayList<>();
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

            /*Verificar os conteudos
            for(Equipamento e : equipamentos) {
                System.out.println(e.id);
            }
            System.out.println("----");
            for(Humano h : humans) {
                System.out.println(h.nome);
            }
            System.out.println("-----");
            for(Zombie z : zombies) {
                System.out.println(z.nome);
            }*/

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
        return false;
    }

    /* Deve devolver true caso já tenha sido
       alcançada uma das condições de paragem
       do jogo e false em caso contrário. */
    public boolean gameIsOver() {
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

    /* Devolve uma lista de Strings que
       representam as criaturas sobreviventes do
       jogo, conforme descrito na secção dos
       “Resultados da execução …”. */
    public List<String> getSurvivors() {
        return null;
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
            if(h.id == creatureId && h.equipamento != null) {
                return true;
            }
        }
        return false;
    }


}