package vendingmachine.machine;

public interface Item {
    public void setStock(int stock);
    public int getStock();
    public int getPrice();
    public String getName();
}
