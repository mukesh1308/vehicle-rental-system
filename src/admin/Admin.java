package admin;

import java.sql.SQLException;

import database.DeleteDate;
import database.DisplayData;
import database.InsertData;
import database.UpdateData;
import resource.Cookie;
import resource.Input;

public class Admin extends Input {
    public static void start() throws SQLException{
        InsertData insert=new InsertData();
        UpdateData update=new UpdateData();
        DeleteDate delete=new DeleteDate();
        DisplayData display=new DisplayData();
        while(true){
            System.out.println("1.ADD Vehicles");
            System.out.println("2.Modify Vehicle");
            System.out.println("3.Delete Vehicle");
            System.out.println("4.List Vehicles");
            System.out.println("5.Change the security deposite");
            System.out.println("6.Report");
            System.out.println("7.Billing");
            System.out.println("8.Log out");
            System.out.print("\nEnter your choice: ");
            int choice=sc.nextInt();
            sc.nextLine();
            if(choice==1){
                System.out.println("\n1.ADD vehicle modal");
                System.out.println("2.Add new vehicle");
                System.out.print("\nEnter your choice: ");
                int c1=sc.nextInt();
                sc.nextLine();
                if(c1==1){
                    System.out.print("\nEnter vehicle name: ");
                    String name=sc.nextLine();
                    System.out.print("Enter vehicle brand: ");
                    String brand=sc.nextLine();
                    System.out.print("Enter vehicle type(two wheeler/four wheeler): ");
                    String type=sc.nextLine();
                    System.out.print("Enter no. of passangers: ");
                    int passanger=sc.nextInt();
                    System.out.print("Enter the Rental charge: ");
                    int rent=sc.nextInt();
                    try{
                        insert.addVehicleModal(name,brand,type,passanger,rent);
                    }
                    catch(SQLException err){
                        System.out.println(err);
                    }
                }
                else if(c1==2){
                    System.out.print("\nEnter Vehicle number: ");
                    String number=sc.nextLine();
                    System.out.print("Enter distance covered: ");
                    int dist=sc.nextInt();
                    System.out.print("Enter Last service at: ");
                    int service=sc.nextInt();
                    System.out.print("Enter Vechicle modal ID: ");
                    int vID=sc.nextInt();
                    try{
                        insert.addVehicle(number,dist,service,vID);
                    }
                    catch(SQLException err){
                        System.out.println(err);
                    }
                }
                System.out.println();
            }
            else if(choice==2){
                System.out.println("\nWhat do you want to modify: ");
                System.out.println("1.Vehical_id of vehicle");
                System.out.println("2.Distance Covered");
                System.out.println("3.Last Service Distance");
                System.out.println("4.vehicle name");
                System.out.println("5.vehicle brand");
                System.out.println("6.vehicle type");
                System.out.println("7.number of passangers");
                System.out.println("8.Rental Charge");
                System.out.println("9.Change Availability");
                System.out.print("\nEnter your choice: ");
                int c2=sc.nextInt();
                sc.nextLine();
                if(c2<=3){
                    System.out.print("\nEnter Vehicle Number: ");
                    String num=sc.nextLine();
                    System.out.print("Enter the updated value: ");
                    int newVal=sc.nextInt();
                    sc.nextLine();
                    try{
                        update.updateVehical(c2, num,newVal);
                    }
                    catch(SQLException err){
                        System.out.println(err);
                    }
                }
                else if(c2<=6){
                    System.out.print("\nEnter Vehicle ID: ");
                    int id=sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter the updated value: ");
                    String newVal=sc.nextLine();
                    try{
                        update.updateVehicalModal(c2,id,newVal);
                    }
                    catch(SQLException err){
                        System.out.println(err);
                    }
                }
                else if(c2<=8){
                    System.out.print("\nEnter Vehicle ID: ");
                    int id=sc.nextInt();
                    System.out.print("Enter the updated value: ");
                    int newVal=sc.nextInt();
                    sc.nextLine();
                    try{
                        update.updateVehicalModal(c2, id, newVal);
                    }
                    catch(SQLException err){
                        System.out.println(err);
                    }
                }
                else if(c2==9){
                    System.out.println("1.make vehicle Availabile");
                    System.out.println("2.make vehicle un-availabile");
                    System.out.print("\nEnter your choice: ");
                    int abl=sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Vehicle Number: ");
                    String num=sc.nextLine();
                    try{
                        if(abl==1){
                            update.setAvailability(num,1);
                        }
                        else if(abl==2){
                            update.setAvailability(num,0);
                        }
                    }
                    catch(SQLException err){
                        System.out.println(err);
                    }
                }
            }
            else if(choice==3){
                System.out.print("\nEnter the vehicle number: ");
                String num=sc.nextLine();
                try{
                    delete.removeVehical(num);
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==4){
                System.out.println("\n1.Order by Vehicle name");
                System.out.println("2.Order by Availability");
                System.out.print("\nEnter Your choice: ");
                int c4=sc.nextInt();
                sc.nextLine();
                try{
                    if(c4==1){
                        display.displayVehicle("Vehicle_name");
                    }
                    else{
                        display.displayVehicle("COUNT(*)");
                    }
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==5){
                System.out.print("\nEnter the User_id: ");
                int id=sc.nextInt();
                System.out.print("Enter new Security deposit amount: ");
                int amount=sc.nextInt();
                sc.nextLine();
                try{
                    update.updateSecurityDeposite(id, amount);
                }
                catch(SQLException err){
                    System.out.println(err);
                }
            }
            else if(choice==6){
                AdminReport.genrate();
            }
            else if(choice==7){
                System.out.println("\n1.Initial Billing");
                System.out.println("2.final Billing");
                System.out.print("\nEnter your choice: ");
                int c7=sc.nextInt();
                sc.nextLine();
                System.out.print("\nEnter Email ID: ");
                String email=sc.nextLine();
                if(c7==1){
                    System.out.print("Enter Deposite Amount: ");
                    int deposit=sc.nextInt();
                    sc.nextLine();
                    try{
                        insert.addBill(email,deposit);
                    }
                    catch(SQLException err){
                        System.out.println(err);
                    }
                }
                else if(c7==2){
                    try{
                        int orderID=update.addBill(email);
                        AdminReport.genrateBill(orderID);
                    }
                    catch(SQLException err){
                        System.out.println(err);
                    }
                }
            }
            else if(choice==8){
                Cookie.userId=-1;
                break;
            }
        }
    }
}
