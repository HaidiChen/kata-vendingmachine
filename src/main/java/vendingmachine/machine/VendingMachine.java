package vendingmachine.machine;

public interface VendingMachine {

    public Products getProducts();
    public boolean takeCoin(int pence);
    public int getRemainingChange();
    public int refund();
    public int popItem(String itemName) 
            throws NotEnoughMoney, StockEmpty, NoItemException;
    public void resetItemStock(String itemName, int stockNumber);
    public int getItemStock(String itemName);

}
