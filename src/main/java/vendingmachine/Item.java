package vendingmachine;

public class Item {
  
  private String name;
  private int price;
  private int stock;

  public Item(String name, int price, int stock) {
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public void setStock(int stock) {
    this.stock = stock;
  }

  public int getStock() {
    return stock;
  }

  public int getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }
}
