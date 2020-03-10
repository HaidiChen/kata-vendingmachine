package vendingmachine;

import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

public class VendingMachine {

  private final Set<Integer> validCoins;
  private HashMap<String, Integer> items;
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
    if (totalValue >= itemPrice) {
      totalValue -= itemPrice;
      return true;
    }
    return false;
  }

  public boolean popCandy() {
    return popItem("Candy");
  }

  public boolean popSnack() {
    if (totalValue >= 50) {
      totalValue -= 50;
      return true;
    }
    return false;
  }

  public boolean popNuts() {
    if (totalValue >= 75) {
      totalValue -= 75;
      return true;
    }
    return false;
  }

}
