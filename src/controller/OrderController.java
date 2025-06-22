/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import data.DataOfMenuFeast;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import model.Customer;
import model.FeastMenu;
import model.Order;
import uitilities.Validation;

/**
 *
 * @author Admin
 */
public class OrderController {

    List<Customer> customerList;
    List<Order> orderList;
    List<FeastMenu> feastMenu;

    public OrderController(List<Customer> customerList, List<Order> orderList, List<FeastMenu> feastMenu) {
        this.customerList = customerList;
        this.orderList = orderList;
        this.feastMenu = feastMenu;
    }
    Scanner scanner = new Scanner(System.in);
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DataOfMenuFeast dataOfMenuFeast = new DataOfMenuFeast();

    public void placeFeastOrder() {
        Order newOrder = new Order();

        int orderID = (orderList.size() + 1);
        newOrder.setOderID(orderID);

        boolean found = false;
        System.out.println("|-----------------------------------------------------------------|");
        System.out.println("|                        Flace feast order                        |");
        System.out.println("|-----------------------------------------------------------------|");
        /*------------------------------------------Input Customer Code--------------------------------------------------*/
        while (true) {
            System.out.println("|-----------------------------------------------------------------|");
            System.out.println("|        Note: Enter 'break' to cancel place feast order.         |");
            System.out.println("|-----------------------------------------------------------------|");
            String inputCode = uitilities.Inputter.getString("Enter customer code:", 5, 5);
            //Check length

            if (inputCode == null) {
                if (Validation.confirmExit("Place feast order is empty. Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Place feast order canceled.");
                return;
            }
            if (inputCode.equalsIgnoreCase("EXIT") || inputCode.equalsIgnoreCase("break")) {
                System.out.println("Place feast order canceled.");
                return;
            }

            for (Customer customer : customerList) {
                if (customer.getCustomerCode().equalsIgnoreCase(inputCode)) {
                    found = true;
                    newOrder.setCustomerCode(customer);
                    break;
                }
            }

            break;
        }
        /*------------------------------------------Input Customer Code--------------------------------------------------*/
        if (!found) {
            System.out.println("This customer does not exist.");
        }
        /*--------------------------------------------Enter code Menu---------------------------------------------------*/
        while (true) {
            dataOfMenuFeast.displayMenu();
            String codeMenu = uitilities.Inputter.getString("Enter menu code:", 5, 5);
            FeastMenu feastMenu = dataOfMenuFeast.findCodeMenu(codeMenu);

            if (feastMenu == null) {
                if (Validation.confirmExit("Menu code not empty. Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Place feast order canceled.");
                return;
            }
            if ("EXIT".equalsIgnoreCase(feastMenu.getCode())) {
                if (Validation.confirmExit("Menu code not exists. Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Place feast order canceled.");
                return;
            }
            newOrder.setCodeMenu(feastMenu);
            break;
        }
        /*--------------------------------------------Enter code Menu---------------------------------------------------*/
 /*--------------------------------------------Enter tables-----------------------------------------------------*/
        int numberTables;
        while (true) {
            System.out.print("Enter number of tables(>0): ");
            String input = scanner.nextLine().trim();
            if (input == null || input.isEmpty()) {
                if (Validation.confirmExit("Number of tables must not be empty!. Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Place feast order canceled.");
                return;
            }
            if (!input.matches("\\d+")) {
                if (Validation.confirmExit("Invalid input! Please enter a valid number. Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Place feast order canceled.");
                return;
            }

            numberTables = Integer.parseInt(input);

            if (numberTables <= 0) {
                if (Validation.confirmExit("Number of tables must be greater than zero!. Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Place feast order canceled.");
                return;
            }

            newOrder.setNumberTables(numberTables);
            break;
        }
        /*--------------------------------------------Enter tables-----------------------------------------------------*/
 /*--------------------------------------------Enter event date-------------------------------------------------*/
        LocalDate eventDate;
        while (true) {
            System.out.println("Enter event date(dd/MM/yyyy):");
            String dateInput = scanner.nextLine().trim();
            if (dateInput == null || dateInput.isEmpty()) {
                if (Validation.confirmExit("Date must not be empty! . Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Place feast order canceled.");
                return;
            }

            try {
                eventDate = LocalDate.parse(dateInput, dateFormatter);
                if (!eventDate.isAfter(LocalDate.now())) {
                    if (Validation.confirmExit("Invalid date.Please enter a future date in format dd/MM/yyyy.Press 'Y' to re-enter or 'N' to cancel.")) {
                        continue;
                    }
                    System.out.println("Place feast order canceled.");
                    return;
                }
                newOrder.setEventDate(eventDate);
                break;
            } catch (Exception e) {
                System.out.println("Invalid or past date! Please enter a future date in format dd/MM/yyyy.");
            }
        }

        /*--------------------------------------------Enter event date-------------------------------------------------*/
 /*--------------------------------------------Calculate totalPrice---------------------------------------------*/
        double totalPrice = 0.0;
        double price = newOrder.getCodeMenu().getPrice();
        totalPrice = price * numberTables;
        newOrder.setTotalPrice(totalPrice);
        /*-------------------------------------------End Calculate totalPrice-------------------------------------------*/
 /*-------------------------------------------Check dupplicate-------------------------------------------*/
        if (uitilities.Validation.isDupplicate(newOrder, orderList)) {
            System.out.println("Dupplicate data !");
            return;
        }
        /*--------------------------------------------Check dupplicate--------------------------------------------------*/
 /*--------------------------------------------msg successfully--------------------------------------------------*/

        orderList.add(newOrder);
        displayOrder(newOrder);
        System.out.println("+-----------------------------------------------------------------+");
        System.out.println("|                         Order successfully.                     |");
        System.out.println("+-----------------------------------------------------------------+");
        /*-------------------------------------------End msg successfully-----------------------------------------------*/
    }

    public void updateOrder() {
        boolean found = false;
        int choice = 0;
        boolean continueUpdate = true;
        while (true) {
            System.out.println(".------------------------------------------------------------------.");
            System.out.println("|       Note: Enter 'break' to cancel the update process.          |");
            System.out.println(".------------------------------------------------------------------.");
            System.out.println("Enter order ID:");
            String input_f = scanner.nextLine().trim();
            if (input_f.isEmpty() || input_f == null) {
                if (Validation.confirmExit("Order ID must not be empty! Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Your feast order has been successfully canceled.");
                return;
            }

            if (!input_f.matches("\\d+")) {
                if (Validation.confirmExit("Invalid input! Please enter a valid number. Press 'Y' to re-enter or 'N' to cancel.")) {
                    continue;
                }
                System.out.println("Place feast order canceled.");
                return;
            }
            if (input_f.equalsIgnoreCase("break")) {
                System.out.println("Your feast order has been successfully canceled.");
                return;
            }
            int findOrderID = Integer.parseInt(input_f);

            for (Order od : orderList) {
                if (findOrderID == od.getOderID()) {
                    displayOrder(od);
                    found = true;
                    while (continueUpdate) {
                        do {
                            System.out.println("1. Update menu code");
                            System.out.println("2. Update number of tables");
                            System.out.println("3. Update event date");
                            System.out.println("4. Exit");
                            System.out.println("Select the option want to update.");
                            String input = scanner.nextLine().trim();
                            try {
                                choice = Integer.parseInt(input); //Đổi sang kiểu Int
                                if (choice < 1 || choice > 4) {
                                    System.out.println("Invalid the choice.");
                                    continue;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Your input" + " " + input + " " + "invalid.Please enter valid number.");
                                continue;
                            }
                            switch (choice) {
                                case 1://Update menu code
                                    while (true) {
                                        dataOfMenuFeast.displayMenu();
                                        String newCodeMenu = uitilities.Inputter.getString("Enter a new menu code:", 5, 5);
                                        FeastMenu feastMenu = dataOfMenuFeast.findCodeMenu(newCodeMenu);
                                        //Check not exists.
                                        if ("EXIT".equalsIgnoreCase(feastMenu.getCode())) {
                                            if (Validation.confirmExit("Menu code not exists. Press 'Y' to re-enter or 'N' to cancel.")) {
                                                continue;
                                            }
                                            displayOrder(od);
                                            System.out.println("Your feast order has been successfully canceled.");
                                            continueUpdate = false;
                                            break;
                                        }
                                        //Check null
                                        if (feastMenu == null) {
                                            if (Validation.confirmExit("Menu code not empty. Press 'Y' to re-enter or 'N' to cancel.")) {
                                                continue;
                                            }
                                            System.out.println("Your feast order has been successfully canceled.");
                                            displayOrder(od);
                                            continueUpdate = false;
                                            break;
                                        }

                                        //Check ton tai
                                        if (newCodeMenu.equalsIgnoreCase(od.getCodeMenu().getCode())) {
                                            if (Validation.confirmExit("This menu code is already in use by this user. Press 'Y' to re-enter or 'N' to cancel.")) {
                                                continue;
                                            }
                                            System.out.println("Your feast order has been successfully canceled.");
                                            displayOrder(od);
                                            continueUpdate = false;
                                            break;

                                        }
                                        od.setCodeMenu(feastMenu);
                                        System.out.println("Code menu updated successfully.");
                                        break;
                                    }
                                    break;
                                case 2: //Update number of tables
                                    while (true) {
                                        System.out.println("Enter new number of tables.");
                                        String input_s = scanner.nextLine().trim();
                                        int oldNumberOfTable = od.getNumberTables();
                                        double price = od.getCodeMenu().getPrice();
                                        //Check id digits not char
                                        if (!input_s.matches("\\d+")) {
                                            if (Validation.confirmExit("Invalid input! Please enter a valid number. Press 'Y' to re-enter or 'N' to cancel.")) {
                                                continue;
                                            }
                                            System.out.println("Place feast order canceled.");
                                            displayOrder(od);
                                            continueUpdate = false;
                                            break;
                                        }
                                        if (input_s.isEmpty() || input_s == null) {
                                            if (Validation.confirmExit("Number tables must be empty. Press 'Y' to re-enter or 'N' to cancel.")) {
                                                continue;
                                            }
                                            System.out.println("Place feast order canceled.");
                                            displayOrder(od);
                                            continueUpdate = false;
                                            break;
                                        }
                                        //Chuyen String sang int 
                                        int newNumberOfTable = Integer.parseInt(input_s);
                                        //newTotalPrice
                                        double newTotalPrice = newNumberOfTable * price;
                                        //Check ton tai
                                        if (newNumberOfTable == oldNumberOfTable) {
                                            if (Validation.confirmExit("Number of table matches current number of table.'Y' to re-enter. 'N' to cancel.")) {
                                                continue;
                                            }
                                            System.out.println("Place feast order canceled.");
                                            displayOrder(od);
                                            continueUpdate = false;
                                            break;
                                        }

                                        od.setNumberTables(newNumberOfTable);
                                        od.setTotalPrice(newTotalPrice);
                                        displayOrder(od);
                                        System.out.println("Number of tables updated successfully");
                                        break;
                                    }
                                    break;
                                case 3://update event date
                                    LocalDate NewEventDate = null;
                                    while (true) {
                                        System.out.println("Enter event date(dd/MM/yyyy):");
                                        String newDateInput = scanner.nextLine().trim();
                                        LocalDate oldDateInput = od.getEventDate();

                                        if (newDateInput == null || newDateInput.isEmpty()) {
                                            if (Validation.confirmExit("Date must not be empty! . Press 'Y' to re-enter or 'N' to cancel.")) {
                                                continue;
                                            }
                                            System.out.println("Place feast order canceled.");
                                            displayOrder(od);
                                            continueUpdate = false;
                                            break;
                                        }

                                        try {
                                            NewEventDate = LocalDate.parse(newDateInput, dateFormatter);
                                            if (NewEventDate.isAfter(LocalDate.now())) {
                                                if (NewEventDate.equals(oldDateInput)) {
                                                    System.out.println("Event date matches current event date. 'Y' to update new event date. 'N' to exit update event date.");
                                                    String answer = scanner.nextLine().trim();
                                                    if (answer.equalsIgnoreCase("y")) {
                                                        continue;
                                                    } else {
                                                        displayOrder(od);
                                                        System.out.println("Place feast order canceled.");
                                                        continueUpdate = false;
                                                    }
                                                } else {
                                                    od.setEventDate(NewEventDate);
                                                    displayOrder(od);
                                                    System.out.println("Update event date successfully.");
                                                }
                                                break;
                                            } else {
                                                System.out.println("Invalid date.Please enter a future date in format dd/MM/yyyy.");
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Invalid or past date! Please enter a future date in format dd/MM/yyyy.");
                                        }

                                    }

                                    break;

                                case 4:
                                    boolean check = true;
                                    while (check) {
                                        System.out.println("Do you want to exit update.(Y/N)");
                                        String answer = scanner.nextLine().trim();
                                        if (answer.equalsIgnoreCase("y")) {
                                            System.out.println("Exit successfully.");
                                            choice = -1;
                                            check = false;
                                        } else if (answer.equalsIgnoreCase("n")) {
                                            displayOrder(od);
                                            check = false;
                                        } else {
                                            System.out.println("Invalid choice");
                                            check = true;
                                        }
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                            }
                        } while (choice != -1);
                        break;
                    }
                }

            }
            if (!found) {
                System.out.println("Order ID not exists.");
            }
            break;
        }
    }

    public void displayOrder(Order newOrder) {
        /*-------------------------------Format price (#,###,###)--------------------*/
        String formattedPrice = formatter.format(newOrder.getCodeMenu().getPrice());
        String fmToltalPrice = formatter.format(newOrder.getTotalPrice());
        /*-------------------------------Format date (dd/MM/yyyy)--------------------*/
        String fmDate = dateFormatter.format(newOrder.getEventDate());
        /*-------------------------------Format Ingredients ------------------------*/
        String formattedIngredients = newOrder.getCodeMenu().getIngredients().replace("#", "\n").replace("\"", "");
        /*------------------------------Display order infomation--------------------*/
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Customer order information [Order ID: " + newOrder.getOderID() + "]");
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Code  :" + newOrder.getCustomerCode().getCustomerCode());
        System.out.println("Name  :" + newOrder.getCustomerCode().getName());
        System.out.println("Phone :" + newOrder.getCustomerCode().getPhoneNumber());
        System.out.println("Email :" + newOrder.getCustomerCode().getEmail());
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Code of set Menu :" + newOrder.getCodeMenu().getCode());
        System.out.println("Set menu name    :" + newOrder.getCodeMenu().getName());
        System.out.println("Event date       :" + fmDate);
        System.out.println("Number of tables :" + newOrder.getNumberTables());
        System.out.println("Price            :" + formattedPrice + " Vnd");
        System.out.println("Ingredients:\n" + formattedIngredients);
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Total Price:" + fmToltalPrice + " Vnd");
        System.out.println("-------------------------------------------------------------------------");
    }

}
