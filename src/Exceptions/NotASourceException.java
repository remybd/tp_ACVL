package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NotASourceException extends Exception {

    public NotASourceException(){
    	super("L'état demandé n'est pas une source");
    }
}
