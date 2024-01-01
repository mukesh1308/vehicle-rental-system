import java.sql.SQLException;

import admin.Admin;
import database.DatabaseConnect;
import database.InsertData;
import database.Login;
import resource.Input;
import user.User;

public class App extends Input{
    public static void main(String[] args){
        try{
            DatabaseConnect.connect();
        }
        catch(SQLException err){
            System.out.println("Database not connected");
        }
        while(true){
            System.out.println("\nWelcome to Vehicle Rental System");
            System.out.println("1.Login");
            System.out.println("2.SignUp");
            System.out.println("3.Exit");
            System.out.println();
            System.out.print("Enter your choice: ");
            int choice=sc.nextInt();
            sc.nextLine();
            if(choice==1){
                System.out.print("\nEnter Email: ");
                String email=sc.nextLine();
                System.out.print("Enter Passward: ");
                String passward=sc.nextLine();
                try{
                    int user=(new Login()).login(email, passward);
                    if(user==1){
                        System.out.println("welcome to user\n");
                        User.start();
                    }
                    else if(user==2){
                        System.out.println("welcome to admin\n");
                        Admin.start();
                    }
                    else if(user==-1){
                        System.out.println("Invalid user");
                    }
                    else{
                        System.out.println("Invalid Passward");
                    }
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==2){
                System.out.print("Enter Email: ");
                String email=sc.nextLine();
                System.out.print("Enter Name: ");
                String name=sc.nextLine();
                System.out.print("Enter Phone no: ");
                String phone=sc.nextLine();
                System.out.print("Enter Passward: ");
                String passward=sc.nextLine();
                try{
                    InsertData insert=new InsertData();
                    insert.CreateUser(name, email, passward, phone,"Renter");
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==3){
                break;
            }
        }
    }
}

