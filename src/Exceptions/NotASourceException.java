package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NotASourceException extends Exception {

    public NotASourceException(){
        System.out.println("L'�tat demand� n'est pas une source");
    }
}
