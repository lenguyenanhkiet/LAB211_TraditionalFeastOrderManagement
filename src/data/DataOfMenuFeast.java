/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.FeastMenu;

/**
 *
 * @author Admin
 */
public class DataOfMenuFeast {

    private final List<FeastMenu> menuList;
    
    public DataOfMenuFeast(){
        this.menuList= new ArrayList<>();
    }

    public List<FeastMenu> getMenuList() {
        return menuList;
    }

    String line;

    public void readFileCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] value = line.split(",", -1);
                String code = value[0];
                String name = value[1];
                double price = Double.parseDouble(value[2]);
                String ingredients = value[3];
                menuList.add(new FeastMenu(code, name, price, ingredients));
            }
            //Sort Price ascending.
         Collections.sort(menuList, Comparator.comparingDouble(FeastMenu::getPrice));

        } catch (IOException e) {
            System.out.println("Cannot read file csv" + e.getMessage());
        }
    }
    public FeastMenu findCodeMenu(String code){
        for (FeastMenu feastMenu : menuList) {
            if(feastMenu.getCode().equalsIgnoreCase(code)){
                return feastMenu;
            }
        }
        return new FeastMenu("EXIT","Not Found",0,"N/A");
    }
    public void displayMenu() {
        //Display menu
        readFileCSV("src\\data\\FeastMenu.csv");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        System.out.println("                                      List of Set Menus for ordering party.                                          ");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        for (FeastMenu feastMenu : menuList) {
            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedPrice = formatter.format(feastMenu.getPrice());
            String formattedIngredients = feastMenu.getIngredients().replace("#", "\n").replace("\"", "");
            System.out.println("Code        :" + feastMenu.getCode());
            System.out.println("Name        :" + feastMenu.getName());
            System.out.println("Price       :" + formattedPrice + " Vnd");
            System.out.println("Ingredients :\n" + formattedIngredients);
            System.out.println("--------------------------------------------------------------------------------------------------------------------");
        }
    }

}
