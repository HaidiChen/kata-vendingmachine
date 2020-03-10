package vendingmachine;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BuyCokeTest {

  private VendingMachine machine;
  private Map<String, Item> items;
  private Coins coins;

  @BeforeEach
  void setUp() {
    items = new HashMap<String, Item>();
    items.put("Coke", new Item("Coke", 150, 10));

    coins = new PenceCoins();
    machine = new VendingMachine(coins, items);
  }

  @DisplayName("Not enough money to buy Coke")
  @Test
  void buyCokeWithoutEnoughMoneyWillThrowException() {
    machine.takeCoin(20);
    machine.takeCoin(100);
    assertThrows(
        VendingMachine.NotEnoughMoney.class, () -> machine.popItem("Coke"));
  }

  @DisplayName("Coke costs 150 pence")
  @Test
  void returnChangesIfAnyAfterGettingCoke() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(100);
    machine.takeCoin(100);
    assertEquals(100+100-150, machine.popItem("Coke"));
  }

}
