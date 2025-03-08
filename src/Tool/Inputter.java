/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tool;

import Bussiness.CarManager;
import Bussiness.InsuranceManager;
import Model.Car;
import Model.Insurance;
import java.util.Scanner;

/**
 *
 * @author MSI PC
 */
public class Inputter {
    

    private Scanner ndl;

    public Inputter() {
        ndl = new Scanner(System.in);
    }

    public String getString(String mess) {
        System.out.print(mess);
        return ndl.nextLine();
    }

    public int getInt(String mess) {
        int num = 0;
        String tmp = getString(mess);
        if (Acceptable.isValid(tmp, Acceptable.INTEGER_VALID)) {
            num = Integer.parseInt(tmp);
        }
        return num;
    }

    public double getDouble(String mess) {
        double num = 0;
        String temp = getString(mess);
        if (Acceptable.isValid(temp, Acceptable.DOUBLE_VALID)) {
            num = Double.parseDouble(mess);
        }
        return num;
    }

    public String inputAndLoop(String mess, String pattern, boolean isUpdate, boolean isPast) {
        String result = "";
        boolean more = true;
        do {
            result = getString(mess);
            // Kiểm tra ngày trong quá khứ
            if (isPast) {
                more = !Acceptable.isValidPastDate(result, pattern);
            } // Kiểm tra ngày trong tương lai
            else if(!isPast && pattern.equals(Acceptable.DATE_VALID)) {
                more = !Acceptable.isValidFutureDate(result, pattern);
            }
            else{
                more = !Acceptable.isValid(result, pattern);
            }

            if (more && !isUpdate) {
                System.out.println("You have entered the request format incorrectly! Re-Enter.....");
            }
        } while (more && !isUpdate);
        return result;
    }

    /**
     * @param isUpdate
     * @param s
     * Khởi tạo đối tượng Car mới. Kiểm tra chế độ cập nhật. 
     * Nếu không cập nhật, nhập và kiểm tra biển số xe. Nhập các thông tin xe: tên chủ xe,
     * số điện thoại, thương hiệu
     * Nhập giá trị xe và ngày đăng ký(nếu không cập nhật), số ghế.
     * Trả về đối tượng Car sau khi nhập xong.
     * @return
     */
    public Car inputCarInfo(boolean isUpdate,CarManager s) {
        Car x = new Car();
        if (!isUpdate) {
            String license;
            boolean check = false;
            do {
                System.out.println("Note: The license plate format should be 50-59 + District Code (1 letter) + 1 digit + 5 digits.");
                license = inputAndLoop("Enter the license plate: ", Acceptable.LICENSE_PLATE_VALID, isUpdate, false);
                x.setLicensePlate(license.toUpperCase());
                if (s.searchCarByLicense(x.getLicensePlate()) != null) {
                    System.out.println("The License Plate already exists in the list.");
                    check = true;
                } else {
                    check = false;
                }
            } while (check);
        }

        x.setOwner(inputAndLoop("Owner Name (2-30 characters): ", Acceptable.OWNER_NAME_VALID, isUpdate,false));
        x.setPhoneNum(inputAndLoop("Phone Number (10 digits): ", Acceptable.PHONE_VALID, isUpdate,false));
        x.setBrand(inputAndLoop("Car Brand: ", Acceptable.CAR_BRAND_VALID, isUpdate,false));
        if (!isUpdate) {
            x.setValueVehicle(inputAndLoop("Value of Vehicle: ", Acceptable.CAR_VALUE_VALID, isUpdate,false));
            x.setRegistrationDate(inputAndLoop("Registration Date (dd/MM/yyyy): ", Acceptable.DATE_VALID, isUpdate,true));
        }
        x.setNumberOfSeats(inputAndLoop("Number of Seats (4-36):", Acceptable.SEAT_VALID, isUpdate,false));
        return x;
    }
    
    /**
     * Khởi tạo đối tượng Insurance mới. 
     * Nhập và kiểm tra Insurance ID (đảm bảo duy nhất). 
     * Nhập biển số xe, kiểm tra xe đã đăng ký và chưa có bảo hiểm.
     * Nhập ngày hiệu lực bảo hiểm (phải là ngày hợp lệ trong tương lai)
     * Nhập thời gian bảo hiểm (12, 24, 36 tháng)
     * Tính phí bảo hiểm tự động dựa trên giá trị xe và thời gian bảo hiểm.
     * Nhập tên người thụ hưởng bảo hiểm (2-25 ký tự).
     * Gán Contract ID và trả về đối tượng Insurance.
     * @return 
     */
    public Insurance inputInsuranceInfo(InsuranceManager i, CarManager c) {
        Insurance insurance = new Insurance();

        // Nhập Insurance ID (4 ký tự, duy nhất)
        String id;
        do {
            id = inputAndLoop("Insurance ID (4 characters, unique): ", Acceptable.INSURANCE_ID_VALID, false, false);
            id = id.trim().toUpperCase();  // Loại bỏ khoảng trắng thừa và chuyển thành chữ hoa
            // Kiểm tra nếu ID đã tồn tại trong InsuranceManager
            if (i.containsKey(id)) {
                System.out.println("This Insurance ID already exists. Please choose a different one.");
            }
        } while (i.containsKey(id));  // Nếu ID đã tồn tại, yêu cầu nhập lại
        insurance.setInsuId(id);  // Lưu ID vào đối tượng insurance

        // Nhập License Plate (phải tồn tại và chưa có bảo hiểm)
        Car car;
        String licensePlate;
        do {
            licensePlate = inputAndLoop("License Plate (Must be registered but not insured): ", Acceptable.LICENSE_PLATE_VALID, false,false);
            car = c.searchCarByLicense(licensePlate);

            if (car == null) {
                System.out.println(" License plate not found in system. Please enter a valid one.");
            } else if (i.isCarInsured(licensePlate)) {
                System.out.println(" This car is already insured. Please choose another car.");
                car = null;
            }
        } while (car == null);
        insurance.setLicensePlate(licensePlate);

        // Nhập Established Date (Ngày hiệu lực bảo hiểm, phải là ngày trong tương lai)
        insurance.setEstablishDate(inputAndLoop("Established Date (dd/MM/yyyy): ", Acceptable.DATE_VALID, false,false));

        // Nhập Insurance Period (Chỉ nhận 12, 24, 36 tháng)
        String insuPeriod = inputAndLoop("Insurance Period (12, 24, 36): ", Acceptable.INSURANCE_PERIOD_VALID, false,false);
        insurance.setInsuPeriod(insuPeriod);
        int insuPeriodNum = Integer.parseInt(insuPeriod);

        // Tính phí bảo hiểm tự động
        double insuranceFees = i.calculateInsuranceFees(car.getValueVehicle(), insuPeriodNum);
        insurance.setInsuFees(String.valueOf(insuranceFees));

        // Nhập Insurance Owner (Tên người thụ hưởng)
        insurance.setInsuOwner(inputAndLoop("Insurance Owner (2-25 characters): ", Acceptable.OWNER_NAME_VALID, false,false));

        insurance.setContractId(i.generateContractId());
        return insurance;
    }

}
