package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import resource.Bill;
import resource.Cookie;

public class DisplayData extends DatabaseConnect{
    PreparedStatement displayById;
    PreparedStatement showCart;
    PreparedStatement showPreviousRent;
    public DisplayData() throws SQLException{
        displayById=conn.prepareStatement("SELECT V.Number_plate,M.Vehicle_name,M.Rental_charge,V.Distance_covered,V.Last_service_at FROM Vehicle V JOIN Vehicle_modal M USING(Vehicle_id) WHERE Vehicle_id=? AND V.Number_plate NOT IN(SELECT Number_plate FROM Renting WHERE Return_date IS NULL) AND ((M.Vehicle_type='four wheeler' AND (V.Distance_covered-V.Last_service_at)<3000) OR (M.Vehicle_type='two wheeler' AND (V.Distance_covered-V.Last_service_at)<1500))");
        showCart=conn.prepareStatement("SELECT V.Number_plate,M.Vehicle_name,M.Rental_charge FROM Vehicle V JOIN Vehicle_modal M USING(Vehicle_id) WHERE V.Number_plate IN (SELECT Number_plate FROM Cart WHERE User_id=?)");
        showPreviousRent=conn.prepareStatement("SELECT O.Order_id,R.Number_plate,R.Start_distance,R.End_distance,R.Damage,R.Rented_date,R.Rented_date,P.Amount FROM Renting R JOIN Orders O USING(Order_id) JOIN Payment P USING(Payment_id) WHERE O.User_id=?");
    }
    public void displayVehicle(String order)throws SQLException{
        StringBuilder quire=new StringBuilder("SELECT M.*,COUNT(*) FROM Vehicle_modal M JOIN Vehicle V USING(Vehicle_id) WHERE Availability=1 AND ((M.Vehicle_type='four wheeler' AND (V.Distance_covered-V.Last_service_at)<3000) OR (M.Vehicle_type='two wheeler' AND (V.Distance_covered-V.Last_service_at)<1500)) AND V.Number_plate NOT IN (SELECT Number_plate FROM Renting WHERE Return_date IS NULL) GROUP BY Vehicle_id ORDER BY ");
        quire.append(order);
        ResultSet res=statement.executeQuery(quire.toString());
        System.out.printf("%-20s%-40s%-20s%-20s%-20s%-20s%-20s\n","Vehicle_id","Vehicle_name","Vehicle_type","Vehicle_brand","No_of_passangers","Rental_charge","No_of_avaliable_vehicles");
        while(res.next()){
            System.out.printf("%-20d%-40s%-20s%-20s%-20d%-20d%-20d\n",res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getInt(5),res.getInt(6),res.getInt(7));
        }
        System.out.println();
    }
    public void displayVehicle(int id)throws SQLException{
        displayById.setInt(1, id);
        ResultSet res=displayById.executeQuery();
        System.out.printf("%-20s%-40s%-20s%-20s%-20s\n","Vehicle Number","Vehicle name","Rental charge","distance covered","last service at");
        while(res.next()){
            System.out.printf("%-20s%-40s%-20d%-20d%-20d\n",res.getString(1),res.getString(2),res.getInt(3),res.getInt(4),res.getInt(5));
        }
        System.out.println();
    }
    public void displayCart()throws SQLException{
        showCart.setInt(1, Cookie.userId);
        ResultSet res=showCart.executeQuery();
        System.out.printf("%-20s%-40s%-20s\n","Vehicle Number","Vehicle name","Rental charge");
        while(res.next()){
            System.out.printf("%-20s%-40s%-20d\n",res.getString(1),res.getString(2),res.getInt(3));
        }
        System.out.println();
    }
    public void previousRent() throws SQLException{
        showPreviousRent.setInt(1, Cookie.userId);
        ResultSet res=showPreviousRent.executeQuery();
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n","Number plate","start distance","end distance","Damage","Rented date","Returning date","Amount payed");
        while(res.next()){
            System.out.printf("%-20s%-20d%-20d%-20s%-20s%-20s%-20d\n",res.getString(1),res.getInt(2),res.getInt(3),res.getString(4),res.getString(5),res.getString(6),res.getInt(7));
        }
        System.out.println();
    }
    public ArrayList<Bill> genrateBill(int OrderID) throws SQLException{
        ArrayList<Bill> arr=new ArrayList<>();
        ResultSet res=statement.executeQuery("SELECT R.damage,DATEDIFF(R.Return_date,R.Rented_date),M.Rental_charge,(R.End_distance-R.Start_distance) FROM Renting R JOIN Vehicle V USING(Number_plate) JOIN Vehicle_modal M USING(Vehicle_id) WHERE R.Order_id="+OrderID);
        while(res.next()){
            arr.add(new Bill(res.getInt(2),res.getString(1),res.getInt(3),res.getInt(4)));
        }
        return arr;
    }
    public void displayService()throws SQLException{
        ResultSet res=statement.executeQuery("SELECT V.Number_plate,M.Vehicle_id,M.Vehicle_name,M.Rental_charge FROM Vehicle V JOIN Vehicle_modal M USING(Vehicle_id) WHERE ((M.Vehicle_type='two wheeler' AND (V.Distance_covered-V.Last_service_at)>=1500) OR (M.Vehicle_type='four wheeler' AND (V.Distance_covered-Last_service_at)>=3000))");
        System.out.printf("%-20s%-20s%-40s%-20s\n","Vehicle Number","Vehicle ID","Vehicle name","Rental Charge");
        while(res.next()){
            System.out.printf("%-20s%-20d%-40s%-20d\n",res.getString(1),res.getInt(2),res.getString(3),res.getInt(4));
        }
        System.out.println();
    }
    public void displayAllVehicle(String order)throws SQLException{
        ResultSet res=statement.executeQuery("SELECT V.Number_plate,M.Vehicle_id,M.Vehicle_name,M.Rental_charge FROM Vehicle V JOIN Vehicle_modal M USING(Vehicle_id) ORDER BY "+order);
        System.out.printf("%-20s%-20s%-40s%-20s\n","Vehicle Number","Vehicle ID","Vehicle name","Rental Charge");
        while(res.next()){
            System.out.printf("%-20s%-20d%-40s%-20d\n",res.getString(1),res.getInt(2),res.getString(3),res.getInt(4));
        }
        System.out.println();
    }
    public void displayRentedVehicle()throws SQLException{
        ResultSet res=statement.executeQuery("SELECT V.Number_plate,M.Vehicle_id,M.Vehicle_name,M.Rental_charge FROM Vehicle V JOIN Vehicle_modal M USING(Vehicle_id) WHERE V.Number_plate IN (SELECT Number_plate FROM Renting WHERE Return_date IS NULL)");
        System.out.printf("%-20s%-20s%-40s%-20s\n","Vehicle Number","Vehicle ID","Vehicle name","Rental Charge");
        while(res.next()){
            System.out.printf("%-20s%-20d%-40s%-20d\n",res.getString(1),res.getInt(2),res.getString(3),res.getInt(4));
        }
        System.out.println();
    }
}
