package Exceptions;

/**
 * Created by r�my on 10/11/2015.
 */
public class BadCorrespondanceBetweenObservateurAndSubjectType extends Exception {
    public BadCorrespondanceBetweenObservateurAndSubjectType(){
        super(" !!! La correspondance entre l'objet du modèle et celui de l'IHM n'est pas bonne !!!");
    }
}
