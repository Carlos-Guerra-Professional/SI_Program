/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ccgue
 */
public class Outsourced extends Parts {
    
    private String companyName;

    public Outsourced(int partId, String partName, double partPrice, int partStock, int partMax, int partMin, String companyName) {
        super(partId, partName, partPrice, partStock, partMin, partMax);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
        
}
