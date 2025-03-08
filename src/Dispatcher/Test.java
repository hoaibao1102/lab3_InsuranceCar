/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dispatcher;

import Bussiness.CarManager;
import Bussiness.InsuranceManager;
import Model.Car;
import Tool.Acceptable;
import Tool.Inputter;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author MSI PC
 */
public class Test {
    public static void main(String[] args) {
        Inputter ndl = new Inputter();
        int choice = 0;
        CarManager carList = new CarManager();
        InsuranceManager insuranceList = new InsuranceManager();
        boolean continueChoice = true;
        
        // Biến kiểm tra nếu có thay đổi chưa lưu
        boolean unsavedChanges = false;
        
        Car newC = ndl.inputCarInfo(false,carList);
                        carList.addNew(newC);
                        System.out.println("Registered successfully");
            
                        String choiceSave = ndl.getString("Are you  want to save this registration? (Y/N): ");
                        if (choiceSave.trim().toUpperCase().equals("Y")) {
                            carList.saveToFileCarInfo();
                            System.out.println("The registration has been successfully save in file [carInfo.dat].");
                            unsavedChanges = false;
                        } else {
                            System.out.println("Car save canceled. Returning to main menu.");
                            unsavedChanges = true;
                        }

                        
        String carLicense = ndl.getString("Enter the license plate number of the car they want to search for: ");
        Car car = carList.searchCarByLicense(carLicense);
        
       
       
    }
}
