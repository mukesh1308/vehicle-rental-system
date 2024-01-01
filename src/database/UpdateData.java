package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import resource.Input;

public class UpdateData  extends DatabaseConnect{
    PreparedStatement updateAvailability;
    PreparedStatement updateSecurityDeposit;
    PreparedStatement updateBill;
    public UpdateData() throws SQLException{
        updateAvailability=conn.prepareStatement("UPDATE Vehicle SET Availability=? WHERE Number_plate=?");
        updateSecurityDeposit=conn.prepareStatement("UPDATE User SET Security_deposit=? WHERE User_id=?");
        updateBill=conn.prepareStatement("UPDATE Renting SET End_distance=? ,Damage=?,Return_date=CURRENT_TIMESTAMP WHERE Order_id=? AND Number_plate=?");
    }
    public void updateVehical(int choice,String num,int newVal)throws SQLException{
        StringBuilder quire=new StringBuilder("UPDATE Vehicle SET ");
        if(choice==1){
            quire.append("vehicle_id=");
        }
        else if(choice==2){
            quire.append("Distance_covered=");
        }
        else{
            quire.append("Last_service_at=");
        }
        quire.append(newVal);
        quire.append(" WHERE Number_plate='");
        quire.append(num);
        quire.append("'");
        statement.executeUpdate(quire.toString());
        System.out.println("DATA Updated");
    }
    public void updateVehicalModal(int choice,int id,String newVal)throws SQLException{
        StringBuilder quire=new StringBuilder("UPDATE Vehicle_modal SET ");
        if(choice==4){
            quire.append("Vehicle_name='");
        }
        else if(choice==5){
            quire.append("Vehicle_brand='");
        }
        else{
            quire.append("Vehicle_type='");
        }
        quire.append(newVal);
        quire.append("' WHERE Vehicle_id=");
        quire.append(id);
        statement.executeUpdate(quire.toString());
        System.out.println("DATA Updated");
    }
    public void updateVehicalModal(int choice,int id,int newVal) throws SQLException{
        StringBuilder quire=new StringBuilder("UPDATE Vehicle_modal SET ");
        if(choice==7){
            quire.append("No_of_passangers=");
        }
        else{
            quire.append("Rental_charge=");
        }
        quire.append(newVal);
        quire.append(" WHERE Vehicle_id=");
        quire.append(id);
        statement.executeUpdate(quire.toString());
        System.out.println("DATA Updated");
    }
    public void setAvailability(String num,int set) throws SQLException{
        updateAvailability.setInt(1, set);
        updateAvailability.setString(2, num);
        updateAvailability.executeUpdate();
        System.out.println("DATA Updated");
    }
    public void updateSecurityDeposite(int id,int amount)throws SQLException{
        updateSecurityDeposit.setInt(1, amount);
        updateSecurityDeposit.setInt(2, id);
        updateSecurityDeposit.executeUpdate();
        System.out.println("DATA Updated");
    }
    public int addBill(String email)throws SQLException{
        ResultSet res=statement.executeQuery("SELECT User_id FROM Login WHERE Email='"+email+"'");
        int id=-1;
        if(res.next()){
            id=res.getInt(1);
        }
        else{
            System.out.println("Invalid User");
            return -1;
        }
        // System.out.println(id);
        int orderID=-1;
        res=statement.executeQuery("SELECT Order_id FROM Orders WHERE User_id="+id+" AND Payment_id IS NULL");
        if(res.next()){
            orderID=res.getInt(1);
        }
        // System.out.println(orderID);
        res=statement.executeQuery("SELECT Number_plate FROM Renting WHERE Order_id="+orderID);
        Statement state=conn.createStatement();
        while(res.next()){
            System.out.println("Enter data for Vehicle Number "+res.getString(1));
            System.out.print("Enter Damage level(none/low/medium/high): ");
            String demage=Input.sc.nextLine();
            System.out.print("Enter End Distance: ");
            int dist=Input.sc.nextInt();
            Input.sc.nextLine();
            updateBill.setInt(1, dist);
            updateBill.setString(2, demage);
            updateBill.setInt(3, orderID);
            updateBill.setString(4, res.getString(1));
            updateBill.executeUpdate();
            state.executeUpdate("UPDATE Vehicle SET Distance_covered="+dist+" WHERE Number_plate='"+res.getString(1)+"'");
            System.out.println("Updated\n");
        }
        return orderID;
    }
}
