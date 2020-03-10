package vendingmachine;

import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class VendingMachine {

  private Coins coins;
  private Map<String, Item> itemList;
  private Map<String, Integer> items;
  private Map<String, Integer> itemsStock;
  private int totalValue = 0;

  public VendingMachine(Coins coins, Map<String, Item> itemList) {
    this.coins = coins;
    this.itemList = itemList;
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
    Item item = itemList.get(itemName);
    int itemPrice = item.getPrice();
    int stock = item.getStock();
    if (totalValue >= itemPrice && stock > 0) {
      totalValue -= itemPrice;
      stock -= 1;
      item.setStock(stock);
      return true;
    }
    return false;
  }

  public void resetStock(int stockNumber) {
    for (Item item: itemList.values()) {
      item.setStock(stockNumber);
    }
  }

  public boolean isEmpty() {
    for (Item item: itemList.values()) {
      if (item.getStock() == 0) {
        return true;
      }
    }
    return false;
  }

}
