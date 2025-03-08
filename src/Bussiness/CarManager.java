/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussiness;

import Model.Car;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MSI PC
 */
public class CarManager extends HashMap<String, Car>{
    private String pathFile;
    private boolean save;
    private final String HEADER_TABLE = ("------------------------------------------------------------------------\n"),
                         FOOTER_TABLE = ("------------------------------------------------------------------------\n");

    public CarManager() {
        super();
        this.pathFile = "./carInfo.dat";
        this.save = true;
        readFromFileCarInfo();
    }
    
    public boolean isSave(){
        return save;
    }
    
    public void addNew(Car newCar){
        this.put(newCar.getLicensePlate(),newCar);
        this.save = false;
    }
    
   
    
    // tìm car bằng biển số xe
    public Car searchCarByLicense(String licensePlateInput){
        Car searchCar = null;
        searchCar =  this.get(licensePlateInput.trim().toUpperCase());
        return searchCar;
    }
    
    /**
     * @param licensePlateInput 
     * tìm kiếm và in ra xe tìm được khi nhập license plate
     */
    public void searchCar(String licensePlateInput) {
        Car car = searchCarByLicense(licensePlateInput);
        if (car != null) {
            display(car);                                                       
        } else {
            System.out.println("No car matches the search criteria!");
        }
    }

    public void updateInfoCar(Car newCar){
        Car oldCar = searchCarByLicense(newCar.getLicensePlate());
        if (oldCar == null) {
            System.out.println("This vehicle does not exist .");
        } else {
            oldCar.setOwner(newCar.getOwner().length() > 0 ? newCar.getOwner() : oldCar.getOwner());
            oldCar.setPhoneNum(newCar.getPhoneNum().length() > 0 ? newCar.getPhoneNum() : oldCar.getPhoneNum());
            oldCar.setBrand(newCar.getBrand().length() > 0 ? newCar.getBrand() : oldCar.getBrand());
            oldCar.setNumberOfSeats(newCar.getNumberOfSeats().length() > 0 ? newCar.getNumberOfSeats() : oldCar.getNumberOfSeats());
            this.save = false;
        }
    } 
    
    public void deleteCarByLicense(Car carDelete) {
        this.remove(carDelete.getLicensePlate());
        this.save = false;
    }
    
    public void display(){
        this.values().forEach(this::display);
    }
    
    public void display(Car car){
        System.out.println(HEADER_TABLE);
        System.out.println(car);
        System.out.print(FOOTER_TABLE);
    }
    
    
    
    public void saveToFileCarInfo() {
        FileOutputStream fos = null;
        try {

            // 1. Thu thập dữ liệu đăng kí hiện tại
            File f = new File(pathFile);

            //2. mở luồng file output
            fos = new FileOutputStream(f);

            //3.tạo đối tương output để tuần tự hóa dữ liệu
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Ghi các đối tượng Car vào file
            for (Car c : this.values()) {  // Lưu trữ từng đối tượng Car trong HashMap
                oos.writeObject(c);  // Ghi đối tượng Car vào file
            }
            // sau khi ghi xong thì đóng lại và lưu lại
            oos.close();
            this.save = true;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CarManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CarManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                // Đóng tất cả luồng để giải phóng tài nguyên
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(CarManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void readFromFileCarInfo(){
        FileInputStream fis = null;
        try { 
            File f = new File(pathFile);
            
            //kiểm tra sự tồn tại của file
            if(!f.exists()){
                System.out.println("File not found");
                return ;
            }
            //tạo đối tương đọc dữ liệu
            fis = new FileInputStream(f);
            
            //tạo đối tượng để đọc dữ liệu từ FILE
            ObjectInputStream ois= new ObjectInputStream(fis);
            
            //cho đọc dữ liệu
            while(fis.available() > 0){
                Car c = (Car) ois.readObject();
                this.put(c.getLicensePlate().toUpperCase(),c);
            }
            
            //sau khi đọc xong thì đóng và lưu lại
            ois.close();
            this.save = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CarManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CarManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CarManager.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                 if (fis != null) fis.close();
            } catch (IOException ex) {
                Logger.getLogger(CarManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
     
    
    
}
