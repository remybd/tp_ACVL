package Exceptions;

/**
 * Created by r�my on 08/11/2015.
 */
public class CantCreateTransitionOnFinal extends Exception {

    public CantCreateTransitionOnFinal(){
        System.out.println("On ne peut pas cr�er une transition � partir d'un �tat final");
    }
}
