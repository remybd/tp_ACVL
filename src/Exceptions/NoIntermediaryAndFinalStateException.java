package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NoIntermediaryAndFinalStateException extends Exception {

    public NoIntermediaryAndFinalStateException(){
    	super("L'état de début de la transition n'est pas un état intermédiare et celui de fin n'est pas un état final");
    }
}
