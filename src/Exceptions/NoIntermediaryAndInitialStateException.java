package Exceptions;

/**
 * Created by rémy on 08/11/2015.
 */
public class NoIntermediaryAndInitialStateException extends Exception {

    public NoIntermediaryAndInitialStateException(){
        System.out.println("L'état de début de la transition n'est pas un état initial et celui de fin n'est pas un état intermédiaire");
    }
}
