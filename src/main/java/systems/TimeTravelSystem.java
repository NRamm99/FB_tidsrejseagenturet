package systems;

import config.DatabaseConfig;
import models.Customer;
import models.Guide;
import models.TimeMachine;
import models.TimePeriod;
import repositories.CustomerRepository;
import repositories.GuideRepository;
import repositories.TimeMachineRepository;
import repositories.TimePeriodRepository;

import java.sql.SQLException;

public class TimeTravelSystem {
    DatabaseConfig databaseConfig;
    CustomerRepository customerRepository;
    GuideRepository guideRepository;
    TimeMachineRepository timeMachineRepository;
    TimePeriodRepository timePeriodRepository;

    public TimeTravelSystem() {
        this.databaseConfig = new DatabaseConfig();
        this.customerRepository = new CustomerRepository(databaseConfig);
        this.guideRepository = new GuideRepository(databaseConfig);
        this.timeMachineRepository = new TimeMachineRepository(databaseConfig);
        this.timePeriodRepository = new TimePeriodRepository(databaseConfig);
    }

    // BOOKING
    public void createBooking() {

    }

    public void editBooking() {

    }

    public void removeBooking() {

    }

    // GUIDE
    public void addGuide(String name, String speciality) throws SQLException {
        guideRepository.add(name, speciality);
    }

    public void setGuideName(String originalName, String newName) throws SQLException {
        guideRepository.setName(originalName, newName);
    }

    public void setGuideSpeciality(String name, String newSpeciality) throws SQLException {
        guideRepository.setSpeciality(name, newSpeciality);
    }

    public Guide getGuideByName(String name) throws SQLException {
        return guideRepository.getGuideByName(name);
    }

    public void removeGuide(String name) throws SQLException {
        guideRepository.remove(name);
    }

    // TIME MACHINE
    public void addTimeMachine(String name, int capacity) {
        timeMachineRepository.add(name, capacity);
    }

    public void setTimeMachineName(String originalName, String newName) throws SQLException {
        timeMachineRepository.setName(originalName, newName);
    }

    public void setTimeMachineCapacity(String name, int newCapacity) throws SQLException {
        timeMachineRepository.setCapacity(name, newCapacity);
    }

    public TimeMachine getTimeMachineByName(String name) throws SQLException {
        return timeMachineRepository.getTimeMachineByName(name);
    }

    public void removeTimeMachine(String name) throws SQLException {
        timeMachineRepository.remove(name);
    }

    public void setTimeMachineStatus(String name, Boolean isFree) throws SQLException {
        timeMachineRepository.setStatus(name, isFree);
    }


    // CUSTOMER
    public void addCustomer(String name, String email) throws SQLException {
        customerRepository.add(name, email);
    }

    public void setCustomerName(String originalName, String newName) throws SQLException {
        customerRepository.setName(originalName, newName);
    }

    public void setCustomerEmail(String name, String newEmail) throws SQLException {
        customerRepository.setCustomerEmail(name, newEmail);
    }

    public void removeCustomer(String name) throws SQLException {
        customerRepository.remove(name);
    }

    public Customer getCustomerByName(String name) throws SQLException {
        return customerRepository.getCustomerByName(name);
    }

    // TIME PERIODS
    public void addTimePeriod(String name, String description) throws SQLException {
        customerRepository.add(name, description);
    }

    public void setTimePeriodName(String originalName, String newName) throws SQLException {
        timePeriodRepository.setName(originalName, newName);
    }

    public void setTimePeriodDescription(String name, String newDescription) throws SQLException {
        timePeriodRepository.setDescription(name, newDescription);
    }

    public TimePeriod getTimePeriodByName(String name) throws SQLException {
        return timePeriodRepository.getTimePeriodByName(name);
    }
}
