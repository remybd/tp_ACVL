package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NoIntermediaryStateException extends Exception {

    public NoIntermediaryStateException(){
        System.out.println("Les �tats de d�but et de fin de la transition ne sont pas des �tats interm�diares");
    }
}
