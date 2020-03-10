package vendingmachine;

import java.util.Map;
import java.util.HashMap;

public class VendingMachine {

  private Coins coins;
  private Map<String, Item> itemList;
  private int remainingChange = 0;

  public VendingMachine(Coins coins, Map<String, Item> itemList) {
    this.coins = coins;
    this.itemList = itemList;
  }

  public boolean takeCoin(int pence) {
    if (acceptCoinValue(pence)) {
      remainingChange += pence;
      return true;
    }
    return false;
  }

  private boolean acceptCoinValue(int coinValue) {
    return coins.hasValue(coinValue);
  }

  public int getRemainingChange() {
    return remainingChange;
  }

  public void refund() {
    remainingChange = 0;
  }

  public int popItem(String itemName) throws NotEnoughMoney, StockEmpty {
    Item item = itemList.get(itemName);
    if (hasEnoughMoneyAndItemInStock(item)) {
      chargeAndDecreaseStock(item);
      return remainingChange;
    }
    else if (ItemStockIsEmpty(item)) {
      throw new StockEmpty("All " + itemName + " sold out");
    }
    else {
      throw new NotEnoughMoney("Not enough money, need " + 
          (item.getPrice() - remainingChange) + " pence more");
    }
  }

  private boolean ItemStockIsEmpty(Item item) {
    return item.getStock() == 0;
  }

  private boolean hasEnoughMoneyAndItemInStock(Item item) {
    return remainingChange >= item.getPrice() && item.getStock() > 0;
  }

  private void chargeAndDecreaseStock(Item item) {
    remainingChange -= item.getPrice();
    item.setStock(item.getStock() - 1);
  }

  public void resetStock(int stockNumber) {
    for (Item item: itemList.values()) {
      item.setStock(stockNumber);
    }
  }

  public boolean isItemEmpty(String itemName) {
    Item item = itemList.get(itemName);
    return item.getStock() == 0;
  }

  public class NotEnoughMoney extends Exception {
    
    public NotEnoughMoney(String s) {
      super(s);
    }

  }

  public class StockEmpty extends Exception {
    
    public StockEmpty(String s) {
      super(s);
    }

  }
}
