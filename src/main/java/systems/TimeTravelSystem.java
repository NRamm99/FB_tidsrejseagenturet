package systems;

import config.DatabaseConfig;
import models.Customer;
import models.Guide;
import models.TimeMachine;
import models.TimePeriod;
import repositories.*;

import java.util.List;
import java.sql.SQLException;

public class TimeTravelSystem {
    DatabaseConfig databaseConfig;
    CustomerRepository customerRepository;
    GuideRepository guideRepository;
    TimeMachineRepository timeMachineRepository;
    TimePeriodRepository timePeriodRepository;
    BookingRepository bookingRepository;

    public TimeTravelSystem() {
        this.databaseConfig = new DatabaseConfig();
        this.customerRepository = new CustomerRepository(databaseConfig);
        this.guideRepository = new GuideRepository(databaseConfig);
        this.timeMachineRepository = new TimeMachineRepository(databaseConfig);
        this.timePeriodRepository = new TimePeriodRepository(databaseConfig);
        this.bookingRepository = new BookingRepository(databaseConfig);
    }

    // BOOKING
    public void addBooking(String customerName, String timeMachineName, String timePeriodName, String guideName) throws SQLException {
        bookingRepository.add(customerName, timeMachineName, timePeriodName, guideName);
    }

    public int countBookingsByTimeMachine(String timeMachineName) throws SQLException {
        return bookingRepository.countByTimeMachineName(timeMachineName);
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

    public List<Guide> getAllGuides() throws SQLException{
        return guideRepository.getAll();
    }

    // TIME MACHINE
    public void addTimeMachine(String name, int capacity) throws SQLException {
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

    public List<TimeMachine> getAllTimeMachines()throws SQLException{
        return timeMachineRepository.getAll();
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

    public List<Customer> getAllCustomers() throws SQLException {
        return customerRepository.getAll();
    }


    // TIME PERIODS
    public void addTimePeriod(String name, String description) throws SQLException {
        timePeriodRepository.add(name, description);
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

    public void removeTimePeriod (String name) throws SQLException{
        timePeriodRepository.remove(name);
    }

    public List<TimePeriod> getAllTimePeriods()throws SQLException{
        return timePeriodRepository.getAll();
    }
}
