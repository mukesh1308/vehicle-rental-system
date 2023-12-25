package user;

import java.sql.SQLException;

import database.DeleteDate;
import database.DisplayData;
import database.InsertData;
import resource.Cookie;
import resource.Input;

public class User extends Input {
    public static void start() throws SQLException{
        DisplayData display=new DisplayData();
        InsertData insert=new InsertData();
        DeleteDate delete=new DeleteDate();
        while(true){
            System.out.println("1.List Vehicles");
            System.out.println("2.ADD to cart");
            System.out.println("3.Remove from cart");
            System.out.println("4.Display cart");
            System.out.println("5.Previous rentals");
            System.out.println("6.Log out");
            System.out.print("\nEnter your choice: ");
            int choice=sc.nextInt();
            sc.nextLine();
            if(choice==1){
                System.out.println("\n1.Order by Vehicle name");
                System.out.println("2.Order by Rental Price");
                System.out.println("3.Order by Availability count");
                System.out.print("\nEnter your choice: ");
                int c1=sc.nextInt();
                sc.nextLine();
                try{
                    if(c1==1){
                        display.displayVehicle("Vehicle_name");
                    }
                    else if(c1==2){
                        display.displayVehicle("Rental_charge");
                    }
                    else if(c1==3){
                        display.displayVehicle("count(*)");
                    }
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==2){
                System.out.print("\nEnter the Vehicle ID: ");
                int id=sc.nextInt();
                sc.nextLine();
                display.displayVehicle(id);
                System.out.print("Enter Vehicle Number: ");
                String num=sc.nextLine();
                try{
                    insert.addToCart(num);
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==3){
                System.out.print("\nEnter the Vehicle Number: ");
                String num=sc.nextLine();
                try{
                    delete.removeFromCart(num);
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==4){
                try{
                    System.out.println();
                    display.displayCart();
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==5){
                try{
                    display.previousRent();
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==6){
                Cookie.userId=-1;
                break;
            }
        }
    }
}
