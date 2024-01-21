/**
*
* @author Chay Wen Ning
*/
package observer;

/**
 * The {@code Observer} interface. 
 * This class is part of the <a href="https://www.geeksforgeeks.org/observer-pattern-set-1-introduction/">Observer design pattern</a>, and it acts as an interface to be implemented by any object acting as {@code Observer}.
 * An {@code Observer} object implementing this interface handles {@code Event} notified by its {@code Subject}. 
 * 
 * @see observer.Event
 * @see observer.Subject
 */
public interface Observer {
    /**
     *
     * Handles {@code Event} notified by the {@code Subject} object.
     * 
     * @param event the notified {@code Event}
     * @see observer.Event
     * @see observer.Subject
     * @author Chay Wen Ning
     */
    void handleEvent(Event event);
}