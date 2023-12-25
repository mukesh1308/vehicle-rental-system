package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import at.favre.lib.crypto.bcrypt.BCrypt;
import resource.Cookie;


public class InsertData extends DatabaseConnect {
    PreparedStatement loginInsert;
    PreparedStatement userInsert;
    PreparedStatement vehicleInsert;
    PreparedStatement vehicleModalInsert;
    PreparedStatement cartInsert;
    PreparedStatement billInsert;
    PreparedStatement paymentInsert;
    public InsertData() throws SQLException{
        super();
        loginInsert=conn.prepareStatement("INSERT INTO LOGIN(Email,Passward,User_id) VALUES(?,?,?)");
        userInsert=conn.prepareStatement("INSERT INTO User(User_name,Phone_no,Role) VALUES(?,?,?)");
        vehicleInsert=conn.prepareStatement("INSERT INTO Vehicle VALUES (?,?,?,?,1)");
        vehicleModalInsert=conn.prepareStatement("INSERT INTO Vehicle_modal(Vehicle_name,Vehicle_brand,Vehicle_type,No_of_passangers,Rental_charge) VALUES(?,?,?,?,?)");
        cartInsert=conn.prepareStatement("INSERT INTO Cart VALUES(?,?)");
        billInsert=conn.prepareStatement("INSERT INTO Renting(Number_plate,Start_distance,Order_id) VALUES (?,?,?)");
        paymentInsert=conn.prepareStatement("INSERT INTO Payment(Payment_mode,Amount) VALUES (?,?)");

    }
    public void CreateUser(String name,String email,String passward,String phone,String role) throws SQLException{
        ResultSet user_check=statement.executeQuery("SELECT User_id FROM Login WHERE Email='"+email+"'");
        if(user_check.next()){
            System.out.println("user already exists");
            return;
        }
        userInsert.setString(1, name);
        userInsert.setString(2, phone);
        userInsert.setString(3, role);
        userInsert.executeUpdate();
        String hash=BCrypt.withDefaults().hashToString(10, passward.toCharArray());
        ResultSet res=statement.executeQuery("SELECT User_id FROM User WHERE Phone_no='"+phone+"'");
        int user_id=-1;
        if(res.next()){
            user_id=res.getInt("User_id");
        }
        loginInsert.setString(1, email);
        loginInsert.setString(2, hash);
        loginInsert.setInt(3, user_id);
        loginInsert.executeUpdate();
        System.out.println("User added");
    }
    public void addVehicle(String num,int dist,int service,int vID) throws SQLException{
        ResultSet res=statement.executeQuery("SELECT Vehicle_id FROM Vehicle_modal WHERE Vehicle_id="+vID);
        if(!res.next()){
            System.out.println("vehicle id does not exist");
            return;
        }
        vehicleInsert.setString(1, num);
        vehicleInsert.setInt(2, vID);
        vehicleInsert.setInt(3, dist);
        vehicleInsert.setInt(4, service);
        vehicleInsert.executeUpdate();
        System.out.println("vehicle added");
    }
    public void addVehicleModal(String name,String brand,String type,int passanger,int rent) throws SQLException{
        vehicleModalInsert.setString(1, name);
        vehicleModalInsert.setString(2, brand);
        vehicleModalInsert.setString(3, type);
        vehicleModalInsert.setInt(4, passanger);
        vehicleModalInsert.setInt(5, rent);
        vehicleModalInsert.executeUpdate();
        System.out.println("vehicle modal added");
    }
    public void addToCart(String num) throws SQLException{
        ResultSet res=statement.executeQuery("SELECT M.Vehicle_type FROM Vehicle V JOIN Vehicle_modal M USING(Vehicle_id) WHERE Number_plate='"+num+"'");
        String type="";
        if(res.next()){
            type=res.getString(1);
        }
        else{
            System.out.println("Invalid Vehicle Number");
            return;
        }
        res=statement.executeQuery("SELECT Security_deposit FROM User WHERE User_id="+Cookie.userId);
        int securityDeposit=0;
        if(res.next()){
            securityDeposit=res.getInt(1);
        }
        if(type.equals("two wheeler")){
            if(securityDeposit<3000){
                System.out.println("your Security deposit is less then 3000");
                return;
            }
        }
        else{
            if(securityDeposit<10000){
                System.out.println("your Security deposit is less then 10000");
                return;
            }
        }
        res=statement.executeQuery("SELECT Number_plate FROM Cart WHERE User_id="+Cookie.userId+" AND Number_plate IN (SELECT Number_plate FROM Vehicle WHERE Vehicle_id IN (SELECT Vehicle_id FROM Vehicle_modal WHERE Vehicle_type='"+type+"'))");
        if(res.next()){
            System.out.println("you can add one bike or car at a time\n");
            return;
        }
        cartInsert.setString(1, num);
        cartInsert.setInt(2, Cookie.userId);
        cartInsert.executeUpdate();
        System.out.println("Added to the cart");
    }
    public void addBill(String email,int deposit)throws SQLException{
        ResultSet res=statement.executeQuery("SELECT User_id FROM Login WHERE Email='"+email+"'");
        int id=-1;
        if(res.next()){
            id=res.getInt(1);
        }
        else{
            System.out.println("Invalid User");
            return;
        }
        statement.executeUpdate("INSERT INTO Orders(User_id,Deposite_paid) VALUES ("+id+","+deposit+")");
        res=statement.executeQuery("SELECT MAX(Order_id) FROM Orders WHERE User_id="+id);
        int orderID=-1;
        if(res.next()){
            orderID=res.getInt(1);
        }
        res=statement.executeQuery("SELECT C.Number_plate,V.Distance_covered FROM Cart C JOIN Vehicle V USING(Number_plate) WHERE User_id="+id);
        Statement state=conn.createStatement();
        while(res.next()){
            ResultSet r1=state.executeQuery("SELECT Number_plate FROM Renting WHERE Return_date IS NULL AND Number_plate='"+res.getString(1)+"'");
            if(r1.next()){
                System.out.println(res.getString(1)+" is already rented");
                return;
            }
            billInsert.setString(1, res.getString(1));
            billInsert.setInt(2, res.getInt(2));
            billInsert.setInt(3,orderID);
            billInsert.executeUpdate();
            System.out.println("bill added for "+res.getString(1));
        }
        statement.executeUpdate("DELETE FROM CART WHERE User_id="+id);
        System.out.println();
    }
    public int addPayment(String pay,int amount,int orderID)throws SQLException{
        paymentInsert.setString(1, ""+Cookie.userId);
        paymentInsert.setInt(2,amount);
        paymentInsert.executeUpdate();
        ResultSet res=statement.executeQuery("SELECT Payment_id FROM Payment WHERE Payment_mode='"+Cookie.userId+"'");
        int payID=-1;
        if(res.next()){
            payID=res.getInt(1);
            statement.executeUpdate("UPDATE Payment SET Payment_mode='"+pay+"' WHERE Payment_id="+payID);
            statement.executeUpdate("UPDATE Orders SET Payment_id="+payID+" WHERE Order_id="+orderID);
        }
        return 0;
    }
}
