package vendingmachine;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BuyNutsTest {

  private VendingMachine machine;
  private Map<String, Item> items;
  private Coins coins;

  @BeforeEach
  void setUp() {
    items = new HashMap<String, Item>();
    items.put("Nuts", new Item("Nuts", 75, 10));

    coins = new PenceCoins();
    machine = new VendingMachine(coins, items);
  }

  private void testNotEnoughMoneyException(String itemName) {
  }

  @DisplayName("Not enough money to buy Nuts")
  @Test
  void buyNutsWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(50);
    assertThrows(
        VendingMachine.NotEnoughMoney.class, () -> machine.popItem("Nuts"));
  }

  @DisplayName("Nuts costs 75 pence")
  @Test
  void returnChangesIfAnyAfterGettingNuts() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(50);
    machine.takeCoin(50);
    assertEquals(50+50-75, machine.popItem("Nuts"));
  }

}
