/**
*
* @author Chay Wen Ning
*/
package observer;

/**
 * The {@code Event} enum; Notified by {@code Subject}, and handled by {@code Observer}.
 * @see observer.Subject
 * @see observer.Observer
 */
public enum Event {
    PieceMove,
    PieceSwitch,
    PlayerWin,
    NewGame;

    private Object arg;
    
    public Object getArgument() { return arg; }
    public Event returnArgument(Object arg) { 
        this.arg = arg;
        return this; 
    }
}