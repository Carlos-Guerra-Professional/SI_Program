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
public class InHouse extends Parts {
    
    private int machineId;

    public InHouse(int partId, String partName, double partPrice, int partStock, int partMax, int partMin, int machineId) {
        super(partId, partName, partPrice, partStock, partMin, partMax);
        this.machineId = machineId;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
    
    
    
}
