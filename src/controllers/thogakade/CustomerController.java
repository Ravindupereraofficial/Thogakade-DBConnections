/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.thogakade;

import dbconnection.thogakade.DBConnection;

import java.sql.*;
import models.thagakade.Customer;

/**
 *
 * @author USER
 */
public class CustomerController {
    public static boolean addCustomer(Customer customer) throws ClassNotFoundException, SQLException{
    Connection connection=DBConnection.getInstance().getConnection();
        System.out.println(connection);
      PreparedStatement stm = connection.prepareStatement("Insert into Customer Values(?,?,?,?) ");
      stm.setObject(1, customer.getId());
      stm.setObject(2, customer.getName());
      stm.setObject(3, customer.getAddress());
      stm.setObject(4, customer.getSalary());
      int res = stm.executeUpdate();
      return res>0;
    }
    
  public static Customer searchCustomer (String id) throws ClassNotFoundException, SQLException{
   Connection connection=DBConnection.getInstance().getConnection();  
   PreparedStatement stm = connection.prepareStatement("SELECT * FROM Customer WHERE id = ?");
   stm.setObject(1, id);
   ResultSet rst=stm.executeQuery();
   if(rst.next()){
   Customer customer = new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getDouble("salary"));
            return customer;
   }
   return null;
  }  
}


