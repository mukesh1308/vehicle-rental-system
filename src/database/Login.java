package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import resource.Cookie;

public class Login extends DatabaseConnect {
    public int login(String email,String passward) throws SQLException{
        ResultSet res=statement.executeQuery("SELECT L.Passward,U.ROLE,L.User_id FROM Login L JOIN User U USING(User_id) WHERE L.Email='"+email+"'");
        if(res.next()){
            BCrypt.Result veri=BCrypt.verifyer().verify(passward.toCharArray(), res.getString(1));
            // // System.out.println(res.getString(1)); 
            Cookie.userId=res.getInt(3);
            if(veri.verified){
                if(res.getString(2).equals("Admin")){
                    return 2;
                }
                else{
                    return 1;
                }
            }
            return -2;
        }
        return -1;
    }
}
