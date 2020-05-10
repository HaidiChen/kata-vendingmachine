package vendingmachine.main;

import vendingmachine.machine.*;

public class MyVendingMachine implements VendingMachine {

    private Coins coins;
    private int remainingChange = 0;
    private Item requestedItem;
    private Products products;

    public MyVendingMachine(Coins coins, Products products) {
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

    public int popItem(String itemName) 
            throws NotEnoughMoney, StockEmpty, NoItemException {
            requestedItem = products.getItem(itemName);
            if (requestedItem == null) {
                throw new NoItemException("No such item:" + itemName +  " in stock");
            }
            else if (notEnoughMoneyPaid()) {
                throw new NotEnoughMoney("Not enough money, need " + 
                        moneyToBuy() + " pence more");
            }
            else if (itemStockIsEmpty()) {
                throw new StockEmpty("All " + itemName + " sold out");
            }
            else { // User paid enough money and item in stock
                chargeAndDecreaseStock();
                return refund();
            }
    }

    private boolean notEnoughMoneyPaid() {
        return remainingChange < requestedItem.getPrice();
    }

    private int moneyToBuy() {
        return requestedItem.getPrice() - remainingChange;
    }

    private boolean itemStockIsEmpty() {
        return requestedItem.getStock() == 0;
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

}
