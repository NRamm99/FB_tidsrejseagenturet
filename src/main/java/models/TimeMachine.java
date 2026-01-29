package models;

public class TimeMachine {
    private String name;
    private int capacity;
    private String status;

    public TimeMachine(int id, String name, int capacity, String status) {
        this.name = name;
        this.capacity = capacity;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getStatus() {
        return status;
    }
}
