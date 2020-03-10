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

  @DisplayName("Cannot buy when water sold out")
  @Test
  void buyWaterWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.takeCoin(100);
    machine.resetItemStock("BottleWater", 0);
    assertThrows(
      VendingMachine.StockEmpty.class, () -> machine.popItem("BottleWater"));
  }

  @Test
  void resetBottleWaterStockToFive() {
    machine.resetItemStock("BottleWater", 5);
    assertEquals(5, machine.getItemStock("BottleWater"));
  }

  @Test
  void stockIsEmptyWhenSoldOut() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.resetItemStock("BottleWater", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(100);
      machine.popItem("BottleWater");
    }
    assertEquals(0, machine.getItemStock("BottleWater"));
  }

}
