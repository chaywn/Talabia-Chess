/**
*
* @author Chay Wen Ning
*/
package observer;

/**
 * The {@code Observer} interface. 
 * @see observer.Subject
 */
public interface Observer {
    /**
     *
     * Handles {@code Event} notified by {@code Subject} object.
     *
     * @param event the {@code Event} enum
     * @see observer.Event
     * @see observer.Subject
     */
    void handleEvent(Event action);
}