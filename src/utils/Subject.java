/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Julietta
 */
public interface Subject {
   
    /**
     *
     * @param observer najit observer
     */
    void registerObserver(Observer observer);
    
    /**
     *
     * @param observer smazat observer
     */
    void deleteObserver (Observer observer);
    
    /**
     *update
     */
    void notifyAllObservers();
}
