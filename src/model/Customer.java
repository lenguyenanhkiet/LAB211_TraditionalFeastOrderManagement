/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 *
 * @author Admin
 */
public class Customer implements Serializable{
    private static final long serialVersionUID=1L; 
    private String customerCode;
    private String name;
    private String phoneNumber;
    private String email;

    public Customer() {
    }

    public Customer(String customerCode, String name, String phoneNumber, String email) {
        this.customerCode = customerCode;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getCustomerCode() {
        return toTitleCase(customerCode);
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getName() {
        return toTitleCase(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerCode=" + customerCode + ", name=" + name + ", phoneNumber=" + phoneNumber + ", email=" + email + '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public static String toTitleCase(String name){
        String rs="";
        StringTokenizer stk = new StringTokenizer(name," ");
        while(stk.hasMoreTokens()){
            String w=stk.nextToken();
            w=(w.charAt(0)+"").toUpperCase()+w.substring(1).toLowerCase();
            rs=rs+" "+w;
        }
        rs= rs.trim();
        
        String []parts = rs.split(" ");
        if(parts.length<2){
            return rs;
        }
        
        String lastName=parts[parts.length-1];
        StringBuilder remainingName = new StringBuilder();
        for (int i = 0; i < parts.length -1 ; i++) {
                remainingName.append(" ");
                remainingName.append(parts[i]);
        }
        
        return  lastName+","+remainingName;
    }
    
}
