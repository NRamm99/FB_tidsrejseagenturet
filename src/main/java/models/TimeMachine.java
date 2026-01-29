package models;

public class TimeMachine {
    private String name;
    private int capacity;
    private boolean isFree;

    public TimeMachine(int id, String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.isFree = true;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean getStatus() {
        return isFree;
    }
}
