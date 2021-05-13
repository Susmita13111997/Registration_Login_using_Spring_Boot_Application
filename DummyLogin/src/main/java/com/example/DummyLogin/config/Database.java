package com.example.DummyLogin.config;
import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
public static Connection createConnection()
{
    Connection con = null;
        try
        {
           Class.forName("com.mysql.jdbc.Driver"); //loading MySQL drivers. This differs for database servers 
           con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testconnection" , "root" , "root"); //attempting to connect to MySQL database
           System.out.println("Printing connection object "+con);
        } 
        catch (Exception e)
        {
           e.printStackTrace();
        } 
        return con;
    }
}