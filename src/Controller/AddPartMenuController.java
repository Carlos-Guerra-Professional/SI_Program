/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Parts;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ccgue
 */
public class AddPartMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    int partIndex;
    Parts selectedPart;
    
    @FXML
    private RadioButton addPartInHouseRadioButton;

    @FXML
    private ToggleGroup addPartTG;

    @FXML
    private RadioButton addPartOutsourcedRadioButton;

    @FXML
    private Label addPartMachineOrCompanyLabel;

    @FXML
    private TextField addPartIdText;

    @FXML
    private TextField addPartNameText;

    @FXML
    private TextField addPartInventoryText;

    @FXML
    private TextField addPartPriceText;

    @FXML
    private TextField addPartMaxText;

    @FXML
    private TextField addPartMinText;

    @FXML
    private TextField addPartMachineOrCompanyText;
    
    @FXML
    void OnActionInHouseSelected(ActionEvent event) {
        addPartMachineOrCompanyLabel.setText("Machine ID:");
    }

    @FXML
    void OnActionOutsourcedSelected(ActionEvent event) {
        addPartMachineOrCompanyLabel.setText("Company Name:");
    }


    @FXML
    void onActionExitAddPartMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel, all text fields will be deleted and not saved? Click OK to proceed, otherwise click CANCEL.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();   
        }        
    }
    
    @FXML
    void onActionSaveAddPart(ActionEvent event) throws IOException { 
        try{
            
            int partId = Integer.parseInt(addPartIdText.getText());
            String partName = addPartNameText.getText();
            double partPrice = Double.parseDouble(addPartPriceText.getText());
            int partStock = Integer.parseInt(addPartInventoryText.getText());
            int partMax = Integer.parseInt(addPartMaxText.getText());
            int partMin = Integer.parseInt(addPartMinText.getText());
            
            
            if(partMin > partMax){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Minimum stock cannot be greater than maximum.");
                alert.showAndWait();
            }
            else if(partStock < partMin || partStock > partMax){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory stock must be in between maximum and minimum inventory limits.");
                alert.showAndWait();
            }
            else { 
                if(addPartInHouseRadioButton.isSelected()) {
                    int machineId = Integer.parseInt(addPartMachineOrCompanyText.getText());
                    ObservableList<Parts> tempPartsList = Inventory.getAllParts();
                    Parts tempPartIdentifier = Inventory.lookupPart(partId);
                    int tempIndex = tempPartsList.indexOf(tempPartIdentifier);
                    partIndex = tempIndex;
                    Inventory.updatePart(partIndex, new InHouse(partId, partName, partPrice, partStock, partMax, partMin, machineId));
                    }
                if(addPartOutsourcedRadioButton.isSelected()) {
                    String companyName = addPartMachineOrCompanyText.getText();
                    ObservableList<Parts> tempPartsList = Inventory.getAllParts();
                    Parts tempPartIdentifier = Inventory.lookupPart(partId);
                    int tempIndex = tempPartsList.indexOf(tempPartIdentifier);
                    partIndex = tempIndex;
                    Inventory.updatePart(partIndex, new Outsourced(partId, partName, partPrice, partStock, partMax, partMin, companyName));
                    }
            }
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
                           
        }
            
        catch(NumberFormatException e){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please enter a valid value for each text field!");
            alert.showAndWait();
            
        }
        
              
    }
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
