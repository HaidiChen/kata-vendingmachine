package vendingmachine;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

  private VendingMachine machine;
  private Map<String, Item> items;
  private Coins coins;

  @BeforeEach
  void setUp() {
    items = new HashMap<String, Item>();
    items.put("Candy", new Item("Candy", 10, 10));
    items.put("Snack", new Item("Snack", 50, 10));
    items.put("Nuts", new Item("Nuts", 75, 10));
    items.put("Coke", new Item("Coke", 150, 10));
    items.put("BottleWater", new Item("BottleWater", 100, 10));

    coins = new PenceCoins();
    machine = new VendingMachine(coins, items);
  }

  @Test
  void refundClearsRemainingChange() {
    machine.takeCoin(50);
    machine.refund();
    assertEquals(0, machine.getRemainingChange());
  }

  @Test
  void resetStockToFiveForEachItem() {
    machine.resetStock(5);
    for (Item item: items.values()) {
      assertEquals(5, machine.getItemStock(item.getName()));
    }
  }

}
