package vendingmachine;

import java.util.Map;
import java.util.HashMap;

public class VendingMachine {

  private Coins coins;
  private Map<String, Item> itemList;
  private int remainingChange = 0;
  private Item requestedItem;

  public VendingMachine(Coins coins, Map<String, Item> itemList) {
    this.coins = coins;
    this.itemList = itemList;
  }

  public boolean takeCoin(int pence) {
    if (isValidCoin(pence)) {
      remainingChange += pence;
      return true;
    }
    return false;
  }

  private boolean isValidCoin(int coinValue) {
    return coins.hasValue(coinValue);
  }

  public int getRemainingChange() {
    return remainingChange;
  }

  public void refund() {
    remainingChange = 0;
  }

  public int popItem(String itemName) throws NotEnoughMoney, StockEmpty {
    requestedItem = itemList.get(itemName);
    if (hasEnoughMoneyAndItemInStock()) {
      chargeAndDecreaseStock();
      return remainingChange;
    }
    else if (itemStockIsEmpty()) {
      throw new StockEmpty("All " + itemName + " sold out");
    }
    else {
      throw new NotEnoughMoney("Not enough money, need " + 
          moneyToBuy() + " pence more");
    }
  }

  private int moneyToBuy() {
    return requestedItem.getPrice() - remainingChange;
  }

  private boolean itemStockIsEmpty() {
    return requestedItem.getStock() == 0;
  }

  private boolean hasEnoughMoneyAndItemInStock() {
    return remainingChange >= requestedItem.getPrice() 
      && requestedItem.getStock() > 0;
  }

  private void chargeAndDecreaseStock() {
    remainingChange -= requestedItem.getPrice();
    requestedItem.setStock(requestedItem.getStock() - 1);
  }

  public void resetStock(int stockNumber) {
    for (Item item: itemList.values()) {
      item.setStock(stockNumber);
    }
  }

  public int getItemStock(String itemName) {
    Item item = itemList.get(itemName);
    return item.getStock();
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
