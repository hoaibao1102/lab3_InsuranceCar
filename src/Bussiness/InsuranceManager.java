/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bussiness;

import Model.Car;
import Model.Insurance;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MSI PC
 */
public class InsuranceManager extends HashMap<String, Insurance>{
    private String pathFile;
    private boolean save;
    private final String HEADER_TABLE = ("====================================================================================================="),
                         FOOTER_TABLE = ("-----------------------------------------------------------------------------------------------------");

    public InsuranceManager() {
        super();
        this.pathFile = "./insurances.dat";
        this.save = true;
        readFromInsuranceFile();
 
    }
    
    //HÀM 5
    public void addNewContract(Insurance newIn) {
            // Thêm vào hệ thống nếu ID chưa tồn tại
            this.put(newIn.getInsuId(), newIn);
    }
    
    
    /***
     * kiểm tra id đưa vào có tồn tại không
     * có trả về true, không trả về false
     * @param idInput
     * @return 
     */
    
    public String generateContractId() {
        return String.valueOf(this.size()+1);
    }
    
    /***
     * kiểm tra licensePlate có tồn tại trong list không
     * có trả về true, không trả về false
     * @param licensePlate
     * @return 
     */
    public boolean isCarInsured(String licensePlate) {
        boolean result = false;
        for (Insurance value : this.values()) {
            if(value.getLicensePlate().equalsIgnoreCase(licensePlate)){
                result = true;
            }
        }
        return result;
    }

    //tính phí bảo hiểm theo giá trị xe
    public double calculateInsuranceFees(String valueVehicle, int insuPeriodNum) {
        double result = 0;
        double valueVe = Double.parseDouble(valueVehicle);
        switch (insuPeriodNum) {
            case 12:
                result = valueVe * 0.25;
                break;
            case 24:
                result = valueVe * 0.2 * 2;
                break;
            case 36:
                result = valueVe * 0.15 *3;
                break;
        }
        return result ;
    }
    
    
    
    //HÀM 6
    
    /**
     * Kiểm tra định dạng năm (4 chữ số). 
     * Duyệt qua danh sách hợp đồng bảo hiểm
     * và thêm vào match nếu năm trong establishDate khớp. 
     * Nếu có hợp đồng, sắp xếp theo ngày và hiển thị danh sách bằng display1().
     * 
     * @param year 
     */
    public void displayFollowYear(String year){
        List<Insurance> match = new ArrayList();
        // Kiểm tra định dạng năm (chỉ nhận số có 4 chữ số)
        if (!year.matches("^\\d{4}$")) {
            System.out.println("Invalid year format! Please enter a 4-digit year.");
        }else{
            for (Insurance i : this.values()) {
                if(i.getEstablishDate().contains(year)){
                    match.add(i);
                }
            }
            if(match.isEmpty()){
                System.out.println("There are no statements in this year.");
            }else{
                match.sort(Comparator.comparing(Insurance::getEstablishDate));
                //display nó ra 
                display1(match, year);
            }
        }

    }
    
    public void display1(List<Insurance> insuranceList, String year) {
        System.out.println(HEADER_TABLE);
        System.out.println("Report: INSURANCE STATEMENTS                    ");
        System.out.println("From  : 01/01/" + year + "  To: 12/31/" + year);
        System.out.println("Sorted by: Established Date");
        System.out.println("Sort type: ASC");
        System.out.println();
        System.out.println(FOOTER_TABLE);
        System.out.printf("| %-3s | %-10s | %-15s | %-15s | %-15s | %-10s | %-9s |\n",
                "No.", "Insurance ID", "Established Date", "License Plate", "Customer", "Period", "Fees");
        System.out.println(FOOTER_TABLE);

        int index = 1;
        DecimalFormat df = new DecimalFormat("$#,###");
        for (Insurance i : insuranceList) {
            System.out.printf("| %-3d | %-12s | %-16s | %-15s | %-15s | %-10s | %-9s |\n",
                    index++, i.getInsuId(), i.getEstablishDate(),
                    i.getLicensePlate(), i.getInsuOwner().toUpperCase(),
                    i.getInsuPeriod(), df.format(Double.parseDouble(i.getInsuFees())));
        }

        System.out.println(FOOTER_TABLE);
    }
    
    
    //HÀM 7
    
    /**
     * Khởi tạo danh sách uninsuredCars và checkedCars.
     * Duyệt qua danh sách xe (CarManager c)
     * Kiểm tra xem mỗi xe đã được kiểm tra hay chưa (bằng cách dùng checkedCars để lưu trạng thái)
     * Nếu chưa kiểm tra, kiểm tra xem xe có hợp đồng bảo hiểm không.
     * Nếu không có bảo hiểm, thêm xe vào danh sách uninsuredCars.
     * Kiểm tra xem danh sách xe chưa có bảo hiểm có rỗng không.
     * Nếu có xe chưa có bảo hiểm, sắp xếp danh sách theo giá trị xe giảm dần.
     * Hiển thị danh sách xe chưa có bảo hiểm bằng phương thức display2().
     * @param c 
     */
    public void reportUninsuredCars(CarManager c) {
        
        // Tạo danh sách xe chưa có bảo hiểm
        List<Car> uninsuredCars = new ArrayList<>();
        HashMap<String, Boolean> checkedCars = new HashMap<>();
        for (Car car : c.values()) {
            if (!checkedCars.containsKey(car.getLicensePlate().toLowerCase().trim())) { // Nếu chưa kiểm tra xe này
                boolean existC = false;
                for (Insurance ins : this.values()) {
                    if (ins.getLicensePlate().equalsIgnoreCase(car.getLicensePlate())) {
                        existC = true;
                        break;
                    }
                }
                if (!existC) {
                    uninsuredCars.add(car);
                }
                checkedCars.put(car.getLicensePlate().toLowerCase().trim(), true); // Đánh dấu đã kiểm tra
            }
        }

    if(uninsuredCars.isEmpty()){
        System.out.println("No information available");
    } else {
        uninsuredCars.sort(Comparator.comparing(Car::getValueVehicle).reversed());
        // display nó ra
        display2(uninsuredCars);
    }
    }
    
    
    public void display2(List<Car> uninsuredCars) {
        System.out.println(HEADER_TABLE);
        System.out.println("Report:UNINSURED CARS                    ");
        System.out.println();
        System.out.println("Sorted by: Vehicle type");
        System.out.println("Sort type:  DESC");
        System.out.println();
        System.out.println(FOOTER_TABLE);
        System.out.printf("| %-3s | %-10s | %-15s | %-15s | %-10s | %-10s | %-12s |\n",
                "No.", "License plate", "Registration Date", "Vehicle Owner", "Brand", "Number of seats", "Value of vehicle");
        System.out.println(FOOTER_TABLE);

        int index = 1;
        DecimalFormat df = new DecimalFormat("#,###");
        for (Car i : uninsuredCars) {
            System.out.printf("| %-3d | %-13s | %-17s | %-15s | %-10s | %-15s | $%-15s |\n",
                    index++, i.getLicensePlate().toUpperCase(), i.getRegistrationDate(),
                    i.getOwner().toUpperCase(), i.getBrand().toUpperCase(),
                    i.getNumberOfSeats(), df.format(Double.parseDouble(i.getValueVehicle())));
        }
        System.out.println(FOOTER_TABLE);
    }

    

    public void saveToFileInsurance() {
        FileOutputStream fos = null;
        try {
            File f = new File(pathFile);

            fos = new FileOutputStream(f);

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            
            // Ghi các đối tượng insurance vào file
            for (Insurance in : this.values()) {  // Lưu trữ từng đối tượng insurance trong HashMap
                oos.writeObject(in);  // Ghi đối tượng Car vào file
            }
            
            oos.close();
            this.save = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InsuranceManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InsuranceManager.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                // Đóng tất cả luồng để giải phóng tài nguyên
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(InsuranceManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void readFromInsuranceFile(){
        FileInputStream fis = null;
        try {
            File f = new File(pathFile);
            if(!f.exists()){
                System.out.println("file not found...");
                return;
            }
            fis = new FileInputStream(f);
            
            ObjectInputStream ois= new ObjectInputStream(fis);
            
            while(fis.available() > 0){
                Insurance x = (Insurance)ois.readObject();
                this.put(x.getLicensePlate(),x);
            }
            ois.close();
            this.save = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InsuranceManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InsuranceManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InsuranceManager.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(InsuranceManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
