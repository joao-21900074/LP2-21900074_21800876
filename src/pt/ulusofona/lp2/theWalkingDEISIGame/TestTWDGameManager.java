package pt.ulusofona.lp2.theWalkingDEISIGame;

import org.junit.Test;
import java.io.File;

public class TestTWDGameManager {
    private TWDGameManager testStart = new TWDGameManager();
    private File ficheiroTest = new File("./test-files/input");
    private Humano humanoTest = new Humano(1,6,"TestH",new int[]{0,0});
    private Zombie zombieTest = new Zombie(2,1,"TestZ",new int[]{0,1});
    private Zombie vampiroTest = new Zombie(3,4,"TestVZ",new int[]{1,0});
    private Zombie idosoZombieTest = new Zombie(4,3,"TestIZ",new int[]{2,0});

    @Test
    public void testLeituraRuim(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("");
        testStart.startGame(ficheiroTest);
    }

    @Test
    public void testLeituraBoa(){
        testStart.startGame(ficheiroTest);
    }

    @Test
    public void testWolrdSize(){
        testLeituraBoa();
        testStart.getWorldSize();
    }

    @Test
    public void testInitialTeam(){
        testLeituraBoa();
        testStart.getInitialTeam();
    }

    @Test
    public void testEquipmentTypeId(){
        testLeituraBoa();
        testStart.getEquipmentTypeId(-1);
    }

    @Test
    public void testEquipmentInfo(){
        testLeituraBoa();
        testStart.getEquipmentInfo(-1);
    }

    @Test
    public void testFicheiroAllEquipments(){
        File ficheiroE = new File("./test-files/allequipments");
        testStart.startGame(ficheiroE);
    }

    @Test
    public void testDoorSafeHavenBoa(){
        testLeituraBoa();
        testStart.isDoorToSafeHaven(6,6);
    }

    @Test
    public void testDoorSafeHavenRuim(){
        testLeituraBoa();
        testStart.isDoorToSafeHaven(5,5);
    }

    @Test
    public void testSaveGame(){
        testLeituraBoa();
        testStart.saveGame(new File("save.txt"));
    }

    @Test
    public void testBatalhaHumanoMorreu(){
        //Humano sem equipamento
        testStart.batalha(humanoTest, zombieTest);
    }

    @Test
    public void testBatalhaMatarZombie(){
        //Destinado para Equipamentos que matam zombies
        //Espada + Estaca + Beskar
        testStart.zombies.add(zombieTest);

        Equipamento espada = Equipamento.criarEquipamento(1,1,new int[]{0,2});
        //Equipamento Estaca = Equipamento.criarEquipamento(2,6,new int[]{0,3});
        //Equipamento Beskar = Equipamento.criarEquipamento(3,10,new int[]{0,4});
        humanoTest.equiparEquipamento(espada);
        //hTest.equiparEquipamento(estaca);
        //hTest.equiparEquipamento(beskar);

        //System.out.println(testStart.zombies.contains(zTest));
        //True, zombie vivo

        testStart.batalha(humanoTest, zombieTest);
        //System.out.println(testStart.zombies.contains(zTest));
        //False, zombie morreu
    }

    //Não sei se vao deixar usar humans.add aqui n
    @Test
    public void testBatalhaMadeira(){
        //Humano com Escudo de Madeira
        testStart.humans.add(humanoTest);

        Equipamento escudoMadeira = Equipamento.criarEquipamento(1,0,new int[]{0,2});
        humanoTest.equiparEquipamento(escudoMadeira);

        testStart.batalha(humanoTest, zombieTest);
        //System.out.println(testStart.humans.contains(hTest));
        //True, escudo com 1 de defesa

        testStart.batalha(humanoTest, zombieTest);
        //System.out.println(testStart.humans.contains(hTest));
        //False, escudo tem 0 de defesa, portanto quebrou + humano morreu
    }

    @Test
    public void testBatalhaPistolaNormal(){
        //Humano com Pistola contra zombies normais
        testStart.zombies.add(zombieTest);
        testStart.humans.add(humanoTest);

        Equipamento pistola = Equipamento.criarEquipamento(1,2,new int[]{0,2});
        humanoTest.equiparEquipamento(pistola);

        testStart.batalha(humanoTest, zombieTest);
        //System.out.println(testStart.zombies.contains(zTest));
        //False, zombie morreu com 1 bala

        for(int i=0; i<2; i++) {
            testStart.zombies.add(zombieTest);
            testStart.batalha(humanoTest, zombieTest);
            //Gastou 2 balas
            //Gastou 3 balas
        }

        testStart.zombies.add(zombieTest);
        testStart.batalha(humanoTest, zombieTest);
        //System.out.println(testStart.humans.contains(hTest));
        //False, morreu, pistola sem balas

    }

    @Test
    public void testBatalhaPistolaZombieVampiro(){
       //Humano com pistola contra ZombieVampiro
        testStart.zombies.add(vampiroTest);

        Equipamento pistola = Equipamento.criarEquipamento(1,2,new int[]{0,2});
        humanoTest.equiparEquipamento(pistola);

        testStart.batalha(humanoTest, vampiroTest);
        //System.out.println(testStart.zombies.contains(vTest));
        //True, zombie vivo, balas não afetam Zombie Vampiro
    }

    @Test
    public void testBatalhaRevistaNormal(){
        //Humano com revista maria contra zombies normais
        testStart.humans.add(humanoTest);

        Equipamento revista = Equipamento.criarEquipamento(1,4,new int[]{0,2});
        humanoTest.equiparEquipamento(revista);

        testStart.batalha(humanoTest, zombieTest);
        //System.out.println(testStart.humans.contains(hTest));
        //False, morreu, revista não protege contra zombies normais
    }

    @Test
    public void testBatalhaRevistaZombieIdoso(){
        //Humano com revista maria, contra Zombie Idoso
        testStart.humans.add(humanoTest);

        Equipamento revista = Equipamento.criarEquipamento(1,4,new int[]{0,2});
        humanoTest.equiparEquipamento(revista);

        testStart.batalha(humanoTest, idosoZombieTest);
        //System.out.println(testStart.humans.contains(hTest));
        //True, sobreviveu, revista protege contra zombies idosos
    }

    @Test
    public void testBatalhaAlhoZombieNormal(){
        //Humano com cabeça de alho, contra zombie normal
        testStart.humans.add(humanoTest);

        Equipamento alho = Equipamento.criarEquipamento(1,5,new int[]{0,2});
        humanoTest.equiparEquipamento(alho);

        testStart.batalha(humanoTest, zombieTest);
        //System.out.println(testStart.humans.contains(hTest));
        //False, morreu, alho não protege contra zombies normais
    }

    @Test
    public void testBatalhaAlhoZombieVampiro(){
        //Humano com cabeça de alho, contra zombie vampiro
        testStart.humans.add(humanoTest);

        Equipamento alho = Equipamento.criarEquipamento(1,5,new int[]{0,2});
        humanoTest.equiparEquipamento(alho);

        testStart.batalha(humanoTest, vampiroTest);
        //System.out.println(testStart.humans.contains(hTest));
        //True, sobreviveu, alho protege contra zombies vampiros
    }

    @Test
    public void testBatalhaLixivia(){
        //Humano com garrafa de lixivia
        testStart.humans.add(humanoTest);

        Equipamento lixivia = Equipamento.criarEquipamento(1,7,new int[]{0,2});
        humanoTest.equiparEquipamento(lixivia);

        testStart.batalha(humanoTest,zombieTest);
        //System.out.println(testStart.humans.contains(humanoTest));
        //True, sobreviveu, gastou 0.3Litros

        for(int i=0; i<2; i++) {
            testStart.zombies.add(zombieTest);
            testStart.batalha(humanoTest, zombieTest);
            //Gastou 0.6Litros
            //Gastou 0.9Litros
        }

        testStart.zombies.add(zombieTest);
        testStart.batalha(humanoTest, zombieTest);
        //System.out.println(testStart.humans.contains(humanoTest));
        //False, morreu, não tem lixivia suficiente para proteção
    }

    /*OLD TESTES, ainda possa ser útil
    @Test
    public void testMoveDireita(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para Direita",testStart.move(1,1,2,1));
    }

    @Test
    public void testMoveEsquerda(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para Esquerda",testStart.move(1,1,0,1));
    }

    @Test
    public void testMoveBasicoCima(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para Cima",testStart.move(1,1,1,0));
    }

    @Test
    public void testMoveBasicoBaixo(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para Baixo",testStart.move(1,1,1,2));
    }

    @Test
    public void testMoveDiagonal(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento na Diagonal",testStart.move(1,1,2,2));
    }

    @Test
    public void testMoveOutOfBounds(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        testStart.startGame(ficheiroTest);
        assertTrue("Movimento para fora do mapa",testStart.move(4,4,4,5));
    }

    @Test
    public void testStartGameBonecoForaDoMapa(){
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("./test-files/input");
        assertTrue("Boneco nasce fora do mapa",testStart.startGame(ficheiroTest));
    }*/
}