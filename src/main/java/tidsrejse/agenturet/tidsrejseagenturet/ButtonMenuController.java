package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import systems.TimeTravelSystem;

public class ButtonMenuController {

    private final FxmlLoader loader = new FxmlLoader();
    private final TimeTravelSystem tts = new TimeTravelSystem();

    @FXML
    private BorderPane mainPane;

    @FXML
    private void handleButtonFrontPageAction (ActionEvent event){
        loadIntoCenter("frontPage");
    }

    @FXML
    private void handleButtonCustomerAction (ActionEvent event){
        loadIntoCenter("customer");
    }

    @FXML
    private void handleButtonTimeMachinesAction (ActionEvent event){
        loadIntoCenter("timeMachines");
    }

    @FXML
    private void handleButtonTimePeriodsAction (ActionEvent event){
        loadIntoCenter("timePeriods");
    }

    @FXML
    private void handleButtonBookingAction (ActionEvent event){
        loadIntoCenter("booking");
    }

    @FXML
    public void handleButtonGuidesAction(ActionEvent actionEvent)  {
        loadIntoCenter("guides");
    }

    private void loadIntoCenter (String pageName) {
        Pane view = loader.getPage(pageName);
        if (view == null){
            System.out.println("Kunne ikke loade " + pageName + ".fxml");
            return;
        }
        mainPane.setCenter(view);
    }

    @FXML
    private void initialize(){
        loadIntoCenter("frontPage");
    }
}