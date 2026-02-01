package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Customer;
import systems.TimeTravelSystem;

import java.sql.SQLException;

public class CustomerController {
    private final TimeTravelSystem tts = new TimeTravelSystem();
    private final ObservableList<Customer> customers = FXCollections.observableArrayList();

    private Customer selected;

    @FXML private TextField customerName;
    @FXML private TextField customerEmail;
    @FXML private ListView<Customer> customerList;
    @FXML private Label customerErrorLabel;

    @FXML
    private void initialize() {
        customerList.setItems(customers);

        // Skjuler ID i ListView (uden at ændre Customer.toString)
        customerList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Customer customer, boolean empty) {
                super.updateItem(customer, empty);
                setText(empty || customer == null ? null : customer.getName() + " – " + customer.getEmail());
            }
        });

        refreshList();
        System.out.println("loaded customers: " + customers.size()); // CONTROLLER TEST

        customerList.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selected = newValue;

                    if (selected == null) {
                        customerName.clear();
                        customerEmail.clear();
                        return;
                    }

                    customerName.setText(selected.getName());
                    customerEmail.setText(selected.getEmail());

                    System.out.println("Selected: " + selected.getName()); // CONTROLLER TEST
                });
    }

    private void refreshList() {
        try {
            customers.setAll(tts.getAllCustomers());
            selected = null;
            customerList.getSelectionModel().clearSelection();
            customerName.clear();
            customerEmail.clear();
            customerErrorLabel.setText("");
        } catch (SQLException e) {
            System.out.println("DB fejl i refreshList: " + e.getMessage());
        }
    }

    @FXML
    private void addCustomer() {
        String name = customerName.getText().trim();
        String email = customerEmail.getText().trim();

        if (name.isEmpty() || email.isEmpty()) {
            customerErrorLabel.setText("Du mangler at skrive Navn og/eller Email");
            return;
        }

        System.out.println("Creating: " + name + " / " + email); // CONTROLLER TEST

        try {
            tts.addCustomer(name, email);
            refreshList();
            customerErrorLabel.setText("");

            System.out.println("After create, count: " + customers.size()); // CONTROLLER TEST
        } catch (SQLException e) {
            System.out.println("DB fejl ved opret: " + e.getMessage());
        }
    }

    @FXML
    private void deleteCustomer() {
        if (selected == null) {
            customerErrorLabel.setText("Vælg en kunde først");
            return;
        }

        System.out.println("Deleting: " + selected.getName()); // CONTROLLER TEST

        try {
            tts.removeCustomer(selected.getName());
            refreshList();
            customerErrorLabel.setText("");
        } catch (SQLException e) {
            System.out.println("DB fejl ved slet: " + e.getMessage());
        }
    }

    @FXML
    private void saveChanges() {
        if (selected == null) {
            customerErrorLabel.setText("Vælg en kunde først");
            return;
        }

        String newName = customerName.getText().trim();
        String newEmail = customerEmail.getText().trim();

        if (newName.isEmpty() || newEmail.isEmpty()) {
            customerErrorLabel.setText("Udfyld både navn og Email");
            return;
        }

        try {
            String originalName = selected.getName();

            System.out.println("Updating: " + originalName + " -> " + newName + " / " + newEmail); // CONTROLLER TEST

            if (!newName.equals(originalName)) {
                tts.setCustomerName(originalName, newName);
            }

            if (!newEmail.equals(selected.getEmail())) {
                // hvis navnet er ændret, bruger newName som nøgle efter rename
                tts.setCustomerEmail(newName, newEmail);
            }

            refreshList();
            customerErrorLabel.setText("");
        } catch (SQLException e) {
            System.out.println("DB fejl ved gem: " + e.getMessage());
        }
    }
}
