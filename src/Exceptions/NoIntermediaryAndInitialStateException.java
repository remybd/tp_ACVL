package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class NoIntermediaryAndInitialStateException extends Exception {

    public NoIntermediaryAndInitialStateException(){
        System.out.println("L'�tat de d�but de la transition n'est pas un �tat initial et celui de fin n'est pas un �tat interm�diaire");
    }
}
