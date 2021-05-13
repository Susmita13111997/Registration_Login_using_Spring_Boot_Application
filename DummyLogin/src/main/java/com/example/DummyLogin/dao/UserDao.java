package com.example.DummyLogin.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.DummyLogin.config.Database;
import com.example.DummyLogin.model.User;
public class UserDao {

	public String registerUser(User user)
    {
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String userName = user.getUserName();
        String password = user.getPassword();
        
        Connection con = null;
        PreparedStatement preparedStatement = null;         
        try
        {
            con = Database.createConnection();
 
            String query = "insert into user(firstname,lastname,username,password) values (?,?,?,?)"; //Insert user details into the table 'USERS'
            
            preparedStatement = con.prepareStatement(query); 
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, userName);
            preparedStatement.setString(4, password);
            
            
            int i= preparedStatement.executeUpdate();
            
            if (i!=0)  //Just to ensure data has been inserted into the database
            	return "SUCCESS"; 
        }
        catch(SQLException e)
        {
           e.printStackTrace();
        }       
        return "Oops.. Something went wrong there..!";  // On failure, send a message from here.
    }
}