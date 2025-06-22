/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CustomerRegister;
import controller.CustomerController;
import controller.OrderController;
import data.DataManagement;
import data.DataOfMenuFeast;
import java.util.ArrayList;
import java.util.Scanner;
import model.Customer;
import model.FeastMenu;
import model.Order;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        /*-----------------------------------------------------------------------*/
        ArrayList<Customer> customerList = new ArrayList<>();
        ArrayList<Order> orderList = new ArrayList<>();
        ArrayList<FeastMenu> feastMenu = new ArrayList<>();
        /*-----------------------------------------------------------------------*/
        CustomerRegister customerRegistrer = new CustomerRegister(customerList);
        DataManagement dataManagement = new DataManagement(customerList,orderList);
        CustomerController customerController = new CustomerController(customerList,orderList,dataManagement);
        DataOfMenuFeast dataOfMenuFeast = new DataOfMenuFeast();
        OrderController orderController = new OrderController(customerList,orderList,feastMenu);
         /*-----------------------------------------------------------------------*/
        dataManagement.readFromFile();//doc file da luu
        
        
        int choice = 0;
        do {
            System.out.println("+=================================================================+");
            System.out.println("|                                                                 |");
            System.out.println("|               Traditional Feast Order Management.               |");
            System.out.println("|                                                                 |");
            System.out.println("+=================================================================+");
            System.out.println("| 1. Register customers.                                          |");
            System.out.println("| 2. Update customer information.                                 |");
            System.out.println("| 3. Search for customer information by name.                     |");
            System.out.println("| 4. Display feast menus.                                         |");
            System.out.println("| 5. Place a feast order.                                         |");
            System.out.println("| 6. Update order information.                                    |");
            System.out.println("| 7. Save data to file.                                           |");
            System.out.println("| 8. Display Customer or Order lists.                             |");
            System.out.println("| 9. Exit the Program:                                            |");
            System.out.println("+=================================================================+");
            System.out.printf("Enter the choice(1-9):");
            String input= sc.nextLine().trim();
            try {
                choice = Integer.parseInt(input); //Đổi sang kiểu Int
                if (choice < 1 || choice > 9) {
                    System.out.println("Invalid the choice.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Your input" + " " + input + " " + "invalid.Please enter valid number.");
                continue;
            }
            switch (choice) {
                case 1:
                    customerRegistrer.addCustomer();
                    break;
                case 2:
                    customerRegistrer.updateCustomer();
                    break;
                case 3:
                    customerController.searchByName();
                    break;
                case 4:
                    dataOfMenuFeast.displayMenu();
                    break;
                case 5:
                    orderController.placeFeastOrder();
                    break;
                case 6:
                    orderController.updateOrder();
                    break;
                case 7:
                    dataManagement.saveToFile();
                    break;
                case 8:
                    customerController.displayInfo();
                    break;
                case 9:
                    customerController.exitProgram();
                    break;
                default:
                    System.out.println("+-----------------------------------------------------------------+");
                    System.out.println("|                 This function is not available.                 |");
                    System.out.println("+-----------------------------------------------------------------+");
            }
        } while (choice != 9);
        sc.close();

    }
}
