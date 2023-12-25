package admin;

import java.sql.SQLException;
import java.util.ArrayList;

import database.DisplayData;
import database.InsertData;
import resource.Bill;
import resource.Input;

public class AdminReport extends Input {
    static void genrateBill(int orderID)throws SQLException{
        DisplayData display=new DisplayData();
        InsertData insert=new InsertData();
        ArrayList<Bill> arr=display.genrateBill(orderID);
        int sum=0;
        for(Bill b:arr){
            // // System.out.println(b);
            sum+=b.rent*b.days;
            System.out.printf("%-40s%-10d\n","Rent per day:-",b.rent);
            System.out.printf("%-40s%-10d\n","no. of days:- ",b.days);
            if(b.distance>=500){
                System.out.printf("%-40s%-10d\n","extra distance covered charge:- ",(int)(b.rent*0.15));
                sum+=(int)((b.rent)*0.15);
            }
            else{
                System.out.printf("%-40s%-10d\n","extra distance covered charge:- ",0);
            }
            if(b.demage.equals("none")){
                System.out.printf("%-40s%-10d\n","demage charge:- ",0);
            }
            else if(b.demage.equals("low")){
                sum+=6000;
                System.out.printf("%-40s%-10d\n","demage charge:- ",6000);
            }
            else if(b.demage.equals("medium")){
                sum+=15000;
                System.out.printf("%-40s%-10d\n","demage charge:- ",15000);
            }
            else{
                sum+=22500;
                System.out.printf("%-40s%-10d\n","demage charge:- ",22500);
            }
            System.out.println();
        }
        System.out.println("-".repeat(50));
        System.out.println("The to total amount to pay :"+sum);
        System.out.print("\nEnter Payment mode(online/offline): ");
        String pay=sc.nextLine();
        insert.addPayment(pay,sum,orderID);
    }
    public static void genrate()throws SQLException{
        DisplayData display= new DisplayData();
        System.out.println("1.List of All vehicle due for servise");
        System.out.println("2.list of all vehicles");
        System.out.println("3.list of vehicle which are rented");
        System.out.print("\nEnter your choice: ");
        int choice=sc.nextInt();
        sc.nextLine();
        if(choice==1){
            display.displayService();
        }
        else if(choice==2){
            System.out.println("1.order by vehicle name");
            System.out.println("2.order by rental price");
            System.out.println("3.order by vehicle number");
            System.out.print("\nEnter your choice: ");
            int c2=sc.nextInt();
            sc.nextLine();
            if(c2==1){
                display.displayAllVehicle("M.Vehicle_name");
            }
            else if(c2==2){
                display.displayAllVehicle("M.Rental_charge");
            }
            else if(c2==3){
                display.displayAllVehicle("V.Number_plate");
            }
        }
        else if(choice==3){
            display.displayRentedVehicle();
        }
    }
}
