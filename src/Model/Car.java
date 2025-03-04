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
public class Car implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private String licensePlate;
    private String owner;
    private String phoneNum;
    private String brand;
    private String valueVehicle;
    private String numberOfSeats;
    private String registrationDate;
    private String registrationPlace;

    public Car() {
    }

    
    public Car(String licensePlate, String owner, String phoneNum, String brand, String valueVehicle, String numberOfSeats, String registrationDate) {
        this.licensePlate = licensePlate;
        this.owner = owner;
        this.phoneNum = phoneNum;
        this.brand = brand;
        this.valueVehicle = valueVehicle;
        this.numberOfSeats = numberOfSeats;
        this.registrationDate = registrationDate;
        this.registrationPlace = setRegistrationPlace();
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getValueVehicle() {
        return valueVehicle;
    }

    public void setValueVehicle(String valueVehicle) {
        this.valueVehicle = valueVehicle;
    }

    public String getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(String numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String setRegistrationPlace() {
        char a = getLicensePlate().trim().charAt(2);
        String registrationPlace ="";
        switch (a) {
            case 'X' :
                registrationPlace = "Thu Duc";
                break;
            case 'S':
                registrationPlace = "Binh Thanh";
                break;
            case 'T':
                registrationPlace = "District 1";
                break;
            case 'V':
               registrationPlace = "Go Vap";
               break;
        }       
        return this.registrationPlace = registrationPlace;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("$#,### ");
        return String.format(
                  "License plate    : %s%n"
                + "Owner            : %s%n"
                + "Phone            : %s%n"
                + "Car brand        : %s%n"
                + "Value of vehicle : %s%n"
                + "Number of seats  : %s%n"
                + "Registration date: %s%n"
                ,
            getLicensePlate().toUpperCase(), getOwner().toUpperCase(), getPhoneNum(), getBrand().toUpperCase(), df.format(Double.parseDouble(getValueVehicle())), getNumberOfSeats(), getRegistrationDate());
    }
 
    
}
