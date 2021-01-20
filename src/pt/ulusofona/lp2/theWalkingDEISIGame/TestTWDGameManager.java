package pt.ulusofona.lp2.theWalkingDEISIGame;

import org.junit.Test;
import pt.ulusofona.lp2.theWalkingDEISIGame.criaturas.*;

import java.io.File;
import java.io.FileNotFoundException;

public class TestTWDGameManager {
    private TWDGameManager testStart = new TWDGameManager();
    private File ficheiroTest = new File("./test-files/input");
    private HumanoAdulto humanoAdulto = new HumanoAdulto(1,6,"TestHA",new int[]{0,0});
    private ZombieAdulto zombieAdulto = new ZombieAdulto(2,1,"TestZA",new int[]{0,1});
    private ZombieVampiro zombieVampiro = new ZombieVampiro(3,4,"TestZV",new int[]{1,0});
    private ZombieIdoso zombieIdoso = new ZombieIdoso(4,3,"TestZI",new int[]{2,0});
    private HumanoCrianca humanoCrianca = new HumanoCrianca(5,5,"TestHC",new int[]{3,0});
    private ZombieCrianca zombieCrianca = new ZombieCrianca(6,0,"TestZC",new int[]{4,0});
    private HumanoMilitar humanoMilitar1 = new HumanoMilitar(7,7,"TestHM",new int[]{5,0});
    private HumanoMilitar humanoMilitar2 = new HumanoMilitar(8,7,"TestHM",new int[]{6,0});


    /*@Test
    public void testLeituraRuim() throws InvalidTWDInitialFileException, FileNotFoundException {
        TWDGameManager testStart = new TWDGameManager();
        File ficheiroTest = new File("");
        testStart.startGame(ficheiroTest);
    }*/

    @Test
    public void testLeituraBoa() throws InvalidTWDInitialFileException, FileNotFoundException {
        testStart.startGame(ficheiroTest);
    }

    @Test
    public void testWolrdSize() throws InvalidTWDInitialFileException, FileNotFoundException {
        testLeituraBoa();
        testStart.getWorldSize();
    }

    @Test
    public void testInitialTeam() throws InvalidTWDInitialFileException, FileNotFoundException {
        testLeituraBoa();
        testStart.getInitialTeam();
    }

    @Test
    public void testEquipmentTypeId() throws InvalidTWDInitialFileException, FileNotFoundException {
        testLeituraBoa();
        testStart.getEquipmentTypeId(-1);
    }

    @Test
    public void testEquipmentInfo() throws InvalidTWDInitialFileException, FileNotFoundException {
        testLeituraBoa();
        testStart.getEquipmentInfo(-1);
    }

    @Test
    public void testFicheiroAllEquipments() throws InvalidTWDInitialFileException, FileNotFoundException {
        File ficheiroE = new File("./test-files/allequipments");
        testStart.startGame(ficheiroE);
    }

    @Test
    public void testDoorSafeHavenBoa() throws InvalidTWDInitialFileException, FileNotFoundException {
        testLeituraBoa();
        testStart.isDoorToSafeHaven(6,6);
    }

    @Test
    public void testDoorSafeHavenRuim() throws InvalidTWDInitialFileException, FileNotFoundException {
        testLeituraBoa();
        testStart.isDoorToSafeHaven(5,5);
    }

    @Test
    public void testSaveGame() throws InvalidTWDInitialFileException, FileNotFoundException {
        testLeituraBoa();
        testStart.saveGame(new File("save.txt"));
    }

    @Test
    public void testBatalhaHumanoMorreu(){
        //Humano sem equipamento
        testStart.batalha(humanoAdulto, zombieAdulto);
    }

    /*@Test
    public void testBatalhaMatarZombie(){
        //Destinado para Equipamentos que matam zombies
        //Espada + Estaca + Beskar
        testStart.zombies.add(zombieAdulto);

        Equipamento espada = Equipamento.criarEquipamento(1,1,new int[]{0,2});
        //Equipamento Estaca = Equipamento.criarEquipamento(2,6,new int[]{0,3});
        //Equipamento Beskar = Equipamento.criarEquipamento(3,10,new int[]{0,4});
        humanoAdulto.equiparEquipamento(espada);
        //hTest.equiparEquipamento(estaca);
        //hTest.equiparEquipamento(beskar);

        //System.out.println(testStart.zombies.contains(zombieAdulto));
        //True, zombie vivo

        testStart.batalha(humanoAdulto, zombieAdulto);
        //System.out.println(testStart.zombies.contains(zombieAdulto));
        //False, zombie morreu
    }*/

    //Não sei se vao deixar usar humans.add aqui n
    @Test
    public void testBatalhaMadeira(){
        //Humano com Escudo de Madeira
        testStart.vivos.add(humanoAdulto);

        Equipamento escudoMadeira = Equipamento.criarEquipamento(1,0,new int[]{0,2});
        humanoAdulto.equiparEquipamento(escudoMadeira);

        testStart.batalha(humanoAdulto, zombieAdulto);
        System.out.println(testStart.vivos.contains(humanoAdulto));
        //True, escudo com 1 de defesa

        testStart.batalha(humanoAdulto, zombieAdulto);
        System.out.println(testStart.vivos.contains(humanoAdulto));
        //False, escudo tem 0 de defesa, portanto quebrou + humano morreu
    }

    @Test
    public void testBatalhaMadeiraMilitar(){
        //Quando militar pega o escudo ele tem que aumentar 1 de defesa
        Equipamento escudoMadeira = Equipamento.criarEquipamento(1,0,new int[]{0,2});
        humanoMilitar1.equiparEquipamento(escudoMadeira);

        testStart.vivos.add(humanoMilitar1);

        for(int i=0; i < 3; i++) {
            //Aqui o Militar vai aguentar 2 batalhas com o Zumbi por causa do upgrade do escudo
            testStart.batalha(humanoMilitar1, zombieAdulto);
            //System.out.println(testStart.humans.contains(humanoMilitar1));
        }
    }

    @Test
    public void testBatalhaUpgradeEscudoMadeira(){
        /*Upgrade de um escudo só funciona uma vez
        Caso um militar equipe o escudo, aumenta defesa para 2
        Depois um OUTRO militar equipa o mesmo escudo, a defesa NÃO vai para 2*/

        Equipamento escudoMadeira = Equipamento.criarEquipamento(1,0,new int[]{0,2});
        humanoMilitar1.equiparEquipamento(escudoMadeira);
        //Testar defesa diretamente
        //EscudoMadeira defesa = (EscudoMadeira) humanoMilitar1.getEquipamento();

        testStart.batalha(humanoMilitar1,zombieAdulto);
        //Aqui uma batalha com Militar1, conseguiu defender (2-1 = 1)
        //System.out.println(defesa.getDefesa());

        humanoMilitar2.equiparEquipamento(escudoMadeira);
        testStart.batalha(humanoMilitar2,zombieAdulto);
        //Aqui outra batalha com o mesmo escudo, mas agora o Militar2, conseguiu defender (1-1 = 0)
        //Portanto na segunda vez não teve upgrade
        //System.out.println(defesa.getDefesa());
    }

    /*@Test
    public void testBatalhaPistolaNormal(){
        //Humano com Pistola contra zombies normais
        testStart.zombies.add(zombieAdulto);
        testStart.vivos.add(humanoAdulto);

        Equipamento pistola = Equipamento.criarEquipamento(1,2,new int[]{0,2});
        humanoAdulto.equiparEquipamento(pistola);

        testStart.batalha(humanoAdulto, zombieAdulto);
        //System.out.println(testStart.zombies.contains(zTest));
        //False, zombie morreu com 1 bala

        for(int i=0; i<2; i++) {
            testStart.zombies.add(zombieAdulto);
            testStart.batalha(humanoAdulto, zombieAdulto);
            //Gastou 2 balas
            //Gastou 3 balas
        }

        testStart.zombies.add(zombieAdulto);
        testStart.batalha(humanoAdulto, zombieAdulto);
        //System.out.println(testStart.humans.contains(hTest));
        //False, morreu, pistola sem balas

    }*/

    @Test
    public void testBatalhaPistolaZombieVampiro(){
       //Humano com pistola contra ZombieVampiro
        testStart.zombies.add(zombieVampiro);

        Equipamento pistola = Equipamento.criarEquipamento(1,2,new int[]{0,2});
        humanoAdulto.equiparEquipamento(pistola);

        testStart.batalha(humanoAdulto, zombieVampiro);
        //System.out.println(testStart.zombies.contains(vTest));
        //True, zombie vivo, balas não afetam Zombie Vampiro
    }

    @Test
    public void testBatalhaRevistaNormal(){
        //Humano com revista maria contra zombies normais
        testStart.vivos.add(humanoAdulto);

        Equipamento revista = Equipamento.criarEquipamento(1,4,new int[]{0,2});
        humanoAdulto.equiparEquipamento(revista);

        testStart.batalha(humanoAdulto, zombieAdulto);
        //System.out.println(testStart.humans.contains(hTest));
        //False, morreu, revista não protege contra zombies normais
    }

    @Test
    public void testBatalhaRevistaZombieIdoso(){
        //Humano com revista maria, contra Zombie Idoso
        testStart.vivos.add(humanoAdulto);

        Equipamento revista = Equipamento.criarEquipamento(1,4,new int[]{0,2});
        humanoAdulto.equiparEquipamento(revista);

        testStart.batalha(humanoAdulto, zombieIdoso);
        //System.out.println(testStart.humans.contains(hTest));
        //True, sobreviveu, revista protege contra zombies idosos
    }

    @Test
    public void testGameResult() throws InvalidTWDInitialFileException, FileNotFoundException {
        //Teste básico de resultado
        testLeituraBoa();
        testStart.getGameResults();
    }

    @Test
    public void testBatalhaAlhoZombieNormal(){
        //Humano com cabeça de alho, contra zombie normal
        testStart.vivos.add(humanoAdulto);

        Equipamento alho = Equipamento.criarEquipamento(1,5,new int[]{0,2});
        humanoAdulto.equiparEquipamento(alho);

        testStart.batalha(humanoAdulto, zombieAdulto);
        //System.out.println(testStart.vivos.contains(humanoAdulto));
        //False, morreu, alho não protege contra zombies normais
    }

    @Test
    public void testBatalhaAlhoZombieVampiro(){
        //Humano com cabeça de alho, contra zombie vampiro
        testStart.vivos.add(humanoAdulto);

        Equipamento alho = Equipamento.criarEquipamento(1,5,new int[]{0,2});
        humanoAdulto.equiparEquipamento(alho);

        testStart.batalha(humanoAdulto, zombieVampiro);
        //System.out.println(testStart.humans.contains(hTest));
        //True, sobreviveu, alho protege contra zombies vampiros
    }

    @Test
    public void testBatalhaLixivia(){
        //Humano com garrafa de lixivia
        testStart.vivos.add(humanoAdulto);

        Equipamento lixivia = Equipamento.criarEquipamento(1,7,new int[]{0,2});
        humanoAdulto.equiparEquipamento(lixivia);

        testStart.batalha(humanoAdulto, zombieAdulto);
        //System.out.println(testStart.humans.contains(humanoTest));
        //True, sobreviveu, gastou 0.3Litros

        for(int i=0; i<2; i++) {
            testStart.zombies.add(zombieAdulto);
            testStart.batalha(humanoAdulto, zombieAdulto);
            //Gastou 0.6Litros
            //Gastou 0.9Litros
        }

        testStart.zombies.add(zombieAdulto);
        testStart.batalha(humanoAdulto, zombieAdulto);
        //System.out.println(testStart.humans.contains(humanoTest));
        //False, morreu, não tem lixivia suficiente para proteção
    }

    /*@Test
    public void testBatalhaCriança(){
        //Criança Humano só pode usar espada contra Criança Zombie
        Equipamento espada = Equipamento.criarEquipamento(1,1,new int[]{0,2});
        humanoCrianca.equiparEquipamento(espada);

        testStart.vivos.add(humanoCrianca);
        testStart.zombies.add(zombieCrianca);
        testStart.batalha(humanoCrianca,zombieCrianca);
        //Criança ficou viva / Zombie Criança morreu
        //System.out.println(testStart.humans.contains(humanoCrianca));
        //System.out.println(testStart.zombies.contains(zombieCrianca));

        testStart.zombies.add(zombieAdulto);
        testStart.batalha(humanoCrianca,zombieAdulto);
        //Criança morreu / Zombie Adulto ficou vivo
        //System.out.println(testStart.vivos.contains(humanoCrianca));
        //System.out.println(testStart.zombies.contains(zombieAdulto));
    }*/

    @Test
    public void testBatalhaVeneno(){
        //Caso humano esteja envenenado não pode morrer
        testStart.vivos.add(humanoAdulto);
        Equipamento veneno = Equipamento.criarEquipamento(1,8,new int[]{0,2});
        humanoAdulto.equiparEquipamento(veneno);

        testStart.batalha(humanoAdulto,zombieAdulto);
        System.out.println(testStart.vivos.contains(humanoAdulto));
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