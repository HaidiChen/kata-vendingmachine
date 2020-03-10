package vendingmachine;

import java.util.Map;
import java.util.HashMap;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class BuyCandyTest {

  private VendingMachine machine;
  private Map<String, Item> items;
  private Coins coins;

  @BeforeEach
  void setUp() {
    items = new HashMap<String, Item>();
    items.put("Candy", new Item("Candy", 10, 10));

    coins = new PenceCoins();
    machine = new VendingMachine(coins, items);
  }

  @DisplayName("Not Enough Money to buy Candy")
  @Test
  void buyCandyWithoutEnoughMoneyWillThrowAnException() {
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(1);
    machine.takeCoin(5);
    assertThrows(
        VendingMachine.NotEnoughMoney.class, () -> machine.popItem("Candy"));
  }

  @DisplayName("Candy costs 10 pence")
  @Test
  void returnChangesIfAnyAfterGettingCandy() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(20);
    assertEquals(20-10, machine.popItem("Candy"));
  }

}
