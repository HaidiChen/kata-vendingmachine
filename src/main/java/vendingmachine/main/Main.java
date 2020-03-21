package vendingmachine.main;

import vendingmachine.coins.*;
import vendingmachine.gui.*;
import vendingmachine.machine.*;
import vendingmachine.products.*;

public class Main {

  public static void main(String[] args) {
    Products products = prepareItemsForMachine();
    Coins penceCoins = PenceCoins.getInstance();
    VendingMachine machine = new MyVendingMachine(penceCoins, products);
    GUIInteractor guiInteractor = new GUIInteractor(machine);
  }

  private static Products prepareItemsForMachine() {
    Products products = new OnSaleProducts();
    products.addItem(new ItemInfo("Candy", 10, 1));
    products.addItem(new ItemInfo("Snack", 50, 10));
    products.addItem(new ItemInfo("Nuts", 75, 10));
    products.addItem(new ItemInfo("Coke", 150, 10));
    products.addItem(new ItemInfo("BottleWater", 100, 10));
    return products;
  }

}
