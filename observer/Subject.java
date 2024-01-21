package observer;

/**
 * The {@code Subject} interface.
 * This class is part of the <a href="https://www.geeksforgeeks.org/observer-pattern-set-1-introduction/">Observer design pattern</a>, and it acts as an interface to be implemented by any object acting as {@code Subject}.
 * A {@code Subject} object implementing this interface notifies or fire {@code Event} to its {@code Observer} to be handled. 
 * 
 * @see observer.Event
 * @see observer.Observer
 * @author Chay Wen Ning
 */
public interface Subject {
    /**
     *
     * Adds an {@code Observer} object.
     *
     * @param observer     the {@code Observer} object to be added
     * @see observer.Observer
     * @author Chay Wen Ning
     */
    void addObserver(Observer observer);

    /**
     *
     * Notifies the {@code Observer} object(s) to handle the fired {@code Event}.
     *
     * @param event    the {@code Event} to be notified
     * @see #addObserver(Observer)
     * @see observer.Observer
     * @see observer.Event
     * @author Chay Wen Ning
     */
    void notifyObservers(Event event);
}
