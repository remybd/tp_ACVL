package Exceptions;

/**
 * Created by r�my on 10/11/2015.
 */
public class BadCorrespondanceBetweenObservateurAndSubjectType extends Exception {
    public BadCorrespondanceBetweenObservateurAndSubjectType(){
        System.out.println(" !!! La correspondance entre l'objet du mod�le et celui de l'IHM n'est pas bonne !!!");
    }
}
