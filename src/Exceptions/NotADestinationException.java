package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NotADestinationException extends Exception {

    public NotADestinationException(){
        System.out.println("L'�tat demand� n'est pas une destination");
    }
}
