/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Order;

/**
 *
 * @author Admin
 */
public class DataManagement {
    private List<Customer>customersList;
    private List<Order> orderList;
    
    public DataManagement(List<Customer> customersList,List<Order>orderList) {
        this.customersList = customersList;
        this.orderList=orderList;
    }
    
    private static final String fileName="registration.dat";
    private static final String fileOrderMenu="feast_order_service.dat";
    /*-------------------------------------------Save to file .dat-----------------------------------------------*/
    public void saveToFile(){
        if(fileName==null || fileName.isEmpty()){
                System.out.println("File has no information please try again.");
            return;
        }
        if(fileOrderMenu==null || fileOrderMenu.isEmpty()){
                System.out.println("File has no information please try again.");
            return;
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(fileOrderMenu));
            oos.writeObject(customersList);
            oos2.writeObject(orderList);
            System.out.println("---------------------------------------------------------");
            System.out.println("                     Save successfully.                  ");
            System.out.println("---------------------------------------------------------");
        } catch (IOException e) {
            System.out.println("Error"+e.getMessage()   );
        }
    } 

    /*-------------------------------------------Save to file .dat-----------------------------------------------*/
    /*-------------------------------------------Read from file .dat-----------------------------------------------*/
    @SuppressWarnings("unchecked")
    public void readFromFile(){
        File file = new File(fileName);
        File file2 = new File(fileOrderMenu);
        
        if(!file.exists()){
            System.out.println("No existing data file Customer not found.");
            return;
        }
        if(!file2.exists()){
            System.out.println("No existing data file Order not found.");
            return;
        }                
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
            ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream(file2));
            orderList.clear();
            orderList.addAll((ArrayList<Order>)ois2.readObject());
            customersList.clear();
            customersList.addAll((ArrayList<Customer>) ois.readObject());
            
        } catch (IOException |ClassNotFoundException e) {
            System.out.println("Error loading data."+e.getMessage());
        }
    }
    
    /*-------------------------------------------Read from file .dat-----------------------------------------------*/
}
