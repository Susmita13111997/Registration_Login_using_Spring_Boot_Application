package com.example.DummyLogin.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.example.DummyLogin.config.Database;

public class UploadDao {

    Connection con = null;
    PreparedStatement pr =null;

    public int uploadFile(String userName,String password,String filepath) {
        int row = 0;
        try {
       
        	
        	String sql = "update user set filepath=? where username=? and password=?";
        	con=Database.createConnection();		
            // Step 2:Create a statement using connection object
        	
        	
        	System.out.println(userName+'\n'+password);
        	pr = con.prepareStatement(sql);
       
        	
        	
        	
              if (filepath != null) 
              {
                  // fetches input stream of the upload file for the blob column
                  
            	  pr.setString(1,filepath);
            	  
            	  
              }
              pr.setString(2,userName);
              pr.setString(3,password);
              
            // sends the statement to the database server
            row = pr.executeUpdate();

            System.out.println("6");
         } 
        catch (Exception e) {
            // process sql exception
        	System.out.println(e);
        }
        System.out.print("row affected = "+row);
        return row;
    }
}