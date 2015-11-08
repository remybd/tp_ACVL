package Exceptions;

/**
 * Created by rémy on 08/11/2015.
 */
public class ParentNotModifiableException extends Exception {

    public ParentNotModifiableException(){
        System.out.println("Le parent de cet élément n'est pas modifiable");
    }
}
