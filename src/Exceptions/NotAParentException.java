package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NotAParentException extends Exception {

    public NotAParentException(){
        System.out.println("L'�l�ment s�lectionn� comme parent n'en est pas un");
    }
}
