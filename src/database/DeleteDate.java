package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import resource.Cookie;

public class DeleteDate extends DatabaseConnect{
    PreparedStatement deleteVehical;
    PreparedStatement deleteFromCart;
    public DeleteDate()throws SQLException{
        super();
        deleteVehical=conn.prepareStatement("DELETE FROM Vehicle WHERE Number_plate=?");
        deleteFromCart=conn.prepareStatement("DELETE FROM Cart WHERE User_id=? AND Number_plate=?");
    }
    public void removeVehical(String num)throws SQLException{
        deleteVehical.setString(1, num);
        deleteVehical.executeUpdate();
        System.out.println("Vehicle DELETED");
    }
    public void removeFromCart(String num) throws SQLException{
        deleteFromCart.setInt(1, Cookie.userId);
        deleteFromCart.setString(2, num);
        deleteFromCart.executeUpdate();
        System.out.println("Vehicle removed from cart");
    }
}
