/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author MSI PC
 */
public class Insurance implements Serializable{
    private static final long serialVersionUID = 1L;

    private String insuId;
    private String licensePlate;
    private String establishDate;
    private String insuPeriod;
    private String insuFees;
    private String insuOwner;
    private String contractId;
    
    public Insurance() {
    }

    public Insurance(String contractId,String insuId, String licensePlate, String establishDate, String insuPeriod, String insuFees, String insuOwner) {
        this.insuId = insuId;
        this.licensePlate = licensePlate;
        this.establishDate = establishDate;
        this.insuPeriod = insuPeriod;
        this.insuFees = insuFees;
        this.insuOwner = insuOwner;
        this.contractId = contractId;
    }

    public String getInsuId() {
        return insuId;
    }

    public void setInsuId(String insuId) {
        this.insuId = insuId;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    public String getInsuPeriod() {
        return insuPeriod;
    }

    public void setInsuPeriod(String insuPeriod) {
        this.insuPeriod = insuPeriod;
    }

    public String getInsuFees() {
        return insuFees;
    }

    public void setInsuFees(String insuFees) {
        this.insuFees = insuFees;
    }

    public String getInsuOwner() {
        return insuOwner;
    }

    public void setInsuOwner(String insuOwner) {
        this.insuOwner = insuOwner;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
    
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#,###");
        return String.format("| %-3s | %-10s | %-15s | %-15s | %-15s | %-10s | $%-11s |"
                , this.contractId, this.insuId,this.licensePlate, this.insuOwner, this.insuPeriod,df.format(this.insuFees)) ;
    }

    
}
