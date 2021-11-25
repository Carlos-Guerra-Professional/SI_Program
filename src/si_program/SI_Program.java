/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package si_program;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Parts;
import Model.Products;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ccgue
 */
public class SI_Program extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainMenu.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Parts part1 = new InHouse(1, "Screw", 0.10, 10000, 100000, 1000, 101);
        Parts part2 = new Outsourced(2, "Nut", 0.20, 25000, 200000, 1000, "Wholesalers-R-Us");
                
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        
        Products product1 = new Products(1, "Fan", 1000, 10.00, 100000, 1000);
        Products product2 = new Products(2, "Toaster", 25000, 20.00, 200000, 1000);
        
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
                
        launch(args);
    }
    
}

