package models;

public class TimePeriod {
    private String name;
    private String description;

    public TimePeriod(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
