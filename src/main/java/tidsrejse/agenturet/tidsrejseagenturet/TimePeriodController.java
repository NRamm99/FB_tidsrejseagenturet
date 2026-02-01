package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import models.TimePeriod;
import systems.TimeTravelSystem;
import java.sql.SQLException;

public class TimePeriodController {

    private final TimeTravelSystem tts = new TimeTravelSystem();
    private final ObservableList<TimePeriod> timePeriods = FXCollections.observableArrayList();

    private TimePeriod selected;

    @FXML private TextField timePeriodName;
    @FXML private TextField timePeriodDescription;
    @FXML private ListView<TimePeriod> timePeriodList;
    @FXML private Label timePeriodErrorLabel;


    @FXML
    private void initialize(){
        timePeriodList.setItems(timePeriods);
        refreshList();
        System.out.println("loaded timeperiods: " + timePeriods.size()); //CONTROLLER TEST
        // Opdater valgt tidsperiod når brugeren klikker i listen
        timePeriodList.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selected = newValue;

                    if (selected == null) {
                        timePeriodName.clear();
                        timePeriodDescription.clear();
                        return;
                    }

                    timePeriodName.setText(selected.getName());
                    timePeriodDescription.setText(selected.getDescription());

                    System.out.println("Selected: " + selected.getName());

                });
    }

    private void refreshList() {
        try {
            timePeriods.setAll(tts.getAllTimePeriods());
            selected = null;
            timePeriodList.getSelectionModel().clearSelection();
            timePeriodName.clear();
            timePeriodDescription.clear();
        } catch (SQLException e) {
            System.out.println("DB fejl i refreshList: " + e.getMessage());
        }
    }

    @FXML
    private void addTimePeriod(){
        String name = timePeriodName.getText().trim();
        String desc = timePeriodDescription.getText().trim();

        if (name.isEmpty() || desc.isEmpty()){
            timePeriodErrorLabel.setText("Du mangler at skrive Navn og/eller Beskrivelse");
            return;
        }

        System.out.println("Creating: " + name + " / " + desc); //CONTROLLER TEST

        try {
            tts.addTimePeriod(name, desc);
            refreshList();
            timePeriodErrorLabel.setText("");

            System.out.println("After create, count: " + timePeriods.size());   //CONTROLLER TEST

        } catch (SQLException e){
            System.out.println("DB fejl ved opret: " + e.getMessage());
        }
    }

    @FXML
    private void deleteTimePeriod(){
        if (selected == null){
            timePeriodErrorLabel.setText("Vælg en tidsperiode først");
            return;
        }

        System.out.println("Deleting: " + selected.getName()); //CONTROLLER TEST

        try {
            tts.removeTimePeriod(selected.getName());
            refreshList();
            timePeriodErrorLabel.setText("");
        } catch (SQLException e){
            System.out.println("DB fejl ved slet: " + e.getMessage());
        }
    }

    @FXML
    private void saveChanges(){
        if (selected == null) {
            timePeriodErrorLabel.setText("Vælg en tidsperiode først");
            return;
        }

        String newName = timePeriodName.getText().trim();
        String newDesc = timePeriodDescription.getText().trim();

        if (newName.isEmpty() || newDesc.isEmpty()){
            timePeriodErrorLabel.setText("Udfyld både navn og beskrivelse");
            return;
        }

        try{
            // Opdater ud fra den oprindeligt valgte
            String originalName = selected.getName();

            System.out.println("Updating: " + originalName + " -> " + newName + " / " + newDesc); // CONTROLLER TEST

            if (!newName.equals(originalName)){
                tts.setTimePeriodName(originalName, newName);
            }
            if (!newDesc.equals(selected.getDescription())){
                // hvis ændret så bruger den newName som nøgle efter rename
                tts.setTimePeriodDescription(newName, newDesc);
            }

            refreshList();
            timePeriodErrorLabel.setText("");
        }catch (SQLException e){
            System.out.println("DB fejl ved gem: " + e.getMessage());
        }
    }
}
