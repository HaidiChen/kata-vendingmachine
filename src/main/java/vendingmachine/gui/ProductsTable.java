package vendingmachine.gui;

import vendingmachine.machine.Products;
import vendingmachine.machine.Item;
import java.util.Iterator;
import javax.swing.*;

public class ProductsTable extends JTable {
 
  static String[][] data;
  static String[] column = {"ID", "Name", "Stock", "Price(Pence)"}; 
 
  public static void setData(Products products) {
    Iterator<String> names = products.getAllItemNames();
    data = new String[count(products.getAllItemNames())][column.length];
    int id = 0;
    while (names.hasNext()) {
      String name = names.next();
      Item item = products.getItem(name);
      data[id][0] = new String("" + id);
      data[id][1] = new String(name);
      data[id][2] = new String("" + item.getStock());
      data[id][3] = new String("" + item.getPrice());
      id++;
    }
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
    super(data, column);
  }
}
