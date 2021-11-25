/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Inventory;
import Model.Parts;
import Model.Products;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ccgue
 */
public class AddProductMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    Products product;
    int productIndex;
    
    @FXML
    private TextField addProductIdText;

    @FXML
    private TextField addProductNameText;

    @FXML
    private TextField addProductInventoryText;

    @FXML
    private TextField addProductPriceText;

    @FXML
    private TextField addProductMaxText;

    @FXML
    private TextField addProductMinText;

    @FXML
    private TextField addProductSearchText;

    @FXML
    private TableView<Parts> addProductTopTableView;

    @FXML
    private TableColumn<Parts, Integer> addProductTopPartIdCol;

    @FXML
    private TableColumn<Parts, String> addProductTopPartNameCol;

    @FXML
    private TableColumn<Parts, Integer> addProductTopPartInventoryCol;

    @FXML
    private TableColumn<Parts, Double> addProductTopPartPriceCol;

    @FXML
    private TableView<Parts> addProductBottomTableView;

    @FXML
    private TableColumn<Parts, Integer> addProductBottomPartIdCol;

    @FXML
    private TableColumn<Parts, String> addProductBottomPartNameCol;

    @FXML
    private TableColumn<Parts, Integer> addProductBottomPartInventoryCol;

    @FXML
    private TableColumn<Parts, Double> addProductBottomPartPriceCol;
   

    @FXML
    void onActionAssociatePartToProduct(ActionEvent event) {  
       Parts part = addProductTopTableView.getSelectionModel().getSelectedItem();
       product.addAssociatedPart(part);
    }

    @FXML
    void onActionDisassociatePartFromProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected item? Click OK to proceed, otherwise click CANCEL.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            product.deleteAssociatedPart(addProductBottomTableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onActionExitAddProductMenu(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel, all text fields and selections will be deleted and not saved? Click OK to proceed, otherwise click CANCEL.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();   
        }
    }

    @FXML
    void onActionSaveAddedProductChanges(ActionEvent event) throws IOException {
        try{
            int productId = Integer.parseInt(addProductIdText.getText());
            String productName = addProductNameText.getText();
            int productStock = Integer.parseInt(addProductInventoryText.getText());
            double productPrice = Double.parseDouble(addProductPriceText.getText());
            int productMax = Integer.parseInt(addProductMaxText.getText());
            int productMin = Integer.parseInt(addProductMinText.getText());
            
            if(productMin > productMax){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Minimum stock cannot be greater than maximum.");
                alert.showAndWait();
            }
            else if(productStock < productMin || productStock > productMax){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setContentText("Inventory stock must be in between maximum and minimum inventory limits.");
                alert.showAndWait();
            }
                 
            product.setProductId(productId);
            product.setProductName(productName);
            product.setProductStock(productStock);
            product.setProductPrice(productPrice);
            product.setProductMax(productMax);
            product.setProductMin(productMin);
            
            Inventory.addProduct(product);
                  
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

    @FXML
    void onActionSearchForProduct(ActionEvent event) {
        try{
            int searchParameter = Integer.parseInt(addProductSearchText.getText());
            Parts foundPart= Inventory.lookupPart(searchParameter);
            addProductTopTableView.getSelectionModel().select(foundPart);
            addProductBottomTableView.getSelectionModel().select(foundPart);

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No match found.");
            alert.setContentText("Please enter a valid ID number or name of part.");
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
        addProductTopTableView.setItems(Inventory.getAllParts());
        
        addProductTopPartIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        addProductTopPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addProductTopPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        addProductTopPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        
        product = new Products(0, null, 0, 0.0, 0, 0);
        addProductBottomTableView.setItems(product.getAllAssociatedParts());
        
        addProductBottomPartIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        addProductBottomPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addProductBottomPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        addProductBottomPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        // TODO
    }    
    
}
