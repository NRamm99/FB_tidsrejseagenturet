package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.TimeMachine;
import systems.TimeTravelSystem;

import java.sql.SQLException;

public class TimeMachineController {

    private final TimeTravelSystem tts = new TimeTravelSystem();
    private final ObservableList<TimeMachine> timeMachines = FXCollections.observableArrayList();

    private TimeMachine selected;

    @FXML private TextField timeMachineName;
    @FXML private TextField timeMachineCapacity;
    @FXML private ListView<TimeMachine> timeMachineList;
    @FXML private Label timeMachineErrorLabel;

    @FXML
    private void initialize(){
        timeMachineList.setItems(timeMachines);
        refreshList();
        System.out.println("loaded timemachine: " + timeMachines.size()); //CONTROLLER TEST
        // Opdater valgt tidsperiod når brugeren klikker i listen
        timeMachineList.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selected = newValue;

                    if (selected == null) {
                        timeMachineName.clear();
                        timeMachineCapacity.clear();
                        return;
                    }

                    timeMachineName.setText(selected.getName());
                    timeMachineCapacity.setText(String.valueOf(selected.getCapacity()));

                    System.out.println("Selected: " + selected.getName());

                });
    }

    private void refreshList(){
        try {
            timeMachines.setAll(tts.getAllTimeMachines());
            selected = null;
            timeMachineList.getSelectionModel().clearSelection();
            timeMachineName.clear();
            timeMachineCapacity.clear();
            timeMachineErrorLabel.setText("");
        }catch (SQLException e) {
            System.out.println("DB fejl i refreshList: " + e.getMessage());
        }
    }

    @FXML
    private void addTimeMachine(){
        String name = timeMachineName.getText().trim();
        String capText = timeMachineCapacity.getText().trim();
        int capacity;

        if (name.isEmpty() || capText.isEmpty()){
            timeMachineErrorLabel.setText("Du mangler at skrive Navn og/eller Kapacitet");
            return;
        }

        try {
            capacity = Integer.parseInt(capText);
        } catch (NumberFormatException e) {
            timeMachineErrorLabel.setText("Kapacitet skal være et tal");
            return;
        }

        System.out.println("Creating: " + name + " / " + capText); //CONTROLLER TEST


        try {
            tts.addTimeMachine(name, capacity);
            refreshList();
            timeMachineErrorLabel.setText("");

            System.out.println("After create, count: " + timeMachines.size());   //CONTROLLER TEST

        } catch (SQLException e){
            System.out.println("DB fejl ved opret: " + e.getMessage());
        }
    }

    @FXML
    private void deleteTimeMachine(){
        if (selected == null){
            timeMachineErrorLabel.setText("Vælg en tidsmaskine først");
            return;
        }

        System.out.println("Deleting: " + selected.getName()); //CONTROLLER TEST

        try {
            tts.removeTimeMachine(selected.getName());
            refreshList();
            timeMachineErrorLabel.setText("");
        } catch (SQLException e){
            System.out.println("DB fejl ved slet: " + e.getMessage());
        }
    }

    @FXML
    private void saveChanges(){
        if (selected == null) {
            timeMachineErrorLabel.setText("Vælg en tidsmaskine først");
            return;
        }

        String newName = timeMachineName.getText().trim();
        String capText = timeMachineCapacity.getText().trim();
        int newCapacity;

        if (newName.isEmpty() || capText.isEmpty()){
            timeMachineErrorLabel.setText("Udfyld både navn og Kapacitet");
            return;
        }

        try {
            newCapacity = Integer.parseInt(capText);
        } catch (NumberFormatException e) {
            timeMachineErrorLabel.setText("Kapacitet skal være et tal");
            return;
        }

        try{
            // Opdater ud fra den oprindeligt valgte
            String originalName = selected.getName();

            System.out.println("Updating: " + originalName + " -> " + newName + " / " + newCapacity); // CONTROLLER TEST

            if (!newName.equals(originalName)){
                tts.setTimeMachineName(originalName, newName);
            }
            if (newCapacity != selected.getCapacity()){
                // hvis ændret så bruger den newName som nøgle efter rename
                tts.setTimeMachineCapacity(newName, newCapacity);
            }

            refreshList();
            timeMachineErrorLabel.setText("");
        }catch (SQLException e){
            System.out.println("DB fejl ved gem: " + e.getMessage());
        }
    }
}

