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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author ccgue
 */
public class MainMenuController implements Initializable {
    
    Stage stage;
    Parent scene;  
       
    @FXML
    private Label label;

    @FXML
    private TextField mainMenuPartsSearchText;
    
    @FXML
    private TableView<Parts> mainMenuPartsTable;

    @FXML
    private TableColumn<Parts, Integer> partIdCol;

    @FXML
    private TableColumn<Parts, String> partNameCol;

    @FXML
    private TableColumn<Parts, Integer> partInventoryCol;

    @FXML
    private TableColumn<Parts, Double> partPriceCol;

    @FXML
    private TextField mainMenuProductsSearchText;
    
    @FXML
    private TableView<Products> mainMenuProductsTable;

    @FXML
    private TableColumn<Products, Integer> productIdCol;

    @FXML
    private TableColumn<Products, String> productNameCol;

    @FXML
    private TableColumn<Products, Integer> productInventoryCol;

    @FXML
    private TableColumn<Products, Double> productPriceCol;
    
    @FXML
    void onActionDeletePartFromMainMenu(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected item? Click OK to proceed, otherwise click CANCEL.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Inventory.deletePart(mainMenuPartsTable.getSelectionModel().getSelectedItem());   
        }
    }    
    
    @FXML
    void onActionDeleteProductFromMainMenu(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete selected item? Click OK to proceed, otherwise click CANCEL.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Inventory.deleteProduct(mainMenuProductsTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    void onActionDisplayAddPartsMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show(); 
    
    }

    @FXML
    void onActionDisplayAddProductsMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show(); 
    }

    @FXML
    void onActionDisplayModifyPartsMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyPartMenu.fxml"));
        loader.load();
        
        ModifyPartMenuController MPARTMController = loader.getController();
        MPARTMController.sendPart(mainMenuPartsTable.getSelectionModel().getSelectedItem(), mainMenuPartsTable.getSelectionModel().getSelectedIndex());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDisplayModifyProductsMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyProductMenu.fxml"));
        loader.load();
        
        ModifyProductMenuController MPRODUCTMController = loader.getController();
        MPRODUCTMController.sendProduct(mainMenuProductsTable.getSelectionModel().getSelectedItem(), mainMenuProductsTable.getSelectionModel().getSelectedIndex());
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionExitInventoryProgram(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionSearchParts(ActionEvent event) {
        try{
            int searchParameter = Integer.parseInt(mainMenuPartsSearchText.getText());
            Parts foundPart= Inventory.lookupPart(searchParameter);
            mainMenuPartsTable.getSelectionModel().select(foundPart);
        }
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No match found.");
            alert.setContentText("Please enter a valid product ID number.");
            alert.showAndWait(); 
        }
    }


    @FXML
    void onActionSearchProducts(ActionEvent event) {
        try{   
            int searchParameter = Integer.parseInt(mainMenuProductsSearchText.getText());
            Products foundProduct= Inventory.lookupProduct(searchParameter);
            mainMenuProductsTable.getSelectionModel().select(foundProduct);
        }
        
        catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No match found.");
            alert.setContentText("Please enter a valid product ID number.");
            alert.showAndWait(); 
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        mainMenuPartsTable.setItems(Inventory.getAllParts());
        mainMenuProductsTable.setItems(Inventory.getAllProducts());
        
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("partStock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice")); 
        
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("productStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice")); 
       
    }    
        
}
