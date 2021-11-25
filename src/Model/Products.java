/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ccgue
 */
public class Products {
    
    private ObservableList<Parts> associatedParts = FXCollections.observableArrayList();
    
    private int productId;
    private String productName;
    private int productStock;
    private double productPrice;
    private int productMax;   
    private int productMin;

    public Products(int productId, String productName, int productStock, double productPrice, int productMax, int productMin) {
        this.productId = productId;
        this.productName = productName;
        this.productStock = productStock;
        this.productPrice = productPrice;
        this.productMax = productMax;
        this.productMin = productMin;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public int getProductMin() {
        return productMin;
    }

    public int getProductMax() {
        return productMax;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    public void setProductMin(int productMin) {
        this.productMin = productMin;
    }

    public void setProductMax(int productMax) {
        this.productMax = productMax;
    }
    
    public void addAssociatedPart(Parts part){
        associatedParts.add(part);          
    }
    
    public void deleteAssociatedPart(Parts part){
        associatedParts.remove(part);
    }
    
    public ObservableList<Parts> getAllAssociatedParts(){
        return associatedParts;
    }
    
}
