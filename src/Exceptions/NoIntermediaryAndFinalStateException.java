package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NoIntermediaryAndFinalStateException extends Exception {

    public NoIntermediaryAndFinalStateException(){
        System.out.println("L'�tat de d�but de la transition n'est pas un �tat interm�diare et celui de fin n'est pas un �tat final");
    }
}
