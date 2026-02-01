package models;

public class TimeMachine {
    private final String name;
    private final int capacity;
    private final boolean isFree;

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
            return "Navn: " + name + " | Kapacitet: " + capacity + " | Status: Ledig";
        }
        return "Navn: " + name + " | Kapacitet: " + capacity + " | Status: Optaget";
    }
}
