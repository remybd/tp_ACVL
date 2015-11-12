package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NotAParentException extends Exception {

    public NotAParentException(){
    	super("L'élément sélectionné comme parent n'en est pas un");
    }
}
