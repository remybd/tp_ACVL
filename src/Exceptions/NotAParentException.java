package Exceptions;

/**
 * Created by rémy on 08/11/2015.
 */
public class NotAParentException extends Exception {

    public NotAParentException(){
        System.out.println("L'élément sélectionné comme parent n'en est pas un");
    }
}
