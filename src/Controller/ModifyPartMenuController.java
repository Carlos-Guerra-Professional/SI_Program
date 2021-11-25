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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ccgue
 */
public class ModifyPartMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    int partIndex;
    Parts selectedPart;

    @FXML
    private RadioButton modifyPartInHouseRadioButton;

    @FXML
    private RadioButton modifyPartOutsourcedRadioButton;

    @FXML
    private Label modifyPartMachineOrCompanyLabel;

    @FXML
    private TextField modifyPartIdText;

    @FXML
    private TextField modifyPartNameText;

    @FXML
    private TextField modifyPartInventoryText;

    @FXML
    private TextField modifyPartPriceText;

    @FXML
    private TextField modifyPartMaxText;

    @FXML
    private TextField modifyPartMinText;

    @FXML
    private TextField modifyPartMachineOrCompanyText;
    
    @FXML
    void OnActionInHouseSelected(ActionEvent event) {
        modifyPartMachineOrCompanyLabel.setText("Machine ID:");

    }

    @FXML
    void OnActionOutsourcedSelected(ActionEvent event) {
       modifyPartMachineOrCompanyLabel.setText("Company Name:");
    }

    @FXML
    void onActionExitModifyPartMenu(ActionEvent event) throws IOException {
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
    void onActionSaveModifiedPart(ActionEvent event) throws IOException {
        try{
            int partId = Integer.parseInt(modifyPartIdText.getText());
            String partName = modifyPartNameText.getText();
            double partPrice = Double.parseDouble(modifyPartPriceText.getText());
            int partStock = Integer.parseInt(modifyPartInventoryText.getText());
            int partMax = Integer.parseInt(modifyPartMaxText.getText());
            int partMin = Integer.parseInt(modifyPartMinText.getText());
            
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
                if(modifyPartInHouseRadioButton.isSelected()) {
                    int machineId = Integer.parseInt(modifyPartMachineOrCompanyText.getText());
                    ObservableList<Parts> tempPartsList = Inventory.getAllParts();
                    Parts tempPartIdentifier = Inventory.lookupPart(partId);
                    int tempIndex = tempPartsList.indexOf(tempPartIdentifier);
                    partIndex = tempIndex;
                    Inventory.updatePart(partIndex, new InHouse(partId, partName, partPrice, partStock, partMax, partMin, machineId));
                    }
                if(modifyPartOutsourcedRadioButton.isSelected()) {
                    String companyName = modifyPartMachineOrCompanyText.getText();
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
 
    
    public void sendPart(Parts part, int index){        
        modifyPartIdText.setText(String.valueOf(part.getPartId()));
        modifyPartNameText.setText(part.getPartName());
        modifyPartInventoryText.setText(String.valueOf(part.getPartStock()));
        modifyPartPriceText.setText(String.valueOf(part.getPartPrice()));
        modifyPartMinText.setText(String.valueOf(part.getPartMin()));
        modifyPartMaxText.setText(String.valueOf(part.getPartMax()));     
            
        if(part instanceof InHouse) {
            modifyPartMachineOrCompanyText.setText(String.valueOf(((InHouse) part).getMachineId()));
            modifyPartInHouseRadioButton.setSelected(true);
            modifyPartOutsourcedRadioButton.setSelected(false);
            modifyPartMachineOrCompanyLabel.setText("Machine ID:");
               
        }
        else {
            modifyPartMachineOrCompanyText.setText(((Outsourced) part).getCompanyName());
            modifyPartOutsourcedRadioButton.setSelected(true);
            modifyPartInHouseRadioButton.setSelected(false);
            modifyPartMachineOrCompanyLabel.setText("Company Name:");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {}
        // TODO
}
        
    

