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
public class ModifyProductMenuController implements Initializable {
    
    Stage stage;
    Parent scene;
    Products product;
    Products selectedProduct;
    int productIndex;

    @FXML
    private TextField modifyProductIdText;

    @FXML
    private TextField modifyProductNameText;

    @FXML
    private TextField modifyProductInventoryText;

    @FXML
    private TextField modifyProductPriceText;

    @FXML
    private TextField modifyProductMaxText;

    @FXML
    private TextField modifyProductMinText;

    @FXML
    private TextField modifyProductSearchText;
    
    @FXML
    private TableView<Parts> modifyProductTopTableView;

    @FXML
    private TableColumn<Parts, Integer>  modifyProductTopPartIdCol;

    @FXML
    private TableColumn<Parts, String> modifyProductTopPartNameCol;

    @FXML
    private TableColumn<Parts, Integer> modifyProductTopPartInventoryCol;

    @FXML
    private TableColumn<Parts, Double> modifyProductTopPartPriceCol;
    
    @FXML
    private TableView<Parts> modifyProductBottomTableView;

    @FXML
    private TableColumn<Parts, Integer> modifyProductBottomPartIdCol;

    @FXML
    private TableColumn<Parts, Integer> modifyProductBottomPartNameCol;

    @FXML
    private TableColumn<Parts, Integer> modifyProductBottomPartInventoryCol;

    @FXML
    private TableColumn<Parts, Double> modifyProductBottomPartPriceCol;

    @FXML
    void onActionAddAssociatedPartToProduct(ActionEvent event) {
       Parts part = modifyProductTopTableView.getSelectionModel().getSelectedItem();
       selectedProduct.addAssociatedPart(part);
    }

    @FXML
    void onActionDeleteAssociatedPartFromProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected item? Click OK to proceed, otherwise click CANCEL.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            selectedProduct.deleteAssociatedPart(modifyProductBottomTableView.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onActionExitModifiedProductMenu(ActionEvent event) throws IOException {
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
    void onActionSaveModifiedProductChanges(ActionEvent event) throws IOException {
        try{
            int productId = Integer.parseInt(modifyProductIdText.getText());
            String productName = modifyProductNameText.getText();
            int productStock = Integer.parseInt(modifyProductInventoryText.getText());
            double productPrice = Double.parseDouble(modifyProductPriceText.getText());
            int productMax = Integer.parseInt(modifyProductMaxText.getText());
            int productMin = Integer.parseInt(modifyProductMinText.getText());
                        
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
           
            ObservableList<Products> tempProductsList = Inventory.getAllProducts();
            Products tempProductIdentifier = Inventory.lookupProduct(productId);
            int tempIndex = tempProductsList.indexOf(tempProductIdentifier);
            productIndex = tempIndex;
            Inventory.updateProduct(productIndex, new Products(productId, productName, productStock, productPrice, productMax, productMin));
                       
            selectedProduct.setProductId(productId);
            selectedProduct.setProductName(productName);
            selectedProduct.setProductStock(productStock);
            selectedProduct.setProductPrice(productPrice);
            selectedProduct.setProductMax(productMax);
            selectedProduct.setProductMin(productMin); 
            
            Inventory.updateProduct(productIndex, selectedProduct);
            
                      
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
            int searchParameter = Integer.parseInt(modifyProductSearchText.getText());
            Parts foundPart= Inventory.lookupPart(searchParameter);
            modifyProductTopTableView.getSelectionModel().select(foundPart);
            modifyProductBottomTableView.getSelectionModel().select(foundPart);

        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No match found.");
            alert.setContentText("Please enter a valid ID number or name of part.");
            alert.showAndWait(); 
        }
    }
    
    public void sendProduct(Products product, int index){        
        modifyProductIdText.setText(String.valueOf(product.getProductId()));
        modifyProductNameText.setText(product.getProductName());
        modifyProductInventoryText.setText(String.valueOf(product.getProductStock()));
        modifyProductPriceText.setText(String.valueOf(product.getProductPrice()));
        modifyProductMaxText.setText(String.valueOf(product.getProductMax())); 
        modifyProductMinText.setText(String.valueOf(product.getProductMin()));
        
        selectedProduct = product;
        modifyProductBottomTableView.setItems(product.getAllAssociatedParts());
        
        modifyProductBottomPartIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        modifyProductBottomPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modifyProductBottomPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        modifyProductBottomPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modifyProductTopTableView.setItems(Inventory.getAllParts());
        
        modifyProductTopPartIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        modifyProductTopPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        modifyProductTopPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        modifyProductTopPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
        
        // TODO
    } 
}