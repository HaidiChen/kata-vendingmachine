package vendingmachine;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class VendingMachine {

  private Coins coins;
  private Map<String, Integer> items;
  private Map<String, Integer> itemsStock;
  private int totalValue = 0;

  public VendingMachine(Coins coins) {
    this.coins = coins;

    items = new HashMap<String, Integer>();
    setItems();

    itemsStock = new HashMap<String, Integer>();
    resetStock(10);
  }

  private void setItems() {
    items.put("Candy", 10);
    items.put("Snack", 50);
    items.put("Nuts", 75);
    items.put("Coke", 150);
    items.put("BottleWater", 100);
  }

  public boolean takeCoin(int pence) {
    if (coins.hasValue(pence)) {
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

  public void resetStock(int stockNumber) {
    itemsStock.put("Candy", stockNumber);
    itemsStock.put("Snack", stockNumber);
    itemsStock.put("Nuts", stockNumber);
    itemsStock.put("Coke", stockNumber);
    itemsStock.put("BottleWater", stockNumber);
  }

  public boolean isEmpty() {
    Set<String> itemNames = itemsStock.keySet();
    for (String name: itemNames) {
      if (itemsStock.get(name) == 0) {
        return true;
      }
    }
    return false;
  }

}
