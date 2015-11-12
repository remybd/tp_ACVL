package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NoIntermediaryStateException extends Exception {

    public NoIntermediaryStateException(){
    	super("Les états de début et de fin de la transition ne sont pas des états intermédiares");
    }
}
