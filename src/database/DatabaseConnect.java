package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import resource.DB_data;

class DatabaseConnect{
    Connection conn=null;
    Statement statement;
    DatabaseConnect() throws SQLException{
        conn=DriverManager.getConnection(DB_data.dbURL,DB_data.user, DB_data.passward);
        statement=conn.createStatement();
    }
}
