package Exceptions;

/**
 * Created by rémy on 08/11/2015.
 */
public class NoIntermediaryAndFinalStateException extends Exception {

    public NoIntermediaryAndFinalStateException(){
        System.out.println("L'état de début de la transition n'est pas un état intermédiare et celui de fin n'est pas un état final");
    }
}
