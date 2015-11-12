package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class ParentNotModifiableException extends Exception {

    public ParentNotModifiableException(){
    	super("Le parent de cet élément n'est pas modifiable");
    }
}
