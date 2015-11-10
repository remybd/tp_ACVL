package Exceptions;

/**
 * Created by rémy on 08/11/2015.
 */
public class NotADestinationException extends Exception {

    public NotADestinationException(){
        System.out.println("L'état demandé n'est pas une destination");
    }
}
