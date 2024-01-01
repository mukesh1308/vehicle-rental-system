package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import resource.DB_data;

public class DatabaseConnect{
    protected static Connection conn=null;
    protected static Statement statement;
    DatabaseConnect(){}
    public static void connect()throws SQLException{
        conn=DriverManager.getConnection(DB_data.dbURL,DB_data.user, DB_data.passward);
        statement=conn.createStatement();
    }
}
