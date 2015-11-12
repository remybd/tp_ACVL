package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NameNotModifiableException extends Exception {

    public NameNotModifiableException(){
    	super("Le nom de cet élément n'est pas modifiable");
    }
}
