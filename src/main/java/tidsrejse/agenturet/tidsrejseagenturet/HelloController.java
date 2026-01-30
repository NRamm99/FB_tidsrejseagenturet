package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class HelloController {

    private final FxmlLoader loader = new FxmlLoader();

    @FXML
    private BorderPane mainPane;

    @FXML
    private void handleButtonFrontPageAction (ActionEvent event){
        loadIntoCenter("FrontPage");
    }

    @FXML
    private void handleButtonCustomerAction (ActionEvent event){
        loadIntoCenter("Customer");
    }

    @FXML
    private void handleButtonTimeTravelMachinesAction (ActionEvent event){
        loadIntoCenter("TimeTravelMachines");
    }

    @FXML
    private void handleButtonTimePeriodsAction (ActionEvent event){
        loadIntoCenter("TimePeriods");
    }

    @FXML
    private void handleButtonBookingAction (ActionEvent event){
        loadIntoCenter("Booking");
    }

    private void loadIntoCenter(String pageName){
        Pane view = loader.getPage(pageName);
        if (view == null){
            System.out.println("Kunne ikke loade " + pageName + ".fxml");
            return;
        }
        mainPane.setCenter(view);

    }
}
