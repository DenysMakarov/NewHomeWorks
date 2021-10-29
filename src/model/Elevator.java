package model;

public class Elevator {
    String name;
    int currentValue;

    public Elevator(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public synchronized void add(int portion) {
//        synchronized (this){
            currentValue += portion;
//        }
    }
}
