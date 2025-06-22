/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import data.DataManagement;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import model.Customer;
import model.Order;

/**
 *
 * @author Admin
 */
public class CustomerController {

    List<Customer> customerList;
    List<Order>orderList;
    DataManagement dataManagement;

    public CustomerController(List<Customer> customersList,List<Order>orderList, DataManagement dataManagement) {
        this.customerList = customersList;
        this.orderList=orderList;
        this.dataManagement=dataManagement;
    }

    Scanner sc = new Scanner(System.in);

    public void searchByName() {
        while (true) {
            boolean found = false;
            System.out.println(".------------------------------------------------------------------.");
            System.out.println("|       Note: Enter 'break' to cancel the search process.          |");
            System.out.println(".------------------------------------------------------------------.");
            System.out.println("Enter the name you want to search:");
            String search = sc.nextLine().toLowerCase();
            if (search == null || search.isEmpty()) {
                System.out.println("Search term cannot be empty. Press 'Y' to retry or 'N' to cancel.");
                while (true) {
                    String answer = sc.nextLine().trim();
                    if (answer.equalsIgnoreCase("n")) {
                        System.out.println("Exiting search...");
                        return;
                    } else if (answer.equalsIgnoreCase("y")) {
                        System.out.println("Please enter the name again:");
                        break;
                    } else {
                        System.out.println("Invalid input. Please enter 'Y' to retry or 'N' to cancel.");
                    }
                }
                continue;
            }
            if(search.equalsIgnoreCase("break")){
                System.out.println("Exiting search...");
                break;
            }
        
            System.out.println("+------+-------------------------+-----------+---------------------------+");
            System.out.printf("|%-6s|%-25s|%-11s|%-27s|\n",
                    " Code ", "      Customer Name      ", "   Phone   ", "          Email          ");
            System.out.println("+------+-------------------------+-----------+---------------------------+");
            for (Customer customer : customerList) {
                if (customer.getName().toLowerCase().contains(search) && !search.isEmpty()) {
                    System.out.printf("|%-6s|%-25s|%-11s|%-27s|\n", customer.getCustomerCode(),
                            customer.getName(),
                            customer.getPhoneNumber(),
                            customer.getEmail());
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("|                    No one matches the search criteria!                 |");
            }
            System.out.println("+------+-------------------------+-----------+---------------------------+");
            return;
        }
    }

   public void exitProgram(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to save the changes before exiting? (Y/N)");
        String choice=sc.nextLine();
        if(choice.equalsIgnoreCase("y")){
           dataManagement.saveToFile();
        }else{
            System.out.println("Are you sure to exit when not save . Y to exit & N to save file.");
            choice=sc.nextLine();
            if(choice.equalsIgnoreCase("n")){
                dataManagement.saveToFile();
            }
        }
         System.exit(0);
    }

    public void displayInfo() {
        /*Display customer
            --sort alphabetical name
          Display Oder
            -- sort by event date.
        */
        int choice = 0;
        boolean found = false;

        do {            
            System.out.println("Do you want to display Customer or Oder.");
            System.out.println("1.Customer\n"+"2.Oder List");
            String input = sc.nextLine().trim();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 2) {
                    System.out.println("Invalid the choice.");
                    continue;
                }
            } catch (NumberFormatException e) {
                 System.out.println("Your input" + " " + input + " " + "invalid.Please enter valid number.");
                continue;
            }
            switch(choice){
                case 1:
                    customerList.sort(Comparator.comparing(Customer::getName));
                    System.out.println("+------+-------------------------+-----------+---------------------------+");
                    System.out.printf("|%-6s|%-25s|%-11s|%-27s|\n",
                            " Code ", "      Customer Name      ", "   Phone   ", "          Email          ");
                    System.out.println("+------+-------------------------+-----------+---------------------------+");
                    for (Customer customer : customerList) {
                        System.out.printf("|%-6s|%-25s|%-11s|%-27s|\n", customer.getCustomerCode(),
                                customer.getName(),
                                customer.getPhoneNumber(),
                                customer.getEmail());
                        found = true;
                        choice =-1;
                    }
                    if (!found) {
                        System.out.println("Does not have any customer information.");
                    }

                    System.out.println("+------+-------------------------+-----------+---------------------------+");
                    break;
                case 2:
                    orderList.sort(Comparator.comparing(Order::getEventDate));
                    System.out.println("---------------------------------------------------------------------------");
                    System.out.printf("%-6s|%-12s|%-10s|%-10s|%-12s|%-8s|%-12s\n",
                            "ID", "Event Date", "Customer ID", "Set Menu", "Price", "Tables", "Cost");
                    System.out.println("---------------------------------------------------------------------------");

                    for (Order od : orderList) {
                        /*-----------------------------Fomat price-----------------------*/
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        String fmTotalPrice = formatter.format(od.getTotalPrice());
                        String fmPrice = formatter.format(od.getCodeMenu().getPrice());
                        /*-----------------------------Fomat date-----------------------*/
                        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String fmDate = formattedDate.format(od.getEventDate());
                        /*--------------------------Print information---------------------------*/
                        System.out.printf("%-6s|%-12s|%-11s|%-10s|%-12s|%-8s|%-12s\n",
                                od.getOderID(),
                                fmDate,
                                od.getCustomerCode().getCustomerCode(),
                                od.getCodeMenu().getCode(),
                                fmPrice,
                                od.getNumberTables(),
                                fmTotalPrice);
                        found = true;
                        choice =-1;
                    }
                    if (!found) {
                        System.out.println("Does not have any order information.");
                    }
                    System.out.println("---------------------------------------------------------------------------");
                     break;
                default:System.out.println("Invalid choice!!!");
                break;
            }
        } while (choice !=-1);
    }
}      
        


     


