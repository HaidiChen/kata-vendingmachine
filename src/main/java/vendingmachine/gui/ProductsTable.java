package vendingmachine.gui;

import java.util.EventObject;
import vendingmachine.machine.Products;
import vendingmachine.machine.Item;
import java.util.Iterator;
import javax.swing.*;

public class ProductsTable extends JTable {

    static Object[][] data;
    static String[] column = {"ID", "Name", "Price(Pence)"}; 

    public static void setData(Products products) {
        Iterator<String> names = products.getAllItemNames();
        data = new Object[count(products.getAllItemNames())][column.length];
        int id = 0;
        while (names.hasNext()) {
            String name = names.next();
            Item item = products.getItem(name);
            data[id][0] = new Integer(id);
            data[id][1] = new String(name);
            data[id][2] = new Integer(item.getPrice());
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
        super(data, column); ;
    }  

    public boolean editCellAt(int row, int column, EventObject e) {
        return false;
    }
}
