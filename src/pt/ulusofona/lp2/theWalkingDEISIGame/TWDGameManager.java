package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TWDGameManager {

    static ArrayList<Humano> humans = new ArrayList<>();
    static ArrayList<Zombie> zombies = new ArrayList<>();
    static ArrayList<Equipamento> equipamentos = new ArrayList<>();
    static int initialTeam;

    //Construtor Vazio
    public void TWDGameManager() {}

    /* Deve fazer a leitura do ficheiro de texto e
       carregar para a memória a informação
       relevante. */
    public static boolean startGame(File ficheiroInicial) {
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
            int[][] mapa = new int[linhas][colunas];

            //Equipe inicial
            initialTeam = Integer.parseInt(conteudo.get(2));

            //Quantidade de criaturas
            int nCriaturas = Integer.parseInt(conteudo.get(3));

            int linhaCriaturas = 4;

            for(String criatura : conteudo.values()){
                if(criatura.length() > 13) {
                    String[] infoCriaturas = conteudo.get(linhaCriaturas).split(":");
                    int id = Integer.parseInt(infoCriaturas[0].trim());
                    int tipo = Integer.parseInt(infoCriaturas[1].trim());
                    String nome = infoCriaturas[2].trim();
                    int posicaoX = Integer.parseInt(infoCriaturas[3].trim());//Adicionar no mapa
                    int posicaoY = Integer.parseInt(infoCriaturas[4].trim());//Adicionar no mapa

                    if(tipo == 0) {
                        Zombie z = new Zombie(id, tipo, nome, new int[]{posicaoX,posicaoY});
                        zombies.add(z);
                    }else{
                        Humano h = new Humano(id, tipo, nome, new int[]{posicaoX,posicaoY});
                        humans.add(h);
                    }

                    linhaCriaturas++;
                }
            }

            //Quantidade de equipamentos
            int nEquimamentos = Integer.parseInt(conteudo.get(linhaCriaturas));
            linhaCriaturas++;

            //Criação de Equipamentos
            //ArrayList<Equipamento> equipamentos = new ArrayList<>();
            for(String equip : conteudo.values()){
                if(equip.length() > 3 && equip.length() < 14) {
                    String[] infoEquipamento = conteudo.get(linhaCriaturas).split(":");
                    int id = Integer.parseInt(infoEquipamento[0].trim());
                    int tipo = Integer.parseInt(infoEquipamento[1].trim());
                    int posicaoX = Integer.parseInt(infoEquipamento[2].trim());//Adicionar no mapa
                    int posicaoY = Integer.parseInt(infoEquipamento[3].trim());//Adicionar no mapa

                    Equipamento e = new Equipamento(id, tipo, new int[]{posicaoX,posicaoY});//Ver o q fazer com o "equipado"

                    equipamentos.add(e);

                    linhaCriaturas++;
                }
            }

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
            }

        }catch(IOException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /* Deve devolver o tamanho do bairro,
       conforme lido do ficheiro respectivo.
       Na posição 0 do array deve ser devolvido o
       número de linhas e na posição 1 deve ser
       devolvido o número de colunas. */
    public int[] getWorldSize() {
        return null;
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

    /* Devolve uma lista de Strings com os
       nomes dos autores do projecto. */
    public List<String> getAuthors() {
        ArrayList<String> creditos = new ArrayList<>();
        creditos.add("João Barreiros");
        creditos.add("Matheus");//COLOCAR O NOME COMPLETO DO MATHEUS
        return creditos;
    }

    /* Deve devolver o ID da equipa que está
       activa no turno actual. */
    public int getCurrentTeamId() {
        return 0;
    }

    /* Deve devolver o ID do objecto/elemento
       que se encontra na posição indicada pelas
       coordenadas (x,y) passadas por
       argumento. */
    public int getElementId(int x, int y) {
        return 0;
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
        return false;
    }

    /* Deve retornar true caso a criatura
       identificada pelo 1º argumento tenha em
       sua posse um equipamento do tipo cujo ID
       for passado como 2º argumento.
       Em caso contrário, deve retornar false. */
    public boolean hasEquipment(int creatureId, int equipmentTypeId) {
        return false;
    }


}
