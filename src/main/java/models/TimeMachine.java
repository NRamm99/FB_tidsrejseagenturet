package models;

public class TimeMachine {
    private String name;
    private int capacity;
    private boolean isFree;

    public TimeMachine(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.isFree = true;
    }

    public TimeMachine(String name, int capacity, boolean isFree) {
        this.name = name;
        this.capacity = capacity;
        this.isFree = isFree;
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

    @Override
    public String toString() {
        if (isFree) {
            return "Name: " + name + " | Capacity: " + capacity + " | Status: Free";
        }
        return "Name: " + name + " | Capacity: " + capacity + " | Status: Occupied";
    }
}
