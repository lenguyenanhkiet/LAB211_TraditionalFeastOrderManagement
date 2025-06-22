/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uitilities;

import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class Inputter {

    private static final Scanner sc = new Scanner(System.in);

    public static String getString(String prompt, int min, int max) {
        String input;
        while (true) {
            System.out.println(prompt);
            input = sc.nextLine().trim().replaceAll("\\s+", " ");
            
            if(input == null || input.isEmpty()){
               return null;
            }
            
             if(input.length()>=min && input.length()<=max){
                return input;
            }
             
            if(input.length()<min){
                System.out.println("Please enter at least " + min + " characters.");
            }else if(input.length()>max){
                System.out.println("Please enter at most " + max + " characters.");
            }
            
            if(!confirmRetry("Invalid length.Press Y to retry or N to exit.")){
                return "EXIT";
            }
        }
    }
    private static boolean confirmRetry(String message) {
        System.out.println(message);
        while (true) {
            String answer = sc.nextLine().trim();
            if (answer.equalsIgnoreCase("y")) {
                return true;
            } else if (answer.equalsIgnoreCase("n")) {
                return false;
            } else {
                System.out.println("Invalid input. Enter Y or N");
            }
        }
    }
}
