package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class ParentNotModifiableException extends Exception {

    public ParentNotModifiableException(){
        System.out.println("Le parent de cet �l�ment n'est pas modifiable");
    }
}
