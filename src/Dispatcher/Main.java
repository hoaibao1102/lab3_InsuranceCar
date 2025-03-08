/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dispatcher;

import Bussiness.CarManager;
import Bussiness.InsuranceManager;
import Model.Car;
import Model.Insurance;
import Tool.Inputter;

/**
 *
 * @author MSI PC
 */
public class Main {

    public static void main(String[] args) {
        Inputter ndl = new Inputter();
        int choice = 0;
        CarManager carList = new CarManager();
        InsuranceManager insuranceList = new InsuranceManager();
        boolean continueChoice = true;
        
        // Biến kiểm tra nếu có thay đổi chưa lưu
        boolean unsavedChanges = false;
        
        do {
            choice = ndl.getInt("=====Car insurance Management=====\n"
                              + "1. Add new car\n"
                              + "2. Find a car by license plate\n"
                              + "3. Update car information\n"
                              + "4. Delete car information\n"
                              + "5. Add an insurance statement\n"
                              + "6. List of insurance statements\n"
                              + "7. Report uninsured cars\n"
                              + "8. Save data\n"
                              + "9. Quit\n"
                              + "ENTER YOUR CHOICE:   ");
            System.out.println();
            
            switch (choice) {
                case 1:
                    do {
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

                        String input1 = ndl.getString("Do you want to continue your registration ?\n"
                                + "  [1] YES     [2]NO\n"
                                + "ENTER YOUR CHOICE: ");
                        if (!input1.trim().equalsIgnoreCase("1")) {
                            continueChoice = false;
                        }
                    } while (continueChoice);
                    continueChoice = true;
                    
                    System.out.println();
                    break;
                    
                case 2:
                    do {
                        String carLicense = ndl.getString("Enter the license plate number of the car they want to search for: ");
                        carList.searchCar(carLicense);
                        System.out.println();

                        String input1 = ndl.getString("Do you want to continue looking for another car? ?\n"
                                + "  [1] YES     [2]NO\n"
                                + "ENTER YOUR CHOICE: ");
                        if (!input1.trim().equalsIgnoreCase("1")) {
                            continueChoice = false;
                        }
                    } while (continueChoice);
                    continueChoice = true;
                    
                    System.out.println();
                    break;

                case 3:
                    do {
                        String carUpdate = ndl.getString("Enter the License plate of car which you want to update: ");
                        Car carExist = carList.searchCarByLicense(carUpdate);
                        if (carExist == null) {
                            System.out.println("This vehicle does not exist .");
                        } else {
                            Car updateCar = ndl.inputCarInfo(true,carList);
                            updateCar.setLicensePlate(carUpdate);
                            carList.updateInfoCar(updateCar);
                            
                            String choiceS = ndl.getString("Do you want to save this change(Y/N): ");
                            if (choiceS.equalsIgnoreCase("y")) {
                                carList.saveToFileCarInfo();
                                System.out.println("Save data successfully");
                                unsavedChanges = false;
                            } else {
                                System.out.println("data not save");
                                unsavedChanges = true;
                            }
                        }
                        System.out.println();
                        String input1 = ndl.getString("Do you want to continue to receive other updates ?\n"
                                + "  [1] YES     [2]NO\n"
                                + "ENTER YOUR CHOICE: ");
                        if (!input1.trim().equalsIgnoreCase("1")) {
                            continueChoice = false;
                        }
                    } while (continueChoice);
                    continueChoice = true;
                    
                    System.out.println();
                    break;
                    
                case 4:
                    Car carExist2 = new Car();
                    
                    do {       
                        String carDelete = ndl.getString("Enter the License plate of car which you want to delete: ");
                        carExist2 = carList.searchCarByLicense(carDelete);
                        if(carExist2 == null){
                            System.out.println(" car not exist");
                        }else{
                            carList.searchCar(carDelete);
                        }
                    } while (carExist2 == null);
                    
                    String choiceDelete = ndl.getString("Are you sure you want to delete this registration? (Y/N): ");
                    if(choiceDelete.trim().toUpperCase().equals("Y")){
                        carList.deleteCarByLicense(carExist2);
                        System.out.println(" The registration has been successfully deleted.");
                        
                        String choiceS = ndl.getString("Do you want to save this change(Y/N): ");
                        if(choiceS.equalsIgnoreCase("y")){
                            carList.saveToFileCarInfo();
                            unsavedChanges = false;
                        }else{
                            System.out.println("data not save");
                            unsavedChanges = true;
                        }
                        
                    }else{
                        System.out.println("Car deletion canceled. Returning to main menu.");
                    }
                    System.out.println();
                    break;

                case 5:
                    do {
                        Insurance newI = ndl.inputInsuranceInfo(insuranceList,carList);
                        insuranceList.addNewContract(newI);
                        System.out.println("Registered successfully");
                        System.out.println();
                        String choiceSave = ndl.getString("Are you  want to save this registration? (Y/N): ");
                        if (choiceSave.trim().toUpperCase().equals("Y")) {
                            insuranceList.saveToFileInsurance();
                            System.out.println("The registration has been successfully save in file [insurances.dat].");
                            unsavedChanges = false;
                        } else {
                            System.out.println("Car save canceled. Returning to main menu.");
                            unsavedChanges = true;
                        }

                        String input1 = ndl.getString("Do you want to continue your registration ?\n"
                                + "  [1] YES     [2]NO\n"
                                + "ENTER YOUR CHOICE: ");
                        if (!input1.trim().equalsIgnoreCase("1")) {
                            continueChoice = false;
                        }
                    } while (continueChoice);
                    continueChoice = true;

                    System.out.println();
                    break;

                case 6:
                    String yearChoice = ndl.getString("Enter the year they want to list: ");
                    insuranceList.displayFollowYear(yearChoice);
                    System.out.println();
                    break;

                case 7:
                    insuranceList.reportUninsuredCars(carList);
                    System.out.println();
                    break;
                    
                case 8:
                    boolean choiceSmall = false;
                    do {
                        int choiceSave = ndl.getInt("Choose which data to save:\n"
                                + " 1. Save customer data.\n"
                                + " 2. Save order data. \n"
                                + " 3. Save both.   \n"
                                + " 4. Do not save.  \n"
                                + "Enter your choice (1, 2, 3, or 4):  ");

                        switch (choiceSave) {
                            case 1:
                                carList.saveToFileCarInfo();
                                System.out.println("Registration data has been successfully saved to `carInfo.dat`");
                                break;
                            case 2:
                                insuranceList.saveToFileInsurance();
                                System.out.println("Registration data has been successfully saved to `insurances.dat`");
                                break;
                            case 3:
                                carList.saveToFileCarInfo();
                                insuranceList.saveToFileInsurance();
                                System.out.println("Saved both files [carInfo.dat] and [insurances.dat] ");
                                unsavedChanges = false;
                                break;
                            case 4:
                                System.out.println("No data saved.");
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                choiceSmall = true;
                                break;
                        }
                    } while (choiceSmall);

                    System.out.println();
                    break;

                case 9:
                    // Nếu người dùng chọn thoát, kiểm tra xem có thay đổi chưa lưu không
                    if (unsavedChanges) {
                        String response = ndl.getString("You have unsaved changes. Do you want to save before exiting? \n"
                                                      + "  [1] YES     [2]NO\n"
                                                      + "ENTER YOUR CHOICE: ");
                        if (response.equalsIgnoreCase("1")) {
                            carList.saveToFileCarInfo();
                            insuranceList.saveToFileInsurance();
                            System.out.println("Exiting with saving.");
                            break; // Thoát chương trình
                        } else if (response.equalsIgnoreCase("2")) {
                            System.out.println("Exiting the program....");
                        }
                    } else {
                        System.out.println("Exiting the program....");
                        // Thoát chương trình nếu không có thay đổi
                    }
                    System.out.println("Bye...bye...");
                    break;

                default:
                    System.out.println("This function is not available. Please try again....");
                    System.out.println();
            }
        } while (choice != 9);
    }

}


