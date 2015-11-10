package Exceptions;

/**
 * Created by rémy on 08/11/2015.
 */
public class CantCreateTransitionOnFinal extends Exception {

    public CantCreateTransitionOnFinal(){
        System.out.println("On ne peut pas créer une transition à partir d'un état final");
    }
}
