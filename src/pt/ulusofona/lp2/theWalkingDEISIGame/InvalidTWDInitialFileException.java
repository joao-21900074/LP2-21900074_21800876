package pt.ulusofona.lp2.theWalkingDEISIGame;

public class InvalidTWDInitialFileException extends Exception {
    private int nrOfCreatures;
    private int creatureDefinition;
    private String erroneousLine;

    //Precisa desse construtor para dar display da linha errada na interface
    public InvalidTWDInitialFileException(String s){
        this.erroneousLine = s;
    }

    //Construtor para linha com argumentos a mais ou faltando + a linha errada
    public InvalidTWDInitialFileException(int nCriaturasStartgame,int argumentosCriaturaStartgame,String linhaErrada) {
        this.nrOfCreatures = nCriaturasStartgame;
        this.creatureDefinition = argumentosCriaturaStartgame;
        this.erroneousLine = linhaErrada;
    }

    //Este método deve devolver true se e só se o ficheiro tiver pelo menos 2 criaturas
    public boolean validNrOfCreatures() {
        return nrOfCreatures > 2;
    }


    //Este método deve devolver true se e só se o ficheiro tiver os dados completos para todas as criaturas
    public boolean validCreatureDefinition() {
        return creatureDefinition == 5;
    }

    //Este método deve devolver a primeira linha que descreve
    //uma criatura que tenha dados a menos ou a mais.
    //Caso mais do que uma linha tenha problemas, apenas
    //deve ser devolvida a primeira
    public String getErroneousLine() {
        return erroneousLine;
    }
}