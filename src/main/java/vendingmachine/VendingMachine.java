package vendingmachine;

public class VendingMachine {

  private Coins coins;
  private int remainingChange = 0;
  private Item requestedItem;
  private Products products;

  public VendingMachine(Coins coins, Products products) {
    this.coins = coins;
    this.products = products;
  }

  public Products getProducts() {
    return products;
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

  public int refund() {
    return returnRemainingChangeToUserAndSetRemaingChangeToZero();
  }

  private int returnRemainingChangeToUserAndSetRemaingChangeToZero() {
    int refundAmount = remainingChange;
    remainingChange = 0;
    return refundAmount;
  }

  public int popItem(String itemName) throws NotEnoughMoney, StockEmpty {
    requestedItem = products.getItem(itemName);
    if (hasEnoughMoneyAndItemInStock()) {
      chargeAndDecreaseStock();
      return refund();
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

  public void resetItemStock(String itemName, int stockNumber) {
    Item item = products.getItem(itemName);
    item.setStock(stockNumber);
  }

  public int getItemStock(String itemName) {
    Item item = products.getItem(itemName);
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
