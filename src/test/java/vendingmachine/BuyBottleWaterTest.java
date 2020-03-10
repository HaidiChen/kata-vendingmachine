package vendingmachine;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BuyBottleWaterTest {

  private VendingMachine machine;
  private Map<String, Item> items;
  private Coins coins;

  @BeforeEach
  void setUp() {
    items = new HashMap<String, Item>();
    items.put("BottleWater", new Item("BottleWater", 100, 10));

    coins = new PenceCoins();
    machine = new VendingMachine(coins, items);
  }

  @DisplayName("Not enough money to buy Water")
  @Test
  void buyBottleWaterWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    assertThrows(
        VendingMachine.NotEnoughMoney.class, () -> machine.popItem("BottleWater"));
  }

  @DisplayName("Bottle Water costs 100 pence")
  @Test
  void returnChangesIfAnyAfterGettingBottleWater() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(100);
    machine.takeCoin(100);
    assertEquals(100+100-100, machine.popItem("BottleWater"));
  }

  @DisplayName("Cannot buy when stock is empty")
  @Test
  void buyItemWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.resetStock(0);
    for (Item item: items.values()) {
      testStockEmptyException(item.getName());
    }
  }

  private void testStockEmptyException(String itemName) {
    assertThrows(
      VendingMachine.StockEmpty.class, () -> machine.popItem(itemName));
  }

}
