package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import models.Customer;
import models.Guide;
import models.TimeMachine;
import models.TimePeriod;
import systems.TimeTravelSystem;

import java.sql.SQLException;

public class BookingController {
    private final TimeTravelSystem tts = new TimeTravelSystem();

    private final ObservableList<Customer> customers = FXCollections.observableArrayList();
    private final ObservableList<TimeMachine> timeMachines = FXCollections.observableArrayList();
    private final ObservableList<TimePeriod> timePeriods = FXCollections.observableArrayList();
    private final ObservableList<Guide> guides = FXCollections.observableArrayList();

    @FXML private ComboBox<Customer> selectCustomer;
    @FXML private ComboBox<TimeMachine> selectTimeMachine;
    @FXML private ComboBox<TimePeriod> selectTimePeriod;
    @FXML private ComboBox<Guide> selectGuide;

    @FXML private Label bookingErrorLabel;

    @FXML
    private void initialize() {
        bookingErrorLabel.setText("");

        selectCustomer.setItems(customers);
        selectTimeMachine.setItems(timeMachines);
        selectTimePeriod.setItems(timePeriods);
        selectGuide.setItems(guides);

        // Skjuler ID i dropdowns (uden at ændre toString) customer + guide
        selectCustomer.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Customer c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getName() + " – " + c.getEmail());
            }
        });
        selectCustomer.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Customer c, boolean empty) {
                super.updateItem(c, empty);
                setText(empty || c == null ? null : c.getName() + " – " + c.getEmail());
            }
        });

        selectGuide.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Guide g, boolean empty) {
                super.updateItem(g, empty);
                setText(empty || g == null ? null : g.getName() + " – " + g.getSpeciality());
            }
        });
        selectGuide.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Guide g, boolean empty) {
                super.updateItem(g, empty);
                setText(empty || g == null ? null : g.getName() + " – " + g.getSpeciality());
            }
        });

        // TimeMachine + TimePeriod bruger default toString
        refreshChoices();
    }

    private void refreshChoices() {
        try {
            customers.setAll(tts.getAllCustomers());
            timeMachines.clear();
            //checker om timemachine er ledig
            for (var tm : tts.getAllTimeMachines()){
                if (tm.getStatus()) {
                    timeMachines.add(tm);
                }
            }
            timePeriods.setAll(tts.getAllTimePeriods());
            guides.setAll(tts.getAllGuides());

            selectCustomer.getSelectionModel().clearSelection();
            selectTimeMachine.getSelectionModel().clearSelection();
            selectTimePeriod.getSelectionModel().clearSelection();
            selectGuide.getSelectionModel().clearSelection();

            bookingErrorLabel.setText("");

        } catch (SQLException e) {
            System.out.println("DB fejl i refreshChoices: " + e.getMessage());
            bookingErrorLabel.setText("DB-fejl ved indlæsning");
        }
    }

    @FXML
    private void book() {
        bookingErrorLabel.setText("");

        Customer customer = selectCustomer.getSelectionModel().getSelectedItem();
        if (customer == null) {
            bookingErrorLabel.setText("Vælg en kunde");
            return;
        }

        TimeMachine timeMachine = selectTimeMachine.getSelectionModel().getSelectedItem();
        if (timeMachine == null) {
            bookingErrorLabel.setText("Vælg en tidsmaskine");
            return;
        }

        TimePeriod timePeriod = selectTimePeriod.getSelectionModel().getSelectedItem();
        if (timePeriod == null) {
            bookingErrorLabel.setText("Vælg en tidsperiode");
            return;
        }

        Guide guide = selectGuide.getSelectionModel().getSelectedItem();
        if (guide == null) {
            bookingErrorLabel.setText("Vælg en guide");
            return;
        }

        try {
            tts.addBooking(customer.getName(), timeMachine.getName(), timePeriod.getName(), guide.getName());

            // tæller bookings på valgt maskine
            int bookingsOnMachine = tts.countBookingsByTimeMachine(timeMachine.getName());

            // når bookings >= capacity, så er den optaget
            if (bookingsOnMachine >= timeMachine.getCapacity()) {
                tts.setTimeMachineStatus(timeMachine.getName(), false); // false = optaget
            }

            customers.remove(customer);
            bookingErrorLabel.setText("Booking oprettet!");

        } catch (SQLException e) {
            System.out.println("DB fejl ved booking: " + e.getMessage());
            bookingErrorLabel.setText("DB-fejl ved booking");
        }
    }
}
