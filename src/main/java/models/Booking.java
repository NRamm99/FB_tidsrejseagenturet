package models;

public class Booking {
    private Customer customer;
    private TimeMachine timeMachine;
    private TimePeriod timePeriod;
    private Guide guide;

    public Booking(Customer customer, TimeMachine timeMachine, TimePeriod timePeriod, Guide guide) {
        this.customer = customer;
        this.timeMachine = timeMachine;
        this.timePeriod = timePeriod;
        this.guide = guide;
    }
}
