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

    coins = PenceCoins.getInstance();
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
  void returnChangesIfAnyAfterGettingCandyAndSetRemainingChangeToZero() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.takeCoin(20);
    assertEquals(20-10, machine.popItem("Candy"));
    assertEquals(0, machine.getRemainingChange());
  }

  @DisplayName("Cannot buy when Candy sold out")
  @Test
  void buyCandyWhenStockIsEmptyWillThrowException() {
    machine.takeCoin(100);
    machine.resetItemStock("Candy", 0);
    assertThrows(
      VendingMachine.StockEmpty.class, () -> machine.popItem("Candy"));
  }

  @Test
  void resetCandyStockToFive() {
    machine.resetItemStock("Candy", 5);
    assertEquals(5, machine.getItemStock("Candy"));
  }

  @Test
  void stockIsEmptyWhenSoldOut() 
    throws VendingMachine.NotEnoughMoney, VendingMachine.StockEmpty {
    machine.resetItemStock("Candy", 2);
    for (int i = 0; i < 2; i++) {
      machine.takeCoin(20);
      machine.popItem("Candy");
    }
    assertEquals(0, machine.getItemStock("Candy"));
  }

}
