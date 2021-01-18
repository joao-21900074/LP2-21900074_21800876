package pt.ulusofona.lp2.theWalkingDEISIGame;

public class InvalidTWDInitialFileException extends Exception {

    /*
    Este método deve devolver true se e só se o ficheiro tiver
    pelo menos 2 criaturas
    */

    public boolean validNrOfCreatures() {
        return false;
    }

    /*
    Este método deve devolver true se e só se o ficheiro tiver
    os dados completos para todas as criaturas
    */

    public boolean validCreatureDefinition() {
        return false;
    }

    /*
    Este método deve devolver a primeira linha que descreve
    uma criatura que tenha dados a menos ou a mais.
    Caso mais do que uma linha tenha problemas, apenas
    deve ser devolvida a primeira
    */

    public String getErroneousLine() {
        return "";
    }

}
