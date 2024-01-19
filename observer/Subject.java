/**
*
* @author Chay Wen Ning
*/
package observer;

/**
 * The {@code Subject} interface.
 * @see observer.Observer
 */
public interface Subject {
    /**
     *
     * Add an {@code Observer} object.
     *
     * @param observer      {@code Observer} to be added
     * @see observer.Observer
     */
    void addObserver(Observer observer);

    /**
     *
     * Notifies observers to handle the triggered {@code Event}.
     *
     * @param event      {@code Event} to be notified
     * @see observer.Observer
     * @see observer.Event
     */
    void notifyObservers(Event action);
}
