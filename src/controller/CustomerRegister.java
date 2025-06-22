/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Scanner;
import uitilities.Validation;
import model.Customer;
import uitilities.Inputter;
/**
 *
 * @author Admin
 */
public class CustomerRegister {

    Scanner sc = new Scanner(System.in);
    List<Customer> customerList ;

    public CustomerRegister(List<Customer> customerList) {
        this.customerList = customerList;
    }
    public void addCustomer() {
        Customer newCustomer = new Customer();
        System.out.println("+-----------------------------------------------------------------+");
        System.out.println("|                         New Registration                        |");
        System.out.println("+-----------------------------------------------------------------+");
        /*-------------------------------------------Add Customer Code--------------------------------------------------*/
        while (true) {
            System.out.println("|-----------------------------------------------------------------|");
            System.out.println("|          Note: Enter 'break' to cancel the add process.         |");
            System.out.println("|-----------------------------------------------------------------|");
            String newCustomerCode =Inputter.getString("Enter customer code (Format: C/G/K followed by 4 digits, e.g., C1234)", 5, 5);
            
            //Check null
            if (newCustomerCode == null) {
                if (Validation.confirmExit("Customer code cannot be empty. Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Customer registration canceled.");
                return;
            }
            //Check length characters and quit before add customer code.
            if(newCustomerCode.equalsIgnoreCase("EXIT")||newCustomerCode.equalsIgnoreCase("break")){
                System.out.println("Customer registration canceled.");
                return;
            }
            
            //Check isValid format customer code.
            if (!Validation.isValid(newCustomerCode, Validation.CUS_CODE_VALID)) {
                if(Validation.confirmExit("Invalid format! Customer code must start with C, G, or K.\n"+
                        "Press 'Y' to try again or 'N' to cancel.")){
                    continue;
                }
                System.out.println("Customer registration canceled.");
                return;
            }
            
            //Check isUnique
            if (Validation.isUniqueCustomerCode(newCustomerCode, customerList)) {
                if (Validation.confirmExit("This customer code already exists. Press 'Y' to enter a different code or 'N' to cancel.")) {
                    continue;
                }
                return;
            }
            //Sau khi thỏa hết điều kiện trên thì add newName
            newCustomer.setCustomerCode(newCustomerCode);
            break;
        }

/*-------------------------------------------End Add Customer Code----------------------------------------------*/
/*-------------------------------------------Add Customer Name--------------------------------------------------*/
        while (true) {            
            String newName = uitilities.Inputter.getString("Enter customer name:(2-25 characters)", 2, 25);
            //Check name null
            if(newName==null){
                if(Validation.confirmExit("Name cannot be empty. Press 'Y' to re-enter or 'N' to cancel.")){
                    continue;
                }
                System.out.println("Customer registration canceled.");
                return;
            }
            //Check length characters.
            if(newName.equalsIgnoreCase("EXIT")){
                System.out.println("Customer registration canceled.");
                return;
            }
            //Check isValid name.
            if (!Validation.isValid(newName, Validation.NAME_VALID)) {
                if(Validation.confirmExit("Invalid name! Special characters are not allowed. Press 'Y' to re-enter or 'N' to cancel.")){
                    continue;
                }
                System.out.println("Customer registration canceled.");
                return;
            } 
            
            newCustomer.setName(newName);
                break;
        }
/*-------------------------------------------End Add Customer Name----------------------------------------------*/
/*-------------------------------------------Add Phone Number---------------------------------------------------*/
        while (true) {            
            String newPhone = uitilities.Inputter.getString("Enter phone number (10 digits):", 10, 10);
            //Check null
            if(newPhone==null){
                if(Validation.confirmExit("Phone number cannot be empty. Press 'Y' to re-enter or 'N' to cancel.")){
                    continue;
                }
                System.out.println("Customer registration canceled.");
                return;
            }
            //Check length
            if(newPhone.equalsIgnoreCase("EXIT")){
                System.out.println("Customer registration canceled.");
                return;
            }
            //Check isValid phone is digits.
            if (!Validation.isValid(newPhone, Validation.PHONE_VALID)){
                if(Validation.confirmExit("Invalid format! Phone number must have 10 digits.\n"+
                        "Press 'Y' to re-enter or 'N' to cancel.")){
                    continue;
                }
                System.out.println("Customer registration canceled.");
                return;
            }
            //Check Invalid Vietnamese network operator.
            if (!(Validation.isValid(newPhone, Validation.MOBI_VALID)
                        || Validation.isValid(newPhone, Validation.VIETTEL_VALID)
                        || Validation.isValid(newPhone, Validation.VINA_VALID))) {
                if(Validation.confirmExit("Invalid network provider! Please enter a valid Viettel, Mobifone, or Vinaphone number.\n"+
                        "Press 'Y' to re-enter or 'N' to cancel.")){
                    continue;
                }
                System.out.println("Customer registration canceled.");
                return;
            }
            newCustomer.setPhoneNumber(newPhone);
            break;
        }
 /*-------------------------------------------End Add Phone Number-----------------------------------------------*/
 /*-------------------------------------------Add Email----------------------------------------------------------*/
        while (true) {            
            String newEmail = uitilities.Inputter.getString("Enter email:", 5, 50);
            //Check null email
            if(newEmail==null){
                if(Validation.confirmExit("Email cannot be empty. Press 'Y' to re-enter or 'N' to cancel.")){
                    continue;
                }
                System.out.println("Customer registration canceled.");
                return;
            }
            //Check length email
            if(newEmail.equalsIgnoreCase("EXIT")){
                System.out.println("Customer registration canceled.");
                return;
            }
            //Check validation email
            if (!Validation.isValid(newEmail, Validation.EMAIL_VALID)) {
                if (Validation.confirmExit("Invalid email format! Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Customer registration canceled.");
                return;
            }
            newCustomer.setEmail(newEmail);
            break;
        }
        /*-------------------------------------------End Add Email------------------------------------------------------*/
        customerList.add(newCustomer);
        System.out.println("+-----------------------------------------------------------------+");
        System.out.println("|                Customer registered successfully!                |");
        System.out.println("+-----------------------------------------------------------------+");
        
    }
    
    public void updateCustomer() {
        Scanner scanner = new Scanner(System.in);
        boolean found = false;
        boolean continueUpdate = true;
        int choice =0;  
        System.out.println("+-----------------------------------------------------------------+");
        System.out.println("|                         Update Customer                         |");
        System.out.println("+-----------------------------------------------------------------+");
        while (true) {
            System.out.println("|-----------------------------------------------------------------|");
            System.out.println("|        Note: Enter 'break' to cancel the update process.        |");
            System.out.println("|-----------------------------------------------------------------|");

            String findCusCode = uitilities.Inputter.getString("Enter customer code:", 5, 5);
            /*---------------------------------------------------------------------------------------*/
            //Check null
            if(findCusCode==null){
                if(Validation.confirmExit("Customer code is empty. Press 'Y' to re-enter or 'N' to cancel.")){
                    continue;
                }
                System.out.println("Customer update process canceled.");
                return;
            }
            //Check length
            if(findCusCode.equalsIgnoreCase("EXIT") || findCusCode.equalsIgnoreCase("break")){
                System.out.println("Customer update process canceled.");
                return;
            }
           
           /*-------------------------------------------------------------------------------------*/
           
            for (Customer customer : customerList) {
                if (customer.getCustomerCode().equalsIgnoreCase(findCusCode)) {
                    displayInfo(customer);
                    found = true;
                    while (continueUpdate) {                        
                        do {
                            System.out.println("1. Update Name");
                            System.out.println("2. Update Phone Number");
                            System.out.println("3. Update Email");
                            System.out.println("4. Exit Update");
                            System.out.println("Select an option::");
                            String input= scanner.nextLine().trim();
                            try {
                                choice = Integer.parseInt(input); //Đổi sang kiểu Int
                                if(choice <1 || choice >5){
                                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                                    continue;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Your input"+" "+input+" "+"invalid.Please enter a valid number.");
                                continue;
                            }
                            switch (choice) {
                                case 1: // Update name
                                    while (true) {
                                        String newName = uitilities.Inputter.getString("Enter new name:(2-25 characters)", 2, 25);
                                        //Check null
                                        if (newName == null) {
                                            if (Validation.confirmExit("Name is empty. Press 'Y' to re-enter or 'N' to cancel.")) {
                                                continue;
                                            }
                                            System.out.println("Name update canceled.");
                                            displayInfo(customer);
                                            continueUpdate = false;
                                            break;
                                        }
                                        //Check length
                                        if(newName.equalsIgnoreCase("EXIT")){
                                            System.out.println("Name update canceled.");
                                            displayInfo(customer);
                                            continueUpdate = false;
                                            break;
                                        }
                                        
                                        String originalName = newName;//Lưu newName ban đầu vào original
                                        newName = Customer.toTitleCase(newName);//Lưu chuyển newName thành dạng LName,FName
                                        //Check isUnique name
                                        if (newName.equalsIgnoreCase(customer.getName())) {
                                            if (Validation.confirmExit("The new name is identical to the current name. Press 'Y' to re-enter or 'N' to cancel.")) {
                                                continue;
                                            }
                                            displayInfo(customer);
                                            System.out.println("Name update canceled.");
                                            continueUpdate = false;
                                            break;
                                        }
                                        newName = originalName;//Chuyển dạng LName,FName thành dạng original
                                        customer.setName(newName);
                                        displayInfo(customer);
                                        System.out.println("Customer name updated successfully.");
                                        break;
                                    }
                                    break;
                                case 2: // Update Phone number
                                    while (true) {
                                        String newPhone = uitilities.Inputter.getString("Enter new phone number (10 digits):", 10, 10);
                                        //Check phone null
                                        if(newPhone==null){
                                            if(Validation.confirmExit("Phone number is empty. Press 'Y' to re-enter or 'N' to cancel.")){
                                                continue;
                                            }
                                            System.out.println("Phone number update canceled.");
                                            displayInfo(customer);
                                            continueUpdate=false;
                                            break;
                                        }
                                        //Check length
                                        if(newPhone.equalsIgnoreCase("EXIT")){
                                            System.out.println("Phone number update canceled.");
                                            displayInfo(customer);
                                            continueUpdate = false;
                                            break;
                                        }
                                        //Check isUnique
                                        if(newPhone.equalsIgnoreCase(customer.getPhoneNumber())){
                                            if(Validation.confirmExit("The new phone number matches the current one. Press 'Y' to re-enter or 'N' to cancel.")){
                                                continue;
                                            }
                                            System.out.println("Phone number update canceled.");
                                            displayInfo(customer);
                                            continueUpdate=false;
                                            break;
                                        }
                                        //Check isValid
                                        if(!Validation.isValid(newPhone, Validation.PHONE_VALID)){
                                            if(Validation.confirmExit("Invalid phone number format. Press 'Y' to re-enter or 'N' to cancel.")){
                                                continue;
                                            }
                                            System.out.println("Phone number update canceled.");
                                            displayInfo(customer);
                                            continueUpdate=false;
                                            break;
                                        }
                                        //Check Invalid Vietnamese network operator.
                                        if (!(Validation.isValid(newPhone, Validation.MOBI_VALID)
                                                || Validation.isValid(newPhone, Validation.VIETTEL_VALID)
                                                || Validation.isValid(newPhone, Validation.VINA_VALID))) {
                                            if (Validation.confirmExit("Invalid Vietnamese network operator.\n"
                                                    + "Y to re-enter or N to cancel.")) {
                                                continue;
                                            }
                                            System.out.println("Phone number update canceled.");
                                            displayInfo(customer);
                                            continueUpdate = false;
                                            break;
                                        }
                                        customer.setPhoneNumber(newPhone);
                                        displayInfo(customer);
                                        System.out.println("Phone number updated successfully.");
                                        break;
                                    }
                                    break;
                                case 3:// Update email
                                    while (true) {                                        
                                        String newEmail = uitilities.Inputter.getString("Enter new email:", 5, 50);
                                        //Check mail null
                                        if(newEmail==null){
                                            if(Validation.confirmExit("Email is empty. Press 'Y' to re-enter or 'N' to cancel.")){
                                                continue;
                                            }
                                            displayInfo(customer);
                                            System.out.println("Email update canceled.");
                                            continueUpdate=false;
                                            break;
                                        }
                                        //Check length
                                        if(newEmail.equalsIgnoreCase("EXIT")){
                                            displayInfo(customer);
                                            System.out.println("Email update canceled.");
                                            continueUpdate = false;
                                            break;
                                        }
                                        //Check isUnique
                                        if (newEmail.equalsIgnoreCase(customer.getEmail())) {
                                            if(Validation.confirmExit("The new email matches the current one. Press 'Y' to re-enter or 'N' to cancel.")){
                                                continue;
                                            }
                                            displayInfo(customer);
                                            System.out.println("Email update canceled.");
                                            continueUpdate = false;
                                            break;
                                        }
                                        customer.setEmail(newEmail);
                                        displayInfo(customer);
                                        System.out.println("Email updated successfully.");
                                        break;
                                    }
                                    break;
                                case 4: //Exit update
                                    boolean check = true;
                                    while (check) {                                        
                                        System.out.println("Do you want to exit.(Y/N)");
                                        String answer = sc.nextLine().trim();
                                        if (answer.equalsIgnoreCase("y")) {
                                            System.out.println("Update process exited successfully.");
                                            check = false;
                                            choice = -1;
                                        } else if (answer.equalsIgnoreCase("n")) {
                                            displayInfo(customer);
                                            check = false;
                                        } else {
                                            System.out.println("Invalid choice. Please enter 'Y' or 'N'.");
                                            check = true;
                                        }
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid the choice.");
                            }
                        } while (choice != -1);
                        break;
                    }
                }
        }
            if (!found) {
                System.out.println("This customer does not exist.");
            }
            return;
        }
        
    }

    /*--------------------------------------------Hàm phụ-----------------------------------------------------------*/
    public static void displayInfo(Customer customer) {
        System.out.println("+------+-------------------------+-----------+---------------------------+");
        System.out.printf("|%-6s|%-25s|%-11s|%-27s|\n",
                " Code ", "      Customer Name      ", "   Phone   ", "           Email          ");
        System.out.println("+------+-------------------------+-----------+---------------------------+");
        System.out.printf("|%-6s|%-25s|%-11s|%-27s|\n",
                customer.getCustomerCode(),
                customer.getName(),
                customer.getPhoneNumber(),
                customer.getEmail());
        System.out.println("+------+-------------------------+-----------+---------------------------+");
    }
    
    
    /*--------------------------------------------Hàm phụ-----------------------------------------------------------*/
 /*      Kiểm tra phone/name/email mới có trùng với số điện thoại đã có hay không
                    - Nếu có: Đưa ra 2 option.
                        + msg: Số điện thoại đã trùng với số đã có bạn có muốn thay đổi không (Y/N)
                            (Y): Enter a new Phone/name/email. -> update new phone/name/email.
                            (N): Dừng updated.
                    - Nếu không: Enter a new phone/name/email -> update new phone/name/mail.    */
}
