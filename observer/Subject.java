package observer;

public interface Subject {
    void addObserver(Observer o);
    void notifyObservers(Event action);
}
