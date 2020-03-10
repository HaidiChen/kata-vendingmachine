package vendingmachine;

import java.util.Set;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class App {
  
  private static boolean refundProcessed = false;
  private static VendingMachine machine;
  private static Scanner scanner = new Scanner(System.in);

  private static void putItemsInMachine(Map<String, Item> items) {
    items.put("Candy", new Item("Candy", 10, 0));
    items.put("Snack", new Item("Snack", 50, 10));
    items.put("Nuts", new Item("Nuts", 75, 10));
    items.put("Coke", new Item("Coke", 150, 10));
    items.put("BottleWater", new Item("BottleWater", 100, 10));
  }

  private static int insertCoins(int moneyInMachine) {
    System.out.println("Insert coin (1, 5, 20, 50, 100) " + 
        "Or press r for requesting a refund:");
    String coinInserted = scanner.nextLine();
    if (coinInserted.equals("r")) {
      System.out.println("Here is your refund: " + machine.refund() + " Pence");
      refundProcessed = true;
    }
    else {
      machine.takeCoin(Integer.parseInt(coinInserted));
    }
    return machine.getRemainingChange();
  }

  private static void buyItem(String itemName, int totalMoneyInMachine) {
    try {
      int changes = machine.popItem(itemName);
      System.out.println(
          "You got yourself a " + itemName + 
          ", and here is your change: " + changes);
    }
    catch (VendingMachine.NotEnoughMoney e) {
      System.out.println(e.getMessage());
      System.out.println("[Money Inserted]: " + machine.getRemainingChange());
      int currentMoneyInMachine = insertCoins(totalMoneyInMachine);
      if (!refundProcessed) {
        buyItem(itemName, currentMoneyInMachine);
      }
    }
    catch (VendingMachine.StockEmpty ex) {
      System.out.println(ex.getMessage());
    }
  }

  private static void displayItemsInMachine(Map<String, Item> items) {
    System.out.println("We have following items: ");
    for (String name: (Set<String>)items.keySet()) {
      Item item = items.get(name);
      System.out.println("--" + name + " (" + item.getPrice() + " Pence) [" + 
          machine.getItemStock(name) + " in stock]");
    }
  }

  private static void chooseOneItemToBuy() {
    System.out.println("which one do you want?");
    String itemName = scanner.nextLine();
    buyItem(itemName, 0);
  }

  public static void main(String[] args) {
    Map<String, Item> items = new HashMap<String, Item>();
    putItemsInMachine(items);
    Coins penceCoins = PenceCoins.getInstance();
    machine = new VendingMachine(penceCoins, items);

    displayItemsInMachine(items);
    chooseOneItemToBuy();

  }

}
