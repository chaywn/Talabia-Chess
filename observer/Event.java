/**
*
* @author Chay Wen Ning
*/
package observer;

/**
 * 
 * The {@code Event} enum; Notified by {@code Subject}, and handled by {@code Observer}.
 * This class represents a list a game events that are notified or fired from one object (subject) to another (observer).
 * 
 * @see observer.Subject
 * @see observer.Observer
 */
public enum Event {
    PIECEMOVE,
    PIECESWITCH,
    PLAYERWIN,
    NEWGAME;

    private Object arg;
    
    /**
     * Returns any argument the {@code Event} has.
     * Returns {@code null} if the event has no argument.
     * 
     * @return the {@code Object} argument, {@code null} if the event has no argument
     * @see #returnArgument(Object)
     * @author Chay Wen Ning
     */
    public Object getArgument() { return arg; }

    /**
     * Returns itself with an argument {@code Object}.
     * 
     * @param arg the argument to return with
     * @return the {@code Event}
     * @see #getArgument()
     * @author Chay Wen Ning
     */
    public Event returnArgument(Object arg) { 
        this.arg = arg;
        return this; 
    }
}