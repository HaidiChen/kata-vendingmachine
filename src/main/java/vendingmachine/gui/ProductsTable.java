package vendingmachine.gui;

import java.util.EventObject;
import vendingmachine.machine.Products;
import vendingmachine.machine.Item;
import java.util.Iterator;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProductsTable extends JTable {
 
  static Object[][] data;
  static String[] column = {"ID", "Name", "Stock", "Price(Pence)"}; 
  static DefaultTableModel model;
 
  public static void setData(Products products) {
    Iterator<String> names = products.getAllItemNames();
    data = new Object[count(products.getAllItemNames())][column.length];
    int id = 0;
    while (names.hasNext()) {
      String name = names.next();
      Item item = products.getItem(name);
      data[id][0] = new Integer(id);
      data[id][1] = new String(name);
      data[id][2] = new Integer(item.getStock());
      data[id][3] = new Integer(item.getPrice());
      id++;
    }
    model = new DefaultTableModel(data, column);
  }

  private static int count(Iterator<String> names) {
    int num = 0;
    while (names.hasNext()) {
      names.next();
      num++;
    }
    return num;
  }

  public ProductsTable() {
    super(model); ;
  }  

  public boolean editCellAt(int row, int column, EventObject e) {
    return false;
  }

  public void updateTable(int value, int row) {
    model.setValueAt(value, row, 2);
  }

}
