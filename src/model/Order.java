/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;


/**
 *
 * @author Admin
 */
public class Order implements Serializable{
    private static final long serialVersionUID=1L; 
    private int oderID;
    private Customer customerCode;
    private FeastMenu codeMenu;
    private LocalDate eventDate;
    private int numberTables;
    private double totalPrice;
    public Order(int oderID, Customer customerCode, FeastMenu code, LocalDate eventDate, int numberTables, double totalPrice) {
        this.oderID = oderID;
        this.customerCode = customerCode;
        this.codeMenu = code;
        this.eventDate = eventDate;
        this.numberTables = numberTables;
        this.totalPrice = totalPrice;
    }

    public Order() {

    }

    public int getOderID() {
        return oderID;
    }

    public void setOderID(int oderID) {
        this.oderID = oderID;
    }

    public Customer getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(Customer customerCode) {
        this.customerCode = customerCode;
    }

    public FeastMenu getCodeMenu() {
        return codeMenu;
    }

    public void setCodeMenu(FeastMenu codeMenu) {
        this.codeMenu = codeMenu;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public int getNumberTables() {
        return numberTables;
    }

    public void setNumberTables(int numberTables) {
        this.numberTables = numberTables;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" + "oderID=" + oderID + ", customerCode=" + customerCode + ", code=" + codeMenu + ", eventDate=" + eventDate + ", numberTables=" + numberTables + ", totalPrice=" + totalPrice + '}';
    }

    

    
}
