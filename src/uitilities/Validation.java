/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uitilities;

import java.util.List;
import java.util.Scanner;
import model.Customer;
import model.Order;

/**
 *
 * @author Admin
 */
public interface Validation {

    public static final String CUS_CODE_VALID = "^[CcGgKk]\\d{4}$";//Valid Customers
    public static final String NAME_VALID = "^[a-zA-Z ]{2,25}$";//Valid Name
    public static final String PHONE_VALID = "^\\d{10}$";//Valid Phone
    public static final String VINA_VALID = "^(091|094|088|083|084|085|081|082)\\d{7}$";
    public static final String MOBI_VALID = "^(070|079|077|076|078|089|090|093)\\d{7}$";
    public static final String VIETTEL_VALID = "^(086|096|097|098|039|038|037|036|035|034|033|032)\\d{7}$";
    public static final String EMAIL_VALID = "^[\\w._+-]+@[\\w-]+\\.[a-zA-Z.]{2,}$";//Valid Email

    public static boolean isValid(String data, String pattern) {
        return data.matches(pattern);
    }

    public static boolean isUniqueCustomerCode(String newCustomerCode, List<Customer> customerList) {
        for (Customer customer : customerList) {
            if (customer.getCustomerCode().equalsIgnoreCase(newCustomerCode)) {
                return true;
            }
        }
        return false;
    }
    
     public static boolean isDupplicate(Order newOrder, List<Order>orderList){
         for (Order order : orderList) {
             if (order.getCustomerCode().getCustomerCode().equalsIgnoreCase(newOrder.getCustomerCode().getCustomerCode()) &&
                order.getCodeMenu().getCode().equalsIgnoreCase(newOrder.getCodeMenu().getCode()) &&
                order.getEventDate().equals(newOrder.getEventDate())) {
                return true; // Đơn hàng trùng lặp
            }
         }
         return false;
     }
     public static boolean confirmExit(String message){
        Scanner sc = new Scanner(System.in);
        System.out.println(message);
        while(true){
            String answer = sc.nextLine().trim();
            if(answer.equalsIgnoreCase("y")){
                return true;
            }else if(answer.equalsIgnoreCase("n")){
                return false;
            }else{
                System.out.println("Invalid choice. Please enter 'Y' or 'N'.");
            }
        }
    }
}
