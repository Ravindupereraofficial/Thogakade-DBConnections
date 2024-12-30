/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.thogakade;

import dbconnection.thogakade.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
import models.thagakade.Order;

/**
 *
 * @author USER
 */
public  class OrderController {
    public static String getLastOrderId() throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT id FROM Orders ORDER BY id DESC LIMIT 1");
        return rst.next() ? rst.getString("id") : null;
    } 
    
      public static boolean placeOrder(Order order) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("Insert into Orders values(?,?,?)");
            stm.setObject(1, order.getId());
            stm.setObject(2, order.getDate());
            stm.setObject(3, order.getCustomerId());
            boolean isAddedOrder = stm.executeUpdate() > 0;
            if (isAddedOrder) {
                boolean addOrderDetails = OrderDatailController.addOrderDetail(order.getOrderDetailList());
                if (addOrderDetails) {
                    boolean updateStock = ItemController.updateStock(order.getOrderDetailList());
                    if (updateStock) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    
}
