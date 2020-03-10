package vendingmachine;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class VendingMachine {

  private final Set<Integer> validCoins;
  private HashMap<String, Integer> items;
  private HashMap<String, Integer> itemsStock;
  private int totalValue = 0;

  public VendingMachine() {
    validCoins = new HashSet<Integer>();
    validCoins.add(1);
    validCoins.add(5);
    validCoins.add(20);
    validCoins.add(50);
    validCoins.add(100);

    items = new HashMap<String, Integer>();
    items.put("Candy", 10);
    items.put("Snack", 50);
    items.put("Nuts", 75);
    items.put("Coke", 150);
    items.put("BottleWater", 100);

    itemsStock = new HashMap<String, Integer>();
    itemsStock.put("Candy", 10);
    itemsStock.put("Snack", 10);
    itemsStock.put("Nuts", 10);
    itemsStock.put("Coke", 10);
    itemsStock.put("BottleWater", 10);
  }

  public boolean takeCoin(int pence) {
    if (validCoins.contains(pence)) {
      totalValue += pence;
      return true;
    }
    return false;
  }

  public int getTotalValue() {
    return totalValue;
  }

  public void refund() {
    totalValue = 0;
  }

  public boolean popItem(String itemName) {
    int itemPrice = items.get(itemName);
    int stock = itemsStock.get(itemName);
    if (totalValue >= itemPrice && stock > 0) {
      totalValue -= itemPrice;
      stock -= 1;
      itemsStock.put(itemName, stock);
      return true;
    }
    return false;
  }

  public void reset(int stockNumber) {
    itemsStock.put("Candy", 0);
    itemsStock.put("Snack", 0);
    itemsStock.put("Nuts", 0);
    itemsStock.put("Coke", 0);
    itemsStock.put("BottleWater", 0);
  }

}
