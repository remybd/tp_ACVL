package Exceptions;

/**
 * Created by rémy on 10/11/2015.
 */
public class BadCorrespondanceBetweenObservateurAndSubjectType extends Exception {
    public BadCorrespondanceBetweenObservateurAndSubjectType(){
        System.out.println(" !!! La correspondance entre l'objet du modèle et celui de l'IHM n'est pas bonne !!!");
    }
}
