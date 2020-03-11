package vendingmachine;

import java.util.Map;
import java.util.HashMap;

public class Main {

  public static void main(String[] args) {
    Map<String, Item> items = prepareItemsForMachine();
    Coins penceCoins = PenceCoins.getInstance();
    VendingMachine machine = new VendingMachine(penceCoins, items);

    MachineInteractor interactor = new MachineInteractor(machine);
    interactor.start();
  }

  private static Map<String, Item> prepareItemsForMachine() {
    Map<String, Item> items = new HashMap<String, Item>();
    items.put("Candy", new Item("Candy", 10, 0));
    items.put("Snack", new Item("Snack", 50, 10));
    items.put("Nuts", new Item("Nuts", 75, 10));
    items.put("Coke", new Item("Coke", 150, 10));
//    items.put("BottleWater", new Item("BottleWater", 100, 10));
    return items;
  }

}
