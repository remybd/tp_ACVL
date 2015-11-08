package Exceptions;

/**
 * Created by rémy on 08/11/2015.
 */
public class NameNotModifiableException extends Exception {

    public NameNotModifiableException(){
        System.out.println("Le nom de cet élément n'est pas modifiable");
    }
}
