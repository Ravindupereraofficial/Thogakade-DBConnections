/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.thogakade;

import java.sql.*;

import com.sun.jdi.connect.spi.Connection;
import dbconnection.thogakade.DBConnection;
import java.util.ArrayList;
import models.thagakade.Item;




/**
 *
 * @author niroth
 */
public class ItemController {
    
public static ArrayList<String> loadAllItemCodes() throws ClassNotFoundException, SQLException{
        
        ResultSet rst=DBConnection.getInstance().getConnection().createStatement().executeQuery("select code from item");
        ArrayList<String> itemCodes=new ArrayList<>();
        while(rst.next()){
            itemCodes.add(rst.getString(1));
        }
        return itemCodes;
    
    }

public static Item searchItem(String itemCode) throws ClassNotFoundException, SQLException {

        java.sql.Connection connection = DBConnection.getInstance().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Item WHERE code='" + itemCode + "'");

        if (rst.next()) {
            Item item = new Item();
            item.setCode(rst.getString(1));
            item.setDescription(rst.getString(2));
            item.setUnitPrice(rst.getDouble(3));
            item.setQtyOnHand(rst.getInt(4));

            return item;
        } else {
            return null;
        }

}
}