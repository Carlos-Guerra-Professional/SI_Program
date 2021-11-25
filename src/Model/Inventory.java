/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ccgue
 */
public class Inventory {
    
    public static boolean noMatch;
    
    
    private static ObservableList<Parts> partsList = FXCollections.observableArrayList();
    private static ObservableList<Products> productsList = FXCollections.observableArrayList();

    public static void addPart(Parts newPart){
        partsList.add(newPart);        
        
    }
    
    public static void addProduct(Products newProduct) {
        productsList.add(newProduct);
    }
    
    public static Parts lookupPart(int partId) {
        for(Parts part : getAllParts()){
            if(part.getPartId() == partId) {
                return part;
            }
        }   
        return null;
    }
    
    public static Products lookupProduct(int productId) {
        for(Products product : getAllProducts()) {
            if(product.getProductId() == productId){
                return product;
            }
        }
        return null;
    }
        
    
    public static ObservableList<Parts> lookupPart(String partName) {
        ObservableList<Parts> filteredParts = FXCollections.observableArrayList();
        for(Parts part : getAllParts()){
            if(part.getPartName().contains(partName)) {
                filteredParts.add(part);
            }
        if(filteredParts.isEmpty()){
            return getAllParts();
        }
        else {
            return filteredParts;
        }
        }
        return null;
    }
    
    public static ObservableList<Products> lookupProduct(String productName) {
        ObservableList<Products> filteredProducts = FXCollections.observableArrayList();
        for(Products product : getAllProducts()) {
            if(product.getProductName().contains(productName)){
                filteredProducts.add(product);
            }
        if(filteredProducts.isEmpty()){
            return getAllProducts();
        }
        else {
            return filteredProducts;
        }
        }
        return null;
    }
    
    public static void updatePart(int index, Parts selectedPart){
        partsList.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Products selectedProduct) {
        productsList.set(index, selectedProduct);
    }
    
    public static void deletePart(Parts selectedPart) {
        partsList.remove(selectedPart);
    }
    
    public static void deleteProduct(Products selectedProduct) {
        productsList.remove(selectedProduct);    
    }
    
    public static ObservableList<Parts> getAllParts() {
        return partsList;
    }

    public static ObservableList<Products> getAllProducts() {
        return productsList;
    }
   
}
