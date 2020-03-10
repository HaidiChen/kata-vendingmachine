package vendingmachine;

import java.util.Map;
import java.util.HashMap;

public class App {

    public static void main(String[] args) {
      Map<String, Item> items = new HashMap<String, Item>();
      items.put("Candy", new Item("Candy", 10, 10));
      items.put("Snack", new Item("Snack", 50, 10));
      items.put("Nuts", new Item("Nuts", 75, 10));
      items.put("Coke", new Item("Coke", 150, 10));
      items.put("BottleWater", new Item("BottleWater", 100, 10));

      Coins penceCoins = PenceCoins.getInstance();
      VendingMachine machine = new VendingMachine(penceCoins, items);

      try {
        machine.popItem("Coke");
      }
      catch (VendingMachine.NotEnoughMoney e) {
        System.out.println(e.getMessage());
//        insertCoins();
      }
      catch (VendingMachine.StockEmpty ex) {
        System.out.println(ex.getMessage());
      }
    }

}
