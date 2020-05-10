package vendingmachine.products;

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import vendingmachine.machine.Products;
import vendingmachine.machine.Item;

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
