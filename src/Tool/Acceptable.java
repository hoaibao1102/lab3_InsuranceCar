/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tool;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author MSI PC
 */
public interface Acceptable {

    public final String LICENSE_PLATE_VALID = "^(5[0-9])[pPsSxXtTvV]\\d{1}\\d{5}$";
    public final String OWNER_NAME_VALID = "^.{2,20}$";
    public final String PHONE_VALID = "^(0[3|5|7|8|9])+([0-9]{8})$";
    public final String DOUBLE_VALID = "^\\d+.?\\d*$";
    public final String INTEGER_VALID = "^[1-9]\\d*$";
    public final String DATE_VALID = "^(0[1-9]|[1|2][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$";
    public final String SEAT_VALID = "^(4|[5-9]|[1-2][0-9]|3[0-6])$";
    public final String CAR_BRAND_VALID = "^.{5,12}$";
    public final String CAR_VALUE_VALID = "^[1-9][0-9]{3,}$";
    public final String INSURANCE_ID_VALID = "^[A-Za-z0-9]{4}$";
    public final String INSURANCE_PERIOD_VALID = "^12|24|36$";

    /**
     * Kiểm tra dữ liệu có trong data có phù hợp với mẫu pattern theo yêu cầu
     * không
     */
    // Static method to validate data against a pattern
    static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }

    static boolean isValidPastDate(String dateStr, String pattern) {
        boolean isValid = true;

        // Kiểm tra xem ngày nhập có đúng định dạng không
        if (!isValid(dateStr, pattern)) {
            System.out.println("Invalid date format.");
            isValid = false;
        }

        try {
            // Chuyển chuỗi ngày nhập vào thành đối tượng LocalDate
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Kiểm tra ngày quá khứ
            if (date.isAfter(LocalDate.now())) { // Ngày phải trước ngày hiện tại
                System.out.println("Registration date must be in the past.");
                isValid = false;
            }

        } catch (DateTimeException e) {
            System.out.println("Invalid date input. Please check the date.");
            isValid = false;
        }

        return isValid;
    }

    static boolean isValidFutureDate(String dateStr, String pattern) {
        boolean isValid = true;

        // Kiểm tra xem ngày nhập có đúng định dạng không
        if (!isValid(dateStr, pattern)) {
            System.out.println("Invalid date format.");
            isValid = false;
        }

        try {
            // Chuyển chuỗi ngày nhập vào thành đối tượng LocalDate
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Kiểm tra ngày tương lai
            if (!date.isAfter(LocalDate.now())) { // Ngày phải sau ngày hiện tại
                System.out.println("Registration date must be in the future.");
                isValid = false;
            }

        } catch (DateTimeException e) {
            System.out.println("Invalid date input. Please check the date.");
            isValid = false;
        }

        return isValid;
    }

}
