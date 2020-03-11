package vendingmachine;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

public class OnSaleProducts implements Products {

  private Map<String, Item> itemsMap;

  public OnSaleProducts() {
    itemsMap = new HashMap<String, Item>();
  }

  public void addItem(Item item) {
    itemsMap.put(item.getName(), item);
  }

  public Item getItem(String itemName) {
    return itemsMap.get(itemName);
  }

  public Iterator getAllItemNames() {
    return itemsMap.keySet().iterator();
  }

}
