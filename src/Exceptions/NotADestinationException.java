package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NotADestinationException extends Exception {

    public NotADestinationException(){
    	super("L'état demandé n'est pas une destination");
    }
}
