/**
*
* @author Chay Wen Ning
*/
package observer;

// Subject notifies Observer of events
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