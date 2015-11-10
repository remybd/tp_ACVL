package Exceptions;

/**
 * Created by rémy on 08/11/2015.
 */
public class NotASourceException extends Exception {

    public NotASourceException(){
        System.out.println("L'état demandé n'est pas une source");
    }
}
