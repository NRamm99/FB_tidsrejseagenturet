package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Guide;
import systems.TimeTravelSystem;

import java.sql.SQLException;

public class GuideController {
    private final TimeTravelSystem tts = new TimeTravelSystem();
    private final ObservableList<Guide> guides = FXCollections.observableArrayList();

    private Guide selected;

    @FXML private TextField guideName;
    @FXML private TextField guideSpeciality;
    @FXML private ListView<Guide> guideList;
    @FXML private Label guideErrorLabel;

    @FXML
    private void initialize() {
        guideList.setItems(guides);

        // Skjuler ID i ListView (uden at ændre Guide.toString)
        guideList.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Guide guide, boolean empty) {
                super.updateItem(guide, empty);
                setText(empty || guide == null ? null : guide.getName() + " – " + guide.getSpeciality());
            }
        });

        refreshList();
        System.out.println("loaded guides: " + guides.size()); // CONTROLLER TEST

        guideList.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selected = newValue;

                    if (selected == null) {
                        guideName.clear();
                        guideSpeciality.clear();
                        return;
                    }

                    guideName.setText(selected.getName());
                    guideSpeciality.setText(selected.getSpeciality());

                    System.out.println("Selected: " + selected.getName()); // CONTROLLER TEST
                });
    }

    private void refreshList() {
        try {
            guides.setAll(tts.getAllGuides());
            selected = null;
            guideList.getSelectionModel().clearSelection();
            guideName.clear();
            guideSpeciality.clear();
            guideErrorLabel.setText("");
        } catch (SQLException e) {
            System.out.println("DB fejl i refreshList: " + e.getMessage());
        }
    }

    @FXML
    private void addGuide() {
        String name = guideName.getText().trim();
        String speciality = guideSpeciality.getText().trim();

        if (name.isEmpty() || speciality.isEmpty()) {
            guideErrorLabel.setText("Du mangler at skrive Navn og/eller Speciale");
            return;
        }

        System.out.println("Creating: " + name + " / " + speciality); // CONTROLLER TEST

        try {
            tts.addGuide(name, speciality);
            refreshList();
            guideErrorLabel.setText("");

            System.out.println("After create, count: " + guides.size()); // CONTROLLER TEST
        } catch (SQLException e) {
            System.out.println("DB fejl ved opret: " + e.getMessage());
        }
    }

    @FXML
    private void deleteGuide() {
        if (selected == null) {
            guideErrorLabel.setText("Vælg en guide først");
            return;
        }

        System.out.println("Deleting: " + selected.getName()); // CONTROLLER TEST

        try {
            tts.removeGuide(selected.getName());
            refreshList();
            guideErrorLabel.setText("");
        } catch (SQLException e) {
            System.out.println("DB fejl ved slet: " + e.getMessage());
        }
    }

    @FXML
    private void saveChanges() {
        if (selected == null) {
            guideErrorLabel.setText("Vælg en guide først");
            return;
        }

        String newName = guideName.getText().trim();
        String newSpeciality = guideSpeciality.getText().trim();

        if (newName.isEmpty() || newSpeciality.isEmpty()) {
            guideErrorLabel.setText("Udfyld både navn og Speciale");
            return;
        }

        try {
            String originalName = selected.getName();

            System.out.println("Updating: " + originalName + " -> " + newName + " / " + newSpeciality); // CONTROLLER TEST

            if (!newName.equals(originalName)) {
                tts.setGuideName(originalName, newName);
            }

            if (!newSpeciality.equals(selected.getSpeciality())) {
                // hvis navnet er ændret, bruger newName som nøgle efter rename
                tts.setGuideSpeciality(newName, newSpeciality);
            }

            refreshList();
            guideErrorLabel.setText("");
        } catch (SQLException e) {
            System.out.println("DB fejl ved gem: " + e.getMessage());
        }
    }
}
