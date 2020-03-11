package vendingmachine;

import java.util.Iterator;

public interface Products {
  public void addItem(Item item);
  public Item getItem(String itemName);
  public Iterator getAllItemNames();
}
