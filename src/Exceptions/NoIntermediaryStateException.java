package Exceptions;

/**
 * Created by rémy on 08/11/2015.
 */
public class NoIntermediaryStateException extends Exception {

    public NoIntermediaryStateException(){
        System.out.println("Les états de début et de fin de la transition ne sont pas des états intermédiares");
    }
}
