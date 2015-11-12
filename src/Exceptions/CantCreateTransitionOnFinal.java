package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class CantCreateTransitionOnFinal extends Exception {

    public CantCreateTransitionOnFinal(){
    	super("On ne peut pas créer une transition à partir d'un état final");
    }
}
