package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NameNotModifiableException extends Exception {

    public NameNotModifiableException(){
        System.out.println("Le nom de cet �l�ment n'est pas modifiable");
    }
}
