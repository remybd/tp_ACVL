package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NoIntermediaryAndInitialStateException extends Exception {

    public NoIntermediaryAndInitialStateException(){
    	super("L'état de début de la transition n'est pas un état initial et celui de fin n'est pas un état intermédiaire");
    }
}
