package models;

public class Booking {
    private final Customer customer;
    private final TimeMachine timeMachine;
    private final TimePeriod timePeriod;
    private final Guide guide;

    public Booking(Customer customer, TimeMachine timeMachine, TimePeriod timePeriod, Guide guide) {
        this.customer = customer;
        this.timeMachine = timeMachine;
        this.timePeriod = timePeriod;
        this.guide = guide;
    }

}
